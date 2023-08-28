package rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolRequestDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.dto.TypeOfSchoolResponseDTO;
import rs.ac.bg.fon.silab.mock_exam.domain.typeofschool.entity.TypeOfSchool;

public interface TypeOfSchoolService {

    TypeOfSchool findByName(String name);

    TypeOfSchoolResponseDTO save(TypeOfSchoolRequestDTO typeOfSchoolDTO);

    TypeOfSchoolResponseDTO getById(Long id);

    Page<TypeOfSchoolResponseDTO> get(Pageable pageable);

    void delete(Long id);

    TypeOfSchoolResponseDTO update(Long id, TypeOfSchoolRequestDTO typeOfSchoolDTO);
}
