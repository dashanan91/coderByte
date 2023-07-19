package main.java;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class basicFunctionalities {

    public static Map<String, String> readingValueFromExcel(String fileName) {
        Map<String, String> valuesFromExcel = new HashMap<>();
        try (InputStream inp = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/" + fileName)) {
            Workbook wb = WorkbookFactory.create(inp);
            Sheet sheet = wb.getSheetAt(0);

            Row row = sheet.getRow(0);
            Row row1 = sheet.getRow(1);
            row.getPhysicalNumberOfCells();
            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                valuesFromExcel.put(row.getCell(i).toString(), row1.getCell(i).toString());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);


        }
        return valuesFromExcel;
    }

        public void waitingForLoaderToDisappear(WebDriver driver) throws InterruptedException {
        int counter = 0;
        String loaderStyle = "block";
        while (counter <= 10 && loaderStyle.contains("block")) {
            counter += 1;
            Thread.sleep(2000);
            loaderStyle = driver.findElement(By.xpath("//app-loader")).getAttribute("style");
        }
    }
    public void dateSelector(WebDriver driver, String id, String date){
        driver.findElement(By.xpath("//input[@id='"+id+"']")).click();

        String[] dateParams = date.split("-");
        Select yearSelect = new Select(driver.findElement(By.xpath("//select[@data-handler='selectYear']")));
        yearSelect.selectByValue(dateParams[2]);


        Select monthSelect = new Select(driver.findElement(By.xpath("//select[@data-handler='selectMonth']")));
        monthSelect.selectByVisibleText(dateParams[1]);

        driver.findElement(By.xpath("//td[@data-handler='selectDay']/a[text()='"+dateParams[0]+"']")).click();
    }
    public static Map<String, String> gettingValuesFromProperties(String propertyFileName) throws IOException {
        Properties properties = new Properties();
        properties.load(
                new FileInputStream(
                        new File
                                (System.getProperty("user.dir")
                                        +"/src/main/resources/"+
                                        propertyFileName)));

        HashMap<String, String> propertiesValues = new HashMap<>();
        properties.keySet().stream().forEach(
                x-> propertiesValues.put(x.toString(), properties.get(x).toString()));

        return propertiesValues;
    }

}
