package org.nova.platform.common.core.utils.excel;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author lisongyu
 * @date 2019/09/23
 */
@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class TestClass {

    @ExcelField(title = "编号", align = 2)
    private Integer id;

    @ExcelField(title = "姓名", align = 2, sort = 1)
    private String name;

    @ExcelField(title = "年龄", align = 2, sort = 2)
    private Integer age;

    @ExcelField(title = "薪水", align = 2, sort = 3)
    private Double salary;

    @ExcelField(title = "出生日期", align = 2, sort = 4)
    private String birthday;


}


@Slf4j
class Test {


    public static void main(String[] args) throws Exception {

        List<TestClass> testClasses = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {

            TestClass testClass = TestClass.builder()
                    .id(i)
                    .name("测试" + i)
                    .age(new Random().nextInt(100))
                    .salary(10000d)
                    .birthday(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                    .build();
            testClasses.add(testClass);
        }

        ExportExcel exportExcel = new ExportExcel("测试导出", TestClass.class).setDataList(testClasses);;
        exportExcel.writeFile("E:\\1.xlsx");
        exportExcel.dispose();


        ImportExcel importExcel = new ImportExcel("E:\\1.xlsx", 1, 0);
        List<TestClass> list = importExcel.getDataList(TestClass.class);
        for (int i = 0; i < list.size(); i++) {
            TestClass excelModelTest = list.get(i);
            log.info(excelModelTest.toString());
        }
    }

}