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
import static com.project.ems.mock.ExperienceMock.getMockedExperiences3;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences4;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences5;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences6;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences7;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences8;
import static com.project.ems.mock.MentorMock.*;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.StudyMock.getMockedStudies1;
import static com.project.ems.mock.StudyMock.getMockedStudies2;
import static com.project.ems.mock.StudyMock.getMockedStudies3;
import static com.project.ems.mock.StudyMock.getMockedStudies4;
import static com.project.ems.mock.StudyMock.getMockedStudies5;
import static com.project.ems.mock.StudyMock.getMockedStudies6;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeMock {

    public static List<Employee> getMockedEmployees() {
        return List.of(
              getMockedEmployee1(), getMockedEmployee2(), getMockedEmployee3(), getMockedEmployee4(), getMockedEmployee5(), getMockedEmployee6(), getMockedEmployee7(), getMockedEmployee8(), getMockedEmployee9(),
              getMockedEmployee10(), getMockedEmployee11(), getMockedEmployee12(), getMockedEmployee13(), getMockedEmployee14(), getMockedEmployee15(), getMockedEmployee16(), getMockedEmployee17(), getMockedEmployee18(),
              getMockedEmployee19(), getMockedEmployee20(), getMockedEmployee21(), getMockedEmployee22(), getMockedEmployee23(), getMockedEmployee24(), getMockedEmployee25(), getMockedEmployee26(), getMockedEmployee27(),
              getMockedEmployee28(), getMockedEmployee29(), getMockedEmployee30(), getMockedEmployee31(), getMockedEmployee32(), getMockedEmployee33(), getMockedEmployee34(), getMockedEmployee35(), getMockedEmployee36());
    }

    public static List<Employee> getMockedEmployeesPage1() {
        return List.of(getMockedEmployee1(), getMockedEmployee2());
    }

    public static List<Employee> getMockedEmployeesPage2() {
        return List.of(getMockedEmployee3(), getMockedEmployee4());
    }

    public static List<Employee> getMockedEmployeesPage3() {
        return List.of(getMockedEmployee5(), getMockedEmployee6());
    }

    public static Employee getMockedEmployee1() {
        return new Employee(1, "Abigail Johnson", "abigail.johnson@example.com", "#Abigail_Johnson_Password0", "+40754321837", "999 Oak St, Athens, Greece", LocalDate.of(2000, 10, 2), getMockedRole1(), EmploymentType.FULL_TIME, Position.FRONTEND, Grade.JUNIOR, getMockedMentor1(), getMockedStudies1(), getMockedExperiences1());
    }

    public static Employee getMockedEmployee2() {
        return new Employee(2, "Michael Davis", "michael.davis@example.com", "#Michael_Davis_Password0", "+40789012638", "111 Oak St, Madrid, Spain", LocalDate.of(1994, 5, 16), getMockedRole1(), EmploymentType.FULL_TIME, Position.FRONTEND, Grade.JUNIOR, getMockedMentor2(), getMockedStudies2(), getMockedExperiences2());
    }

    public static Employee getMockedEmployee3() {
        return new Employee(3, "Mia Wilson", "mia.wilson@example.com", "#Mia_Wilson_Password0", "+40723145639", "333 Elm St, Tokyo, Japan", LocalDate.of(1990, 12, 29), getMockedRole1(), EmploymentType.FULL_TIME, Position.FRONTEND, Grade.MID, getMockedMentor3(), getMockedStudies3(), getMockedExperiences3());
    }

    public static Employee getMockedEmployee4() {
        return new Employee(4, "James Lee", "james.lee@example.com", "#James_Lee_Password0", "+40787654340", "555 Pine St, Seoul, South Korea", LocalDate.of(1991, 8, 11), getMockedRole1(), EmploymentType.FULL_TIME, Position.FRONTEND, Grade.SENIOR, getMockedMentor4(), getMockedStudies4(), getMockedExperiences4());
    }

    public static Employee getMockedEmployee5() {
        return new Employee(5, "Charlotte Thompson", "charlotte.thompson@example.com", "#Charlotte_Thompson_Password0", "+40754321841", "777 Elm St, Beijing, China", LocalDate.of(1993, 3, 24), getMockedRole1(), EmploymentType.FULL_TIME, Position.BACKEND, Grade.JUNIOR, getMockedMentor5(), getMockedStudies5(), getMockedExperiences5());
    }

    public static Employee getMockedEmployee6() {
        return new Employee(6, "Ethan Smith", "ethan.smith@example.com", "#Ethan_Smith_Password0", "+40789012642", "999 Oak St, Cape Town, South Africa", LocalDate.of(1989, 11, 6), getMockedRole1(), EmploymentType.FULL_TIME, Position.BACKEND, Grade.JUNIOR, getMockedMentor6(), getMockedStudies6(), getMockedExperiences6());
    }

    public static Employee getMockedEmployee7() {
        return new Employee(7, "Amelia Johnson", "amelia.johnson@example.com", "#Amelia_Johnson_Password0", "+40723145643", "111 Elm St, Buenos Aires, Argentina", LocalDate.of(1994, 6, 19), getMockedRole1(), EmploymentType.FULL_TIME, Position.BACKEND, Grade.MID, getMockedMentor7(), getMockedStudies1(), getMockedExperiences7());
    }

    public static Employee getMockedEmployee8() {
        return new Employee(8, "Emily Davis", "emily.davis@example.com", "#Emily_Davis_Password0", "+40787654344", "333 Elm St, Rio de Janeiro, Brazil", LocalDate.of(1998, 1, 1), getMockedRole1(), EmploymentType.FULL_TIME, Position.BACKEND, Grade.SENIOR, getMockedMentor8(), getMockedStudies2(), getMockedExperiences8());
    }

    public static Employee getMockedEmployee9() {
        return new Employee(9, "Henry Wilson", "henry.wilson@example.com", "#Henry_Wilson_Password0", "+40754321845", "555 Pine St, Mexico City, Mexico", LocalDate.of(2001, 8, 14), getMockedRole1(), EmploymentType.FULL_TIME, Position.DEVOPS, Grade.JUNIOR, getMockedMentor9(), getMockedStudies3(), getMockedExperiences1());
    }

    public static Employee getMockedEmployee10() {
        return new Employee(10, "Scarlett Thompson", "scarlett.thompson@example.com", "#Scarlett_Thompson_Password0", "+40789012646", "777 Elm St, Vancouver, Canada", LocalDate.of(2002, 3, 28), getMockedRole1(), EmploymentType.FULL_TIME, Position.DEVOPS, Grade.JUNIOR, getMockedMentor10(), getMockedStudies4(), getMockedExperiences2());
    }

    public static Employee getMockedEmployee11() {
        return new Employee(11, "Jacob Brown", "jacob.brown@example.com", "#Jacob_Brown_Password0", "+40723145647", "999 Pine St, Paris, France", LocalDate.of(1999, 11, 10), getMockedRole1(), EmploymentType.FULL_TIME, Position.DEVOPS, Grade.MID, getMockedMentor11(), getMockedStudies5(), getMockedExperiences3());
    }

    public static Employee getMockedEmployee12() {
        return new Employee(12, "Ava Smith", "ava.smith@example.com", "#Ava_Smith_Password0", "+40787654348", "111 Pine St, London, UK", LocalDate.of(1986, 6, 23), getMockedRole1(), EmploymentType.FULL_TIME, Position.DEVOPS, Grade.SENIOR, getMockedMentor12(), getMockedStudies6(), getMockedExperiences4());
    }

    public static Employee getMockedEmployee13() {
        return new Employee(13, "Oliver Johnson", "oliver.johnson@example.com", "#Oliver_Johnson_Password0", "+40754321849", "333 Elm St, Berlin, Germany", LocalDate.of(1988, 2, 5), getMockedRole1(), EmploymentType.FULL_TIME, Position.TESTING, Grade.JUNIOR, getMockedMentor13(), getMockedStudies1(), getMockedExperiences5());
    }

    public static Employee getMockedEmployee14() {
        return new Employee(14, "Sophia Wilson", "sophia.wilson@example.com", "#Sophia_Wilson_Password0", "+40789012650", "555 Elm St, Moscow, Russia", LocalDate.of(1994, 9, 19), getMockedRole1(), EmploymentType.FULL_TIME, Position.TESTING, Grade.JUNIOR, getMockedMentor14(), getMockedStudies2(), getMockedExperiences6());
    }

    public static Employee getMockedEmployee15() {
        return new Employee(15, "William Davis", "william.davis@example.com", "#William_Davis_Password0", "+40723145651", "777 Pine St, Athens, Greece", LocalDate.of(1996, 4, 3), getMockedRole1(), EmploymentType.FULL_TIME, Position.TESTING, Grade.MID, getMockedMentor15(), getMockedStudies3(), getMockedExperiences7());
    }

    public static Employee getMockedEmployee16() {
        return new Employee(16, "Mia Johnson", "mia.johnson@example.com", "#Mia_Johnson_Password0", "+40787654352", "999 Oak St, Madrid, Spain", LocalDate.of(1998, 11, 16), getMockedRole1(), EmploymentType.FULL_TIME, Position.TESTING, Grade.SENIOR, getMockedMentor16(), getMockedStudies4(), getMockedExperiences8());
    }

    public static Employee getMockedEmployee17() {
        return new Employee(17, "James Lee", "james.lee@example.com", "#James_Lee_Password0", "+40754321853", "111 Elm St, Tokyo, Japan", LocalDate.of(1997, 6, 29), getMockedRole1(), EmploymentType.FULL_TIME, Position.DESIGN, Grade.JUNIOR, getMockedMentor17(), getMockedStudies5(), getMockedExperiences1());
    }

    public static Employee getMockedEmployee18() {
        return new Employee(18, "Charlotte Brown", "charlotte.brown@example.com", "#Charlotte_Brown_Password0", "+40789012654", "333 Pine St, Seoul, South Korea", LocalDate.of(2000, 2, 12), getMockedRole1(), EmploymentType.FULL_TIME, Position.DESIGN, Grade.JUNIOR, getMockedMentor18(), getMockedStudies6(), getMockedExperiences2());
    }

    public static Employee getMockedEmployee19() {
        return new Employee(19, "Ethan Smith", "ethan.smith@example.com", "#Ethan_Smith_Password0", "+40723145655", "555 Elm St, Beijing, China", LocalDate.of(1998, 9, 25), getMockedRole1(), EmploymentType.PART_TIME, Position.DESIGN, Grade.MID, getMockedMentor19(), getMockedStudies1(), getMockedExperiences3());
    }

    public static Employee getMockedEmployee20() {
        return new Employee(20, "Emily Davis", "emily.davis@example.com", "#Emily_Davis_Password0", "+40787654356", "777 Elm St, Cape Town, South Africa", LocalDate.of(2001, 7, 9), getMockedRole1(), EmploymentType.PART_TIME, Position.DESIGN, Grade.SENIOR, getMockedMentor20(), getMockedStudies2(), getMockedExperiences4());
    }

    public static Employee getMockedEmployee21() {
        return new Employee(21, "Jacob Thompson", "jacob.thompson@example.com", "#Jacob_Thompson_Password0", "+40754321857", "999 Pine St, Buenos Aires, Argentina", LocalDate.of(1996, 2, 22), getMockedRole1(), EmploymentType.PART_TIME, Position.DATA_ANALYST, Grade.JUNIOR, getMockedMentor21(), getMockedStudies3(), getMockedExperiences5());
    }

    public static Employee getMockedEmployee22() {
        return new Employee(22, "Sophia Wilson", "sophia.wilson@example.com", "#Sophia_Wilson_Password0", "+40789012658", "111 Elm St, Rio de Janeiro, Brazil", LocalDate.of(1995, 10, 7), getMockedRole1(), EmploymentType.PART_TIME, Position.DATA_ANALYST, Grade.JUNIOR, getMockedMentor22(), getMockedStudies4(), getMockedExperiences6());
    }

    public static Employee getMockedEmployee23() {
        return new Employee(23, "Oliver Johnson", "oliver.johnson@example.com", "#Oliver_Johnson_Password0", "+40723145659", "333 Elm St, Mexico City, Mexico", LocalDate.of(1998, 7, 21), getMockedRole1(), EmploymentType.PART_TIME, Position.DATA_ANALYST, Grade.MID, getMockedMentor23(), getMockedStudies5(), getMockedExperiences7());
    }

    public static Employee getMockedEmployee24() {
        return new Employee(24, "Scarlett Wilson", "scarlett.wilson@example.com", "#Scarlett_Wilson_Password0", "+40787654360", "555 Elm St, Vancouver, Canada", LocalDate.of(2001, 5, 5), getMockedRole1(), EmploymentType.PART_TIME, Position.DATA_ANALYST, Grade.SENIOR, getMockedMentor24(), getMockedStudies6(), getMockedExperiences8());
    }

    public static Employee getMockedEmployee25() {
        return new Employee(25, "William Davis", "william.davis@example.com", "#William_Davis_Password0", "+40754321861", "777 Pine St, Paris, France", LocalDate.of(1999, 12, 17), getMockedRole1(), EmploymentType.PART_TIME, Position.MACHINE_LEARNING, Grade.JUNIOR, getMockedMentor25(), getMockedStudies1(), getMockedExperiences1());
    }

    public static Employee getMockedEmployee26() {
        return new Employee(26, "Mia Johnson", "mia.johnson@example.com", "#Mia_Johnson_Password0", "+40789012662", "999 Oak St, London, UK", LocalDate.of(2002, 9, 30), getMockedRole1(), EmploymentType.PART_TIME, Position.MACHINE_LEARNING, Grade.JUNIOR, getMockedMentor26(), getMockedStudies2(), getMockedExperiences2());
    }

    public static Employee getMockedEmployee27() {
        return new Employee(27, "James Lee", "james.lee@example.com", "#James_Lee_Password0", "+40723145663", "111 Elm St, Berlin, Germany", LocalDate.of(1996, 5, 14), getMockedRole1(), EmploymentType.PART_TIME, Position.MACHINE_LEARNING, Grade.MID, getMockedMentor27(), getMockedStudies3(), getMockedExperiences3());
    }

    public static Employee getMockedEmployee28() {
        return new Employee(28, "Charlotte Brown", "charlotte.brown@example.com", "#Charlotte_Brown_Password0", "+40787654364", "333 Elm St, Moscow, Russia", LocalDate.of(2002, 12, 27), getMockedRole1(), EmploymentType.PART_TIME, Position.MACHINE_LEARNING, Grade.SENIOR, getMockedMentor28(), getMockedStudies4(), getMockedExperiences4());
    }

    public static Employee getMockedEmployee29() {
        return new Employee(29, "Elijah Roberts", "elijah.roberts@example.com", "#Elijah_Roberts_Password0", "+40754321865", "555 Pine St, Sydney, Australia", LocalDate.of(2003, 1, 27), getMockedRole1(), EmploymentType.PART_TIME, Position.BUSINESS_ANALYST, Grade.JUNIOR, getMockedMentor29(), getMockedStudies5(), getMockedExperiences5());
    }

    public static Employee getMockedEmployee30() {
        return new Employee(30, "Amelia Walker", "amelia.walker@example.com", "#Amelia_Walker_Password0", "+40789012666", "777 Oak St, Rome, Italy", LocalDate.of(1992, 8, 12), getMockedRole1(), EmploymentType.PART_TIME, Position.BUSINESS_ANALYST, Grade.JUNIOR, getMockedMentor30(), getMockedStudies6(), getMockedExperiences6());
    }

    public static Employee getMockedEmployee31() {
        return new Employee(31, "Daniel Green", "daniel.green@example.com", "#Daniel_Green_Password0", "+40723145667", "999 Elm St, Moscow, Russia", LocalDate.of(1996, 3, 27), getMockedRole1(), EmploymentType.PART_TIME, Position.BUSINESS_ANALYST, Grade.MID, getMockedMentor31(), getMockedStudies1(), getMockedExperiences7());
    }

    public static Employee getMockedEmployee32() {
        return new Employee(32, "Liam Hall", "liam.hall@example.com", "#Liam_Hall_Password0", "+40787654368", "111 Oak St, Athens, Greece", LocalDate.of(1998, 11, 9), getMockedRole1(), EmploymentType.PART_TIME, Position.BUSINESS_ANALYST, Grade.SENIOR, getMockedMentor32(), getMockedStudies2(), getMockedExperiences8());
    }

    public static Employee getMockedEmployee33() {
        return new Employee(33, "Sophia Young", "sophia.young@example.com", "#Sophia_Young_Password0", "+40754321869", "333 Pine St, Madrid, Spain", LocalDate.of(1995, 6, 23), getMockedRole1(), EmploymentType.PART_TIME, Position.SCRUM_MASTER, Grade.JUNIOR, getMockedMentor33(), getMockedStudies3(), getMockedExperiences1());
    }

    public static Employee getMockedEmployee34() {
        return new Employee(34, "Noah Clark", "noah.clark@example.com", "#Noah_Clark_Password0", "+40789012670", "555 Elm St, Tokyo, Japan", LocalDate.of(1997, 2, 5), getMockedRole1(), EmploymentType.PART_TIME, Position.SCRUM_MASTER, Grade.JUNIOR, getMockedMentor34(), getMockedStudies4(), getMockedExperiences2());
    }

    public static Employee getMockedEmployee35() {
        return new Employee(35, "Olivia Hill", "olivia.hill@example.com", "#Olivia_Hill_Password0", "+40723145671", "777 Oak St, Seoul, South Korea", LocalDate.of(2001, 9, 20), getMockedRole1(), EmploymentType.PART_TIME, Position.SCRUM_MASTER, Grade.MID, getMockedMentor35(), getMockedStudies5(), getMockedExperiences3());
    }

    public static Employee getMockedEmployee36() {
        return new Employee(36, "Michaela Allen", "michaela.allen@example.com", "#Michaela_Allen_Password0", "+40787654372", "999 Pine St, Beijing, China", LocalDate.of(1998, 5, 6), getMockedRole1(), EmploymentType.PART_TIME, Position.SCRUM_MASTER, Grade.SENIOR, getMockedMentor36(), getMockedStudies6(), getMockedExperiences4());
    }
}
