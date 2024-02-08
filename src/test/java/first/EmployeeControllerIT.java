package first;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class EmployeeControllerIT {

    @Test
    public void testCreateEmployeeEndpoint() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"employeeId\": 4, \"employeeName\": \"John Doe\", \"jobTitle\": \"Engineer\", \"contactNumber\": \"1234567890\", \"salary\": 5000}")
                .when()
                .post("/employee")
                .then()
                .statusCode(200)
                .body("employeeId", equalTo(4),
                        "employeeName", equalTo("John Doe"),
                        "jobTitle", equalTo("Engineer"),
                        "contactNumber", equalTo("1234567890"),
                        "salary", equalTo(5000));
    }

    @Test
    public void testGetAllEmployeesEndpoint() {
        given()
                .when()
                .get("/employee")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetEmployeeByIdEndpoint() {
        given()
                .pathParam("id", 2)
                .when()
                .get("/employee/{id}")
                .then()
                .statusCode(200)
                .body("employeeId", equalTo(2));
    }

    @Test
    public void testUpdateEmployeeEndpoint() {
        given()
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
        given()
                .pathParam("id", 4)
                .when()
                .delete("/employee/{id}")
                .then()
                .statusCode(204);
    }
}