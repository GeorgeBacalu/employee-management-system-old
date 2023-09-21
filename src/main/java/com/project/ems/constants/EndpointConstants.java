package com.project.ems.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EndpointConstants {

    public static final String API_AUTHORITIES = "/api/authorities";
    public static final String API_EMPLOYEES = "/api/employees";
    public static final String API_EXPERIENCES = "/api/experiences";
    public static final String API_FEEDBACKS = "/api/feedbacks";
    public static final String API_MENTORS = "/api/mentors";
    public static final String API_ROLES = "/api/roles";
    public static final String API_STUDIES = "/api/studies";
    public static final String API_USERS = "/api/users";

    public static final String AUTHORITIES = "/authorities";
    public static final String EMPLOYEES = "/employees";
    public static final String EXPERIENCES = "/experiences";
    public static final String FEEDBACKS = "/feedbacks";
    public static final String MENTORS = "/mentors";
    public static final String ROLES = "/roles";
    public static final String STUDIES = "/studies";
    public static final String USERS = "/users";

    public static final String API_PAGINATION = "/pagination?page={page}&size={size}&sort={field},{direction}&key={key}";
    public static final String API_PAGINATION_V2 = "/pagination?page=%s&size=%s&sort=%s,%s&key=%s";
}
