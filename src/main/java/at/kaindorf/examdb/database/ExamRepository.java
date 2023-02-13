package at.kaindorf.examdb.database;

import at.kaindorf.examdb.pojos.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    @Query("select e from Exam e WHERE e.student.studentId = ?1")
    List<Exam> getExamsByStudentId(Long id);
}
