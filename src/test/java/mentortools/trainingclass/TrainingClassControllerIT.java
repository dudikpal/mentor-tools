package mentortools.trainingclass;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from trainingclasses")
class TrainingClassControllerIT {

    private static final String TRAININGCLASS_URL = "/api/trainingclasses";

    @Autowired
    TestRestTemplate template;

    TrainingClassDTO trainingClass1;

    Long trainingClass1Id;

    Interval interval1;

    @BeforeEach
    void setUp() {
        interval1 = new Interval(LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 05, 01));

        trainingClass1 = template.postForObject(
                TRAININGCLASS_URL,
                new CreateTrainingClassCommand("trainingClass 1",
                        interval1),
                TrainingClassDTO.class
        );

        trainingClass1Id = trainingClass1.getId();
    }

    @Test
    void getTrainingClasses() {
        TrainingClassDTO trainingClass2 = createTrainingClassDTO("trainingclass 2", interval1);
        List<TrainingClassDTO> traqiningClasses = getTrainingClassDTOs();

        assertThat(traqiningClasses)
                .hasSize(2)
                .extracting(TrainingClassDTO::getName)
                .containsExactly("trainingClass 1", "trainingclass 2");
    }

    @Test
    void createTrainingClass() {
        TrainingClassDTO trainingClass2 = createTrainingClassDTO("trainingclass 2", interval1);
        TrainingClassDTO traininClassFindById = getForTrainingClass(trainingClass2.getId());
        assertEquals("trainingclass 2", traininClassFindById.getName());
    }

    @Test
    void findTrainingClassById() {
        TrainingClassDTO traininClassFindById = getForTrainingClass(trainingClass1.getId());
        assertEquals("trainingClass 1", traininClassFindById.getName());
    }

    @Test
    void updateTrainingClass() {
        Interval interval2 = new Interval(LocalDate.of(2021, 4, 4),
                LocalDate.of(2021, 8, 4));
        template.put(
                TRAININGCLASS_URL + "/" + trainingClass1Id,
                new UpdateTrainingClassCommand("updated trainingClass 1",
                        interval2)
        );
        TrainingClassDTO updatedTrainingClass = getForTrainingClass(trainingClass1Id);

        assertEquals("updated trainingClass 1", updatedTrainingClass.getName());
    }

    @Test
    void deleteTrainingClass() {
        template.delete(TRAININGCLASS_URL + "/" + trainingClass1Id);
        List<TrainingClassDTO> trainingClasses = getTrainingClassDTOs();

        assertThat(trainingClasses)
                .hasSize(0);
    }


    private List<TrainingClassDTO> getTrainingClassDTOs() {
        return template.exchange(
                TRAININGCLASS_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TrainingClassDTO>>() {
                }
        ).getBody();
    }


    private TrainingClassDTO createTrainingClassDTO(String name, Interval interval) {
        return template.postForObject(
                TRAININGCLASS_URL,
                new CreateTrainingClassCommand(name, interval),
                TrainingClassDTO.class
        );
    }


    private TrainingClassDTO getForTrainingClass(long id) {
        return template.getForObject(
                TRAININGCLASS_URL + "/" + id,
                TrainingClassDTO.class
        );
    }
}