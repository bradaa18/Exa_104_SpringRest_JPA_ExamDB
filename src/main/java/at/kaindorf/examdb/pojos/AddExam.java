package at.kaindorf.examdb.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddExam {
    private Long examId;
    private Long subjectId;
    private Long studentId;
    private LocalDate dateOfExam;
    private Integer duration;
}
