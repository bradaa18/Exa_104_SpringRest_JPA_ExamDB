package at.kaindorf.examdb.pojos;

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
public class Classname {
    @Id
    private Long classId;
    @Column(length = 10)
    private String classname;
    
    @OneToMany(mappedBy = "classname")
    @ToString.Exclude
    private List<Student> students = new ArrayList<>();
}
