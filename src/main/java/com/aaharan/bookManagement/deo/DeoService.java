package com.aaharan.bookManagement.deo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface DeoService {

    DeoDto updateByUserId(Deo obj, int id);

    DeoDto getByID(int id);

    List<DeoDto> getAll(Pageable pageable);


    DeoDto getByUserId(int userId);

    void delete(int id);

    List<DeoDto> getAllByCriteria(Specification<Deo> specification, Pageable pageable);

    void updateApprovalStatus(Boolean isApproved, int deoUserId);
}
