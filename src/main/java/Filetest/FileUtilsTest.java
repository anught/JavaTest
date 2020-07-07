package Filetest;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.EmptyFileFilter;

public class FileUtilsTest {
	public static void main(String[] args) throws IOException {
		testCommonsIo();
	}

	public static void testCommonsIo() throws IOException {
		Collection<File> a = FileUtils.listFiles(new File("./"), EmptyFileFilter.NOT_EMPTY, null);
		for (File f : a) {
			System.out.println(f.getName());
			FileUtils.write(new File("fut"), f.getAbsolutePath() + "\n", "UTF-8", true);
		}

	}
}
