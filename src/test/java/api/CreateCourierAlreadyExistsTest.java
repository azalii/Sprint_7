package api;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateCourierAlreadyExistsTest {
    private final String login = "qweqwe1231232222";
    private final String password = "qweqwe";

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void test() {
        Allure.step("Успешное создание курьера");

        JSONObject jo = new JSONObject();
        jo.put("login", login);
        jo.put("password", password);

         given()
                .log().all()
                .header("Content-type", "application/json")
                .body(jo.toString())
                .when()
                .post("/api/v1/courier")
                .then()
                .log().all()
                .assertThat()
                .body("ok", equalTo(true))
                .statusCode(201);

        Allure.step("Создание курьера с таким же логином");

        given()
                .log().all()
                .header("Content-type", "application/json")
                .body(jo.toString())
                .when()
                .post("/api/v1/courier")
                .then()
                .log().all()
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .statusCode(409);
    }

    @After
    public void tearDown() {
        String courierId = login();
        delete(courierId);
    }

    @Step("Login")
    String login() {
        JSONObject jo = new JSONObject();
        jo.put("login", login);
        jo.put("password", password);

        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(jo.toString())
                .when()
                .post("/api/v1/courier/login");

        return response
                .then()
                .log().all()
                .assertThat()
                .body("id", notNullValue())
                .statusCode(200)
                .extract().path("id").toString();
    }

    @Step("Delete")
    void delete(String id) {
        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/" + id);

        response
                .then()
                .log().all()
                .assertThat()
                .body("ok", equalTo(true))
                .statusCode(200);
    }
}
