package at.kaindorf.examdb.api;

import at.kaindorf.examdb.database.SubjectRepository;
import at.kaindorf.examdb.pojos.Subject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping(value = "/subjects", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class SubjectController {
    private final SubjectRepository subjectRepository;

    public SubjectController(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Subject>> getSubjects(){
        return ResponseEntity.of(Optional.of(subjectRepository.findAll()));
    }
}
