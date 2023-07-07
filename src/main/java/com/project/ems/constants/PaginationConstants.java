package com.project.ems.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationConstants {

    public static final String EMPLOYEE_FILTER_QUERY = "select e from Employee e where lower(concat(e.name, ' ', e.email, ' ', e.mobile, ' ', e.address, ' ', e.birthday, ' ', e.role.authority, ' ', e.employmentType, ' ', e.position, ' ', e.grade, ' ', e.mentor.name)) like %:key%";
    public static final String EXPERIENCE_FILTER_QUERY = "select e from Experience e where lower(concat(e.title, ' ', e.organization, ' ', e.description, ' ', e.type, ' ', e.startedAt, ' ', e.finishedAt)) like %:key%";
    public static final String FEEDBACK_FILTER_QUERY = "select f from Feedback f where lower(concat(f.type, ' ', f.description, ' ', f.sentAt, ' ', f.user.name)) like %:key%";
    public static final String MENTOR_FILTER_QUERY = "select m from Mentor m left join m.supervisingMentor sm where lower(concat(m.name, ' ', m.email, ' ', m.mobile, ' ', m.address, ' ', m.birthday, ' ', m.role.authority, ' ', m.employmentType, ' ', m.position, ' ', m.grade, ' ', coalesce(sm.name, ''), ' ', m.nrTrainees, ' ', m.maxTrainees, ' ', m.isTrainingOpen)) like %:key%";
    public static final String STUDY_FILTER_QUERY = "select s from Study s where lower(concat(s.title, ' ', s.institution, ' ', s.description, ' ', s.type, ' ', s.startedAt, ' ', s.finishedAt)) like %:key%";
    public static final String USER_FILTER_QUERY = "select u from User u where lower(concat(u.name, ' ', u.email, ' ', u.mobile, ' ', u.address, ' ', u.birthday, ' ', u.role.authority)) like %:key%";

    public static final String EMPLOYEE_FILTER_KEY = "front";
    public static final String EXPERIENCE_FILTER_KEY = "intern";
    public static final String FEEDBACK_FILTER_KEY = "optimization";
    public static final String MENTOR_FILTER_KEY = "front";
    public static final String STUDY_FILTER_KEY = "special";
    public static final String USER_FILTER_KEY = "admin";

    public static final Pageable pageable = PageRequest.of(0, 2, Sort.Direction.ASC, "id");
    public static final Pageable pageable2 = PageRequest.of(1, 2, Sort.Direction.ASC, "id");
    public static final Pageable pageable3 = PageRequest.of(2, 2, Sort.Direction.ASC, "id");
}
