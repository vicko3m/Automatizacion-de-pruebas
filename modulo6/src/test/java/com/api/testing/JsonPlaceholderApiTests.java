package com.api.testing;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas automatizadas para API REST usando JSONPlaceholder
 * API de prueba: https://jsonplaceholder.typicode.com/
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("API REST Tests - JSONPlaceholder")
public class JsonPlaceholderApiTests {
    
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String POSTS_ENDPOINT = "/posts";
    private static final String USERS_ENDPOINT = "/users";
    private static final String COMMENTS_ENDPOINT = "/comments";
    
    @BeforeAll
    static void setUp() {
        // Configuración base para REST Assured
        RestAssured.baseURI = BASE_URL;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
        // Configurar timeout
        RestAssured.config = RestAssured.config()
            .httpClient(io.restassured.config.HttpClientConfig.httpClientConfig()
                .setParam("http.connection.timeout", 5000)
                .setParam("http.socket.timeout", 5000));
        
        System.out.println("=== INICIANDO PRUEBAS DE API REST ===");
        System.out.println("Base URL: " + BASE_URL);
    }
    
    @Test
    @Order(1)
    @DisplayName("GET /posts - Obtener todos los posts")
    void testGetAllPosts() {
        System.out.println("\n--- Test: GET All Posts ---");
        
        given()
            .contentType(ContentType.JSON)
        .when()
            .get(POSTS_ENDPOINT)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", greaterThan(0))
            .body("size()", equalTo(100))
            .body("[0].userId", notNullValue())
            .body("[0].id", notNullValue())
            .body("[0].title", notNullValue())
            .body("[0].body", notNullValue())
            .time(lessThan(3000L));
        
        System.out.println("✓ GET /posts ejecutado correctamente");
    }
    
    @Test
    @Order(2)
    @DisplayName("GET /posts/{id} - Obtener post específico")
    void testGetPostById() {
        System.out.println("\n--- Test: GET Post by ID ---");
        
        int postId = 1;
        
        given()
            .pathParam("id", postId)
        .when()
            .get(POSTS_ENDPOINT + "/{id}")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(postId))
            .body("userId", notNullValue())
            .body("title", not(emptyString()))
            .body("body", not(emptyString()))
            .time(lessThan(2000L));
        
        System.out.println("✓ GET /posts/" + postId + " ejecutado correctamente");
    }
    
    @Test
    @Order(3)
    @DisplayName("GET /posts/{id} - Post no existente (404)")
    void testGetNonExistentPost() {
        System.out.println("\n--- Test: GET Non-existent Post ---");
        
        int nonExistentId = 999;
        
        given()
            .pathParam("id", nonExistentId)
        .when()
            .get(POSTS_ENDPOINT + "/{id}")
        .then()
            .statusCode(404);
        
        System.out.println("✓ GET /posts/" + nonExistentId + " retornó 404 como esperado");
    }
    
    @Test
    @Order(4)
    @DisplayName("POST /posts - Crear nuevo post")
    void testCreatePost() {
        System.out.println("\n--- Test: POST Create New Post ---");
        
        String newPost = """
            {
                "title": "Título de prueba",
                "body": "Contenido del post de prueba",
                "userId": 1
            }
            """;
        
        Response response = given()
            .contentType(ContentType.JSON)
            .body(newPost)
        .when()
            .post(POSTS_ENDPOINT)
        .then()
            .statusCode(201)
            .contentType(ContentType.JSON)
            .body("title", equalTo("Título de prueba"))
            .body("body", equalTo("Contenido del post de prueba"))
            .body("userId", equalTo(1))
            .body("id", notNullValue())
            .extract().response();
        
        int createdId = response.path("id");
        System.out.println("✓ POST /posts ejecutado correctamente. ID creado: " + createdId);
    }
    
    @Test
    @Order(5)
    @DisplayName("PUT /posts/{id} - Actualizar post completo")
    void testUpdatePost() {
        System.out.println("\n--- Test: PUT Update Post ---");
        
        int postId = 1;
        String updatedPost = """
            {
                "id": 1,
                "title": "Título actualizado",
                "body": "Contenido actualizado del post",
                "userId": 1
            }
            """;
        
        given()
            .contentType(ContentType.JSON)
            .pathParam("id", postId)
            .body(updatedPost)
        .when()
            .put(POSTS_ENDPOINT + "/{id}")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(postId))
            .body("title", equalTo("Título actualizado"))
            .body("body", equalTo("Contenido actualizado del post"))
            .body("userId", equalTo(1));
        
        System.out.println("✓ PUT /posts/" + postId + " ejecutado correctamente");
    }
    
    @Test
    @Order(6)
    @DisplayName("PATCH /posts/{id} - Actualizar post parcialmente")
    void testPatchPost() {
        System.out.println("\n--- Test: PATCH Update Post ---");
        
        int postId = 1;
        String patchData = """
            {
                "title": "Título parcialmente actualizado"
            }
            """;
        
        given()
            .contentType(ContentType.JSON)
            .pathParam("id", postId)
            .body(patchData)
        .when()
            .patch(POSTS_ENDPOINT + "/{id}")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("id", equalTo(postId))
            .body("title", equalTo("Título parcialmente actualizado"))
            .body("userId", notNullValue())
            .body("body", notNullValue());
        
        System.out.println("✓ PATCH /posts/" + postId + " ejecutado correctamente");
    }
    
    @Test
    @Order(7)
    @DisplayName("DELETE /posts/{id} - Eliminar post")
    void testDeletePost() {
        System.out.println("\n--- Test: DELETE Post ---");
        
        int postId = 1;
        
        given()
            .pathParam("id", postId)
        .when()
            .delete(POSTS_ENDPOINT + "/{id}")
        .then()
            .statusCode(200);
        
        System.out.println("✓ DELETE /posts/" + postId + " ejecutado correctamente");
    }
    
    @Test
    @Order(8)
    @DisplayName("GET /users - Obtener todos los usuarios")
    void testGetAllUsers() {
        System.out.println("\n--- Test: GET All Users ---");
        
        given()
            .contentType(ContentType.JSON)
        .when()
            .get(USERS_ENDPOINT)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", equalTo(10))
            .body("[0].id", notNullValue())
            .body("[0].name", not(emptyString()))
            .body("[0].email", matchesPattern(".*@.*\\..*"))
            .body("[0].address", notNullValue())
            .body("[0].address.geo", notNullValue())
            .time(lessThan(3000L));
        
        System.out.println("✓ GET /users ejecutado correctamente");
    }
    
    @Test
    @Order(9)
    @DisplayName("GET /comments?postId=1 - Filtrar comentarios por post")
    void testGetCommentsByPostId() {
        System.out.println("\n--- Test: GET Comments by PostID ---");
        
        int postId = 1;
        
        given()
            .queryParam("postId", postId)
        .when()
            .get(COMMENTS_ENDPOINT)
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", greaterThan(0))
            .body("findAll { it.postId == " + postId + " }.size()", 
                  greaterThan(0))
            .body("[0].postId", equalTo(postId))
            .body("[0].email", matchesPattern(".*@.*\\..*"))
            .time(lessThan(2000L));
        
        System.out.println("✓ GET /comments?postId=" + postId + " ejecutado correctamente");
    }
    
    @Test
    @Order(10)
    @DisplayName("Validación de estructura JSON de respuesta")
    void testJsonStructureValidation() {
        System.out.println("\n--- Test: JSON Structure Validation ---");
        
        Response response = given()
            .contentType(ContentType.JSON)
        .when()
            .get(POSTS_ENDPOINT + "/1")
        .then()
            .statusCode(200)
            .extract().response();
        
        // Validar que todos los campos requeridos están presentes
        assertNotNull(response.path("id"), "Campo 'id' debe estar presente");
        assertNotNull(response.path("userId"), "Campo 'userId' debe estar presente");
        assertNotNull(response.path("title"), "Campo 'title' debe estar presente");
        assertNotNull(response.path("body"), "Campo 'body' debe estar presente");
        
        // Validar tipos de datos
        assertTrue(response.path("id") instanceof Integer, "Campo 'id' debe ser entero");
        assertTrue(response.path("userId") instanceof Integer, "Campo 'userId' debe ser entero");
        assertTrue(response.path("title") instanceof String, "Campo 'title' debe ser string");
        assertTrue(response.path("body") instanceof String, "Campo 'body' debe ser string");
        
        System.out.println("✓ Validación de estructura JSON completada");
    }
}