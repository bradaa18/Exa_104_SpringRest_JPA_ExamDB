package at.kaindorf.examdb.api;

import at.kaindorf.examdb.database.ClassnameRepository;
import at.kaindorf.examdb.pojos.Classname;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping(value = "/classnames", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ClassnameController {
    private final ClassnameRepository classnameRepository;

    public ClassnameController(ClassnameRepository classnameRepository) {
        this.classnameRepository = classnameRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Classname>> getAllClassnames(){
        return ResponseEntity.of(Optional.of(classnameRepository.findAll(Sort.by("classname"))));
    }
}
