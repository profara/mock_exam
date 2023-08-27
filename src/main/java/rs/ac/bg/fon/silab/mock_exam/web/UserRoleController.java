package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.dto.UserRoleResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.service.UserRoleService;



@RestController
@RequestMapping("/api/userRoles")
public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping
    public ResponseEntity<UserRoleResponseDTO> save(@Valid @RequestBody UserRoleRequestDTO userRoleDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userRoleService.save(userRoleDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRoleResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(userRoleService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<UserRoleResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(userRoleService.get(pageable));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        userRoleService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRoleResponseDTO> update(@PathVariable Long id,@Valid @RequestBody UserRoleRequestDTO userRoleDTO){
        return ResponseEntity.ok(userRoleService.update(id, userRoleDTO));
    }
}
