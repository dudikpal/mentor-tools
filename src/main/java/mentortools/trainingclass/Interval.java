package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Interval {

    private LocalDate startingDate;

    private LocalDate endingDate;

}
