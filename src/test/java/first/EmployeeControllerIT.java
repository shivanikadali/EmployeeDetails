package first;

import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import jakarta.transaction.Transactional;

@Transactional
@QuarkusTest
public class EmployeeControllerIT {

    private static final int port = 8080;
    private static final String HOST = "localhost";
    private static WireMockServer server = new WireMockServer(port);

    @BeforeAll
    public static void setUp() {
        server.start();
        ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();
        mockResponse.withStatus(200);

        WireMock.configureFor(HOST, server.port());
        WireMock.stubFor(WireMock.get("/examplee")
                .willReturn(mockResponse));
    }

    @AfterAll
    public static void tearDown() {
        if (null != server && server.isRunning()) {
            server.shutdownServer();
        }
    }

    @Test
    public void testCreateEmployeeEndpoint() {
        RestAssured.given()
                .contentType("application/json")
                .body("{\"employeeId\": 11, \"employeeName\": \"John Doe\", \"jobTitle\": \"Engineer\", \"contactNumber\": \"1234567890\", \"salary\": 5000}")
                .when()
                .post("/employee")
                .then()
                .assertThat()
                .statusCode(200)
                .body("employeeId", equalTo(11),
                        "employeeName", equalTo("John Doe"),
                        "jobTitle", equalTo("Engineer"),
                        "contactNumber", equalTo("1234567890"),
                        "salary", equalTo(5000));
    }

    @Test
    public void testGetAllEmployeesEndpoint() {
        RestAssured.given()
                .when()
                .get("/employee")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetEmployeeByIdEndpoint() {
        RestAssured.given()
                .pathParam("id", 2)
                .when()
                .get("/employee/{id}")
                .then()
                .statusCode(200)
                .body("employeeId", equalTo(2));
    }

    @Test
    public void testUpdateEmployeeEndpoint() {
        RestAssured.given()
                .pathParam("id", 6)
                .queryParam("salary", 60000)
                .when()
                .put("/employee/{id}")
                .then()
                .statusCode(200)
                .body("salary", equalTo(60000));
    }

    @Test
    public void testDeleteEmployeeEndpoint() {
        RestAssured.given()
                .pathParam("id", 1)
                .when()
                .delete("/employee/{id}")
                .then()
                .statusCode(204);
    }
}