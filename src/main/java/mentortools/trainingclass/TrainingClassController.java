package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import mentortools.syllabus.CreateSyllabusCommand;
import mentortools.syllabus.SyllabusDTO;
import mentortools.syllabus.SyllabusService;
import mentortools.syllabus.UpdateSyllabusCommand;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/trainingclasses")
public class TrainingClassController {

    private TrainingClassService trainingClassService;


    @GetMapping
    public List<TrainingClassDTO> getTrainingClasses(@RequestParam Optional<String> prefix) {
        return trainingClassService.getTrainingClasses(prefix);
    }


    @PostMapping
    public TrainingClassDTO createTrainingClass(@Valid @RequestBody CreateTrainingClassCommand command) {
        return trainingClassService.createTrainingClass(command);
    }


    @GetMapping("/{id}")
    public TrainingClassDTO findTrainingClassById(@PathVariable long id) {
        return trainingClassService.findTrainingClassById(id);
    }


    @PutMapping("/{id}")
    public TrainingClassDTO updateTrainingClass(@PathVariable long id, @RequestBody UpdateTrainingClassCommand command) {
        return trainingClassService.updateTrainingClass(id, command);
    }


    @DeleteMapping("/{id}")
    public void deleteTrainingClass(@PathVariable long id) {
        trainingClassService.deleteTrainingClass(id);
    }


}
