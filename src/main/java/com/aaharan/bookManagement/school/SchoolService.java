package com.aaharan.bookManagement.school;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SchoolService {

    School updateByUserId(School obj, int id);

    School getByID(int id);

    List<School> getAll(Pageable pageable);


    School getByUserId(int userId);

    void delete(int id);

    List<School> getAllByCriteria(Specification<School> specification, Pageable pageable);

    void updateApprovalStatus(Boolean isApproved, int schoolUserId);
}
