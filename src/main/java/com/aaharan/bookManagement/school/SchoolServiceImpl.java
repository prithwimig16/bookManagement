package com.aaharan.bookManagement.school;

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
public class SchoolServiceImpl implements SchoolService {

    private @Resource SchoolRepository repository;

    @Override
    public School updateByUserId(School obj, int id) {
        return repository.findByUserId(id)
                .map(it -> {
                    it.setSchoolName(obj.getSchoolName());
                    it.setAddress(obj.getAddress());
                    it.setPinCode(obj.getPinCode());
                    it.setGovt(obj.isGovt());
                    it.setDistrict(obj.getDistrict());
                    it.setUpdatedAt(LocalDateTime.now());
                    return repository.save(it);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Id does not exist"));
    }


    @Override
    public School getByID(int id) {
        return this.repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Id does not exist"));
    }

    @Override
    public List<School> getAll(Pageable pageable) {
//        List<School> schoolList = this.repository.findAll(pageable).getContent();
//        return schoolList;
        return this.repository.findAll(pageable).getContent();
    }


    @Override
    public School getByUserId(int userId) {
        return repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User id doesn't exist"));
    }


    @Override
    public void delete(int id) {
        School school = this.repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Id doesn't exist"));
        this.repository.delete(school);
    }

    @Override
    public List<School> getAllByCriteria(Specification<School> specification, Pageable pageable) {
        return this.repository.findAll(specification, pageable);
    }

    @Override
    public void updateApprovalStatus(Boolean isApproved, int schoolUserId) {
        repository.updateApprovalStatus(isApproved, schoolUserId);
    }

}

