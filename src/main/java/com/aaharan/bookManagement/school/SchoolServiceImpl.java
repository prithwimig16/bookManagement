package com.aaharan.bookManagement.school;

import com.amazonaws.services.dlm.model.ResourceNotFoundException;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {

    private @Resource SchoolRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public SchoolDto updateByUserId(School obj, int id) {
        return repository.findByUserId(id)
                .map(it -> {
                    it.setSchoolName(obj.getSchoolName());
                    it.setAddress(obj.getAddress());
                    it.setPinCode(obj.getPinCode());
                    it.setGovt(obj.isGovt());
                    it.setIsUpdated(true);
                    it.setDistrict(obj.getDistrict());
                    it.setUpdatedAt(LocalDateTime.now());
                    repository.save(it);
                   return this.modelMapper.map(it, SchoolDto.class);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Id does not exist"));
    }


    @Override
    public SchoolDto getByID(int id) {
        School schoolObj = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School id doesn't exist"));
        return this.modelMapper.map(schoolObj, SchoolDto.class);
    }

    @Override
    public List<SchoolDto> getAll(Pageable pageable) {
        List<School> schoolList = this.repository.findAll(pageable).getContent();
        List<SchoolDto>resultList=new ArrayList<>();
        schoolList.forEach(it->{
            SchoolDto schoolDto = this.modelMapper.map(it, SchoolDto.class);
            resultList.add(schoolDto);

        });
        return resultList;
    }


    @Override
    public SchoolDto getByUserId(int userId) {
        School schoolObj = repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("School id doesn't exist"));
        return this.modelMapper.map(schoolObj, SchoolDto.class);
    }


    @Override
    public void delete(int id) {
        School school = this.repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Id doesn't exist"));
        this.repository.delete(school);
    }

    @Override
    public List<SchoolDto> getAllByCriteria(Specification<School> specification, Pageable pageable) {
        List<School> allIsList = this.repository.findAll(specification, pageable);
        List<SchoolDto> resultList = new ArrayList<>();

        allIsList.forEach(it -> {
            resultList.add(this.modelMapper.map(it, SchoolDto.class));
        });
        return resultList;
    }

    @Override
    public void updateApprovalStatus(Boolean isApproved, int schoolUserId) {
        repository.updateApprovalStatus(isApproved, schoolUserId);
    }

}

