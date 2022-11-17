package de.workshops.bookshelf;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookRestControllerRestAssuredTest {

    @LocalServerPort
    int port;

    @Value("${server.port}")
    int serverPort;

    @Test
    void shouldGetBookByIsbn() {
        RestAssured.given()
                .log().all()
                .auth().basic("dbUser", "password")
                .when()
                .get("http://localhost:" + port + "/book/978-3826655487")
                .then()
                .statusCode(200);
    }
}