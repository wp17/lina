package com.github.wp17.lina.config.excel;

import org.apache.poi.ss.formula.BaseFormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**封装一个excel表格*/
public class ExcelWorkbook {
	protected Workbook workbook;
	protected String fileName;

	public ExcelWorkbook(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		workbook = WorkbookFactory.create(fis);
		fis.close();
		fileName = file.getName();
	}

	/**计算表数据*/
	public void evaluate() {
		BaseFormulaEvaluator.evaluateAllFormulaCells(workbook);
	}
	
	/**
	 * 关闭工作薄。
	 */
	public void close() {
		try {
			workbook.close();
		} catch (IOException e) {
		}
	}
	
	/**
	 * @param index 页索引
	 * @return 指定页工作表
	 */
	public ExcelSheet getSheet(int index) {
		return new ExcelSheet(workbook.getSheetAt(index), fileName);
	}
	
	/**
	 * @return 页数
	 */
	public int getNumberOfSheets() {
		return workbook.getNumberOfSheets();
	}
}
