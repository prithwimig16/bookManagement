package com.aaharan.bookManagement.is;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IsService {

    IsDto updateByUserId(Is obj, int id);

    IsDto getByID(int id);

    List<IsDto> getAll(Pageable pageable);


    IsDto getByUserId(int userId);

    void delete(int id);

    List<IsDto> getAllByCriteria(Specification<Is> specification, Pageable pageable);

    void updateApprovalStatus(Boolean isApproved, int IsUserId);
}
