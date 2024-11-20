package entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class EmployeeEntity {
    private String empID;
    private String name;
    private String email;
    private String contactNo;
    private String status;


    public EmployeeEntity(String empID, String name, String email, String contactNo, String status) {
        this.empID = empID;
        this.name = name;
        this.email = email;
        this.contactNo = contactNo;
        this.status = status;
    }

}
