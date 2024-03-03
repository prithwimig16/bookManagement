package com.aaharan.bookManagement.is;
import com.aaharan.bookManagement.school.School;
import com.aaharan.bookManagement.school.SchoolDto;
import com.aaharan.bookManagement.school.SchoolService;
import com.aaharan.bookManagement.utils.GenericResponse;
import com.aaharan.bookManagement.utils.SpecificationManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "Is")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/is")
public class IsController {
    @Autowired
    private IsService service;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "This method getting all the list of IS available in the system")
    public GenericResponse<List<IsDto>> getAll(@RequestParam(defaultValue = "0", required = false) Integer page,
                                                   @RequestParam(defaultValue = "5", required = false) Integer pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return GenericResponse.success(new ArrayList<>(service.getAll(pageable)));
    }

    @PutMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL')")
    @ApiOperation(value = "Update a particular school by id")
    public GenericResponse<IsDto> updateByUserId(@RequestBody Is isObj, @PathVariable int id) {
        return service.updateByUserId(isObj, id);
        //return GenericResponse.success(updatedSchool);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/approve/{IsUserId}")
    public GenericResponse<?> updateSchoolStatus(@PathVariable(name = "IsUserId") int IsUserId, @RequestParam Boolean isApproved) {
        String msg = "IS Approval Status Updated Successfully";
        service.updateApprovalStatus(isApproved, IsUserId);
        return GenericResponse.success(msg);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete School by id")
    public GenericResponse<String> delete(@PathVariable int id) {
        this.service.delete(id);
        return GenericResponse.success("School deleted successfully");
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL')")
    @ApiOperation(value = "Get a particular school by id")
    public GenericResponse<IsDto> getByID(@PathVariable int id) {
        return GenericResponse.success(this.service.getByID(id));
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL')")
    @ApiOperation(value = "Get a particular school by User id")
    public GenericResponse<IsDto> getByUserID(@PathVariable int id) {
        return GenericResponse.success(this.service.getByUserId(id));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCHOOL')")
    @ApiOperation(value = "Search school by different criteria")
    public GenericResponse<List<IsDto>> getAllByCriteria(@RequestParam(value = "district", required = false) String district,
                                                          @RequestParam(value = "pinCode", required = false) String pinCode,
                                                         @RequestParam(value = "address", required = false) String address,
                                                          @RequestParam(defaultValue = "0", required = false) Integer page,
                                                          @RequestParam(defaultValue = "5", required = false) Integer pageSize) {
        Specification<Is> specification = SpecificationManager.getIsSpec(district, pinCode,address);
        Pageable pageable = PageRequest.of(page, pageSize);
        List<IsDto> schoolList = new ArrayList<>(service.getAllByCriteria(specification, pageable));

        return GenericResponse.success(schoolList);
    }

}
