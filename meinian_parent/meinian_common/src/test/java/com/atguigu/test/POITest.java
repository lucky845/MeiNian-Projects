package com.atguigu.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 测试POI类操作办公软件
 */
public class POITest {

    /**
     * 测试POI读取Excel文件
     */
    @Test
    public void testPOIRead() throws IOException {
        // 创建一个Workbook对象解析Excel文件
        Workbook wb = new XSSFWorkbook("E:\\Projects\\atguigu-projects\\MeiNian-Projects\\meinian_parent\\meinian_web\\src\\main\\webapp\\template\\test.xlsx");
        // 读取文件中的一个表,根据表的顺序获取
        Sheet sheet = wb.getSheetAt(0);
        // 遍历工作表获取行对象
        for (Row row : sheet) {
            // 遍历行对象获取单元格对象
            for (Cell cell : row) {
                // 获得单元格中的值
                String value = cell.getStringCellValue();// 注意: 数字类型,需要修改excel单元格的类型否者报错
                // 每打印一列就空两格
                System.out.print(value + "\t\t"); // new String(value.getBytes("UTF-8"),"GBK");
            }
            // 打印完一行后就换行
            System.out.println();
        }
        // 关闭工作簿
        wb.close();
    }

    /**
     * 测试POI读取Excel文件
     */
//    @Test
    public void testPOIRead2() throws IOException {
        // 创建工作簿
        Workbook wb = new XSSFWorkbook("E:\\Projects\\atguigu-projects\\MeiNian-Projects\\meinian_parent\\meinian_web\\src\\main\\webapp\\template\\test.xlsx");
        // 获取工作表
        Sheet sheet = wb.getSheetAt(0);
        // 获取当前工作表的最后一行的行号, 行号从0开始
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i < lastRowNum; i++) {
            // 根据行号获取单元格对象
            Row row = sheet.getRow(i);
            // 再获取单元格对象
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                // 获取单元格对象的值
                String value = row.getCell(j).getStringCellValue();
                System.out.print("\t\t" + value);
            }
            System.out.println();
        }
        // 关闭工作簿
        wb.close();
    }

    /**
     * 测试POI写一个Excel文件
     */
//    @Test
    public void testPOIWrite() throws IOException {
        // 在内存中创建一个Excel文件
        Workbook wb = new XSSFWorkbook();
        // 创建工作表,指定工作表名称
        Sheet sheet = wb.createSheet("test2");

        // 创建第一行
        Row row1 = sheet.createRow(0);
        // 创建单元格, 0表示第一个单元格, 并设置内容
        row1.createCell(0).setCellValue("姓名");
        row1.createCell(1).setCellValue("性别");
        row1.createCell(2).setCellValue("地址");

        // 创建第二行
        Row row2 = sheet.createRow(1);
        // 创建单元格, 0表示第一个单元格, 并设置内容
        row2.createCell(0).setCellValue("张三");
        row2.createCell(1).setCellValue("男");
        row2.createCell(2).setCellValue("武汉");

        // 创建第三行
        Row row3 = sheet.createRow(2);
        // 创建单元格, 0表示第一个单元格, 并设置内容
        row3.createCell(0).setCellValue("李四");
        row3.createCell(1).setCellValue("男");
        row3.createCell(2).setCellValue("上海");

        // 通过输出流将workbook对象下载到磁盘中
        FileOutputStream fos = new FileOutputStream("E:\\Projects\\atguigu-projects\\MeiNian-Projects\\meinian_parent\\meinian_web\\src\\main\\webapp\\template\\test2.xlsx");
        wb.write(fos);
        fos.flush();
        // 关闭输出流
        fos.close();
        // 关闭工作簿
        wb.close();

    }

}
