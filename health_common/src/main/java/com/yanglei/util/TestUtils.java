package com.yanglei.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class TestUtils {
    @Test
    public void test() throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("杨雷工资表");

        HSSFRow row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue("一月");
        row1.createCell(1).setCellValue("二月");
        row1.createCell(3).setCellValue("三月");
        for (int i = 1; i < 4; i++) {
            HSSFRow row = sheet.createRow(i);
            for (int j = 0; j < 3; j++) {
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(3000 + 100 * j);
            }
        }
        FileOutputStream out = new FileOutputStream("D:/test.xls");
        workbook.write(out);
        out.close();
        workbook.close();
    }
}
