package mentortools.module;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ModuleService {

    private ModuleRepository moduleRepository;

    private ModelMapper modelMapper;


    public List<ModuleDTO> getModules(Optional<String> prefix) {
        return moduleRepository.findAll().stream()
                .filter(module -> prefix.isEmpty() || module.getTitle().toLowerCase().startsWith(prefix.get().toLowerCase().trim()))
                .map(module -> modelMapper.map(module, ModuleDTO.class))
                .toList();
    }

    public ModuleDTO createModule(CreateModuleCommand command) {
        Module module = new Module(command.getTitle(), command.getUrl());

        return modelMapper.map(moduleRepository.save(module), ModuleDTO.class);
    }

    public ModuleDTO findModuleById(long id) {
        return modelMapper.map(getModuleById(id), ModuleDTO.class);
    }

    private Module getModuleById(long id) {
        return moduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cannot find module with id: " + id));
    }

    @Transactional
    public ModuleDTO updateModule(long id, UpdateModuleCommand command) {
        Module module = getModuleById(id);
        module.setTitle(command.getTitle());
        module.setUrl(command.getUrl());
        return modelMapper.map(module, ModuleDTO.class);
    }


    public void deleteModuleById(long id) {
        moduleRepository.deleteById(id);
    }
}
