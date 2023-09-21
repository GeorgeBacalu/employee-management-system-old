package com.project.ems.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessageConstants {

    public static final String RESOURCE_NOT_FOUND = "Resource not found: %s";
    public static final String INVALID_REQUEST = "Invalid request: %s";

    public static final String AUTHORITY_NOT_FOUND = "Authority with id %s not found";
    public static final String EMPLOYEE_NOT_FOUND = "Employee with id %s not found";
    public static final String EXPERIENCE_NOT_FOUND = "Experience with id %s not found";
    public static final String FEEDBACK_NOT_FOUND = "Feedback with id %s not found";
    public static final String MENTOR_NOT_FOUND = "Mentor with id %s not found";
    public static final String ROLE_NOT_FOUND = "Role with id %s not found";
    public static final String STUDY_NOT_FOUND = "Study with id %s not found";
    public static final String USER_NOT_FOUND = "User with id %s not found";

    public static final String USERNAME_NOT_FOUND = "User %s was not found";
}
