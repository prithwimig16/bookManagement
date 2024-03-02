package com.aaharan.bookManagement.deo;

import com.aaharan.bookManagement.school.School;
import com.aaharan.bookManagement.school.SchoolRepository;
import com.aaharan.bookManagement.school.SchoolService;
import com.amazonaws.services.dlm.model.ResourceNotFoundException;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class DeoServiceImpl implements DeoService {

    private @Resource DeoRepository repository;

    @Override
    public Deo updateByUserId(Deo obj, int id) {
        return repository.findByUserId(id)
                .map(it -> {
                    it.setAddress(obj.getAddress());
                    it.setPinCode(obj.getPinCode());
                    it.setDistrict(obj.getDistrict());
                    it.setUpdatedAt(LocalDateTime.now());
                    return repository.save(it);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Id does not exist"));
    }


    @Override
    public Deo getByID(int id) {
        return this.repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Id does not exist"));
    }

    @Override
    public List<Deo> getAll(Pageable pageable) {
        return this.repository.findAll(pageable).getContent();
    }


    @Override
    public Deo getByUserId(int userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User id doesn't exist"));
    }


    @Override
    public void delete(int id) {
        Deo deo = this.repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Id doesn't exist"));
        this.repository.delete(deo);
    }

    @Override
    public List<Deo> getAllByCriteria(Specification<Deo> specification, Pageable pageable) {
        return this.repository.findAll(specification, pageable);
    }

    @Override
    public void updateApprovalStatus(Boolean isApproved, int deoUserId) {
        repository.updateApprovalStatus(isApproved, deoUserId);
    }

}

