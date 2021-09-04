package mentortools.trainingclass;

import lombok.AllArgsConstructor;
import mentortools.student.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrainingClassService {

    private TrainingClassRepository trainingClassRepository;

    private StudentRepository studentRepository;

    private ModelMapper modelMapper;


    public List<TrainingClassDTO> getTrainingClasses(Optional<String> prefix) {
        return trainingClassRepository.findAll().stream()
                .filter(tc -> prefix.isEmpty() || tc.getName().toLowerCase().startsWith(prefix.get().toLowerCase().trim()))
                .map(tc -> modelMapper.map(tc, TrainingClassDTO.class))
                .toList();
    }

    public TrainingClassDTO createTrainingClass(CreateTrainingClassCommand command) {
        TrainingClass tc = new TrainingClass(command.getName(),
                new Interval(command.getInterval().getStartingDate(), command.getInterval().getEndingDate()));
        return modelMapper.map(trainingClassRepository.save(tc), TrainingClassDTO.class);
    }

    public TrainingClassDTO findTrainingClassById(long id) {
        return modelMapper.map(getTrainingClassById(id), TrainingClassDTO.class);
    }

    public TrainingClass getTrainingClassById(long id) {
        return trainingClassRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find trainingclass with id: " + id));
    }

    @Transactional
    public TrainingClassDTO updateTrainingClass(long id, UpdateTrainingClassCommand command) {
        TrainingClass tc = getTrainingClassById(id);
        tc.setName(command.getName());
        tc.setInterval(new Interval(command.getInterval().getStartingDate(), command.getInterval().getEndingDate()));

        return modelMapper.map(tc, TrainingClassDTO.class);
    }

    public void deleteTrainingClass(long id) {
        trainingClassRepository.deleteById(id);
    }


}
