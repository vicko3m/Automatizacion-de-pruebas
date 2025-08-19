package com.feature.stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class StepsLogin {

    // Asegúrate de tener una clase "Hooks" con el WebDriver estático "driver"
    private WebDriver driver = Hooks.driver;
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @Given("que estoy en la página de inicio de sesión")
    public void que_estoy_en_la_pagina_de_inicio_de_sesion() {
        driver.get("https://mi-carnet-infantil.web.app/login");
    }

@When("ingreso el RUT {string} y la contraseña {string}")
public void ingreso_el_RUT_y_la_contraseña(String rut, String password) {
    WebElement rutField = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.xpath("//input[@type='text' and contains(@placeholder, 'Ingresa tu RUN')]")));
    WebElement passwordField = driver.findElement(
        By.xpath("//input[@type='password' and contains(@placeholder, 'ClaveÚnica')]"));

    rutField.clear();
    rutField.sendKeys(rut);
    passwordField.clear();
    passwordField.sendKeys(password);
}


    @When("hago clic en el botón de ingresar")
    public void hago_clic_en_el_boton_de_ingresar() {
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button[type='submit']")));
        loginButton.click();
    }

   @Then("debería estar en la página principal")
public void deberia_estar_en_la_pagina_principal() {
    // Espera a que la URL cambie a una que indique inicio exitoso
    wait.until(ExpectedConditions.urlContains("/dashboard")); // Ajusta según URL real
    String currentUrl = driver.getCurrentUrl();
    assertTrue(currentUrl.contains("dashboard") || currentUrl.contains("inicio"), 
        "La URL no indica que estamos en la página principal: " + currentUrl);
}

@Then("debería quedar la misma url")
public void debería_quedar_la_misma_url() {
    String urlActual = driver.getCurrentUrl();
    assertEquals("https://mi-carnet-infantil.web.app/login", urlActual);
}

}
