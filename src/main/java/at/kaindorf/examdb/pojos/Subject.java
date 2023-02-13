package at.kaindorf.examdb.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Subject {
    @Id
    private Long subjectId;
    private String longname;
    @Column(length = 10)
    private String shortname;
    @Column(nullable = false)
    private boolean written;

    @OneToMany(mappedBy = "subject")
    @ToString.Exclude
    @JsonIgnore
    private List<Exam> exams = new ArrayList<>();
}
