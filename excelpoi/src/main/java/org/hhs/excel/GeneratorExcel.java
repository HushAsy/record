package org.hhs.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hhs.common.Mode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class GeneratorExcel {
    public static void main(String...args) throws IOException, InvalidFormatException {
        createExcel(0);
    }

    public static void createExcel(int mode) throws IOException, InvalidFormatException {
        Workbook workBook = null;
        if (Mode.VERSION_H == mode){
            workBook = new HSSFWorkbook();
        }else if (Mode.VERSION_L == mode){
            workBook = new XSSFWorkbook();
        }else {
            workBook = new HSSFWorkbook();
        }

        //创建工作簿
        Sheet sheet = workBook.createSheet();
        sheet.setDefaultColumnWidth(30);
        sheet.setDefaultRowHeight((short) 20);

        //创建样式对象
        CellStyle cellStyle = workBook.createCellStyle();
        Font font = workBook.createFont();
        font.setBold(true);
        font.setFontName("黑体");
        cellStyle.setFont(font);

        //创建第一个行对象
        Row row = sheet.createRow(1);
        row.setHeightInPoints(23);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setBorderTop(BorderStyle.THICK);
        cellStyle.setBorderBottom(BorderStyle.DOUBLE);
        cellStyle.setBorderLeft(BorderStyle.MEDIUM);
        cellStyle.setBorderRight(BorderStyle.MEDIUM);
        DataFormat format = workBook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("m/d/yy h:mm"));
        Cell cell = row.createCell(1);
        cell.setCellValue(new Date());
        cell.setCellStyle(cellStyle);
        Cell cell1 = row.createCell(2);
        cell1.setCellValue("docker容器");

        FileOutputStream os = new FileOutputStream("test.xls");
        workBook.write(os);
        os.close();
    }

    /**
     * 读取 office 2003 excel
     *
     * @throws IOException
     */
    private static List<List<Object>> read2003Excel(File file)
            throws IOException {
        List<List<Object>> list = new LinkedList<List<Object>>();
        HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheet = hwb.getSheetAt(0);
        Object value = null;
        HSSFRow row = null;
        HSSFCell cell = null;
        System.out.println("读取office 2003 excel内容如下：");
        for (int i = sheet.getFirstRowNum(); i <= sheet
                .getPhysicalNumberOfRows(); i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            List<Object> linked = new LinkedList<Object>();
            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                cell = row.getCell(j);
                if (cell == null) {
                    continue;
                }
                DecimalFormat df = new DecimalFormat("0");// 格式化 number String
                // 字符
                SimpleDateFormat sdf = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");// 格式化日期字符串
                DecimalFormat nf = new DecimalFormat("0.00");// 格式化数字
                switch (cell.getCellType()) {
                    case XSSFCell.CELL_TYPE_STRING:
                        // System.out.println(i + "行" + j + " 列 is String type");
                        value = cell.getStringCellValue();
                        System.out.print("  " + value + "  ");
                        break;
                    case XSSFCell.CELL_TYPE_NUMERIC:
                        // System.out.println(i + "行" + j
                        // + " 列 is Number type ; DateFormt:"
                        // + cell.getCellStyle().getDataFormatString());
                        if ("@".equals(cell.getCellStyle().getDataFormatString())) {
                            value = df.format(cell.getNumericCellValue());

                        } else if ("General".equals(cell.getCellStyle()
                                .getDataFormatString())) {
                            value = nf.format(cell.getNumericCellValue());
                        } else {
                            value = sdf.format(HSSFDateUtil.getJavaDate(cell
                                    .getNumericCellValue()));
                        }
                        System.out.print("  " + value + "  ");
                        break;
                    case XSSFCell.CELL_TYPE_BOOLEAN:
                        // System.out.println(i + "行" + j + " 列 is Boolean type");
                        value = cell.getBooleanCellValue();
                        System.out.print("  " + value + "  ");
                        break;
                    case XSSFCell.CELL_TYPE_BLANK:
                        // System.out.println(i + "行" + j + " 列 is Blank type");
                        value = "";
                        System.out.print("  " + value + "  ");
                        break;
                    default:
                        // System.out.println(i + "行" + j + " 列 is default type");
                        value = cell.toString();
                        System.out.print("  " + value + "  ");
                }
                if (value == null || "".equals(value)) {
                    continue;
                }
                linked.add(value);

            }
            System.out.println("");
            list.add(linked);
        }

        return list;
    }
}
