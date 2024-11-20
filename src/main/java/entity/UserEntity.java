package entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UserEntity {
    private String username;
    private String password;
    private String email;
    private String name;
    private String contactNo;

    public UserEntity(String username, String password, String email, String name, String contactNo) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name=name;
        this.contactNo=contactNo;
    }
}
