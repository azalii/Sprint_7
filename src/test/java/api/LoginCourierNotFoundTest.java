package api;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierNotFoundTest {
    private final String login = "oioiooioioio";
    private final String password = "ggggggggg";

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

                .and()
                .body(jo.toString())
                .when()
                .post("/api/v1/courier")
                .then()
                .log().all()
                .assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(201);

        Allure.step("Попытка логина без обязательных полей");

        JSONObject loginJo = new JSONObject();
        loginJo.put("login", login + "pppppppppppp");
        loginJo.put("password", password);

        given()
                .log().all()
                .header("Content-type", "application/json")
                .body(loginJo.toString())
                .when()
                .post("/api/v1/courier/login")
                .then()
                .log().all()
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .statusCode(404);
    }

    @After
    public void tearDown() {
        JSONObject jo = new JSONObject();
        jo.put("login", login);
        jo.put("password", password);

        String courierId = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(jo.toString())
                .when()
                .post("/api/v1/courier/login")
                .then()
                .log().all()
                .assertThat()
                .body("id", notNullValue())
                .statusCode(200)
                .extract().path("id").toString();

        given()
                .log().all()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/" + courierId).then()
                .log().all()
                .assertThat()
                .body("ok", equalTo(true))
                .statusCode(200);
    }
}
