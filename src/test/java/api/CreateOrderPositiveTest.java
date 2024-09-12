package api;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderPositiveTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Number rentTime;
    private final String deliveryDate;
    private final String comment;
    private final List<String> colors;

    public CreateOrderPositiveTest(String firstName, String lastName, String address, String metroStation, String phone,
                                   Number rentTime, String deliveryDate, String comment, List<String> colors) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+78003553535", 5, "2020-06-06", "Saske, come back", Arrays.asList("BLACK", "GREY")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+78003553535", 5, "2020-06-06", "Saske, come back", Arrays.asList("BLACK")},
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+78003553535", 5, "2020-06-06", "Saske, come back", Arrays.asList()},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void positive() {
        Allure.step("Успешное создание ордера");
        JSONObject jo = new JSONObject();
        jo.put("firstName", firstName);
        jo.put("lastName", lastName);
        jo.put("address", address);
        jo.put("metroStation", metroStation);
        jo.put("phone", phone);
        jo.put("rentTime", rentTime);
        jo.put("deliveryDate", deliveryDate);
        jo.put("comment", comment);
        jo.put("rentTime", rentTime);
        jo.put("color", colors);

        given()
                .log().all()
                .header("Content-type", "application/json")
                .body(jo.toString())
                .when()
                .post("/api/v1/orders")
                .then()
                .log().all()
                .assertThat()
                .body("track", notNullValue())
                .statusCode(201);
    }
}