package com.huisa.pages;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;


public class FormPage {

    private WebDriver driver;
    private WebDriverWait wait;
    String regex = "^[0-9]{3}-[0-9]{7}$";
    Pattern pattern = Pattern.compile(regex);

    @FindBy(id = "validationCustom01")
    private WebElement firstName;

    @FindBy(xpath = "//input[@type = 'tel']")
    private WebElement contactNumber;

    @FindBy(id = "validationCustom04")
    private WebElement paymentMethod;

    @FindBy(xpath = "//input[@type='date']")
    private WebElement datepicker;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitButton;

    public FormPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void abrir() {
        driver.get("https://practice.expandtesting.com/form-validation");
    }

    public void llenarFormulario(String nombre, String contacto, String metodo_pago, String dia){
        try{
            LocalDate.parse(dia, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }catch (DateTimeParseException e){
            System.out.println("Error, formato de fecha incorrecto. Use 'yyyy-MM-dd'.");
            throw new RuntimeException("Formato de fecha incorrecto");
        }
        wait.until(ExpectedConditions.visibilityOf(firstName)).sendKeys(nombre);
        if (pattern.matcher(contacto).matches()){
            contactNumber.sendKeys(contacto);
        }else {
            throw new RuntimeException("Error, el contacto no cumple debe ser telefono");
        }
        Select select = new Select(paymentMethod);
        select.selectByVisibleText(metodo_pago);
        datepicker.click();
        datepicker.sendKeys(dia);
    }

    public void enviar(){
        wait.until(ExpectedConditions.elementToBeClickable(submitButton)).click();
    }

    public String obtenerMensajeExito(){
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-info")));
        return alert.getText();
    }





}
