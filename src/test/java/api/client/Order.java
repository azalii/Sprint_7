package api.client;

import io.restassured.response.ValidatableResponse;
import org.json.JSONObject;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Order {
    private static final String BASE_URL = "/api/v1/orders";

    public ValidatableResponse create(String firstName, String lastName, String address,
                                      String metroStation, String phone, Number rentTime,
                                      String deliveryDate, String comment, List<String> colors) {
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

        return given()
                .log().all()
                .header("Content-type", "application/json")
                .body(jo.toString())
                .when()
                .post(BASE_URL)
                .then()
                .log().all();
    }

    public ValidatableResponse getList() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(BASE_URL)
                .then()
                .log().all();
    }
}
