package HEAcalculate;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author YEW-Lenovo
 * @Description 用于读取excel表的工具类函数
 * @date 2022/2/13
 */
public class ReadUtils {

    private XSSFSheet sheet = null;
    private FileInputStream fips = null;
    private XSSFWorkbook workbook = null;

    /**
     * @param filePath  excel文件的路径
     * @param sheetName 工作簿名称
     * @throws Exception
     * @Description 初始化excel数据，获取到所需的工作簿
     */
    public void initdata(String filePath, String sheetName) throws HeaException {
        try {
            fips = new FileInputStream(filePath);
            workbook = new XSSFWorkbook(fips);
            sheet = workbook.getSheet(sheetName);
        } catch (IOException e) {
            throw new HeaException("路径或文件名不正确！");
        }

        if ((fips == null) | (sheet == null)) {
            throw new HeaException("工作簿名称不正确！");
        }
    }

    /**
     * @param row
     * @param column
     * @return
     * @Description 根据行和列的索引获取单元格的数据
     */
    public String getDataByIndex(int row, int column) throws HeaException {
        XSSFRow rowIndex = sheet.getRow(row);//获取到行
        XSSFCell cell = rowIndex.getCell(column);//获取到单元格
        String cellData = cell.toString();
        if (cellData != "") {
            return cellData;
        } else {
            throw new HeaException("表中存在空单元格!");
        }
    }

    /**
     * @param row
     * @return
     * @Description 获取某一行的列数
     */
    public int getColNumByRow(int row) {
        XSSFRow rowIndex = sheet.getRow(row);//获取到行
        int cells = rowIndex.getPhysicalNumberOfCells();//获取此行的列数
        return cells;
    }

    /**
     * @param row
     * @return
     * @Description 获取某一行的所有数据，返回一个list
     */
    public ArrayList<Float> getRowByIndex(int row) throws HeaException {
        ArrayList<Float> rowList = new ArrayList<>();
        int cells = getColNumByRow(row);//获取此行的列数
        String cellData = null;
        float v = 0;

        for (int i = 1; i < cells; i++) {
            cellData = getDataByIndex(row, i);
            try {
                v = Float.parseFloat(cellData);
            } catch (NumberFormatException e) {
                throw new HeaException("表中数据存在非数值型错误！");
            }
            rowList.add(v);
        }
        return rowList;
    }

    public void CloseResource() {
        try {
            if (workbook != null) {
                workbook.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (fips != null) {
                fips.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public XSSFSheet getSheet() {
        return sheet;
    }
}
