package first;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/employee")
// all the rest api's in this should be authenticated if not explicitly
// specified
// @Authenticated
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Transactional
@ApplicationScoped
public class EmployeeController {

    @Inject
    EmployeeRepository employeeRepository;

    @POST
    @Path("/create")
    @SecurityRequirement(name="keycloak")
    // @RolesAllowed("ceo") //amar
    public Employee createEmployee(Employee employee) {
        try {
            employeeRepository.persist(employee);
            return employee;
        } catch (Exception e) {
            throw new InternalServerErrorException("An error occurred while creating the employee");
        }
    }

    @DELETE
    @Path("/delete/{id}")
    @SecurityRequirement(name="keycloak")
    // @RolesAllowed("admin") // admin
    public void deleteEmployeeById(@PathParam("id") Long id) {
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            throw new NotFoundException("Employee not found");
        } else {
            employeeRepository.delete(employee);
        }
    }

    @PUT
    @Path("/update/{id}")
    // @RolesAllowed("manager") //user:suresh
    public Employee updateEmployee(@PathParam("id") Long id,
            @QueryParam("salary") int salary) {
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            throw new NotFoundException("Employee with id " + id + " not found");
        }
        employee.setSalary(salary);
        employeeRepository.persist(employee);
        return employee;
    }

    @GET
    // @PermitAll
    @Path("/all")
    public List<Employee> getAllEmployees() {
        return employeeRepository.listAll();
    }

    @GET
    @Path("/by/{id}")
    // @PermitAll
    public Employee getEmployeeById(@PathParam("id") Long id) {
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            throw new NotFoundException("Employee not found");
        }
        return employee;
    }
}