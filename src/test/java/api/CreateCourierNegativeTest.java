package api;

import api.client.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CreateCourierNegativeTest extends BaseTest {
    private Courier client;
    private final String login;
    private final String password;

    public CreateCourierNegativeTest(String login, String password) {
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
    @Description("Attempt to create a courier without required fields")
    public void withoutRequiredFields() {
        create();
    }

    @Step("Create")
    void create() {
        client.create(login, password)
                .assertThat()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .statusCode(400);
    }
}
