package com.huisa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class RegisterPage {

    private final WebDriver driver;

    private WebDriverWait wait;
    private final String url = "https://practice.expandtesting.com/register";

    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By confirmPasswordInput = By.id("confirmPassword");
    private final By registerButton= By.xpath("//button[@type='submit']");
    private final By message = By.id("flash");

    public RegisterPage (WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get(url);
    }

    public void register (String username, String confirmPassword, String password){
        WebElement button = driver.findElement(registerButton);
        WebElement usernameInputElement = driver.findElement(usernameInput);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", button);

        wait.until(ExpectedConditions.elementToBeClickable(usernameInputElement)).click();

        try{
            driver.findElement(usernameInput).sendKeys(username);
            Thread.sleep(1000);
            driver.findElement(passwordInput).sendKeys(password);
            Thread.sleep(1000);
            driver.findElement(confirmPasswordInput).sendKeys(confirmPassword);
            Thread.sleep(1000);
            button.click();

        }catch (InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    public String getMessage() {
        return driver.findElement(message).getText();
    }
}
