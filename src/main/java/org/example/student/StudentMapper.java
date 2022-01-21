package org.example.student;

import java.util.List;

import org.example.rest.model.StudentDTO;
import org.mapstruct.Mapper;

@Mapper
public interface StudentMapper {

    List<StudentDTO> toDTOList(List<Student> students);

    StudentDTO toDTO(Student student);

    Student toEntity(StudentDTO studentDTO);
}
