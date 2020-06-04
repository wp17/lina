package com.github.wp17.lina.config.excel;

import com.github.wp17.lina.util.NumberUtil;
import com.github.wp17.lina.util.StringUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 封装一个工作表页。
 */
public class ExcelSheet {
    protected Sheet sheet;
    protected String fileName;

    /**
     * 封装一个poi工作表。
     */
    public ExcelSheet(Sheet sheet, String fileName) {
        this.sheet = sheet;
        this.fileName = fileName;
    }

    /**
     * @return 工作表行数
     */
    public int getRows() {
        return this.sheet.getLastRowNum() + 1;
    }

    /**
     * @param row 从0开始的行号
     * @return 工作表某一行的列数
     */
    public int getColumns(int row) {
        return this.sheet.getRow(row).getLastCellNum() + 1;
    }

    /**
     * @return 工作表标题。
     */
    public String getName() {
        return sheet.getSheetName();
    }

    /**
     * 读取原始单元格字符串。
     *
     * @param row 从0开始的行号
     * @param col 从0开始的列号
     * @return
     */
    public String getString(int row, int col) {
        Row hrow = sheet.getRow(row);
        if (hrow == null) {
            return "";
        }
        Cell cell = hrow.getCell(col);
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case BLANK:
            case ERROR:
                return "";
            case BOOLEAN:
                return cell.getBooleanCellValue() ? "1" : "0";
            case NUMERIC: {
                String ret = String.format("%.4f", cell.getNumericCellValue());
                if (ret.endsWith(".0000")) {
                    ret = ret.substring(0, ret.length() - 5);
                }
                return ret;
            }
            case STRING:
                return StringUtil.normalizeCRLF(cell.getStringCellValue());
            case FORMULA: {
                try {
                    String ret = String.format("%.2f", cell.getNumericCellValue());
                    if (ret.endsWith(".00")) {
                        ret = ret.substring(0, ret.length() - 3);
                    }
                    return ret;
                } catch (IllegalStateException e) {
                    return StringUtil.normalizeCRLF(cell.getStringCellValue());
                }
            }
        }
        return "";
    }

    /**
     * 读取一个string数组。
     *
     * @param row       从0开始的行号
     * @param col       从0开始的列号
     * @param delimiter 分隔符，可以用正则表达式，因为这个函数内部用String.split来切分
     * @return 如果单元内容为空，返回一个空数组
     */
    public String[] getStrings(int row, int col, String delimiter) {
        String str = getString(row, col);
        if (str.length() == 0) {
            return new String[0];
        }
        return str.split(delimiter);
    }

    /**
     * 读取一个整数值。
     *
     * @param row 从0开始的行号
     * @param col 从0开始的列号
     */
    public int getInt(int row, int col) {
        String str = getString(row, col);
        if (str.length() == 0) {
            return 0;
        }
        if (str.contains(".")) {
            str = str.substring(0, str.indexOf('.'));
        }
        return NumberUtil.parseInt(str);
    }

    /**
     * 读取一个int数组。数组元素之间用逗号分隔。
     *
     * @param row 从0开始的行号
     * @param col 从0开始的列号
     * @return 如果单元内容为空，返回一个空数组
     */
    public int[] getInts(int row, int col) {
        String str = getString(row, col);
        if (str.length() == 0) {
            return new int[0];
        }
        return StringUtil.stringToIntArray(str, ',');
    }

    /**
     * 读取一个浮点数值。
     *
     * @param row 从0开始的行号
     * @param col 从0开始的列号
     */
    public float getFloat(int row, int col) {
        String str = getString(row, col);
        if (str.length() == 0) {
            return 0;
        }
        return Float.parseFloat(str);
    }

    /**
     * 读取一个浮点数组。数组元素之间用逗号分隔。
     *
     * @param row 从0开始的行号
     * @param col 从0开始的列号
     * @return 如果单元内容为空，返回一个空数组
     */
    public float[] getFloats(int row, int col) {
        String str = getString(row, col);
        if (str.length() == 0) {
            return new float[0];
        }
        return StringUtil.stringToFloatArray(str, ',');
    }

    /**
     * 读取一个双精度浮点数值
     *
     * @param row
     * @param col
     */
    public double getDouble(int row, int col) {
        String str = getString(row, col);
        if (str.length() == 0) {
            return 0;
        }
        return Double.parseDouble(str);
    }

    /**
     * 读取一个长整数值。
     *
     * @param row 从0开始的行号
     * @param col 从0开始的列号
     */
    public long getLong(int row, int col) {
        String str = getString(row, col);
        if (str.length() == 0) {
            return 0;
        }
        if (str.contains(".")) {
            str = str.substring(0, str.indexOf('.'));
        }
        return NumberUtil.parseLong(str);
    }

    /**
     * 读取一个布尔值。布尔值用整数表示，0为false，非0为true。
     *
     * @param row 从0开始的行号
     * @param col 从0开始的列号
     */
    public boolean getBoolean(int row, int col) {
        return getInt(row, col) != 0;
    }
}
