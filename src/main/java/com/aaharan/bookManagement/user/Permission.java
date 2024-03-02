package com.aaharan.bookManagement.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    IS_READ("is:read"),
    IS_UPDATE("is:update"),
    IS_CREATE("is:create"),
    IS_DELETE("is:delete"),

//    PVT_SCHOOL_CREATE("pvt_school:create"),
//    PVT_SCHOOL_READ("pvt_school:read"),
//    PVT_SCHOOL_UPDATE("pvt_school:update"),
//    PVT_SCHOOL_DELETE("pvt_school:delete"),

    SCHOOL_CREATE("school:create"),
    SCHOOL_READ("school:read"),
    SCHOOL_UPDATE("school:update"),
    SCHOOL_DELETE("school:delete"),

    DEO_CREATE("deo:create"),
    DEO_READ("deo:read"),
    DEO_UPDATE("deo:update"),
    DEO_DELETE("deo:delete")
    



    ;

    @Getter
    private final String permission;
}
