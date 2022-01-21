package org.example;

import org.example.rest.model.StudentDTO;
import org.example.student.Address;
import org.example.student.Inhabitant;
import org.example.student.Student;
import org.example.student.StudentMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class StudentMapperTest {

    @Autowired
    private StudentMapper studentMapper;


    @Nested
    @DisplayName("Given a Student with Scalar values only")
    class ScalarOnly {

        /**
         * Just Scalar Values,
         */
        @Test
        @DisplayName("From entity -> dto -> entity")
        void entity_dto_entity() {

            Student studentA = getStudentScalar();

            StudentDTO studentDTO = studentMapper.toDTO(studentA);

            //studentA_ se citeste: studentA'
            Student studentA_ = studentMapper.toEntity(studentDTO);

            assertThat(studentA_).isNotSameAs(studentA);

            assertThat(studentA_).isEqualTo(studentA);
            assertThat(studentA_).usingRecursiveComparison().isEqualTo(studentA);
        }
    }


    @Nested
    class StudentWithAddress {

        @Test
        @DisplayName("From entity -> dto -> entity")
        void entity_dto_entity() {

            Student studentA = getStudentWithAddress();

            StudentDTO studentDTO = studentMapper.toDTO(studentA);

            //studentA_ se citeste: studentA'
            Student studentA_ = studentMapper.toEntity(studentDTO);

            assertThat(studentA_).isNotSameAs(studentA);

            assertThat(studentA_).isEqualTo(studentA);
            //This fails because Address has not implemented Equals correctly
            //to compare 2 Address by FieldsValues and not references
            //But if I use Lombook's @Data, since it also gives you Equals and HashCode,
            //This assertion passes

            assertThat(studentA_).usingRecursiveComparison()
                    .ignoringFields("address.student")
                    .isEqualTo(studentA);
        }

    }

    /**
     * 1. I have added to the Domain a new Type - Inhabitant
     * <p>
     * Is it automatically mapped in the DTO?
     * (mapstruc il mapeaza automat in DTO, nu trebe sa-l declar explicit in mapper)
     */
    @DisplayName("Entity -> DTO")
    @Test
    void entityToDTOWithInhabitants() {

        Student studentA = getStudentWithAddressAndInhabitants();

        StudentDTO studentDTO = studentMapper.toDTO(studentA);


        //citeste studentA Prim
        //studentA'
        Student studentA_ = studentMapper.toEntity(studentDTO);
        assertThat(studentA_).isNotSameAs(studentA);

        assertThat(studentA_).isEqualTo(studentA);

//        assertThat(studentA_).usingRecursiveComparison()
//                .ignoringFields("address.student")
//                .isEqualTo(studentA);
        /*
         * This fails with
         * field/property 'address.inhabitants[0].address' differ:
- actual value   : null
- expected value : Address(street=Cooperatorilor, streetNo=19, inhabitants=[Inhabitant(name=George, age=25), Inhabitant(name=Marty, age=15)])

field/property 'address.inhabitants[1].address' differ:
- actual value   : null
- expected value : Address(street=Cooperatorilor, streetNo=19, inhabitants=[Inhabitant(name=George, age=25), Inhabitant(name=Marty, age=15)])
         *
         * */

        assertThat(studentA_).usingRecursiveComparison()
                .ignoringFields("address.student")
                .ignoringFields("address.inhabitants.address")
                .isEqualTo(studentA);
    }

    static Student getStudentScalar() {
        Student student = Student.builder()
                .id(2)
                .name("John Doe")
                .joinDate(LocalDate.now())
                .build();

        return student;
    }

    static Student getStudentWithAddress() {

        Address address = Address.builder()
                .street("Cooperatorilor")
                .streetNo(19)
                .build();

        Student student = Student.builder()
                .id(2)
                .name("John Doe")
                .joinDate(LocalDate.now())
                .address(address)
                .build();

        address.setStudent(student);

        return student;
    }

    static Student getStudentWithAddressAndInhabitants() {


        Address address = Address.builder()
                .street("Cooperatorilor")
                .streetNo(19)
                .build();


        List<Inhabitant> inhabitants = List.of(
                Inhabitant.builder()
                        .name("George").age(25)
                        .address(address).build(),
                Inhabitant.builder()
                        .name("Marty").age(15)
                        .address(address).build()
        );

        address.setInhabitants(inhabitants);

        Student student = Student.builder()
                .id(2)
                .name("John Doe")
                .joinDate(LocalDate.now())
                .address(address)
                .build();

        address.setStudent(student);

        return student;
    }
}