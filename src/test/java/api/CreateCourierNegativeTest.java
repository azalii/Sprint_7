package api;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateCourierNegativeTest {
    private final String requestBody;

    public CreateCourierNegativeTest(String requestBody) {
        this.requestBody = requestBody;
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
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
    public void withoutRequiredFields() {
        Allure.step("Создание курьера без обязательных полей");

        Response response = given()
                .log().all()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/courier");

        response
                .then()
                .log().all()
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }
}
