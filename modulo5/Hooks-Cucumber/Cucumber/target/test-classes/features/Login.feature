Feature: Funcionalidad de inicio de sesión

  @smoke
  Scenario: Login exitoso con credenciales válidas
    Given que estoy en la página de inicio de sesión
    When ingreso el RUT "12.345.678-9" y la contraseña "user1234"
    And hago clic en el botón de ingresar
    Then debería estar en la página principal

  @negative
  Scenario: Login fallido con credenciales inválidas
    Given que estoy en la página de inicio de sesión
    When ingreso el RUT "11.111.111-1" y la contraseña "clave_incorrecta"
    And hago clic en el botón de ingresar
    Then debería quedar la misma url
