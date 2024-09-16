package api;

import api.client.Courier;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.Step;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateCourierPositiveTest extends BaseTest {
    private Courier client;
    private final String login = "azali2";
    private final String password = "123";

    @Before
    public void setUp() {
        client = new Courier();
    }

    @Test
    @Description("Successful creation of a courier")
    public void createCourier() {
        create();
    }

    @After
    public void tearDown() {
        String courierId = loginForDelete();
        delete(courierId);
    }

    @Step
    void create() {
        client.create(login, password)
                .assertThat()
                .body("ok", equalTo(true))
                .statusCode(201);
    }

    @Step("Login for delete")
    String loginForDelete() {
        return client.login(login, password)
                .assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(200)
                .extract().path("id").toString();
    }

    @Step("Delete")
    void delete(String id) {
        client.delete(id)
                .assertThat()
                .body("ok", equalTo(true))
                .and()
                .statusCode(200);
    }
}