package com.mcnc.utils;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public abstract class ExcelUtil<T> {
	public static final String PATTERN_yyyyMMdd_SLASH = "yyyy/MM/dd";

	public abstract T getMappingColumn(Row row) throws Exception;

	public abstract Row setMappingColumn(Row row, Object model, String cause)
			throws Exception;

	public String getCellContents(Cell cell) {
		String contents = "";
		if (null != cell) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				contents = cell.getRichStringCellValue().getString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (org.apache.poi.ss.usermodel.DateUtil
						.isCellDateFormatted(cell)) {
					contents = DateFormatUtils.format(cell.getDateCellValue(),
							PATTERN_yyyyMMdd_SLASH);
				} else {
					contents = String.valueOf(new Double(cell
							.getNumericCellValue()).intValue());
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				contents = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				contents = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_BLANK:
				contents = "";
				break;
			default:
				contents = cell.getStringCellValue();
				break;
			}
		}
		return contents;
	}

	public boolean isNotBlankRow(Sheet sheet, int firstRow, int iRowNum) {
		int ret = 0;
		for (int i = 0; i < sheet.getRow(firstRow).getLastCellNum(); i++) {
			Row row = sheet.getRow(iRowNum);
			if (null != row.getCell(i)
					&& Cell.CELL_TYPE_BLANK == row.getCell(i).getCellType()) {
				ret++;
			}
		}
		return !(ret == sheet.getRow(firstRow).getLastCellNum());
	}
}
