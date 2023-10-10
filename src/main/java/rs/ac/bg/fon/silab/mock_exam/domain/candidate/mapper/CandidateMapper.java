package rs.ac.bg.fon.silab.mock_exam.domain.candidate.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.dto.CandidateResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;
import rs.ac.bg.fon.silab.mock_exam.domain.school.service.SchoolService;
import rs.ac.bg.fon.silab.mock_exam.domain.userprofile.service.UserProfileService;

@Mapper(componentModel = "spring",uses = {SchoolService.class, UserProfileService.class})
public interface CandidateMapper {
    @Mapping(source = "school",target = "school")
    @Mapping(source = "userProfile.email", target = "userProfile" )
    Candidate map(CandidateRequestDTO candidateDTO);

    CandidateResponseDTO map(Candidate candidate);


    void update(@MappingTarget Candidate candidate, CandidateRequestDTO candidateDTO);
}
