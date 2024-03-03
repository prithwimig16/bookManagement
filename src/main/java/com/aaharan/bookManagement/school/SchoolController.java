package com.aaharan.bookManagement.school;
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

@Api(tags = "Schools")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/school")
public class SchoolController {
    @Autowired
    private SchoolService service;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "This method getting all the list of schools available in the system")
    public GenericResponse<List<SchoolDto>> getAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                @RequestParam(defaultValue = "5", required = false) Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return GenericResponse.success(new ArrayList<>(service.getAll(pageable)));
    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL')")
    @ApiOperation(value = "Update a particular school by id")
    public GenericResponse<SchoolDto> updateByUserId(@RequestBody School school, @PathVariable int id) {
      return service.updateByUserId(school, id);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/approve/{schoolUserId}")
    public GenericResponse<?> updateSchoolStatus(@PathVariable(name = "schoolUserId") int schoolUserId, @RequestParam Boolean isApproved) {
        String msg = "School Approval Status Updated Successfully";
        service.updateApprovalStatus(isApproved, schoolUserId);
        return GenericResponse.success(msg);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete School by id")
    public GenericResponse<String> deleteSchool(@PathVariable int id) {
        this.service.delete(id);
        return GenericResponse.success("School deleted successfully");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL')")
    @ApiOperation(value = "Get a particular school by id")
    public GenericResponse<SchoolDto> getByID(@PathVariable int id) {
        return GenericResponse.success(this.service.getByID(id));
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL')")
    @ApiOperation(value = "Get a particular school by User id")
    public GenericResponse<SchoolDto> getByUserID(@PathVariable int id) {
        return GenericResponse.success(this.service.getByUserId(id));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL')")
    @ApiOperation(value = "Search school by different criteria")
    public GenericResponse<List<SchoolDto>> getAllByCriteria(@RequestParam(value = "district", required = false) String district,
                                                          @RequestParam(value = "pinCode", required = false) String pinCode,
                                                          @RequestParam(value = "isGovt", required = false) boolean isGovt,
                                                          @RequestParam(defaultValue = "0", required = false) Integer page,
                                                          @RequestParam(defaultValue = "5", required = false) Integer pageSize) {
        Specification<School> specification = SpecificationManager.getSchoolSpec(district, pinCode, isGovt);
        Pageable pageable = PageRequest.of(page, pageSize);
        List<SchoolDto> schoolList = new ArrayList<>(service.getAllByCriteria(specification, pageable));
        return GenericResponse.success(schoolList);
    }

}
