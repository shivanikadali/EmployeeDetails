package first;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
public class EmployeeControllerIT {

        private static final int port = 8089;
        private static final String HOST = "localhost";
        private static WireMockServer server = new WireMockServer(port);

        @BeforeAll
        public static void setUp() {
                // starting wiremock server
                server.start();
                WireMock.configureFor(HOST, port);

                // Stub GET endpoints
                WireMock.stubFor(WireMock.get("/employee")
                                .willReturn(WireMock.aResponse().withStatus(200)));
                WireMock.stubFor(WireMock.get("/employee/1")
                                .willReturn(WireMock.aResponse().withStatus(200)));

                // Stub PUT endpoint
                WireMock.stubFor(WireMock.put("/employee/1")
                                .willReturn(WireMock.aResponse().withStatus(200)));

                // Stub DELETE endpoint
                WireMock.stubFor(WireMock.delete("/employee/1")
                                .willReturn(WireMock.aResponse().withStatus(204)));

                // Stub POST endpoint
                WireMock.stubFor(WireMock.post("/employee")
                                .willReturn(WireMock.aResponse().withStatus(200)));
        }

        @AfterAll
        public static void tearDown() {
                // stopping wiremock server
                if (server.isRunning()) {
                        server.stop();
                }
        }

        @Test
        public void testGetAllEmployeesEndpoint() {
                RestAssured.given()
                                .when()
                                // sending http request using rest assured
                                .get("http://localhost:8089/employee")
                                .then()
                                // asserting the responce status code
                                .statusCode(200);
        }

        @Test
        public void testGetEmployeeByIdEndpoint() {
                RestAssured.given()
                                .when()
                                .get("http://localhost:8089/employee/1")
                                .then()
                                .statusCode(anyOf(is(200)));

        }

        @Test
        public void testDeleteEmployeeEndpoint() {
                RestAssured.given()
                                .when()
                                .delete("http://localhost:8089/employee/1")
                                .then()
                                .statusCode(anyOf(is(204),
                                                is(404)));
        }

        @Test
        public void testUpdateEmployeeEndpoint() {
                RestAssured.given()
                                .queryParam("salary", 2000)
                                .when()
                                .put("http://localhost:8089/employee/1")
                                .then()
                                // asserting responce code should be 200 or 404
                                .statusCode(anyOf(is(200),
                                                is(404)));
        }

        @Test
        public void testCreateEmployeeEndpoint() {
                RestAssured.given()
                                // giving request body as json string using body method
                                .contentType("application/json")
                                .body("{\"employeeId\": 11, \"employeeName\": \"John Doe\", \"jobTitle\": \"Engineer\", \"contactNumber\": \"1234567890\", \"salary\": 5000}")
                                .when()
                                // sending post request to the below url
                                .post("http://localhost:8089/employee")
                                .then()
                                .statusCode(200);
        }
}
