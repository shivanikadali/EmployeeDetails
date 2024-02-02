package first;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;

@Path("/employees")
@Produces("application/json")
@Consumes("application/json")

public class EmployeeController {

    @Inject
    EmployeeRepository employeeRepository;
    
    @GET
    public List<Employee> getAllEmployees() {
           
        return employeeRepository.listAll();
    }
      
    @GET
    @Path("{id}")
    public Employee getEmployee(int id){
        Employee employee =employeeRepository.findById((long) id);
        if(employee ==null)
        {
            return null;
        }
        return employee;
    }
}
