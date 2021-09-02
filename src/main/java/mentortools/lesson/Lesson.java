package mentortools.lesson;

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
@Table(name = "lessons")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Length(max = 255)
    private String title;

    @NotNull
    @Length(max = 255)
    private String url;

    public Lesson(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
