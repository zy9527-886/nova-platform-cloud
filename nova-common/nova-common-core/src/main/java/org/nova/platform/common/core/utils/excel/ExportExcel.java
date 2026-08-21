package org.nova.platform.common.core.utils.excel;


import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author lisongyu
 * @date 2019/09/23
 */
@Slf4j
public class ExportExcel {

    /**
     * 工作薄对象
     */
    private SXSSFWorkbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 样式列表
     */
    private Map<String, CellStyle> styles;

    /**
     * 当前行号
     */
    private int rownum;

    /**
     * 注解列表（Object[]{ ExcelField, Field/Method }）
     */
    private List<Object[]> annotationList = new ArrayList<>();

    /**
     * 构造函数
     *
     * @param title 表格标题，传“空值”，表示无标题
     * @param cls   实体对象，通过annotation.ExportField获取标题
     */
    public ExportExcel(String title, Class<?> cls) {
        //通过反射获取类的字段
        Field[] fs = cls.getDeclaredFields();
        for (Field f : fs) {
            //获取类中包含注解的字段
            ExcelField ef = f.getAnnotation(ExcelField.class);
            if (ef != null) {
                //将注解ExcelField和字段保存
                annotationList.add(new Object[]{ef, f});
            }
        }
        //获取方法
        Method[] ms = cls.getDeclaredMethods();
        for (Method m : ms) {
            ExcelField ef = m.getAnnotation(ExcelField.class);
            if (ef != null) {
                //将注解ExcelField和方法名保存
                annotationList.add(new Object[]{ef, m});
            }
        }
        //通过注解中的sort排序
        annotationList.sort(Comparator.comparing((Object[] o) -> ((ExcelField) o[0]).sort()));
        //初始化表格
        List<String> headerList = new ArrayList<>();
        for (Object[] os : annotationList) {
            //获取标题
            String t = ((ExcelField) os[0]).title();
            headerList.add(t);
        }
        initialize(title, headerList);
    }

    /**
     * 初始化表头
     *
     * @param title title
     * @param headerList  headerList
     */
    private void initialize(String title, List<String> headerList) {
        this.wb = new SXSSFWorkbook(500);
        this.sheet = wb.createSheet(title);
        this.styles = createStyles(wb);
        // Create title
        if (StringUtils.isNotBlank(title)) {
            Row titleRow = sheet.createRow(rownum++);
            titleRow.setHeightInPoints(30);
            //创建列
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(styles.get("title"));
            titleCell.setCellValue(title);
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
                    titleRow.getRowNum(), titleRow.getRowNum(), headerList.size() - 1));
        }
        // Create header
        if (headerList == null) {
            throw new RuntimeException("headerList not null!");
        }
        //创建行
        Row headerRow = sheet.createRow(rownum++);
        headerRow.setHeightInPoints(16);
        for (int i = 0; i < headerList.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(styles.get("header"));
            String[] ss = StringUtils.split(headerList.get(i), "**", 2);
            if (ss.length == 2) {
                cell.setCellValue(ss[0]);
                Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
                        new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                comment.setString(new XSSFRichTextString(ss[1]));
                cell.setCellComment(comment);
            } else {
                cell.setCellValue(headerList.get(i));
            }
            if (sheet instanceof SXSSFSheet) {
                ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
            } else {
                sheet.autoSizeColumn(i);
            }
        }
        for (int i = 0; i < headerList.size(); i++) {
            int colWidth = sheet.getColumnWidth(i) * 2;
            sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
        }
        log.debug("Initialize success.");
    }

    /**
     * 创建表格样式
     *
     * @param wb 工作薄对象
     * @return 样式列表
     */
    private static Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<>(8);

        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        style.setFont(titleFont);
        styles.put("title", style);

        style = wb.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        styles.put("data", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.LEFT);
        styles.put("data1", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        styles.put("data2", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        styles.put("data3", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("header", style);

        return styles;
    }

    /**
     * 添加数据（通过annotation.ExportField添加数据）
     *
     * @return list 数据列表
     */
    public <E> ExportExcel setDataList(List<E> list) {
        for (E e : list) {
            int colunm = 0;
            Row row = this.addRow();
            StringBuilder sb = new StringBuilder();
            for (Object[] os : annotationList) {
                ExcelField ef = (ExcelField) os[0];
                Object val = null;
                // Get entity value
                try {
                    if (StringUtils.isNotBlank(ef.value())) {
                        val = Reflections.invokeGetter(e, ef.value());
                    } else {
                        if (os[1] instanceof Field) {
                            val = Reflections.invokeGetter(e, ((Field) os[1]).getName());
                        } else if (os[1] instanceof Method) {
                            val = Reflections.invokeMethod(e, ((Method) os[1]).getName(), new Class[]{}, new Object[]{});
                        }
                    }
                } catch (Exception ex) {
                    log.info(ex.toString());
                    val = "";
                }
                //如果是枚举开启转化
                if(!"".equals(ef.convert())){
                    JSONObject jsonObject = JSONObject.parseObject(ef.convert());
                    Object res=jsonObject.get(val);
                    val=(res==null?val:res);
                }
                this.addCell(row, colunm++, val, ef.align());
                sb.append(val).append(",");
            }
        }
        return this;
    }

    /**
     * 添加一行
     *
     * @return 行对象
     */
    private Row addRow() {
        return sheet.createRow(rownum++);
    }

    /**
     * 添加一个单元格
     *
     * @param row    添加的行
     * @param column 添加列号
     * @param val    添加值
     * @param align  对齐方式（1：靠左；2：居中；3：靠右）
     * @return 单元格对象
     */
    private Cell addCell(Row row, int column, Object val, int align) {
        Cell cell = row.createCell(column);
        CellStyle style = styles.get("data" + (align >= 1 && align <= 3 ? align : ""));
        try {
            if (val == null) {
                cell.setCellValue("");
            } else if (val instanceof Integer) {
                cell.setCellValue((Integer) val);
            } else if (val instanceof Long) {
                cell.setCellValue((Long) val);
            } else if (val instanceof Double) {
                cell.setCellValue((Double) val);
            } else if (val instanceof BigDecimal) {
                cell.setCellValue(val.toString());
            } else if (val instanceof Float) {
                cell.setCellValue((Float) val);
            } else if (val instanceof Date) {
                DataFormat format = wb.createDataFormat();
                style.setDataFormat(format.getFormat("yyyy-MM-dd"));
                cell.setCellValue((Date) val);
            } else {
                cell.setCellValue((String) val);
            }
        } catch (Exception ex) {
            log.info("Set cell value [" + row.getRowNum() + "," + column + "] error: " + ex.toString());
            assert val != null;
            cell.setCellValue(val.toString());
        }
        cell.setCellStyle(style);
        return cell;
    }

    /**
     * 输出数据流
     *
     * @param os 输出数据流
     */
    public ExportExcel write(OutputStream os) throws IOException {
        wb.write(os);
        return this;
    }

    /**
     * 输出到客户端
     *
     * @param fileName 输出文件名
     */
    public ExportExcel write(HttpServletResponse response, String fileName) throws IOException {
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8"));
        write(response.getOutputStream());
        return this;
    }

    /**
     * 输出到客户端 解决乱码问题
     *
     * @param fileName 输出文件名
     */
    public ExportExcel write2(HttpServletResponse response, String fileName) throws IOException {
        fileName = new String(fileName.getBytes(), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        write(response.getOutputStream());
        return this;
    }

    /**
     * 输出到文件
     *
     * @param fileName 输出文件名
     */
    public void writeFile(String fileName) throws IOException {
        FileOutputStream os = new FileOutputStream(fileName);
        this.write(os);
    }

    /**
     * 清理临时文件
     */
    public ExportExcel dispose() {
        wb.dispose();
        return this;
    }

    /**
     * Description  动态表格导出excle
     *
     * @param excleList  数据里面是[{sheet:"sheet名",columns:[{id:"表头id",name:”表头汉字“}],data:[{表头id:值}]}]
     * @param fileName 文件名
     * @param response io
     * @return
     * @auther 张岳
     * @date 2021/4/9
     */
    public static int exportExcelTrend(List<Map<String, Object>> excleList,
                                       String fileName,
                                       HttpServletResponse response) throws IOException {
        SXSSFWorkbook wb = new SXSSFWorkbook(1000);

        for( Map<String, Object> map  : excleList){
            Sheet sheet = wb.createSheet(String.valueOf(map.get("sheet")));//设置sheet
            List<Map<String, Object>> colmuns=(List<Map<String, Object>>)map.get("colmuns");//获取表头
            List<Map<String, Object>> data= (List<Map<String, Object>>)map.get("data");//获取data
            Map<String, CellStyle> styles = createStyles(wb);
            SXSSFRow row;
            SXSSFCell cell;
            //绘制表头
            row = (SXSSFRow) sheet.createRow(0);
            for (int i = 0; i < colmuns.size(); i++) {
                cell = row.createCell(i);
                cell.setCellValue(String.valueOf(colmuns.get(i).get("name")));
                cell.setCellStyle(styles.get("header"));
            }
            //填内容
            for (int j = 0; j < data.size(); j++) {
                row = (SXSSFRow) sheet.createRow(j + 1);
                for (int i = 0; i < colmuns.size(); i++) {
                    cell = row.createCell(i);
                    String val = String.valueOf(data.get(j).get(String.valueOf(colmuns.get(i).get("id"))));
                    cell.setCellValue(val == null ? "" : val);
                    cell.setCellStyle(styles.get("data"));
                }
            }
        }
        //导出
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8") + ".xlsx");
        wb.write(response.getOutputStream());
        return 1;
    }
}
