package com.globant;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TestLoginHerokuPage {
    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeClass
    private void initDriver() {
        TestLoginHerokuPage.driver = TestUtils.getWebDriver();
        TestLoginHerokuPage.wait = new WebDriverWait(driver, Duration.ofSeconds(10).getSeconds());
        TestLoginHerokuPage.driver.get("https://the-internet.herokuapp.com/login");
    }

    @AfterClass
    private void closeDriver() {
        TestLoginHerokuPage.driver.close();
        TestLoginHerokuPage.driver = null;
    }

    @Test
    public void validLogin() throws InterruptedException {
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");

        driver.findElement(By.cssSelector("button[type=\"submit\"].radius")).click();

        String expectedMessage = "You logged into a secure area!\n" + "×";
        WebElement notificationLoggedIn = driver.findElement(By.id("flash"));
        String message = notificationLoggedIn.getText();
        Assert.assertTrue(notificationLoggedIn.isDisplayed());
        Assert.assertEquals(expectedMessage, message);

        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

        WebElement element = TestLoginHerokuPage.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href=\"/logout\"].button.secondary.radius")));
        Assert.assertEquals("Logout", element.getText());

        element.click();
    }

    @Test
    public void incorrectUsername() {
        driver.findElement(By.id("username")).sendKeys("incorrectUsername");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");

        driver.findElement(By.cssSelector("button[type=\"submit\"].radius")).click();

        String expectedMessage = "Your username is invalid!\n" + "×";
        WebElement notificationLoggedIn = driver.findElement(By.id("flash"));
        String message = notificationLoggedIn.getText();
        Assert.assertTrue(notificationLoggedIn.isDisplayed());
        Assert.assertEquals(expectedMessage, message);
    }

    @Test
    public void incorrectPassword() {
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("IncorrectPassword");

        driver.findElement(By.cssSelector("button[type=\"submit\"].radius")).click();

        String expectedMessage = "Your password is invalid!\n" + "×";
        WebElement notificationLoggedIn = driver.findElement(By.id("flash"));
        String message = notificationLoggedIn.getText();
        Assert.assertTrue(notificationLoggedIn.isDisplayed());
        Assert.assertEquals(expectedMessage, message);
    }
}
