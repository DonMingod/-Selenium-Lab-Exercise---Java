package com.huisa.tests;


import com.huisa.pages.RegisterPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class RegisterPageTest {

      private WebDriver driver;
      private WebDriverWait wait;
      private RegisterPage registerPage;

      @BeforeEach
      void setup(){
          WebDriverManager.chromedriver().setup();
          driver = new ChromeDriver();
          driver.manage().window().maximize();
          registerPage = new RegisterPage(driver);
          wait = new WebDriverWait(driver, Duration.ofSeconds(10));
      }

      @Test
     void registerSuccess(){
          registerPage.open();
          registerPage.register("auronplay2322", "password123", "password123");

          String mensaje = registerPage.getMessage();
          Assertions.assertTrue(mensaje.contains("Successfully registered, you can log in now."));
      }

      @Test
      void registerWithInvalidUsername(){
          registerPage.open();
          registerPage.register("ar","test", "test");

          String mensaje = registerPage.getMessage();
            Assertions.assertTrue(mensaje.contains("Username must be at least 3 characters long."));
      }

      @Test
      void registerWithDifferentPasswords(){
          registerPage.open();
          registerPage.register("johan", "123456", "234567");

          String mensaje = registerPage.getMessage();
          Assertions.assertTrue(mensaje.contains("Passwords do not match."));
      }

      @Test
      void registerWithShortPassword(){
          registerPage.open();
          registerPage.register("rubios","12","12");

          String mensaje = registerPage.getMessage();
            Assertions.assertTrue(mensaje.contains("Password must be at least 4 characters long."));
      }

      @AfterEach
      void tearDown(){
          if(driver != null){
              driver.quit();
          }
      }

}
