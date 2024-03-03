package com.aaharan.bookManagement.school;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Integer> {

    Optional<School> findByUserId(int userId);


    boolean existsBySchoolName(String schoolName);
    List<School> findAll(Specification<School> specification, Pageable pageable);

    @Modifying
    @Query("update School s set s.isApproved=?1 where s.user.id=?2 ")
    void updateApprovalStatus(Boolean status, Integer id);
}
