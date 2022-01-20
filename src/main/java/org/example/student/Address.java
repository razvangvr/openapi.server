package org.example.student;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

    private String street;
    private int streetNo;
}
