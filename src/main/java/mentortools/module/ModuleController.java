package mentortools.module;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/modules")
public class ModuleController {

    private ModuleService moduleService;


    @GetMapping
    public List<ModuleDTO> getModules(@RequestParam Optional<String> prefix) {
        return moduleService.getModules(prefix);
    }


    @GetMapping("/{id}")
    public ModuleDTO findModuleById(@PathVariable long id) {
        return moduleService.findModuleById(id);
    }


    @PostMapping
    public ModuleDTO createModule(@RequestBody CreateModuleCommand command) {
        return moduleService.createModule(command);
    }


    @PutMapping("/{id}")
    public ModuleDTO updateModule(@PathVariable("id") long id, @RequestBody UpdateModuleCommand command) {
        return moduleService.updateModule(id, command);
    }


    @DeleteMapping("/{id}")
    public void deleteModuleById(@PathVariable long id) {
        moduleService.deleteModuleById(id);
    }
}
