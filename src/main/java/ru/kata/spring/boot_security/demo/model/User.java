package ru.kata.spring.boot_security.demo.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.DTO.RoleDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotEmpty(message = "Не должно быть пустым")
    @Size(max = 32, message = "Не должно быть пустым")
    private String firstname;

    @NotEmpty(message = "Не должно быть пустым")
    @Size(max = 32, message = "Не должно быть пустым")
    private String lastname;

    @Min(value = 0, message = "Не должно быть пустым")
    private Byte age;

    @NotEmpty(message = "Не должно быть пустым")
    private String password;

    @NotEmpty(message = "Не должно быть пустым")
    @Email(message = "Не должно быть пустым")
    private String email;


    @LazyCollection(LazyCollectionOption.EXTRA)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {}

    public User(String firstname, String lastname, Byte age, String password, String email, Set<Role> roles) {
        this.firstname = this.firstname;
        this.lastname = this.lastname;
        this.age = age;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstName) {
        this.firstname = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastName) {
        this.lastname = lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    @Override
    public Collection<Role> getAuthorities() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return firstname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String rolesToString() {
        return roles.stream()
                .map(role -> role.getName().replace("ROLE_", ""))
                .collect(Collectors.joining(", "));
    }

    public void setRoles(List<RoleDTO> roles) {

    }
}
