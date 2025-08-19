package com.websolutions;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AppTest {
    @Test
    public void testDemo() {
        // Configura WebDriverManager para Chrome
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Abre una página de ejemplo (puedes cambiarla)
            driver.get("https://www.google.com");
            String title = driver.getTitle();
            System.out.println("Título de la página: " + title);

            // Verificación básica (puedes personalizarla)
            assertTrue(title.contains("Google"), "El título no contiene 'Google'");

        } finally {
            // Cierra el navegador al finalizar
            driver.quit();
        }
    }
}