package first;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@ApplicationScoped
public class EmployeeController {

    @Inject
    EmployeeRepository employeeRepository;
    
    @POST
    public Employee createEmployee(Employee employee) {
         employeeRepository.persist(employee);
         return employee;
    }

    @PUT
    @Path("{id}")
    public Employee updateEmployee(@PathParam("id") Long id,@QueryParam("salary")int salary) {
        Employee employee=employeeRepository.findById(id);
        if(employee==null)
        {
            throw new NotFoundException("Employee not found");
        }
        employee.setSalary(salary);
        employeeRepository.persist(employee);
        return employee;
    }

    @GET
    public  List<Employee> getAllEmployees() {
        return employeeRepository.listAll(); 
    }

    @GET
    @Path("{id}")
    public Employee getEmployeeById(@PathParam("id") Long id) {
        Employee employee=employeeRepository.findById(id);
        if(employee==null)
        {
            throw new NotFoundException("Employee not found");
        }
        else
        {
            return employee;
        }
    }

    @DELETE
    @Path("{id}")
    public void deleteEmployeeById(@PathParam("id") Long id) {
        Employee employee = employeeRepository.findById(id);
        if(employee == null) {
            throw new NotFoundException("Employee not found");
        }
        employeeRepository.delete(employee);
    }
}