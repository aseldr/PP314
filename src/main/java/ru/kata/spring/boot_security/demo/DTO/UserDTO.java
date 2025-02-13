package ru.kata.spring.boot_security.demo.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private Long id;

    @NotEmpty(message = "Не должно быть пустым")
    @Size(max = 32, message = "Не должно быть пустым")
    private String firstname;

    @NotEmpty(message = "Не должно быть пустым")
    @Size(max = 32, message = "Не должно быть пустым")
    private String lastname;

    @NotEmpty(message = "Не должно быть пустым")
    @Email(message = "Не должно быть пустым")
    private String email;

    @Min(value = 1, message = "Не должно быть пустым")
    private int age;

    @NotEmpty(message = "Не должно быть пустым")
    private String userPassword;

    public @Min(value = 1, message = "Не должно быть пустым") int getAge() {
        return age;
    }

    private List<RoleDTO> roles = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }
}
