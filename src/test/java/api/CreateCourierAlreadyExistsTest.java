package api;

import api.client.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateCourierAlreadyExistsTest extends BaseTest {
    private Courier client;
    private final String login = "qweqwe1231232222";
    private final String password = "qweqwe";

    @Before
    public void setUp() {
        client = new Courier();
    }

    @Test
    @Description("Verification that creating a courier with an already existing login is not possible")
    public void alreadyExistTest() {
        create();
        createAlreadyExist();
    }

    @After
    public void tearDown() {
        String courierId = loginForDelete();
        delete(courierId);
    }

    @Step("Successful create")
    void create() {
        client.create(login, password)
                .assertThat()
                .body("ok", equalTo(true))
                .statusCode(201);
    }

    @Step("Create already exist")
    void createAlreadyExist() {
        client.create(login, password)
                .assertThat()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .statusCode(409);
    }

    @Step("Login for delete")
    String loginForDelete() {
        return client.login(login, password)
                .assertThat()
                .body("id", notNullValue())
                .statusCode(200)
                .extract().path("id").toString();
    }

    @Step("Delete")
    void delete(String id) {
        client.delete(id)
                .assertThat()
                .body("ok", equalTo(true))
                .statusCode(200);
    }
}
