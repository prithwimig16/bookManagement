package com.aaharan.bookManagement.is;

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
public class IsServiceImpl implements IsService {

    private @Resource IsRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public IsDto updateByUserId(Is obj, int id) {
        return repository.findByUserId(id)
                .map(it -> {
                    it.setAddress(obj.getAddress());
                    it.setPinCode(obj.getPinCode());
                    it.setDistrict(obj.getDistrict());
                    it.setUpdatedAt(LocalDateTime.now());
                    repository.save(it);
                    return this.modelMapper.map(it, IsDto.class);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Id does not exist"));
    }


    @Override
    public IsDto getByID(int id) {
        Is isObj = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Is id doesn't exist"));
        return this.modelMapper.map(isObj, IsDto.class);

    }

    @Override
    public List<IsDto> getAll(Pageable pageable) {
        List<Is> schoolList = this.repository.findAll(pageable).getContent();
        List<IsDto> resultList = new ArrayList<>();
        schoolList.forEach(it -> {
            IsDto isDto = this.modelMapper.map(it, IsDto.class);
            resultList.add(isDto);

        });
        return resultList;
    }


    @Override
    public IsDto getByUserId(int userId) {
        Is isObj = repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User id doesn't exist"));
        return this.modelMapper.map(isObj, IsDto.class);

    }


    @Override
    public void delete(int id) {
        Is isObj = this.repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Id doesn't exist"));
        this.repository.delete(isObj);
    }

    @Override
    public List<IsDto> getAllByCriteria(Specification<Is> specification, Pageable pageable) {
        List<Is> allIsList = this.repository.findAll(specification, pageable);
        List<IsDto> resultList = new ArrayList<>();

        allIsList.forEach(it -> {
            resultList.add(this.modelMapper.map(it, IsDto.class));
        });
        return resultList;
    }

    @Override
    public void updateApprovalStatus(Boolean isApproved, int isObjUserId) {
        repository.updateApprovalStatus(isApproved, isObjUserId);
    }

}

