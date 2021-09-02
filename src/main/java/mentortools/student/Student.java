package mentortools.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull
    @Length(max = 255)
    private String name;

    @Column(name = "email")
    @NotNull
    @Length(max = 255)
    private String email;

    @Column(name = "github_name")
    @NotNull
    @Length(max = 255)
    private String gitHubName;

    @Column(name = "description")
    private String description;

    public Student(String name, String email, String gitHubName) {
        this.name = name;
        this.email = email;
        this.gitHubName = gitHubName;
    }
}
