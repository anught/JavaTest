package SFTPTest;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

public class SftpTrans {

	private static String confFilePath = "batchreq.properties";
	public static final Properties batchRequestConf;
	private static String requestUsername;
	private static String requestPassword;
	private static String requestFilePath;
	private static String requestHost;
	private static String requestPort;

	private static int scanRate;

	private static String forwardTmpPath;

	private static Map<String, String> providerInfos = new HashMap<>(); // <"1","root,123456aB,192.192.191.100,8080">
	private static String providerFilePath;

	private static Map<String, Long> timeStartLogMap = new HashMap<>();
	private static Map<String, Long> timeEndLogMap = new HashMap<>();

	public static final String handlerId = "inner";

	static {
		batchRequestConf = new Properties();
		InputStream fin = null;
		try {
			fin = Thread.currentThread().getContextClassLoader().getResourceAsStream(confFilePath);
			batchRequestConf.load(fin);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	// 服务启动方法
	public static void launchService() {
		init();
		new Thread(requestFileScan()).start();
		for (String key : providerInfos.keySet()) {
			new Thread(responseFileScan(key + ":" + providerInfos.get(key))).start();
		}
	}

	// 读取配置
	private static void init() {

		requestUsername = batchRequestConf.getProperty("requestUsername");
		requestPassword = batchRequestConf.getProperty("requestPass");
		requestFilePath = batchRequestConf.getProperty("requestFilePath");
		requestHost = batchRequestConf.getProperty("requestHost");
		requestPort = batchRequestConf.getProperty("requestPort");

		String providerInfoStr = batchRequestConf.getProperty("providerInfos");// providerInfos=hc:root,123456aB,192.192.191.15,8080;rz:hezx,123,192.192.109.100,8088
		providerFilePath = batchRequestConf.getProperty("providerFilePath");
		scanRate = Integer.parseInt(batchRequestConf.getProperty("scanRate", "5"));
		String[] infos = providerInfoStr.split(";");
		for (int i = 0; i < infos.length; i++) {
			if (infos[i] != null && !infos[i].trim().equals("")) {
				String[] providerInfo = infos[i].split(":");
				providerInfos.put(providerInfo[0], providerInfo[1]);
			}
		}
	}

	// 扫描请求方文件并上传
	private static Runnable requestFileScan() {
		return new Runnable() {
			@Override
			public void run() {
//                logger.info("扫描请求方文件");
				SFTPUtil sftp = new SFTPUtil(requestUsername, requestPassword, requestHost,
						Integer.parseInt(requestPort));

				sftp.login();

				while (true) {
					if (sftp.getSftp() == null || !sftp.getSftp().isConnected()) {
						System.out.println("尝试重新登录请求文件暂存sftp服务器");
						sftp.login();
						try {
							Thread.sleep(1000 * scanRate);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						continue;
					}
					sftp.mkdir(requestFilePath);
					System.out.println("req sftp alive,扫描请求方文件路径" + requestFilePath);
					Vector fileList = null;
					try {
						fileList = sftp.listFiles(requestFilePath);
					} catch (SftpException e) {
						e.printStackTrace();
					} finally {
						try {
							sftp.cd2root();
						} catch (SftpException e) {
							e.printStackTrace();
						}
					}
					if (fileList != null && fileList.size() > 0) {
						for (Object file : fileList) {
							String fileName = ((ChannelSftp.LsEntry) file).getFilename();
							if (fileName.split("_").length == 3) {
								try {
									String[] reqInfos = fileName.replaceAll(".txt", "").split("_"); // enterId_hc(3)|rz(5)_reqId
									byte[] fileData = sftp.download(requestFilePath, fileName);
									if (providerInfos.get(reqInfos[1]) == null) {
										System.out.println("未知提供方：" + reqInfos[1]);
										continue;
									}
									timeStartLogMap.put(reqInfos[2], System.currentTimeMillis());
									String[] providerInfo = providerInfos.get(reqInfos[1]).split(",");
									SFTPUtil uploadSftp = new SFTPUtil(providerInfo[0], providerInfo[1],
											providerInfo[2], Integer.parseInt(providerInfo[3]));
									uploadSftp.login();
									if (uploadSftp.getSftp() == null || !uploadSftp.getSftp().isConnected()) {
										System.out.println("登录sftp服务器失败，向" + reqInfos[1] + "方提交文件" + fileName + "失败");
										continue;
									}
									InputStream in = new ByteArrayInputStream(fileData);
									uploadSftp.upload(".", providerFilePath, fileName, in);
									uploadSftp.logout();
									sftp.delete(requestFilePath, fileName);
									System.out.println("上传文件" + fileName);
								} catch (SftpException e) {
									e.printStackTrace();
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
					try {
						Thread.sleep(1000 * scanRate);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

	// 扫描提供方文件并下载
	private static Runnable responseFileScan(String provider) {
		return new Runnable() {
			@Override
			public void run() {
				String providerCode = provider.split(":")[0];
				String providerInfo = provider.split(":")[1];

				String[] infos = providerInfo.split(",");
				SFTPUtil sftp = new SFTPUtil(infos[0], infos[1], infos[2], Integer.parseInt(infos[3]));

				sftp.login();

				while (true) {
					if (sftp.getSftp() == null || !sftp.getSftp().isConnected()) {
						System.out.println("尝试重新登录提供方" + providerCode + "sftp服务器");
						sftp.login();
						try {
							Thread.sleep(1000 * scanRate);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						continue;
					}
					sftp.mkdir(providerFilePath);
					System.out.println(providerCode + "prov sftp alive,扫描提供方" + providerCode + "路径" + providerFilePath);
					Vector fileList = null;
					try {
						fileList = sftp.listFiles(providerFilePath);
					} catch (SftpException e) {
						e.printStackTrace();
					} finally {
						try {
							sftp.cd2root();
						} catch (SftpException e) {
							e.printStackTrace();
						}
					}
					if (fileList != null && fileList.size() > 0) {
						for (Object file : fileList) {
							String fileName = ((ChannelSftp.LsEntry) file).getFilename();
							if (fileName.split("_").length == 4) {
								try {
									String[] resInfos = fileName.replaceAll(".txt", "").split("_");
									timeEndLogMap.put(resInfos[2], System.currentTimeMillis());
									byte[] fileData = sftp.download(providerFilePath, (fileName));
									SFTPUtil reqSftp = new SFTPUtil(requestUsername, requestPassword, requestHost,
											Integer.parseInt(requestPort));
									reqSftp.login();
									if (reqSftp.getSftp() == null || !reqSftp.getSftp().isConnected()) {
										System.out.println("登录sftp服务器失败，回发结果文件" + fileName + "失败");
										continue;
									}
									InputStream in = new ByteArrayInputStream(fileData);
									reqSftp.upload(".", requestFilePath, fileName, in);
									reqSftp.logout();
									String suffix = "";
									if (resInfos[1].equals("hc")) {
										suffix = "3";
									} else if (resInfos[1].equals("rz")) {
										suffix = "5";
									}
//									Log2DB.record(resInfos[0], resInfos[1], "200",
//											timeStartLogMap.get(resInfos[2]) == null ? timeEndLogMap.get(resInfos[2])
//													: timeStartLogMap.get(resInfos[2]),
//											timeEndLogMap.get(resInfos[2]), resInfos[2], "", suffix, 0);
									sftp.cd2root();
									sftp.delete(providerFilePath, fileName);
									timeStartLogMap.remove(resInfos[2]);
									timeEndLogMap.remove(resInfos[2]);
									System.out.println("获取" + providerCode + "提供方结果文件" + fileName);
								} catch (IOException | SftpException e) {
									e.printStackTrace();
								}

							}
						}
					}
					try {
						Thread.sleep(1000 * scanRate);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
	}

}
