package com.angMetal.orders.payloads.request;



import lombok.Getter;


@Getter
public class UpdateUserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;


}