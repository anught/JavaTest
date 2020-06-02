package Filetest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;

public class DealTarGz {
	public void dealTarGz() throws FileNotFoundException, IOException {
		// tar.gz 文件路径
		String sourcePath = "D:\\daleyzou.tar.gz";
		// 要解压到的目录
		String extractPath = "D:\\test\\daleyzou";
		File sourceFile = new File(sourcePath);
		// decompressing *.tar.gz files to tar
		TarArchiveInputStream fin = new TarArchiveInputStream(
				new GzipCompressorInputStream(new FileInputStream(sourceFile)));
		File extraceFolder = new File(extractPath);
		TarArchiveEntry entry;
		// 将 tar 文件解压到 extractPath 目录下
		while ((entry = fin.getNextTarEntry()) != null) {
			if (entry.isDirectory()) {
				continue;
			}
			File curfile = new File(extraceFolder, entry.getName());
			File parent = curfile.getParentFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			// 将文件写出到解压的目录
			IOUtils.copy(fin, new FileOutputStream(curfile));
		}
	}

	/********************************************************************************************************/
	// 解压的思路是先解压tar.gz 得到 tar 文件 unCompressArchiveGz 再解压tar文件 unCompressTar
	/**
	 * 解压
	 * 
	 * @param archive
	 * @author yutao
	 * @throws IOException
	 * @date 2017年5月27日下午4:03:29
	 */
	private static void unCompressArchiveGz(String archive) throws IOException {

		File file = new File(archive);

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

		String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));

		String finalName = file.getParent() + File.separator + fileName;

		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(finalName));

		GzipCompressorInputStream gcis = new GzipCompressorInputStream(bis);

		byte[] buffer = new byte[1024];
		int read = -1;
		while ((read = gcis.read(buffer)) != -1) {
			bos.write(buffer, 0, read);
		}
		gcis.close();
		bos.close();

		unCompressTar(finalName);
	}

	/**
	 * 解压tar
	 * 
	 * @param finalName
	 * @author yutao
	 * @throws IOException
	 * @date 2017年5月27日下午4:34:41
	 */
	private static void unCompressTar(String finalName) throws IOException {

		File file = new File(finalName);
		String parentPath = file.getParent();
		TarArchiveInputStream tais = new TarArchiveInputStream(new FileInputStream(file));

		TarArchiveEntry tarArchiveEntry = null;

		while ((tarArchiveEntry = tais.getNextTarEntry()) != null) {
			String name = tarArchiveEntry.getName();
			File tarFile = new File(parentPath, name);
			if (tarArchiveEntry.isDirectory()) {
				tarFile.mkdir();
				continue;
			}
			if (!tarFile.getParentFile().exists()) {
				tarFile.getParentFile().mkdirs();
			}
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tarFile));

			int read = -1;
			byte[] buffer = new byte[1024];
			while ((read = tais.read(buffer)) != -1) {
				bos.write(buffer, 0, read);
			}
			bos.close();
		}
		tais.close();
		file.delete();// 删除tar文件
	}

	/****************************************************************************************************************/

	/**
	 * 归档 上面代码使用方法：只需要调archive(文件或目录路径)。
	 * 
	 * ①archive方法里面主要调了两个方法archiveDir和archiveHandle，返回的是最后打包文件名的全路径。
	 * ②archiveDir方法是个递归的方法。目的就是为了把文件夹中里的所有文件都遍历出来。其实更确切的说是把文件相对路径（相对于打包目录，我这里就是test目录）通过递归拼接好，为真正的打包写入操作做准备。
	 * ③archiveHandle方法，是真正的打包方法。参数为：1、打包输出流2、需要打包的文件3、打包文件里的路径（之前拼接好的路径）。
	 * ④tos.closeArchiveEntry()这里一定要注意；官网api解释为：
	 * http://commons.apache.org/proper/commons-compress/javadocs/api-release/index.html
	 * 
	 * 所有包含数据的entry都必须调用此方法。 原因是为了满足缓存区基于记录的写入，我们必须缓冲写入流里的数据。因此可能有数据片段仍然被组装
	 * （我认为应该是肯能还有数据在缓存区里），所以必须在该条目（entry）关闭之前写入输出流，并写入下个条目。
	 * 
	 * 
	 * 
	 * @param entry
	 * @throws IOException
	 * @author yutao
	 * @return
	 * @date 2017年5月27日下午1:48:23
	 */
	private static String archive(String entry) throws IOException {
		File file = new File(entry);

		TarArchiveOutputStream tos = new TarArchiveOutputStream(new FileOutputStream(file.getAbsolutePath() + ".tar"));
		String base = file.getName();
		if (file.isDirectory()) {
			archiveDir(file, tos, base);
		} else {
			archiveHandle(tos, file, base);
		}

		tos.close();
		return file.getAbsolutePath() + ".tar";
	}

	/**
	 * 递归处理，准备好路径
	 * 
	 * @param file
	 * @param tos
	 * @param base
	 * @throws IOException
	 * @author yutao
	 * @date 2017年5月27日下午1:48:40
	 */
	private static void archiveDir(File file, TarArchiveOutputStream tos, String basePath) throws IOException {
		File[] listFiles = file.listFiles();
		for (File fi : listFiles) {
			if (fi.isDirectory()) {
				archiveDir(fi, tos, basePath + File.separator + fi.getName());
			} else {
				archiveHandle(tos, fi, basePath);
			}
		}
	}

	/**
	 * 具体归档处理（文件）
	 * 
	 * @param tos
	 * @param fi
	 * @param base
	 * @throws IOException
	 * @author yutao
	 * @date 2017年5月27日下午1:48:56
	 */
	private static void archiveHandle(TarArchiveOutputStream tos, File fi, String basePath) throws IOException {
		TarArchiveEntry tEntry = new TarArchiveEntry(basePath + File.separator + fi.getName());
		tEntry.setSize(fi.length());

		tos.putArchiveEntry(tEntry);

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fi));

		byte[] buffer = new byte[1024];
		int read = -1;
		while ((read = bis.read(buffer)) != -1) {
			tos.write(buffer, 0, read);
		}
		bis.close();
		tos.closeArchiveEntry();// 这里必须写，否则会失败
	}

	/***********************************************************************************************************/
	/**
	 * 把tar包压缩成gz
	 * 
	 * @param path
	 * @throws IOException
	 * @author yutao
	 * @return
	 * @date 2017年5月27日下午2:08:37
	 */
	public static String compressArchive(String path) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));

		GzipCompressorOutputStream gcos = new GzipCompressorOutputStream(
				new BufferedOutputStream(new FileOutputStream(path + ".gz")));

		byte[] buffer = new byte[1024];
		int read = -1;
		while ((read = bis.read(buffer)) != -1) {
			gcos.write(buffer, 0, read);
		}
		gcos.close();
		bis.close();
		return path + ".gz";
	}

}
