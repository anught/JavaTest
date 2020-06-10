package UTIL;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Util {
	/**
	 *
	 * @param key         加密的密码
	 * @param zipOutPath  压缩文件输出的路径
	 * @param zipFilePath 要压缩的文件
	 * @throws Exception
	 */

	public static void encrypt(String key, String zipFilePath, String zipOutPath) throws Exception {
		OutputStream zipOutputPathStream = new FileOutputStream(new File(zipOutPath));
		SecretKey secretKeySpec = new SecretKeySpec(key.getBytes(), "DESede");
		Cipher instance = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		instance.init(Cipher.ENCRYPT_MODE, secretKeySpec);

		OutputStream cipherOutputStream = new CipherOutputStream(zipOutputPathStream, instance);
		ZipOutputStream zipOutputStream = new ZipOutputStream(cipherOutputStream);
		zipOutputStream.putNextEntry(new ZipEntry(zipFilePath));

		InputStream fileInputStream = new FileInputStream(new File(zipFilePath));

		BufferedInputStream bis = new BufferedInputStream(fileInputStream);
		BufferedOutputStream bos = new BufferedOutputStream(zipOutputStream);

		byte[] bytes = new byte[1024];
		int len = 0;
		while ((len = bis.read(bytes)) != -1) {
			bos.write(bytes, 0, len);
		}
		bos.flush();

		bos.close();
		bis.close();
		fileInputStream.close();
		zipOutputStream.close();
		cipherOutputStream.close();
		zipOutputPathStream.close();
	}

	/**
	 *
	 * @param key         解密的密码
	 * @param unZipPath   要解密的压缩文件路径
	 * @param outFilePath 解密解压缩之后的文件路径
	 * @throws Exception
	 */
	public static void decrypt(String key, String decFilePath, String outFilePath) throws Exception {
		InputStream zipInputStream = new FileInputStream(new File(decFilePath));
		SecretKey secretKeySpec = new SecretKeySpec(key.getBytes(), "DESede");
		Cipher cipher = Cipher.getInstance("DESede");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

		CipherInputStream cipherInputStream = new CipherInputStream(zipInputStream, cipher);
		ZipInputStream decryptZipInputStream = new ZipInputStream(cipherInputStream);
		if (decryptZipInputStream.getNextEntry() == null) {
			return;
		}
		FileOutputStream fileOutputStream = new FileOutputStream(new File(outFilePath));

		BufferedInputStream bis = new BufferedInputStream(decryptZipInputStream);
		BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

		byte[] bytes = new byte[1024];
		int len = 0;
		while ((len = bis.read(bytes)) != -1) {
			bos.write(bytes, 0, len);
		}
		bos.flush();

		bos.close();
		bis.close();
		fileOutputStream.close();
		decryptZipInputStream.close();
		cipherInputStream.close();
		zipInputStream.close();
	}

	public static void unZip(String srcFilePath, String dstPath, ArrayList<String> pathList) throws Exception {
		File srcFile = new File(srcFilePath);
		if (!srcFile.isFile()) {
			throw new Exception(srcFilePath + " is not exists");
		}

		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(srcFilePath);
			Enumeration<?> entries = zipFile.entries();
			// System.out.println(entries.hasMoreElements());
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				if (entry.isDirectory()) {
					String dirPath = dstPath + entry.getName();
					// System.out.println(dirPath);
					File dir = new File(dirPath);
					dir.mkdirs();
				} else {
					File targetFile = new File(dstPath + "/" + entry.getName());

					if (!targetFile.getParentFile().exists()) {
						targetFile.getParentFile().mkdirs();//
					}
					targetFile.createNewFile();

					InputStream fin = zipFile.getInputStream(entry);
					FileOutputStream fos = new FileOutputStream(targetFile);

					int len = 0;
					byte[] buf = new byte[1024];
					while ((len = fin.read(buf)) != -1) {
						fos.write(buf, 0, len);
					}

					fos.close();
					fin.close();
					pathList.add(targetFile.getPath());
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				zipFile.close();
			} catch (Exception e) {

			}
		}

	}

	public static String MD5(String srcStr) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			return (new BigInteger(1, md5.digest(srcStr.getBytes("UTF-8")))).toString(16);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void decryptAES128(String fCryptoKey, String decFilePath, String outFilePath)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException {
		// MD5加密密钥
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(fCryptoKey.getBytes(StandardCharsets.UTF_8));
		byte[] bKey = m.digest();
		// AES
		byte[] bDes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00 };
		System.arraycopy(bKey, 0, bDes, 0, 16);

		// 解密文件
		// DESede
		// AES
		InputStream inputStream = new FileInputStream(new File(decFilePath));
		SecretKey secretKeySpec = new SecretKeySpec(bDes, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");/// ECB/PKCS5Padding
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

		CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
		// File OutputFile = new File(fWaitDir + fileName.substring(0,
		// fileName.lastIndexOf(".")) + ".zip");
		FileOutputStream fileOutputStream = new FileOutputStream(new File(outFilePath));

		BufferedInputStream bis = new BufferedInputStream(cipherInputStream);
		BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);

		byte[] bytes = new byte[1024 * 1024];
		int len;
		while ((len = bis.read(bytes)) != -1) {
			bos.write(bytes, 0, len);
		}
		bos.flush();

		bos.close();
		bis.close();
		fileOutputStream.close();
		// decryptZipInputStream.close();
		cipherInputStream.close();
		inputStream.close();

	}

	public static void encryptAES128(String fCryptoKey, String zipFilePath, String zipOutPath) throws Exception {
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(fCryptoKey.getBytes(StandardCharsets.UTF_8));
		byte[] bKey = m.digest();
		// AES
		byte[] bDes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00 };
		System.arraycopy(bKey, 0, bDes, 0, 16);
		OutputStream zipOutputPathStream = new FileOutputStream(new File(zipOutPath));
		SecretKey secretKeySpec = new SecretKeySpec(bDes, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

		OutputStream cipherOutputStream = new CipherOutputStream(zipOutputPathStream, cipher);

		InputStream fileInputStream = new FileInputStream(new File(zipFilePath));

		BufferedInputStream bis = new BufferedInputStream(fileInputStream);
		BufferedOutputStream bos = new BufferedOutputStream(cipherOutputStream);

		byte[] bytes = new byte[1024];
		int len = 0;
		while ((len = bis.read(bytes)) != -1) {
			bos.write(bytes, 0, len);
		}
		bos.flush();
		bos.close();
		bis.close();
		fileInputStream.close();
		cipherOutputStream.close();
		zipOutputPathStream.close();
	}

	public static boolean isFormatTime(String time, String formatString) {

		try {
			SimpleDateFormat sFormate = new SimpleDateFormat(formatString);// "yyyyMMddHH"
			if (time == null || time.length() != formatString.length()) {
				return false;
			}
			sFormate.setLenient(false);// 设置严格判断时间格式
			sFormate.parse(time);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void main(String args[]) throws Exception {
		String inputFilePath = "E:\\tmp\\test\\test.txt";
		String outputFilePath = "E:\\tmp\\test\\test.txt.bin";
		String key = "123456";
		encryptAES128(key, inputFilePath, outputFilePath);

		decryptAES128(key, outputFilePath, "E:\\tmp\\test\\test-end.txt");

	}

}
