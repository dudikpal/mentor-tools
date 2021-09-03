package mentortools.syllabus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = "delete from syllabuses")
class SyllabusControllerTest {

    public static final String SYLLABUS_URL = "/api/syllabuses";
    @Autowired
    TestRestTemplate template;

    SyllabusDTO syllabus1;

    Long syllabus1Id;

    @BeforeEach
    void setUp() {
        syllabus1 = createSyllabusDTO("syllabus 1");
        syllabus1Id = syllabus1.getId();
    }

    @Test
    void getSyllabuses() {
        SyllabusDTO syllabus2 = createSyllabusDTO("syllabus 2");
        List<SyllabusDTO> syllabuses = getSyllabusDTOs();

        assertThat(syllabuses)
                .hasSize(2)
                .extracting(SyllabusDTO::getName)
                .containsExactly("syllabus 1", "syllabus 2");
    }

    @Test
    void createSyllabus() {
        SyllabusDTO syllabus2 = createSyllabusDTO("syllabus 2");
        SyllabusDTO savedSyllabus = getForSyllabus(syllabus2.getId());

        assertEquals("syllabus 2", savedSyllabus.getName());
    }


    @Test
    void findSyllabusById() {
        SyllabusDTO findedSyllabus = getForSyllabus(syllabus1Id);

        assertEquals("syllabus 1", findedSyllabus.getName());
    }

    @Test
    void updateSyllabus() {
        template.put(
                SYLLABUS_URL + "/" + syllabus1Id,
                new UpdateSyllabusCommand("new syllabusname 1"),
                SyllabusDTO.class
        );
        SyllabusDTO updatedSyllabus = getForSyllabus(syllabus1Id);

        assertEquals("new syllabusname 1", updatedSyllabus.getName());
    }

    @Test
    void deleteSyllabus() {

        template.delete(SYLLABUS_URL + "/" + syllabus1Id);

        List<SyllabusDTO> syllabuses = getSyllabusDTOs();

        assertThat(syllabuses)
                .hasSize(0);
    }


    private List<SyllabusDTO> getSyllabusDTOs() {
        return template.exchange(
                SYLLABUS_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<SyllabusDTO>>() {
                }
        ).getBody();
    }


    private SyllabusDTO createSyllabusDTO(String name) {
        return template.postForObject(
                SYLLABUS_URL,
                new CreateSyllabusCommand(name),
                SyllabusDTO.class
        );
    }


    private SyllabusDTO getForSyllabus(long id) {
        return template.getForObject(
                SYLLABUS_URL + "/" + id,
                SyllabusDTO.class
        );
    }
}
