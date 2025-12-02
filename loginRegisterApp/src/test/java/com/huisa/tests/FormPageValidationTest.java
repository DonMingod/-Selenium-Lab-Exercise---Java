package com.huisa.tests;

import com.huisa.pages.FormPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class FormPageValidationTest {

    private WebDriver driver;

    private FormPage formPage;

    @BeforeAll
    void setup() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        formPage = new FormPage(driver);
    }

    @Test
    void testFormularioCompleto(){
        formPage.abrir();
        formPage.llenarFormulario("John",
                "012-3456789",
                "cash on delivery",
                "2024-04-20");

        formPage.enviar();

        String mensaje = formPage.obtenerMensajeExito();
        assertTrue(mensaje.contains("Thank you for validating your ticket"));
    }

    @Test
    void testContactFormBad() {
        formPage.abrir();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            formPage.llenarFormulario("Carlos", "343-3232", "cash on delivery", "2024-04-20");
        });
        assertEquals("Error, el contacto no cumple debe ser telefono", exception.getMessage());
    }

    @Test
    void testDateFormBad() {
        formPage.abrir();
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            formPage.llenarFormulario("Carlos", "012-3456789", "cash on delivery", "20/04/0000");
        });
        assertEquals("Formato de fecha incorrecto", exception.getMessage());
    }


    @AfterEach
    void teardown(){
        if(driver != null){
            driver.quit();
        }
    }

}