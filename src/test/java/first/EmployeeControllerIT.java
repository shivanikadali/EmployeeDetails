package first;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

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

        WireMock.configureFor(HOST, port);

        WireMock.stubFor(WireMock.get("/employee")
                .willReturn(mockResponse));

        WireMock.stubFor(WireMock.get("/employee/2")
                .willReturn(mockResponse));

        WireMock.stubFor(WireMock.put("/employee/6")
        .willReturn(mockResponse));

        WireMock.stubFor(WireMock.delete("/employee/1")
                .willReturn(mockResponse));
    }

    @AfterAll
    public static void tearDown() {
        if (null != server && server.isRunning()) {
            server.shutdown();
        }
    }

    @Test
    public void testGetAllEmployeesEndpoint() {
        RestAssured.given()
                .when()
                .get("http://localhost:8080/employee")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testGetEmployeeByIdEndpoint() {
        RestAssured.given()
                .contentType("application/json")
                .pathParam("id", 2)
                .when()
                .get("http://localhost:8080/employee/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteEmployeeEndpoint() {
        RestAssured.given()
                .contentType("application/json")
                .pathParam("id", 6)
                .when()
                .delete("http://localhost:8080/employee/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void testUpdateEmployeeEndpoint() {
        RestAssured.given()
                .contentType("application/json")
                .pathParam("id", 4)
                .queryParam("salary", 199)
                .when()
                .put("http://localhost:8080/employee/{id}")
                .then()
                .statusCode(200);
    }
}