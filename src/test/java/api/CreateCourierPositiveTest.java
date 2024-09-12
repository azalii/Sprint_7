package api;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateCourierPositiveTest {
    private final String login;
    private final String password;
    private final String firstName;

    public CreateCourierPositiveTest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"azali2", "123", "saske"},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void positive() {
        Allure.step("Успешное создание курьера");

        JSONObject jo = new JSONObject();
        jo.put("login", login);
        jo.put("password", password);
        jo.put("firstName", firstName);

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
    }

    @After
    public void tearDown() {
        String courierId = login(login, password);
        delete(courierId);
    }

    @Step("Login")
    String login(String login, String password) {
        JSONObject jo = new JSONObject();
        jo.put("login", login);
        jo.put("password", password);

        return given()
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
    }

    @Step("Delete")
    void delete(String id) {
        given()
                .log().all()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/" + id).then()
                .log().all()
                .assertThat()
                .body("ok", equalTo(true))
                .statusCode(200);
    }
}