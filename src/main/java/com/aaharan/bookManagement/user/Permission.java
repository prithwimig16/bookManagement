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

    PVT_SCHOOL_CREATE("pvt_school:create"),
    PVT_SCHOOL_READ("pvt_school:read"),
    PVT_SCHOOL_UPDATE("pvt_school:update"),
    PVT_SCHOOL_DELETE("pvt_school:delete"),

    GOVT_SCHOOL_CREATE("govt_school:create"),
    GOVT_SCHOOL_READ("govt_school:read"),
    GOVT_SCHOOL_UPDATE("govt_school:update"),
    GOVT_SCHOOL_DELETE("govt_school:delete"),

    DEO_CREATE("deo_school:create"),
    DEO_READ("deo_school:read"),
    DEO_UPDATE("deo_school:update"),
    DEO_DELETE("deo_school:delete")
    



    ;

    @Getter
    private final String permission;
}
