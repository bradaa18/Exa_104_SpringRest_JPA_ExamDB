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
public class Student {
    @Id
    private long studentId;
    @Column(length = 80)
    private String firstname;
    @Column(length = 80)
    private String lastname;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(nullable = false, name = "classname")
    @JsonIgnore
    private Classname classname;

    @OneToMany(mappedBy = "student")
    @ToString.Exclude
    @JsonIgnore
    private List<Exam> exams = new ArrayList<>();
}
