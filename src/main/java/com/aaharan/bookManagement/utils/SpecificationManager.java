package com.aaharan.bookManagement.utils;

import com.aaharan.bookManagement.deo.Deo;
import com.aaharan.bookManagement.school.School;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class SpecificationManager {

    public static Specification<School> getSchoolSpec(String district, String experience, boolean isGovt) {
        return ((root, query, criteriaBuilder) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if (district != null && !(district.isEmpty()))
                predicates.add(criteriaBuilder.equal(root.get("user").get("address").get("pinCode"), district));
            if (experience != null)
                predicates.add(criteriaBuilder.equal(root.get("experience"), experience));
            predicates.add(criteriaBuilder.equal(root.get("isGovt"), isGovt));
            predicates.add(criteriaBuilder.equal(root.get("isUpdated"), Boolean.TRUE));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

//    public static Specification<School> getDoctorSpecFromClinic(String district) {
//        return ((root, query, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            if (district != null && !(district.isEmpty()))
//                predicates.add(criteriaBuilder.equal(root.get("address").get("district"), district));
//
//            predicates.add(criteriaBuilder.equal(root.get("school").get("isUpdated"), Boolean.TRUE));
//            predicates.add(criteriaBuilder.equal(root.get("school").get("isApproved"), Boolean.TRUE));
//            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//        });
//    }

    public static Specification<Deo> getDeoSpec(String district) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (district != null && !(district.isEmpty()))
                predicates.add(criteriaBuilder.equal(root.get("address").get("district"), district));
            predicates.add(criteriaBuilder.equal(root.get("deo").get("isUpdated"), Boolean.TRUE));
            predicates.add(criteriaBuilder.equal(root.get("deo").get("isApproved"), Boolean.TRUE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        });
    }

//    public static Specification<Drug> getDrugSpec(String keywords, Boolean isDrugName) {
//        return ((root, query, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//            if (keywords != null && !(keywords.isEmpty()) && isDrugName)
//                predicates.add(criteriaBuilder.like(root.get("drugName"), "%" + keywords + "%"));
//            if (keywords != null && !(keywords.isEmpty()) && !isDrugName)
//                predicates.add(criteriaBuilder.like(root.get("brand").get("brandName"), "%" + keywords + "%"));
//            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//        });
//    }
//
//    public static Specification<MedicalTest> getMedTestSpec(String keywords) {
//        return ((root, query, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//            if (keywords != null && !(keywords.isEmpty()))
//                predicates.add(criteriaBuilder.like(root.get("medTestName"), "%" + keywords + "%"));
//            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//        });
//    }
}
