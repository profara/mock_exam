package rs.ac.bg.fon.silab.mock_exam.domain.candidate.repository;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.bg.fon.silab.mock_exam.domain.candidate.entity.Candidate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Long>, JpaSpecificationExecutor<Candidate> {
    Optional<Candidate> findByUserProfile_Email(String email);

    @Query("SELECT app.candidate FROM Application app JOIN app.appointments apt WHERE apt.id = :appointmentId")
    Page<Candidate> findByAppointmentId(@Param("appointmentId") Long appointmentId, Pageable pageable);

    @Query("SELECT app.candidate FROM Application app JOIN app.appointments apt WHERE apt.id = :appointmentId " +
            "AND (:zipCode IS NULL OR app.candidate.city.zipCode = :zipCode) " +
            "AND (:schoolCode IS NULL OR app.candidate.school.code = :schoolCode) " +
            "AND (:attendedPreparation IS NULL OR app.candidate.attendedPreparation = :attendedPreparation)")
    List<Candidate> findAllByAppointmentId(@Param("appointmentId") Long appointmentId,
                                           @Param("zipCode") Long zipCode,
                                           @Param("schoolCode") Long schoolCode,
                                           @Param("attendedPreparation") Boolean attendedPreparation);

    default Page<Candidate> filterByCriteria(Long zipCode, Long schoolCode, Boolean attendedPreparation, Pageable pageable){
        return findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(zipCode != null){
                predicates.add(cb.equal(root.get("city").get("zipCode"), zipCode));
            }

            if(schoolCode != null){
                predicates.add(cb.equal(root.get("school").get("code"), schoolCode));
            }

            if(attendedPreparation != null){
                predicates.add(cb.equal(root.get("attendedPreparation"), attendedPreparation));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

    @Query("SELECT app.candidate FROM Application app JOIN app.appointments apt WHERE apt.id = :appointmentId " +
    "AND (:zipCode IS NULL OR app.candidate.city.zipCode = :zipCode) " +
    "AND (:schoolCode IS NULL OR app.candidate.school.code = :schoolCode) " +
    "AND (:attendedPreparation IS NULL OR app.candidate.attendedPreparation = :attendedPreparation)")
    Page<Candidate> filterByAppointmentIdAndCriteria(@Param("appointmentId")Long appointmentId,
                                                     @Param("zipCode") Long zipCode,
                                                     @Param("schoolCode") Long schoolCode, Boolean attendedPreparation, Pageable pageable);

}
