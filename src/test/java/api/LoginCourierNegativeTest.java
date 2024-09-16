package api;

import api.client.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class LoginCourierNegativeTest extends BaseTest {
    private Courier client;
    private final String login;
    private final String password;
    private final String originalLogin = "wwwwwwwweeeeeeee";
    private final String originalPassword = "wwwwwwwweeeeeeee";

    public LoginCourierNegativeTest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][]{
                {"123", null},
                {null, "123"},
                {null, null},
        };
    }

    @Before
    public void setUp() {
        client = new Courier();
    }

    @Test
    @Description("Negative courier login")
    public void withoutRequiredFields() {
        create();
        login();
    }

    @After
    public void tearDown() {
        String courierId = loginForDelete();
        delete(courierId);
    }

    @Step("Create")
    void create() {
        client.create(originalLogin, originalPassword)
                .assertThat()
                .body("ok", equalTo(true))
                .statusCode(201);
    }

    @Step("Login")
    void login() {
        client.login(login, password)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для входа"))
                .statusCode(400);
    }

    @Step("Login for delete")
    String loginForDelete() {
        return client.login(originalLogin, originalPassword)
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
