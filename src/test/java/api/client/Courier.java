package api.client;

import io.restassured.response.ValidatableResponse;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class Courier {
    private static final String BASE_URL = "/api/v1/courier";

    public ValidatableResponse create(String login, String password) {
        JSONObject jo = new JSONObject();

        if (login != null) {
            jo.put("login", login);
        }

        if (password != null) {
            jo.put("password", password);
        }

        return given()
                .log().all()
                .header("Content-type", "application/json")
                .body(jo.toString())
                .when()
                .post(BASE_URL)
                .then()
                .log().all();
    }

    public ValidatableResponse login(String login, String password) {
        JSONObject jo = new JSONObject();

        if (login != null) {
            jo.put("login", login);
        }

        if (password != null) {
            jo.put("password", password);
        }

        return given()
                .log().all()
                .header("Content-type", "application/json")
                .body(jo.toString())
                .when()
                .post(BASE_URL + "/login")
                .then()
                .log().all();
    }

    public ValidatableResponse delete(String courierId) {
        return given()
                .log().all()
                .header("Content-type", "application/json")
                .when()
                .delete(BASE_URL + "/" + courierId)
                .then()
                .log().all();
    }
}
