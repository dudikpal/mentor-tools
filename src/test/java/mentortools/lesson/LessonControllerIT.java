package mentortools.lesson;

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
@Sql(statements = "delete from lessons")
class LessonControllerIT {

    @Autowired
    TestRestTemplate template;

    LessonDTO lesson;

    Long lesson1Id;

    @BeforeEach
    void setUp() {
        lesson = template.postForObject("/api/lessons",
                new CreateLessonCommand("lesson 1", "http://lesson1.com"),
                LessonDTO.class);
        lesson1Id = lesson.getId();
    }

    @Test
    void getLessons() {
        LessonDTO lesson2 = template.postForObject("/api/lessons",
                new CreateLessonCommand("lesson 2", "http://lesson2.com"),
                LessonDTO.class);

        List<LessonDTO> lessons = template.exchange(
                "/api/lessons",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LessonDTO>>() {
                }
        ).getBody();

        assertThat(lessons)
                .extracting(LessonDTO::getTitle)
                .containsExactly("lesson 1", "lesson 2");
    }

    @Test
    void createLesson() {
        LessonDTO lesson2 = template.postForObject("/api/lessons",
                new CreateLessonCommand("lesson 2", "http://lesson2.com"),
                LessonDTO.class);

        assertEquals("http://lesson2.com", lesson2.getUrl());
    }

    @Test
    void findLessonById() {
        LessonDTO lessonDto = template.getForObject(
                "/api/lessons/" + lesson1Id,
                LessonDTO.class
        );

        assertEquals("lesson 1", lessonDto.getTitle());
    }

    @Test
    void updateLesson() {
        template.put("/api/lessons/" + lesson1Id,
                new UpdateLessonCommand("lesson 1 updated", "http://lesson1new.com"));
        LessonDTO updatedLesson = template.getForObject("/api/lessons/1", LessonDTO.class);
        assertEquals("lesson 1 updated", updatedLesson.getTitle());
    }

    @Test
    void deleteLesson() {
        template.delete("/api/lessons/" + lesson1Id);
        List<LessonDTO> lessons = template.exchange(
                "/api/lessons",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LessonDTO>>() {
                }
        ).getBody();

        assertThat(lessons)
                .hasSize(0);
    }
}