package first;

import java.util.List;

import org.hibernate.exception.DataException;

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
        try {
            employeeRepository.persist(employee);
            return employee;
        } catch (Exception e) {
            throw new BadRequestException("there was an error in while saving the Employee");
        }
    }

    @PUT
    @Path("{id}")
    public Employee updateEmployee(@PathParam("id") Long id, @QueryParam("salary") int salary) {
        try {
            Employee employee = employeeRepository.findById(id);
            if (employee == null) {
                throw new NotFoundException("Employee not found");
            } else {
                employee.setSalary(salary);
                employeeRepository.persist(employee);
                return employee;
            }
        } catch (DataException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @GET
    public List<Employee> getAllEmployees() {
        return employeeRepository.listAll();
    }

    @GET
    @Path("{id}")
    public Employee getEmployeeById(@PathParam("id") Long id) {
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            throw new NotFoundException("Employee not found");
        } else {
            return employee;
        }
    }

    @DELETE
    @Path("{id}")
    public void deleteEmployeeById(@PathParam("id") Long id) {
        try {
            Employee employee = employeeRepository.findById(id);
            if (employee == null) {
                throw new NotFoundException("Employee not found");
            } else {
                employeeRepository.delete(employee);
            }
        } catch (DataException e) {
            throw new BadRequestException(e.getMessage());
        }

    }
}