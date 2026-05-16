package org.example.bookingsystem.customer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "You must enter a firstname.")
    private String firstname;

    @NotNull(message = "You must enter a lastname.")
    private String lastname;

    @Column(name = "identification_number", unique = true)
    @NotNull(message = "You must enter an identification number.")
    @Pattern(regexp = "^(\\d{6}|\\d{8})-\\d{4}$", message = "invalid format of identification number.")
    private String identificationNumber;

    @Column(name = "email", unique = true)
    @NotNull(message = "You must enter a email.")
    @Email(message = "Email format is invalid.")
    private String email;

    @NotBlank(message = "You must enter a password.")
    private String password;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    public Customer() {}

    public Customer(String firstname, String lastname, String identificationNumber, String email, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.identificationNumber = identificationNumber;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
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

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
