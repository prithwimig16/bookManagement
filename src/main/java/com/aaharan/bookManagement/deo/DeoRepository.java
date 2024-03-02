package com.aaharan.bookManagement.deo;
import com.aaharan.bookManagement.school.School;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DeoRepository extends JpaRepository<Deo, Integer> {

    Optional<Deo> findByUserId(int userId);



    List<Deo> findAll(Specification<Deo> specification, Pageable pageable);

    @Modifying
    @Query("update Deo d set d.isApproved=?1 where d.user.id=?2 ")
    void updateApprovalStatus(Boolean status, Integer id);
}
