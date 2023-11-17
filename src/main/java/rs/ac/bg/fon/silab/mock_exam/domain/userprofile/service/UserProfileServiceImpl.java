package rs.ac.bg.fon.silab.mock_exam.domain.userprofile.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.RegistrationResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileRequestUpdateDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.dto.UserProfileUpdateRoleRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.exception.DuplicateUserException;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.repository.UserProfileRepository;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.entity.UserProfile;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.mapper.UserProfileMapper;
import rs.ac.bg.fon.silab.mock_exam.domain.userrole.repository.UserRoleRepository;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.email.EmailSender;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.exception.EntityNotFoundException;
import rs.ac.bg.fon.silab.mock_exam.infrastructure.jwt.JWTUtil;

import java.util.Optional;

import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.CONFIRMATION_LINK;
import static rs.ac.bg.fon.silab.mock_exam.infrastructure.config.Constants.USER_ROLE;

/**
 * Implementation of the UserProfileService interface.
 * Manages user profile related business logic such as user registration, updating, and retrieval.
 */
@Service
public class UserProfileServiceImpl implements UserProfileService{

    private final UserProfileRepository userProfileRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserProfileMapper mapper;
    private final JWTUtil jwtUtil;
    private final EmailSender emailSender;

    /**
     * Constructs a new UserProfileServiceImpl with necessary dependencies.
     *
     * @param userProfileRepository the repository for accessing user profile data
     * @param userRoleRepository the repository for accessing user role data
     * @param passwordEncoder the encoder for user password hashing
     * @param mapper the mapper for converting between entity and DTO
     * @param jwtUtil utility for handling JSON Web Tokens
     * @param emailSender service for sending emails
     */
    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, UserProfileMapper mapper, JWTUtil jwtUtil, EmailSender emailSender) {
        this.userProfileRepository = userProfileRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
        this.jwtUtil = jwtUtil;
        this.emailSender = emailSender;
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfile findByEmail(String email) {
        return userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class.getSimpleName(), "email", email));
    }

    @Override
    @Transactional
    public RegistrationResponseDTO save(UserProfileRequestUpdateDTO userProfileDTO) {
        String email = userProfileDTO.email();

        Optional<UserProfile> existingUser = userProfileRepository.findByEmail(email);

        UserProfile userProfile;

        if(existingUser.isPresent()) {
            userProfile = existingUser.get();
            if(userProfile.isEnabled()) {
                throw new DuplicateUserException("Uneti email je vec registrovan");
            }
        } else {
            userProfile = mapper.map(userProfileDTO);
            userProfile.setUserRole(userRoleRepository.findByName(USER_ROLE));
            userProfile.setPassword(passwordEncoder.encode(userProfile.getPassword()));
            userProfileRepository.save(userProfile);
        }

        String jwtToken = jwtUtil.issueShortToken(email, userProfile.getUserRole().getName());
        String link = CONFIRMATION_LINK + jwtToken;
        emailSender.send(email, buildEmail(link));

        return mapper.map(userProfile, jwtToken);
    }


    @Override
    @Transactional(readOnly = true)
    public UserProfileResponseDTO getById(Long id) {
        var userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class.getSimpleName(), "id", id));

        return mapper.map(userProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserProfileResponseDTO> get(Pageable pageable) {
        return userProfileRepository.findAll(pageable).map(mapper::map);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!userProfileRepository.existsById(id)){
            throw new EntityNotFoundException(UserProfile.class.getSimpleName(),"id",id);
        }

        userProfileRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserProfileResponseDTO updateUserRole(Long id, UserProfileUpdateRoleRequestDTO userProfileDTO) {
        var userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class.getSimpleName(), "id", id));

        mapper.updateUserRole(userProfile, userProfileDTO);

        userProfileRepository.save(userProfile);

        return mapper.map(userProfile);
    }

    @Override
    @Transactional
    public UserProfileResponseDTO update(Long id, UserProfileRequestUpdateDTO userProfileDTO) {
        var userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class.getSimpleName(),"id",id));

        mapper.update(userProfile, userProfileDTO);

        userProfileRepository.save(userProfile);

        return mapper.map(userProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponseDTO getByEmail(String email) {
        var userProfile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class.getSimpleName(), "email", email));

        return mapper.map(userProfile);
    }

    @Override
    public UserProfileResponseDTO enableProfile(String token) {
        String email = jwtUtil.getSubject(token);

        var userProfile = userProfileRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(UserProfile.class.getSimpleName(), "email", email));

        userProfile.setEnabled(true);

        userProfileRepository.save(userProfile);

        return mapper.map(userProfile);
    }

    private String buildEmail(String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Potvrda email adrese</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Poštovani" + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Hvala Vam što ste započeli proces registracije. Da biste se uspešno registrovali, potrebno je da potvrdite svoju e-mail adresu klikom na link ispod: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Potvrdi</a> </p></blockquote>\n Link ističe za 15 minuta. <p>Pozdrav</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}
