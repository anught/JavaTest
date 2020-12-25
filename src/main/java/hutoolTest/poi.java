package hutoolTest;

import java.util.List;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;

public class poi {
	public static void main(String[] args) {
		BitMapBloomFilter filter = new BitMapBloomFilter(10);
		filter.add("a");
		filter.add("b");
		filter.add("c");
		System.out.println(filter.contains("e"));
		System.out.println(filter.contains("a"));
		System.out.println(filter.contains("b"));
		write("5");

	}

	public static void read() {
		ExcelReader reader = ExcelUtil.getReader("d:/aaa.xlsx");

	}

	public static void write(String sheet) {

		List<String> row1 = CollUtil.newArrayList("aa666", "bb", "cc", "dd");
		List<String> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1");
		List<String> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2");
		List<String> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3");
		List<String> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4");

		List<List<String>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);

		// 通过工具类创建writer
		ExcelWriter writer = ExcelUtil.getWriter("d:/writeTest.xlsx", sheet);
		// 通过构造方法创建writer
		// ExcelWriter writer = new ExcelWriter("d:/writeTest.xls");

		// 跳过当前行，既第一行，非必须，在此演示用
		writer.passCurrentRow();

		// 合并单元格后的标题行，使用默认标题样式
		// writer.merge(row1.size() - 1, "测试标题");

		writer.merge(0, 0, 0, 3, "阿达", false);

		writer.merge(0, 0, 4, 8, "阿达dd", false);

		// 一次性写出内容，强制输出标题
		writer.write(rows, true);
		// 关闭writer，释放内存
		writer.close();

	}
}
