package com.globant;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestAutomationPage {
    private static WebDriver driver;

    @BeforeSuite
    private void initDriver() {
        TestAutomationPage.driver = TestUtils.getWebDriver();
    }

    @BeforeTest
    private void goToLoginPage() {
        TestAutomationPage.driver.get("https://practicetestautomation.com/practice-test-login/");
    }

    @AfterSuite
    private void closeDriver() {
        TestAutomationPage.driver.close();
    }

    @Test
    public void loginPageTestAutomation() {
        driver.findElement(By.id("username")).sendKeys("student");
        driver.findElement(By.id("password")).sendKeys("Password123");

        driver.findElement(By.id("submit")).click();

        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("practicetestautomation.com/logged-in-successfully/"));

        WebElement title = driver.findElement(By.cssSelector("h1.post-title"));
        String titleMessage = title.getText();
        Assert.assertEquals(titleMessage, "Logged In Successfully");

        WebElement paragraph = driver.findElement(By.cssSelector("p.has-text-align-center > strong"));
        String pMessage = paragraph.getText();
        Assert.assertEquals(pMessage, "Congratulations student. You successfully logged in!");

        String buttonSelector = "a[href=\"https://practicetestautomation.com/practice-test-login/\"]";
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
        WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(buttonSelector)));
        String bMessage = logoutButton.getText();
        Assert.assertEquals(bMessage, "Log out");
    }

    @Test
    public void invalidUsername() {
        driver.findElement(By.id("username")).sendKeys("invalidStudent");
        driver.findElement(By.id("password")).sendKeys("Password123");

        driver.findElement(By.id("submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
        WebElement errorNotification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
        Assert.assertTrue(errorNotification.isDisplayed());
        Assert.assertEquals(errorNotification.getText(), "Your username is invalid!");
    }

    @Test
    public void invalidPassword() {
        driver.findElement(By.id("username")).sendKeys("student");
        driver.findElement(By.id("password")).sendKeys("IncorrectPassword");

        driver.findElement(By.id("submit")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
        WebElement errorNotification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("error")));
        Assert.assertTrue(errorNotification.isDisplayed());
        Assert.assertEquals(errorNotification.getText(), "Your password is invalid!");
    }
}
