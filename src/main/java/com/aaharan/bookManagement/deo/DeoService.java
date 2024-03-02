package com.aaharan.bookManagement.deo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface DeoService {

    Deo updateByUserId(Deo obj, int id);

    Deo getByID(int id);

    List<Deo> getAll(Pageable pageable);


    Deo getByUserId(int userId);

    void delete(int id);

    List<Deo> getAllByCriteria(Specification<Deo> specification, Pageable pageable);

    void updateApprovalStatus(Boolean isApproved, int deoUserId);
}
