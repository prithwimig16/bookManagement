package com.aaharan.bookManagement.school;

import com.aaharan.bookManagement.utils.GenericResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SchoolService {

    GenericResponse<SchoolDto> updateByUserId(School obj, int id);

    SchoolDto getByID(int id);

    List<SchoolDto> getAll(Pageable pageable);


    SchoolDto getByUserId(int userId);

    void delete(int id);

    List<SchoolDto> getAllByCriteria(Specification<School> specification, Pageable pageable);

    void updateApprovalStatus(Boolean isApproved, int schoolUserId);
}
