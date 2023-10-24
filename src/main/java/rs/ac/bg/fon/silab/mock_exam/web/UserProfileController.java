package rs.ac.bg.fon.silab.mock_exam.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.RegistrationResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileUpdateRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.service.UserProfileService;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.email.EmailSender;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.jwt.JWTUtil;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.CONFIRMATION_LINK;

@RestController
@RequestMapping("/api/userProfiles")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final JWTUtil jwtUtil;


    public UserProfileController(UserProfileService userProfileService, JWTUtil jwtUtil) {
        this.userProfileService = userProfileService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody UserProfileRequestUpdateDTO userProfileDTO){
        RegistrationResponseDTO registrationResponseDTO = userProfileService.save(userProfileDTO);
        String jwtToken = registrationResponseDTO.jwtToken();

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponseDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(userProfileService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<UserProfileResponseDTO>> get(Pageable pageable){
        return ResponseEntity.ok(userProfileService.get(pageable));
    }

    @GetMapping(params = "email")
    public ResponseEntity<UserProfileResponseDTO> getByEmail(@RequestParam String email){
        return ResponseEntity.ok(userProfileService.getByEmail(email));
    }

    @GetMapping("/confirm")
    public ResponseEntity<UserProfileResponseDTO> confirm(@RequestParam("token") String token){
        return ResponseEntity.ok(userProfileService.enableProfile(token));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        userProfileService.delete(id);
    }

    @PatchMapping("/changeRole/{id}")
    public ResponseEntity<UserProfileResponseDTO> updateUserRole(@PathVariable Long id, @Valid @RequestBody UserProfileUpdateRoleRequestDTO userProfileDTO){
        return ResponseEntity.ok(userProfileService.updateUserRole(id,userProfileDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserProfileResponseDTO> update(@PathVariable Long id, @Valid @RequestBody UserProfileRequestUpdateDTO userProfileDTO){
        return ResponseEntity.ok(userProfileService.update(id,userProfileDTO));
    }


}
