package api;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class LoginCourierNegativeTest {
    private final String requestBody;
    private final String login = "oioiooioioio";
    private final String password = "ggggggggg";

    public LoginCourierNegativeTest(String requestBody) {
        this.requestBody = requestBody;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {"{\"login\":\"123\"}"},
                {"{\"password\":\"123\"}"},
                {"{}"},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void test () {
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

        Allure.step("Попытка логина без обязательных полей");

        given()
                .log().all()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/courier/login")
                .then()
                .log().all()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
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
