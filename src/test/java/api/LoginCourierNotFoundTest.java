package api;

import api.client.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class LoginCourierNotFoundTest extends BaseTest {
    private Courier client;
    private final String login = "oioiooioioiopppppppppppp";
    private final String password = "ggggggggg";

    @Before
    public void setUp() {
        client = new Courier();
    }

    @Test
    @Description("Successful courier login")
    public void notFound() {
        login();
    }

    @Step("Login")
    void login () {
        client.login(login, password)
                .assertThat()
                .body("message", equalTo("Учетная запись не найдена"))
                .statusCode(404);
    }
}
