package com.aaharan.bookManagement.is;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IsRepository extends JpaRepository<Is, Integer> {

    Optional<Is> findByUserId(int userId);

    boolean existsByPinCode(String pinCode);

    boolean existsByAddress(String address);

    List<Is> findAll(Specification<Is> specification, Pageable pageable);

    @Modifying
    @Query("update Is i set i.isApproved=?1 where i.user.id=?2 ")
    void updateApprovalStatus(Boolean status, Integer id);
}
