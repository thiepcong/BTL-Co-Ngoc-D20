package vn.edu.ptit.sqa.selenium;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class Login {
    String siteUrl = "https://thiepcong.github.io/BLT-Co-Ngoc-Demo-Web/";
    WebDriver driver;
    JavascriptExecutor js;

    @BeforeAll
    static void setUp() {

    }

    @BeforeEach
    public void init() {
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver","C:/Users/Admin/Downloads/chromedriver.exe");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        js = (JavascriptExecutor) driver;
        driver.get(siteUrl);
    }

    @AfterEach
    public void afterTest() {
        driver.quit();
    }

    @Test
    public void loginDisplayDone() throws InterruptedException {
        Thread.sleep(5000);
        assertAll("Login display",
                () -> assertTrue(((String) driver.getCurrentUrl()).contains(siteUrl+"#login")),
                () -> assertEquals("Đăng nhập",
                        driver.findElement(By.id("flt-semantic-node-3"))
                                .getAttribute("aria-label")),
                () -> assertEquals("Tên người dùng",
                        driver.findElement(By.id("flt-semantic-node-4")).findElement(By.cssSelector("input"))
                                .getAttribute("aria-label")),
                () -> assertEquals("Mật khẩu",
                        driver.findElement(By.id("flt-semantic-node-5")).findElement(By.cssSelector("input"))
                                .getAttribute("aria-label")),
                () -> assertEquals("Đăng nhập",
                        driver.findElement(By.cssSelector("flt-semantics[role=\"button\"]"))
                                .getAttribute("aria-label"))
                );
    }

    @Test
    public void loginHappy() throws InterruptedException {
        Thread.sleep(5000);
        WebElement userNameInput = driver.findElement(By.cssSelector("#flt-semantic-node-4 input"));
                //(WebElement) js.executeScript("return document.querySelector(\"#flt-semantic-node-4 input\")");
        userNameInput.sendKeys("admin");
        Thread.sleep(1000);
        WebElement passwordInput = driver.findElement(By.cssSelector("#flt-semantic-node-5 input"));
                //(WebElement) js.executeScript("return document.querySelector(\"#flt-semantic-node-5 input\")");
        passwordInput.sendKeys("123123123");
        Thread.sleep(1000);

        WebElement loginButton = driver.findElement(By.cssSelector("flt-semantics[role=\"button\"]"));
        loginButton.click();
        Thread.sleep(5000);

        assertFalse(((String) driver.getCurrentUrl()).contains(siteUrl+"#login"));
    }

    @Test
    public void loginLostUserName() throws InterruptedException {
        Thread.sleep(5000);
        WebElement passwordInput = driver.findElement(By.cssSelector("#flt-semantic-node-5 input"));
        passwordInput.sendKeys("123123123");
        Thread.sleep(1000);

        WebElement loginButton = driver.findElement(By.cssSelector("flt-semantics[role=\"button\"]"));
        loginButton.click();
        Thread.sleep(1000);

        assertAll("Login Lost UserName",
                () -> assertTrue(((String) driver.getCurrentUrl()).contains(siteUrl+"#login")),
                () -> assertEquals("Vui lòng nhập tên người dùng!",
                        driver.findElement(By.cssSelector("#flt-semantic-node-4 flt-semantics-container flt-semantics"))
                                .getAttribute("aria-label"))
                );
    }

    @Test
    public void loginLostPassword() throws InterruptedException {
        Thread.sleep(5000);
        WebElement usernameInput = driver.findElement(By.cssSelector("#flt-semantic-node-4 input"));
        //(WebElement) js.executeScript("return document.querySelector(\"#flt-semantic-node-5 input\")");
        usernameInput.sendKeys("admin");
        Thread.sleep(1000);

        WebElement loginButton = driver.findElement(By.cssSelector("flt-semantics[role=\"button\"]"));
        loginButton.click();
        Thread.sleep(1000);

        assertAll("Login Lost Password",
                () -> assertTrue(((String) driver.getCurrentUrl()).contains(siteUrl+"#login")),
                () -> assertEquals("Vui lòng nhập mật khẩu!",
                        driver.findElement(By.cssSelector("#flt-semantic-node-5 flt-semantics-container flt-semantics"))
                                .getAttribute("aria-label"))
        );
    }

    @Test
    public void loginBadCredential() throws InterruptedException {
        Thread.sleep(5000);
        WebElement userNameInput = driver.findElement(By.cssSelector("#flt-semantic-node-4 input"));
        //(WebElement) js.executeScript("return document.querySelector(\"#flt-semantic-node-4 input\")");
        userNameInput.sendKeys("admin2");
        Thread.sleep(1000);
        WebElement passwordInput = driver.findElement(By.cssSelector("#flt-semantic-node-5 input"));
        //(WebElement) js.executeScript("return document.querySelector(\"#flt-semantic-node-5 input\")");
        passwordInput.sendKeys("123123123");
        Thread.sleep(1000);

        WebElement loginButton = driver.findElement(By.cssSelector("flt-semantics[role=\"button\"]"));
        loginButton.click();
        Thread.sleep(5000);

        assertTrue(((String) driver.getCurrentUrl()).contains(siteUrl+"#login"));
    }

    @Test
    public void loginNavigationDone() throws InterruptedException {
        Thread.sleep(5000);
        WebElement userNameInput = driver.findElement(By.cssSelector("#flt-semantic-node-4 input"));
        //(WebElement) js.executeScript("return document.querySelector(\"#flt-semantic-node-4 input\")");
        userNameInput.sendKeys("admin");
        Thread.sleep(1000);
        WebElement passwordInput = driver.findElement(By.cssSelector("#flt-semantic-node-5 input"));
        passwordInput.sendKeys("123123123");
        Thread.sleep(1000);

        WebElement loginButton = driver.findElement(By.cssSelector("flt-semantics[role=\"button\"]"));
        loginButton.click();
        Thread.sleep(5000);

        assertAll("Login Navigation Done",
                () -> assertTrue(((String) driver.getCurrentUrl()).contains(siteUrl+"#manager")),
                () -> assertTrue(driver.findElement(By.cssSelector("#flt-semantic-node-39"))
                                .getAttribute("aria-label").contains("Quản lý:"))
        );
    }
}
