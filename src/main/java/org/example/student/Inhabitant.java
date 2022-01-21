package org.example.student;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
public class Inhabitant {

    private String name;
    private int age;

//    @JoinColumn(name = "address_id"
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Address address;
}
