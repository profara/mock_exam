package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileUpdateRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.service.UserProfileService;

@RestController
@RequestMapping("/api/userProfiles")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping
    public ResponseEntity<UserProfileResponseDTO> save(@Valid @RequestBody UserProfileRequestDTO userProfileDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfileService.save(userProfileDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(userProfileService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<UserProfileResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(userProfileService.get(pageable));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        userProfileService.delete(id);
    }

    @PatchMapping("/changeRole/{id}")
    public ResponseEntity<UserProfileResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserProfileUpdateRoleRequestDTO userProfileDTO){
        return ResponseEntity.ok(userProfileService.updateUserRole(id,userProfileDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserProfileResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserProfileRequestUpdateDTO userProfileDTO){
        return ResponseEntity.ok(userProfileService.update(id,userProfileDTO));
    }
}
