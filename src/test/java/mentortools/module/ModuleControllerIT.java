package mentortools.module;

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
@Sql(statements = "delete from modules")
class ModuleControllerIT {

    ModuleDTO module1;

    Long module1Id;

    @Autowired
    TestRestTemplate template;

    @BeforeEach
    void setUp() {
        module1 = template.postForObject(
                "/api/modules",
                new CreateModuleCommand("module 1", "http://module1.com"),
                ModuleDTO.class
        );
        module1Id = module1.getId();
    }

    @Test
    void getModules() {
        ModuleDTO module2 = template.postForObject(
                "/api/modules",
                new CreateModuleCommand("module 2", "http://module2.com"),
                ModuleDTO.class
        );
        List<ModuleDTO> modules = template.exchange(
                "/api/modules",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ModuleDTO>>() {
                }
        ).getBody();
        assertThat(modules)
                .hasSize(2)
                .extracting(ModuleDTO::getTitle)
                .containsExactly("module 1", "module 2");
    }

    @Test
    void findModuleById() {
        ModuleDTO findedModule = template.getForObject(
                "/api/modules/" + module1Id,
                ModuleDTO.class
        );
        assertEquals("module 1", findedModule.getTitle());
    }

    @Test
    void createModule() {
        ModuleDTO module2 = template.postForObject(
                "/api/modules",
                new CreateModuleCommand("module 2", "http://module2.com"),
                ModuleDTO.class
        );
        assertEquals("module 2", module2.getTitle());
    }

    @Test
    void updateModule() {
        template.put("/api/modules/" + module1Id,
                new UpdateModuleCommand("module 1 edited", "http://module1updated.com"));
        ModuleDTO updatedModule = template.getForObject("/api/modules/" + module1Id,
                ModuleDTO.class);
        assertEquals("module 1 edited", updatedModule.getTitle());
    }

    @Test
    void deleteModuleById() {
        template.delete("/api/modules/" + module1Id);

        List<ModuleDTO> modules = template.exchange(
                "/api/modules",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ModuleDTO>>() {
                }
        ).getBody();

        assertThat(modules)
                .hasSize(0);
    }
}