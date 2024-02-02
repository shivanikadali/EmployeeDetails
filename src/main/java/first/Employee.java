package first;
import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity(name = "Employee")
@Table(name = "Employees") 
public class Employee {

    @Id
    @GeneratedValue
    @Column(name = "employee_id")
    private int employeeId;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "salary")
    private BigDecimal salary;

    @Column(name = "contact_number")
    private String contactNumber;

    // Constructors
    public Employee() {
    }

    public Employee(int employeeId, String employeeName, String jobTitle, BigDecimal salary, String contactNumber) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.contactNumber = contactNumber;
    }

    // Getters and Setters
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", salary=" + salary +
                ", contactNumber='" + contactNumber + '\'' +
                '}';
    }

}