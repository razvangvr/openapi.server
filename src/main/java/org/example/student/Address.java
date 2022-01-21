package org.example.student;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@Builder
public class Address {

    private String street;
    private int streetNo;

    /*ai nevoie de Referinta Asta JPA ForeignKey*/
//    @OneToOne()
//    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "FK_approval_process__offer"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Student student;

    private List<Inhabitant> inhabitants;
}
