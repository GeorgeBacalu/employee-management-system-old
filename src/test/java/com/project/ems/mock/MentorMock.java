package com.project.ems.mock;

import com.project.ems.employee.enums.EmploymentType;
import com.project.ems.employee.enums.Grade;
import com.project.ems.employee.enums.Position;
import com.project.ems.mentor.Mentor;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences2;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.StudyMock.getMockedStudies1;
import static com.project.ems.mock.StudyMock.getMockedStudies2;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MentorMock {

    public static List<Mentor> getMockedMentors() {
        return List.of(getMockedMentor1(), getMockedMentor2());
    }

    public static Mentor getMockedMentor1() {
        return Mentor.builder()
              .id(1)
              .name("Mentor_name1")
              .email("Mentor_email1@email.com")
              .password("#Mentor_password1")
              .mobile("+40700000001")
              .address("Mentor_address1")
              .birthday(LocalDate.of(1990, 1, 1))
              .role(getMockedRole1())
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.FRONTEND)
              .grade(Grade.SENIOR)
              .supervisingMentor(null)
              .studies(getMockedStudies1())
              .experiences(getMockedExperiences1())
              .nrTrainees(3)
              .maxTrainees(6)
              .isTrainingOpen(false)
              .build();
    }

    public static Mentor getMockedMentor2() {
        return Mentor.builder()
              .id(2)
              .name("Mentor_name2")
              .email("Mentor_email2@email.com")
              .password("#Mentor_password2")
              .mobile("+40700000002")
              .address("Mentor_address2")
              .birthday(LocalDate.of(1990, 1, 2))
              .role(getMockedRole2())
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.BACKEND)
              .grade(Grade.SENIOR)
              .supervisingMentor(getMockedMentor1())
              .studies(getMockedStudies2())
              .experiences(getMockedExperiences2())
              .nrTrainees(2)
              .maxTrainees(5)
              .isTrainingOpen(true)
              .build();
    }
}
