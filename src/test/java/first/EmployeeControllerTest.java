package first;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

@QuarkusTest
class EmployeeResourceTest {

    @Inject
    EmployeeController employeeController;

    @InjectMock 
    EmployeeRepository employeeRepository;

    private Employee employee;
    
    @BeforeEach
     void setup(){
        Employee employee=new Employee();
        employee.setEmployeeId(1);
        employee.setEmployeeName("janu");
        employee.setJobTitle("manager");
        employee.setContactNumber("8987675645");
        employee.setSalary(4000);

     }

    @Test
    void getAllEmployeesTest()
    {
        List<Employee> employees=new ArrayList<>();
        employees.add(employee);
        Mockito.when(employeeRepository.listAll()).thenReturn(employees);
        List<Employee> result = employeeController.getAllEmployees();
        Mockito.verify(employeeRepository, Mockito.times(1)).listAll();
        assertEquals(employees,result);
        assertNotNull(result);
    }

    @Test
    void getEmployeeByIdTest()
    {
        Employee employee1=new Employee();
        employee1.setEmployeeId(2);
        employee1.setEmployeeName("thanshi");
        employee1.setJobTitle("HR");
        employee1.setContactNumber("7858934098");
        employee1.setSalary(4000);

        List<Employee> employees=new ArrayList<>();
        employees.add(employee);
        employees.add(employee1);

        Mockito.when(employeeRepository.findById((long) 2)).thenReturn(employee1);
        Employee result = employeeController.getEmployeeById((long) 2);
        Mockito.verify(employeeRepository, Mockito.times(1)).findById((long) 2);
       // assertEquals(employee,result);
       // assertNotNull(result);
    }

    @Test
    void updateEmployeeTest()
    {
        List<Employee> employees=new ArrayList<>();
        employees.add(employee);
        Mockito.when(employeeRepository.findById((long) 1)).thenReturn(employee);
        Employee result = employeeController.getEmployeeById((long) 1);
        Mockito.verify(employeeRepository, Mockito.times(1)).findById();
      
    }



}
