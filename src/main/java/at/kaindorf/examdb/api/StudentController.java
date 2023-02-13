package at.kaindorf.examdb.api;

import at.kaindorf.examdb.database.StudentRepository;
import at.kaindorf.examdb.pojos.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping(value = "/students", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/classes/{id}")
    public ResponseEntity<Page<Student>> getAllStudentsFromClass(@PathVariable("id") Long id, @RequestParam(name = "pageNo", defaultValue = "0") int pageNo){
        Pageable page = PageRequest.of(pageNo, 10,
                Sort.by("lastname").descending());
        return ResponseEntity.of(Optional.of(studentRepository.getStudentByClassname(id, page)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable("id") Long id){
        return ResponseEntity.of(studentRepository.findById(id));
    }
}
