package first;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@QuarkusTest
class EmployeeControllerTest {

    @Inject
    EmployeeController employeeController;

    @InjectMock
    EmployeeRepository employeeRepository;

    private Employee employee;
    private Employee employee1;

    @BeforeEach
    void setUp() {
        this.employee = new Employee();
        this.employee.setEmployeeId(1);
        this.employee.setEmployeeName("janu");
        this.employee.setJobTitle("manager");
        this.employee.setContactNumber("8987675645");
        this.employee.setSalary(4000);

        this.employee1 = new Employee();
        this.employee1.setEmployeeId(2);
        this.employee1.setEmployeeName("thanshi");
        this.employee1.setJobTitle("HR");
        this.employee1.setContactNumber("7858934098");
        this.employee1.setSalary(4000);
    }

    @Test
    void getAllEmployeesTest() {
        // creating list
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        Mockito.when(employeeRepository.listAll()).thenReturn(employees);
        List<Employee> result = employeeController.getAllEmployees();
        Mockito.verify(employeeRepository, Mockito.times(1)).listAll();
        assertEquals(employees, result);
        assertNotNull(result);
    }

    @Test
    void getEmployeeByIdTest() {
        Mockito.when(employeeRepository.findById((long) 1)).thenReturn(employee);
        Employee result = employeeController.getEmployeeById((long) 1);
        Mockito.verify(employeeRepository, Mockito.times(1)).findById((long) 1);
        assertEquals(employee, result);
        assertNotNull(result);

        // when the employee not found
        Mockito.when(employeeRepository.findById(anyLong())).thenReturn(null);
        try {
            employeeController.deleteEmployeeById(anyLong());
        } catch (NotFoundException e) {

        }
    }

    @Test
    void updateEmployeeTest() {
        employee.setSalary(1000);
        Mockito.when(employeeRepository.findById((long) 1)).thenReturn(employee);
        Employee updatedEmployee = employeeController.updateEmployee(1l, 3000);
        Mockito.verify(employeeRepository, Mockito.times(1)).findById((long) 1);
        assertEquals(3000, updatedEmployee.getSalary());

        // when the employee not found
        Mockito.when(employeeRepository.findById(anyLong())).thenReturn(null);
        try {
            employeeController.deleteEmployeeById(anyLong());
        } catch (NotFoundException e) {

        }
    }

    @Test
    void deleteEmployeeTest() {
        Mockito.when(employeeRepository.findById((long) 1)).thenReturn(employee);
        employeeController.deleteEmployeeById(1L);
        assertEquals(1, employee.getEmployeeId());
        // when the employee not found
        Mockito.when(employeeRepository.findById(anyLong())).thenReturn(null);
        try {
            employeeController.deleteEmployeeById(anyLong());
        } catch (NotFoundException e) {

        }
    }

    // @Test
    // void createEmployeeTest() {
    // List<Employee> employees = new ArrayList<>();
    // employees.add(employee);
    // Mockito.when(employeeRepository.persist(employee)).thenReturn(employee);
    // employeeController.createEmployee(employee);
    // }
}