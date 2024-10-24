package controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class UserController implements UserService{
    private String username;
    private String password;
    private String email;


    @Override
    public void setUserName(String name) {
        username = name;
        System.out.println("Username set to: " + username);  // Debugging
    }

    @Override
    public String getUserName() {
        System.out.println("Username retrieved: " + username);  // Debugging
        return username;
    }


    @Override
    public void setPassword(String Password) {
        this.password = Password;
    }

    @Override
    public void setEmail(String Email) {
        this.email=Email;
    }



    @Override
    public String getPassowrd() {
        return this.password;
    }

    @Override
    public String getEmail() {
        return this.email;
    }
}
