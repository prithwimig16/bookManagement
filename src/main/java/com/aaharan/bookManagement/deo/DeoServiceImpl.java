package com.aaharan.bookManagement.deo;

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
public class DeoServiceImpl implements DeoService {

    private @Resource DeoRepository repository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DeoDto updateByUserId(Deo obj, int id) {
        return repository.findByUserId(id)
                .map(it -> {
                    it.setAddress(obj.getAddress());
                    it.setPinCode(obj.getPinCode());
                    it.setDistrict(obj.getDistrict());
                    it.setUpdatedAt(LocalDateTime.now());
                    repository.save(it);
                    return this.modelMapper.map(it, DeoDto.class);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Id does not exist"));
    }


    @Override
    public DeoDto getByID(int id) {
        Deo obj = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("School id doesn't exist"));
        return this.modelMapper.map(obj, DeoDto.class);
    }

    @Override
    public List<DeoDto> getAll(Pageable pageable) {
        List<Deo> deoList = this.repository.findAll(pageable).getContent();
        List<DeoDto>resultList=new ArrayList<>();
        deoList.forEach(it->{
            DeoDto deoDto = this.modelMapper.map(it, DeoDto.class);
            resultList.add(deoDto);

        });
        return resultList;
    }


    @Override
    public DeoDto getByUserId(int userId) {
        Deo Obj = repository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("School id doesn't exist"));
        return this.modelMapper.map(Obj, DeoDto.class);
    }


    @Override
    public void delete(int id) {
        Deo deo = this.repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Id doesn't exist"));
        this.repository.delete(deo);
    }

    @Override
    public List<DeoDto> getAllByCriteria(Specification<Deo> specification, Pageable pageable) {
        List<Deo> allIsList = this.repository.findAll(specification, pageable);
        List<DeoDto> resultList = new ArrayList<>();

        allIsList.forEach(it -> {
            resultList.add(this.modelMapper.map(it, DeoDto.class));
        });
        return resultList;
    }

    @Override
    public void updateApprovalStatus(Boolean isApproved, int deoUserId) {
        repository.updateApprovalStatus(isApproved, deoUserId);
    }

}

