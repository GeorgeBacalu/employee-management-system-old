package com.project.ems.mock;

import com.project.ems.employee.Employee;
import com.project.ems.employee.enums.EmploymentType;
import com.project.ems.employee.enums.Grade;
import com.project.ems.employee.enums.Position;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences2;
import static com.project.ems.mock.MentorMock.getMockedMentor1;
import static com.project.ems.mock.MentorMock.getMockedMentor2;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.StudyMock.getMockedStudies1;
import static com.project.ems.mock.StudyMock.getMockedStudies2;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeMock {

    public static List<Employee> getMockedEmployees() {
        return List.of(getMockedEmployee1(), getMockedEmployee2());
    }

    public static Employee getMockedEmployee1() {
        return Employee.builder()
              .id(1)
              .name("Employee_name1")
              .email("Employee_email1@email.com")
              .password("#Employee_password1")
              .mobile("40700000001")
              .address("Employee_address1")
              .birthday(LocalDate.of(2000, 1, 1))
              .role(getMockedRole1())
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.FRONTEND)
              .grade(Grade.MID)
              .mentor(getMockedMentor1())
              .studies(getMockedStudies1())
              .experiences(getMockedExperiences1())
              .build();
    }

    public static Employee getMockedEmployee2() {
        return Employee.builder()
              .id(2)
              .name("Employee_name2")
              .email("Employee_email2@email.com")
              .password("#Employee_password2")
              .mobile("40700000002")
              .address("Employee_address2")
              .birthday(LocalDate.of(2000, 1, 2))
              .role(getMockedRole2())
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.BACKEND)
              .grade(Grade.JUNIOR)
              .mentor(getMockedMentor2())
              .studies(getMockedStudies2())
              .experiences(getMockedExperiences2())
              .build();
    }
}
