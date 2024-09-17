package api;

import api.client.Order;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.Matchers.*;

public class GetListOfOrdersTest extends BaseTest {
    private Order client;

    @Before
    public void setUp() {
        client = new Order();
    }

    @Test
    @Description("Successful getting an order list")
    public void getAllOrders() {
        getAll();
    }

    @Step("Get all")
    void getAll() {
        client.getList()
                .assertThat()
                .body("orders", notNullValue())
                .body("orders.size()", not(0))
                .statusCode(200);
    }
}