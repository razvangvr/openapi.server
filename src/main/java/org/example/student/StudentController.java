package org.example.student;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.example.exception.NotFoundException;
import org.example.rest.api.StudentApi;
import org.example.rest.model.StudentDTO;

@RestController
public class StudentController implements StudentApi {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public ResponseEntity<List<StudentDTO>> getStudents() {
        return ResponseEntity.ok(studentMapper.toDTOList(studentService.getStudents()));
    }

    @Override
    public ResponseEntity<StudentDTO> getStudent(Integer id) {
        return ResponseEntity
            .ok(studentMapper.toDTO(studentService.getStudent(id).orElseThrow(NotFoundException::new)));
    }

    @Override
    public ResponseEntity<StudentDTO> addStudent(@Valid StudentDTO studentDTO) {
        return ResponseEntity.ok(studentMapper.toDTO(
            studentService.addStudent(
                studentMapper.toEntity(studentDTO)
            )
        ));
    }

    @Override
    public ResponseEntity<StudentDTO> updateStudent(@Valid StudentDTO studentDTO) {
        return ResponseEntity.ok(studentMapper.toDTO(
            studentService.updateStudent(
                studentMapper.toEntity(studentDTO)
            )
        ));
    }

    @Override
    public ResponseEntity<Void> removeStudent(Integer id) {
        studentService.removeStudent(id);
        return ResponseEntity.ok().build();
    }
}
