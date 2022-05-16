package cn.px.base.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.px.base.support.context.ApplicationContextHolder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.*;

public class ExportExcelUtil {
    private static Logger logger = LogManager.getLogger(ExportExcelUtil.class);
    private static final String exportDir = ApplicationContextHolder.applicationContext.getEnvironment().getProperty("export.baseDir");

    public static String doExportFile(ExportParams entity, Class<?> pojoClass,
                                      Collection<?> dataSet){
        Workbook sheets = ExcelExportUtil.exportExcel(entity, pojoClass, dataSet);
        File file = new File(exportDir);
        if(!file.exists()){
            if(!file.mkdirs()){
                logger.error("创建导出目录失败");
            };
        }
        String fileName = getFileName();
        try (FileOutputStream output = new FileOutputStream(exportDir+"/"+fileName);) {
            sheets.write(output);
            output.flush();
            return fileName;
        } catch (Exception e) {
        //    e.printStackTrace();
            logger.error("导出excel文件失败");
            throw new RuntimeException("导出excel文件失败");
        }
    }

    private static String getFileName(){
        String uuid = UUIDUtil.getOrderIdByUUId()+".xls";
        return "live"+uuid;
    }

    /**
     *
     * @param list 导出的数据集合
     * @param titles 表头对应的属性名
     * @param titleNames 表头名称
     * @return
     */
    public static Workbook createWorkbook(List<Map<String, Object>> list, String titles[], String[] titleNames){
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow(0);
        for(int i = 0; i < titleNames.length; i++){
            Cell headCell = row.createCell(i);
            headCell.setCellType(CellType.STRING);
            headCell.setCellValue(titleNames[i]);
        }
        for(int j = 0; j < list.size(); j++){
            Row rowData = sheet.createRow(j + 1);
            Map<String, Object> map = list.get(j);
            for(int k = 0; k < titles.length; k++){
                Cell cell = rowData.createCell(k);
                cell.setCellType(CellType.STRING);
                if(map.get(titles[k]) != null){
                    cell.setCellValue(String.valueOf(map.get(titles[k])));
                }
            }
        }
        return wb;
    }
}
