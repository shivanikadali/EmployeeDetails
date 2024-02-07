package first;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
@jakarta.transaction.Transactional
public class EmployeeControllerIT extends EmployeeControllerTest {

    @InjectMock
    EmployeeController employeeController;

    @InjectMock
    EmployeeRepository employeeRepository;

    @Test
    void getAllEmployeesTest() {
        List<Employee> employees = new ArrayList<>();
        employees.add(createEmployee(1, "pooja", "manager", "8987675645", 4000));
        Mockito.when(employeeRepository.listAll()).thenReturn(employees);

        List<Employee> result = employeeController.getAllEmployees();

        assertEquals(employees.size(), result.size());
        assertNotNull(result.get(0));
    }

    @Test
    void getEmployeeByIdTest() {
        Employee employee = createEmployee(1, "janu", "manager", "8987675645", 4000);
        Mockito.when(employeeRepository.findById(1L)).thenReturn(employee);

        Employee result = employeeController.getEmployeeById(1L);

        assertNotNull(result);
        assertEquals(employee.getEmployeeId(), result.getEmployeeId());
    }

    private Employee createEmployee(int id, String name, String jobTitle, String contactNumber, int salary) {
        Employee employee = new Employee();
        employee.setEmployeeId(id);
        employee.setEmployeeName(name);
        employee.setJobTitle(jobTitle);
        employee.setContactNumber(contactNumber);
        employee.setSalary(salary);
        return employee;
    }
}
