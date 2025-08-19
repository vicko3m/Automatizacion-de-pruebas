import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase de pruebas para el sistema de login y navegación de DemoBlaze
 * Implementa pruebas automatizadas con Selenium WebDriver y JUnit 5
 */
@DisplayName("Pruebas de Autenticación y Navegación en DemoBlaze")
public class LoginTest {
    
    // Driver para controlar el navegador Firefox
    private WebDriver driver;
    
    // Wait para manejar esperas explícitas
    private WebDriverWait wait;
    
    // Mapa para almacenar variables temporales (no utilizado actualmente)
    private Map<String, Object> vars;

    /**
     * Configuración inicial antes de cada prueba
     * Inicializa el driver de Firefox, configura el tamaño de ventana
     * y establece el tiempo de espera predeterminado
     */
    @BeforeEach
    @DisplayName("Configuración inicial del navegador")
    public void setUp() {
        // Inicializar el driver de Firefox
        driver = new FirefoxDriver();
        
        // Configurar tamaño de ventana a 1024x768 px
        driver.manage().window().setSize(new Dimension(1024, 768));
        
        // Configurar espera explícita con tiempo máximo de 15 segundos
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        
        // Inicializar mapa de variables
        vars = new HashMap<>();
    }

    /**
     * Limpieza después de cada prueba
     * Cierra el navegador si está abierto
     */
    @AfterEach
    @DisplayName("Cierre del navegador")
    public void tearDown() {
        if (driver != null) {
            driver.quit();  // Cierra el navegador y finaliza la sesión
        }
    }

    /**
     * Prueba completa de login fallido y navegación
     * Valida el flujo completo desde el login hasta la visualización de productos
     */
    @Test
    @DisplayName("Prueba completa de login fallido y navegación")
    public void testLoginAndNavigation() {
        try {
            // --- PASO 1: Navegación inicial ---
            navigateToHomePage();
            
            // --- PASO 2: Proceso de login fallido ---
            attemptFailedLogin("usuario", "clave_incorrecta");
            
            // --- PASO 3: Navegación a categoría ---
            navigateToLaptopsCategory();
            
            // --- PASO 4: Ver detalle de producto ---
            viewProductDetails("Sony vaio i5");

        } catch (Exception e) {
            // Manejo de errores con mensaje descriptivo
            fail("La prueba falló con excepción: " + e.getMessage());
            throw e;
        }
    }

    // ========== MÉTODOS AUXILIARES ==========

    /**
     * Navega a la página principal y valida que cargó correctamente
     */
    private void navigateToHomePage() {
        // Cargar la URL de DemoBlaze
        driver.get("https://www.demoblaze.com/");
        
        // Validar que el logo es visible (confirmando carga correcta)
        WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nava")));
        assertTrue(logo.isDisplayed(), "La página principal no se cargó correctamente");
    }

    /**
     * Intenta realizar login con credenciales inválidas
     * @param username Nombre de usuario inválido
     * @param password Contraseña inválida
     */
    private void attemptFailedLogin(String username, String password) {
        // Abrir modal de login
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("login2")));
        loginButton.click();
        
        // Validar que el modal es visible
        WebElement loginModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModal")));
        assertTrue(loginModal.isDisplayed(), "El modal de login no se mostró");

        // Ingresar credenciales
        fillLoginForm(username, password);
        
        // Enviar formulario
        submitLoginForm();
        
        // Validar mensaje de error
        validateAlertMessage("Wrong password.");
        
        // Cerrar modal
        closeLoginModal();
    }

    /**
     * Llena el formulario de login
     * @param username Nombre de usuario a ingresar
     * @param password Contraseña a ingresar
     */
    private void fillLoginForm(String username, String password) {
        // Ingresar nombre de usuario
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginusername")));
        usernameField.clear();
        usernameField.sendKeys(username);

        // Ingresar contraseña
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginpassword")));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    /**
     * Envía el formulario de login
     */
    private void submitLoginForm() {
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[contains(text(), 'Log in')]")));
        submitButton.click();
    }

    /**
     * Valida el mensaje de alerta
     * @param expectedMessage Mensaje esperado en la alerta
     */
    private void validateAlertMessage(String expectedMessage) {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        assertEquals(expectedMessage, alertText, "El mensaje de alerta no coincide");
        alert.accept();
    }

    /**
     * Cierra el modal de login
     */
    private void closeLoginModal() {
        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@id='logInModal']//button[text()='Close']")));
        closeButton.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("logInModal")));
    }

    /**
     * Navega a la categoría Laptops
     */
    private void navigateToLaptopsCategory() {
        WebElement laptopsLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Laptops")));
        laptopsLink.click();
        
        // Validar que al menos un producto es visible
        validateProductVisible("Sony vaio i5");
    }

    /**
     * Valida que un producto específico es visible
     * @param productName Nombre del producto a validar
     */
    private void validateProductVisible(String productName) {
        WebElement product = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//a[contains(text(), '" + productName + "')]")));
        assertTrue(product.isDisplayed(), "El producto no está visible");
    }

    /**
     * Visualiza los detalles de un producto
     * @param productName Nombre del producto a visualizar
     */
    private void viewProductDetails(String productName) {
        // Seleccionar producto
        WebElement product = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//a[contains(text(), '" + productName + "')]")));
        product.click();
        
        // Validar detalles del producto
        validateProductDetails(productName);
    }

    /**
     * Valida los detalles del producto
     * @param expectedName Nombre esperado del producto
     */
    private void validateProductDetails(String expectedName) {
        // Validar nombre del producto
        WebElement productName = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h2[@class='name']")));
        assertEquals(expectedName, productName.getText(), "El nombre del producto no coincide");

        // Validar que el precio es visible
        WebElement productPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h3[@class='price-container']")));
        assertTrue(productPrice.isDisplayed(), "El precio no está visible");
    }
}