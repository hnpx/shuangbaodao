package cn.px.base.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExcelUploadUtil {

    private  static List<Map<String, Object>> analysisData(Workbook wb, String columns[], int firstRowNum, int firstCellNum){
        Sheet sheet = null;
        Row row = null;
        List<Map<String,Object>> list = null;
        String cellData = null;
        if(wb != null){
            //用来存放表中数据
            list = new ArrayList<Map<String,Object>>();
            //获取第一个sheet
            sheet = wb.getSheetAt(0);
            //获取最大行数
            int rownum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(0);
            //获取最大列数
            int colnum = row.getPhysicalNumberOfCells();
            for (int i = firstRowNum; i<rownum; i++) {
                Map<String,Object> map = new LinkedHashMap<String,Object>();
                row = sheet.getRow(i);
                if(row !=null){
                    int columsIndex = 0;
                    for (int j=firstCellNum;j<colnum;j++){
                        cellData = (String) getCellFormatValue(row.getCell(j));
                        if(columsIndex < columns.length){
                            map.put(columns[columsIndex], cellData);
                        }
                        columsIndex ++;
                    }
                }else{
                    break;
                }
                list.add(map);
            }
        }
        return list;
    }

    /**
     *
     * @param file 文件
     * @param columns 字段
     * @param firstRowNum 获取数据的起始行
     * @param firstCellNum 获取数据的起始列
     * @return
     */
    public static List<Map<String, Object>> readExcelByMultipartFile(MultipartFile file, String columns[], int firstRowNum, int firstCellNum){
        InputStream is = null;
        Workbook wb = null;
        String fileName = file.getOriginalFilename();
        String extString = fileName.substring(fileName.lastIndexOf("."));
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            is = file.getInputStream();
            if(is==null){
                return list;
            }
            if(".xls".equals(extString)){
                list = analysisData(new HSSFWorkbook(is), columns, firstRowNum, firstCellNum);
            }else if(".xlsx".equals(extString)){
                list = analysisData(new XSSFWorkbook(is), columns, firstRowNum, firstCellNum);
            }
            return list;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     *
     * @param filePath 文件路径
     * @param columns 字段
     * @param firstRowNum 获取数据的起始行
     * @param firstCellNum 获取数据的起始列
     * @return
     */
    public static List<Map<String, Object>> readExcelByFilePath(String filePath, String columns[], int firstRowNum, int firstCellNum){
        Workbook wb = null;
        if(filePath==null){
            return null;
        }
        String extString = filePath.substring(filePath.lastIndexOf("."));
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if(".xls".equals(extString)){
                return analysisData(new HSSFWorkbook(is), columns, firstRowNum, firstCellNum);
            }else if(".xlsx".equals(extString)){
                return analysisData(new XSSFWorkbook(is), columns, firstRowNum, firstCellNum);
            }else{
                return null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getCellFormatValue(Cell cell){
        Object cellValue = null;
        if(cell!=null){
            DecimalFormat df = new DecimalFormat("#");
            //判断cell类型
            CellType cellType = cell.getCellType();
            if(CellType.NUMERIC.equals(cellType)){
                cellValue = String.valueOf(df.format(cell.getNumericCellValue()));
            } else if(CellType.FORMULA.equals(cellType)){
                //判断cell是否为日期格式
                if(DateUtil.isCellDateFormatted(cell)){
                    //转换为日期格式YYYY-mm-dd
                    cellValue = cell.getDateCellValue();
                }else{
                    //数字
                    cellValue = String.valueOf(cell.getNumericCellValue());
                }
            } else if(CellType.STRING.equals(cellType)){
                cellValue = cell.getRichStringCellValue().getString();
            } else {
                cellValue = "";
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }
}
