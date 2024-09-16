package api;

import io.restassured.RestAssured;

public class BaseTest {
    public BaseTest() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }
}
