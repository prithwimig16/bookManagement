package com.aaharan.bookManagement.deo;
import com.aaharan.bookManagement.school.School;
import com.aaharan.bookManagement.school.SchoolService;
import com.aaharan.bookManagement.utils.GenericResponse;
import com.aaharan.bookManagement.utils.SpecificationManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "Deo")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/deo")
public class DeoController {
    @Autowired
    private DeoService service;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "This method getting all the list of schools available in the system")
    public GenericResponse<List<DeoDto>> getAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                @RequestParam(defaultValue = "5", required = false) Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return GenericResponse.success(new ArrayList<>(service.getAll(pageable)));
    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEO')")
    @ApiOperation(value = "Update a particular school by id")
    public GenericResponse<DeoDto> updateByUserId(@RequestBody  Deo deo, @PathVariable int id) {
        DeoDto updatedResult = service.updateByUserId(deo, id);
        return GenericResponse.success(updatedResult);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/approve/{deoUserId}")
    public GenericResponse<?> updateSchoolStatus(@PathVariable(name = "deoUserId") int deoUserId, @RequestParam Boolean isApproved) {
        String msg = "Deo Approval Status Updated Successfully";
        service.updateApprovalStatus(isApproved, deoUserId);
        return GenericResponse.success(msg);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete Deo by id")
    public GenericResponse<String> deleteDeo(@PathVariable int id) {
        this.service.delete(id);
        return GenericResponse.success("Deo deleted successfully");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DEO')")
    @ApiOperation(value = "Get a particular deo by id")
    public GenericResponse<DeoDto> getByID(@PathVariable int id) {
        return GenericResponse.success(this.service.getByID(id));
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL')")
    @ApiOperation(value = "Get a particular school by User id")
    public GenericResponse<DeoDto> getByUserID(@PathVariable int id) {
        return GenericResponse.success(this.service.getByUserId(id));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL')")
    @ApiOperation(value = "Search deo by different criteria")
    public GenericResponse<List<DeoDto>> getAllByCriteria(@RequestParam(value = "district", required = false) String district,
                                                          @RequestParam(defaultValue = "0", required = false) Integer page,
                                                          @RequestParam(defaultValue = "5", required = false) Integer pageSize) {
        Specification<Deo> specification = SpecificationManager.getDeoSpec(district);
        Pageable pageable = PageRequest.of(page, pageSize);
        List<DeoDto> resultList = new ArrayList<>(service.getAllByCriteria(specification, pageable));
        return GenericResponse.success(resultList);
    }

}
