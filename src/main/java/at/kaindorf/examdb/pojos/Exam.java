package at.kaindorf.examdb.pojos;

import jakarta.annotation.sql.DataSourceDefinitions;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long examId;
    @Column(name = "dateofexam")
    private LocalDate dateOfExam;
    private Integer duration;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false, name = "student")
    private Student student;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false, name = "subject")
    private Subject subject;

    public void setStudent(Student student) {
        this.student = student;
        student.getExams().add(this);
    }
}
