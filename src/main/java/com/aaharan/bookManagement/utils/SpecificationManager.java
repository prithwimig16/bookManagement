package com.aaharan.bookManagement.utils;

import com.aaharan.bookManagement.book.Book;
import com.aaharan.bookManagement.deo.Deo;
import com.aaharan.bookManagement.is.Is;
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

    public static Specification<Book> getBookSpec(String bookName, String authorName, String className) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (bookName != null && !(bookName.isEmpty()))
                predicates.add(criteriaBuilder.equal(root.get("bookName"), bookName));

            predicates.add(criteriaBuilder.equal(root.get("authorName"),authorName));
            predicates.add(criteriaBuilder.equal(root.get("className"),className));
            predicates.add(criteriaBuilder.equal(root.get("isActive"), Boolean.TRUE));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

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

    public static Specification<Is> getIsSpec(String district,String pinCode,String address) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (pinCode != null && !(pinCode.isEmpty()))
                predicates.add(criteriaBuilder.like(root.get("pinCode"),pinCode));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
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
