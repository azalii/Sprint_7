package api;

import api.client.Order;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderPositiveTest extends BaseTest {
    private Order client;
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
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+78003553535", 5, "2020-06-06", "Saske, come back", Arrays.asList("GREY")},
        };
    }

    @Before
    public void setUp() {
        client = new Order();
    }

    @Test
    @Description("Successful creating an order")
    public void successfulCreate() {
        create();
    }

    @Step("Create")
    void create() {
        client.create(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, colors)
                .assertThat()
                .body("track", notNullValue())
                .statusCode(201);
    }
}