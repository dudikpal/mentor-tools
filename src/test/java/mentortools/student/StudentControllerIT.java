package mentortools.student;

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
@Sql(statements = "delete from students")
class StudentControllerIT {

    @Autowired
    TestRestTemplate template;

    StudentDTO student1;

    Long student1Id;

    @BeforeEach
    void setUp() {
        student1 = template.postForObject(
                "/api/students",
                new CreateStudentCommand("Student 1", "email 1", "github 1", null),
                StudentDTO.class
        );
        student1Id = student1.getId();
    }

    @Test
    void getStudents() {
        StudentDTO student2 = template.postForObject("/api/students",
                new CreateStudentCommand("student 2", "email 2", "guthub 2", null),
                StudentDTO.class);
        List<StudentDTO> students = template.exchange(
                "/api/students",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StudentDTO>>() {
                }
        ).getBody();

        assertThat(students)
                .hasSize(2)
                .extracting(StudentDTO::getEmail)
                .containsExactly("email 1", "email 2");
    }

    @Test
    void createStudent() {
        StudentDTO student2 = template.postForObject("/api/students",
                new CreateStudentCommand("student 2", "email 2", "guthub 2", null),
                StudentDTO.class);

        assertEquals("student 2", student2.getName());
    }

    @Test
    void findStudentById() {
        StudentDTO findedStudent = template.getForObject("/api/students/" + student1Id, StudentDTO.class);

        assertEquals("github 1", findedStudent.getGitHubName());
    }

    @Test
    void updateStudent() {
        template.put("/api/students/" + student1Id,
                new UpdateStudentCommand("student 1 edited", "new email 1", "github update 1", null));

        StudentDTO updatedStudent = template.getForObject("/api/students/" + student1Id,
                StudentDTO.class);

        assertEquals("new email 1", updatedStudent.getEmail());
    }

    @Test
    void deleteStudent() {
        template.delete("/api/students/" + student1Id);
        List<StudentDTO> students = template.exchange(
                "/api/students/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<StudentDTO>>() {
                }
        ).getBody();

        assertThat(students)
                .hasSize(0);
    }
}