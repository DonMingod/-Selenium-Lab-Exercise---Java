package com.huisa.tests;

import com.huisa.pages.FormPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FormPageValidationTest {

    private WebDriver driver;
    private FormPage formPage;

    @BeforeAll
    void setupClass(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        formPage = new FormPage(driver);
    }

    @Test
    void testFormularioCompleto(){
        formPage.abrir();
        formPage.llenarFormulario(
                "Carlos",
                "9011471666",
                "cash on delivery",
                "20/04/2019"
        );
        formPage.enviar();

        String mensaje = formPage.obtenerMensajeExito();
        Assertions.assertTrue(mensaje.contains("Thank you for your order!"));
    }

    @Test
    void testFormularioContactoIncorrecto(){
        formPage.abrir();

        RuntimeException exception = assertThrows(RuntimeException.class,() ->{
            formPage.llenarFormulario(
                    "Carlos",
                    "901147166",
                    "cash on delivery",
                    "20/04/2019"
            );
        });
        assertEquals("Error, el contacto debe ser un telefono valido", exception.getMessage());
    }

    @Test
    void testFormularioDiaIncorrecto(){
        formPage.abrir();

        RuntimeException exception = assertThrows(RuntimeException.class,() ->{
            formPage.llenarFormulario(
                    "Carlos",
                    "9011471666",
                    "cash on delivery",
                    "32/04/2019"
            );
        });
        assertEquals("Error, la fecha ingresada no es valida", exception.getMessage());
    }

    @AfterEach
    void teardown(){
        if(driver != null){
            driver.quit();
        }
    }









}
