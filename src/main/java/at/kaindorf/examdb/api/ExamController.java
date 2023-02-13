package at.kaindorf.examdb.api;

import at.kaindorf.examdb.database.ExamRepository;
import at.kaindorf.examdb.database.StudentRepository;
import at.kaindorf.examdb.database.SubjectRepository;
import at.kaindorf.examdb.pojos.AddExam;
import at.kaindorf.examdb.pojos.Exam;
import at.kaindorf.examdb.pojos.Student;
import at.kaindorf.examdb.pojos.Subject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping(value = "/exams", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ExamController {
    private final ExamRepository examRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public ExamController(ExamRepository examRepository, StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.examRepository = examRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Exam>> getAllExamsFromStudent(@PathVariable("id") Long id){
        return ResponseEntity.of(Optional.of(examRepository.getExamsByStudentId(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Exam> deleteExam(@PathVariable("id") Long id){
        examRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add")
    public ResponseEntity<Exam> addExam(@RequestBody AddExam addExam){
        Student student = studentRepository.findById(addExam.getStudentId()).get();
        Subject subject = subjectRepository.findById(addExam.getSubjectId()).get();
        Exam exam = new Exam();
        exam.setSubject(subject);
        exam.setDuration(addExam.getDuration());
        exam.setDateOfExam(addExam.getDateOfExam());
        exam.setStudent(student);
        examRepository.save(exam);
        return ResponseEntity.ok(exam);
    }

    @PatchMapping("/update")
    public ResponseEntity<Exam> updateExam(@RequestBody AddExam addExam){
        Student student = studentRepository.findById(addExam.getStudentId()).get();
        Subject subject = subjectRepository.findById(addExam.getSubjectId()).get();
        Exam patch = new Exam();
        patch.setExamId(addExam.getExamId());
        patch.setSubject(subject);
        patch.setDuration(addExam.getDuration());
        patch.setDateOfExam(addExam.getDateOfExam());
        patch.setStudent(student);

        Optional<Exam> examOpt = examRepository.findById(patch.getExamId());
        if (!examOpt.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Exam exam = examOpt.get();
        try {
            for (Field field : Exam.class.getDeclaredFields()) {
                field.setAccessible(true);
                Object value = ReflectionUtils.getField(field, patch);
                if (value != null && !value.toString().trim().isEmpty()) {
                    ReflectionUtils.setField(field, exam, value);
                }
            }
            examRepository.save(exam);
            return ResponseEntity.ok(exam);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
