package com.angMetal.orders.payloads.request;


import lombok.Getter;

import javax.validation.constraints.*;


@Getter
public class SignUpRequest {

    @NotEmpty(message = "Username cannot be NULL/empty")
    @Size(min = 3, max = 20)
    private String username;

    @NotEmpty(message = "E-mail cannot be NULL/empty")
    @Size(max = 50)
    @Email
    private String email;

    @NotEmpty(message = "Password cannot be NULL/empty")
    @Size(min = 6, max = 40)
    private String password;

    @NotEmpty(message = "First name cannot be NULL/empty")
    @Size(min = 1)
    private String firstName;

    @NotEmpty(message = "Last name cannot be NULL/empty")
    @Size(min = 1)
    private String lastName;

    @NotEmpty(message = "Address cannot be NULL/empty")
    @Size(min = 1)
    private String address;

    @NotEmpty(message = "Phone number cannot be NULL/empty")
    @Size(min = 1)
    private String phoneNumber;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}