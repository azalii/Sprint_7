package api;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetListOfOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void test() {
        given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders").then()
                .log().all()
                .assertThat()
                .body("orders", notNullValue())
                .body("orders.size()", not(0))
                .statusCode(200);
    }
}