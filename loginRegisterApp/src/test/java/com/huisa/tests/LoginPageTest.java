package com.huisa.tests;

import com.huisa.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class LoginPageTest {

    private WebDriver driver;

    private LoginPage loginPage;

    private String validUsername = "testuser";
    private String validPassword = "SuperSecretPassword!";

    @BeforeEach
    void setup(){
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
    }

    @Test
    void loginWithInvalidCredentials(){
        loginPage.open();
        loginPage.login("usuario_invaldo", "clave_erronea");

        String mensaje = loginPage.getMessage();
        Assertions.assertTrue(mensaje.contains("your username is invalid!"));
    }

    @Test
    void loginWithValidCredentials(){
        loginPage.open();
        loginPage.login(validUsername, validPassword);

        String mensaje = loginPage.getMessage();
        Assertions.assertTrue(mensaje.contains("You logged into a secure area!"));
    }

    @Test
    void loginWithInvalidPassword(){
        loginPage.open();
        loginPage.login(validUsername, "invalid_password");

        String mensaje = loginPage.getMessage();
        Assertions.assertTrue(mensaje.contains("your password is invalid!"));
    }

    @AfterEach
    void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }
}

