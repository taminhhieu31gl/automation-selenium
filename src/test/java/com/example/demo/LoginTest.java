package com.example.demo;

import com.opencsv.CSVReader;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LoginTest {

    WebDriver driver;
    String url = "https://hiitfigure.com/login";

    @Deprecated
    @BeforeClass
    public void init() {
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/example/demo/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

    }

    @Deprecated
    @DataProvider(name = "TestData")
    public Object[][] getData() throws IOException {
        String csvFile = "excel/Login_TC.csv";
        CSVReader reader = new CSVReader(new FileReader(csvFile), ',', '\'', 1);

        int rows = 0;
        int cols = 0;
        List<String[]> header = reader.readAll();
        if (header != null) {
            rows = header.size();
            cols = header.get(0).length;
        }

        Object[][] data = new Object[rows][cols];
        int lineNumber = 0;
        for (String[] nextLine: header) {
            for (int colNumber = 0; colNumber < nextLine.length; colNumber++) {
                data[lineNumber][colNumber] = nextLine[colNumber];
            }
            lineNumber++;
        }

        return data;
    }

    @Test(dataProvider = "TestData")
    public void LoginTest(String username, String password) {

        driver.get(url);

        List<WebElement> elementUsername = driver.findElements(By.name("username"));
        List<WebElement> elementPassword = driver.findElements(By.name("password"));
        List<WebElement> login = driver.findElements(By.className("btn-block"));

        elementUsername.get(0).clear();
        elementUsername.get(0).sendKeys(username);

        elementPassword.get(0).clear();
        elementPassword.get(0).sendKeys(password);

        login.get(0).click();

        String errorUrl = "https://hiitfigure.com/login";
        String actualUrl = driver.getCurrentUrl();
//
        Assert.assertTrue("Login failed!", !actualUrl.contains(errorUrl));
    }

    @AfterClass
    public void quit() {
        driver.close();
        driver.quit();
    }
}

