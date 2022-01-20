package org.example;

import org.example.rest.model.StudentDTO;
import org.example.student.Address;
import org.example.student.Student;
import org.example.student.StudentMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

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

            StudentDTO studentDTO = studentMapper.toStudentDTO(studentA);

            //studentA_ se citeste: studentA'
            Student studentA_ = studentMapper.toStudent(studentDTO);

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

            StudentDTO studentDTO = studentMapper.toStudentDTO(studentA);

            //studentA_ se citeste: studentA'
            Student studentA_ = studentMapper.toStudent(studentDTO);

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
}