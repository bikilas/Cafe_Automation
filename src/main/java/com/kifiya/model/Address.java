package com.kifiya.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Address {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private  long id;
    //  private String city;
    //  private String state;
    //  private String country;
    //  private String pincode;
 }
