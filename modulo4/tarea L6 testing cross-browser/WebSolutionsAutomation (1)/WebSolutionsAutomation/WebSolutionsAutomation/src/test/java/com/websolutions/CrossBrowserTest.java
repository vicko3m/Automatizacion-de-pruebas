package com.websolutions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class CrossBrowserTest {
    @Parameters("browser")
    @Test
    public void testCrossBrowser(String browser) {
        WebDriver driver;
        
        // Configura el navegador según el parámetro
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless"); // Modo headless
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless"); // Modo headless
            driver = new FirefoxDriver(options);
        } else {
            throw new IllegalArgumentException("Navegador no soportado: " + browser);
        }

        try {
            // Ejemplo: Verificar carga de página
            driver.get("https://www.google.com");
            System.out.println("Título en " + browser + ": " + driver.getTitle());
            assert driver.getTitle().contains("Google") : "La página no se cargó correctamente";
        } finally {
            driver.quit();
        }
    }
}