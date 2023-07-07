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
import static com.project.ems.mock.ExperienceMock.getMockedExperiences3;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences4;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences5;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences6;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences7;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences8;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.StudyMock.getMockedStudies1;
import static com.project.ems.mock.StudyMock.getMockedStudies2;
import static com.project.ems.mock.StudyMock.getMockedStudies3;
import static com.project.ems.mock.StudyMock.getMockedStudies4;
import static com.project.ems.mock.StudyMock.getMockedStudies5;
import static com.project.ems.mock.StudyMock.getMockedStudies6;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MentorMock {

    public static List<Mentor> getMockedMentors() {
        return List.of(
              getMockedMentor1(), getMockedMentor2(), getMockedMentor3(), getMockedMentor4(), getMockedMentor5(), getMockedMentor6(), getMockedMentor7(), getMockedMentor8(), getMockedMentor9(),
              getMockedMentor10(), getMockedMentor11(), getMockedMentor12(), getMockedMentor13(), getMockedMentor14(), getMockedMentor15(), getMockedMentor16(), getMockedMentor17(), getMockedMentor18(),
              getMockedMentor19(), getMockedMentor20(), getMockedMentor21(), getMockedMentor22(), getMockedMentor23(), getMockedMentor24(), getMockedMentor25(), getMockedMentor26(), getMockedMentor27(),
              getMockedMentor28(), getMockedMentor29(), getMockedMentor30(), getMockedMentor31(), getMockedMentor32(), getMockedMentor33(), getMockedMentor34(), getMockedMentor35(), getMockedMentor36());
    }

    public static List<Mentor> getMockedFilteredMentors() {
        return List.of(getMockedMentor1(), getMockedMentor2(), getMockedMentor3(), getMockedMentor4());
    }

    public static Mentor getMockedMentor1() {
        return new Mentor(1, "John Doe", "john.doe@example.com", "#John_Doe_Password0", "+40721543701", "123 Main St, Boston, USA", LocalDate.of(1980, 2, 15), getMockedRole2(), EmploymentType.FULL_TIME, Position.FRONTEND, Grade.SENIOR, null, getMockedStudies1(), getMockedExperiences1(), 7, 10, true);
    }

    public static Mentor getMockedMentor2() {
        return new Mentor(2, "Jane Smith", "jane.smith@example.com", "#Jane_Smith_Password0", "+40756321802", "456 Oak St, London, UK", LocalDate.of(1982, 7, 10), getMockedRole2(), EmploymentType.FULL_TIME, Position.FRONTEND, Grade.SENIOR, null, getMockedStudies2(), getMockedExperiences2(), 7, 10, true);
    }

    public static Mentor getMockedMentor3() {
        return new Mentor(3, "Michael Johnson", "michael.johnson@example.com", "#Michael_Johnson_Password0", "+40789712303", "789 Pine St, Madrid, Spain", LocalDate.of(1990, 11, 20), getMockedRole2(), EmploymentType.FULL_TIME, Position.FRONTEND, Grade.SENIOR, null, getMockedStudies3(), getMockedExperiences3(), 7, 10, true);
    }

    public static Mentor getMockedMentor4() {
        return new Mentor(4, "Laura Brown", "laura.brown@example.com", "#Laura_Brown_Password0", "+40734289604", "333 Elm St, Paris, France", LocalDate.of(1985, 8, 25), getMockedRole2(), EmploymentType.FULL_TIME, Position.FRONTEND, Grade.SENIOR, null, getMockedStudies4(), getMockedExperiences4(), 7, 10, false);
    }

    public static Mentor getMockedMentor5() {
        return new Mentor(5, "Robert Davis", "robert.davis@example.com", "#Robert_Davis_Password0", "+40754321805", "555 Oak St, Berlin, Germany", LocalDate.of(1988, 5, 12), getMockedRole1(), EmploymentType.FULL_TIME, Position.BACKEND, Grade.SENIOR, null, getMockedStudies5(), getMockedExperiences5(), 7, 10, false);
    }

    public static Mentor getMockedMentor6() {
        return new Mentor(6, "Emily Wilson", "emily.wilson@example.com", "#Emily_Wilson_Password0", "+40789012606", "777 Pine St, Sydney, Australia", LocalDate.of(1995, 9, 8), getMockedRole1(), EmploymentType.FULL_TIME, Position.BACKEND, Grade.SENIOR, null, getMockedStudies6(), getMockedExperiences6(), 7, 10, false);
    }

    public static Mentor getMockedMentor7() {
        return new Mentor(7, "Michaela Taylor", "michaela.taylor@example.com", "#Michaela_Taylor_Password0", "+40723145607", "999 Elm St, Rome, Italy", LocalDate.of(1983, 12, 7), getMockedRole1(), EmploymentType.FULL_TIME, Position.BACKEND, Grade.SENIOR, getMockedMentor1(), getMockedStudies1(), getMockedExperiences7(), 1, 5, true);
    }

    public static Mentor getMockedMentor8() {
        return new Mentor(8, "David Anderson", "david.anderson@example.com", "#David_Anderson_Password0", "+40787654308", "111 Oak St, Moscow, Russia", LocalDate.of(1992, 4, 23), getMockedRole1(), EmploymentType.FULL_TIME, Position.BACKEND, Grade.SENIOR, getMockedMentor1(), getMockedStudies2(), getMockedExperiences8(), 1, 5, true);
    }

    public static Mentor getMockedMentor9() {
        return new Mentor(9, "Sophia Garcia", "sophia.garcia@example.com", "#Sophia_Garcia_Password0", "+40754321809", "333 Pine St, Athens, Greece", LocalDate.of(1998, 7, 30), getMockedRole1(), EmploymentType.FULL_TIME, Position.DEVOPS, Grade.SENIOR, getMockedMentor1(), getMockedStudies3(), getMockedExperiences1(), 1, 5, false);
    }

    public static Mentor getMockedMentor10() {
        return new Mentor(10, "Joseph Wilson", "joseph.wilson@example.com", "#Joseph_Wilson_Password0", "+40789012610", "555 Elm St, Madrid, Spain", LocalDate.of(1991, 3, 14), getMockedRole1(), EmploymentType.FULL_TIME, Position.DEVOPS, Grade.SENIOR, getMockedMentor1(), getMockedStudies4(), getMockedExperiences2(), 1, 5, true);
    }

    public static Mentor getMockedMentor11() {
        return new Mentor(11, "Olivia Martinez", "olivia.martinez@example.com", "#Olivia_Martinez_Password0", "+40723145611", "777 Oak St, Tokyo, Japan", LocalDate.of(1999, 10, 17), getMockedRole1(), EmploymentType.FULL_TIME, Position.DEVOPS, Grade.SENIOR, getMockedMentor1(), getMockedStudies5(), getMockedExperiences3(), 1, 5, true);
    }

    public static Mentor getMockedMentor12() {
        return new Mentor(12, "Daniel Thompson", "daniel.thompson@example.com", "#Daniel_Thompson_Password0", "+40787654312", "999 Elm St, Seoul, South Korea", LocalDate.of(1994, 6, 9), getMockedRole1(), EmploymentType.FULL_TIME, Position.DEVOPS, Grade.SENIOR, getMockedMentor1(), getMockedStudies6(), getMockedExperiences4(), 1, 5, false);
    }

    public static Mentor getMockedMentor13() {
        return new Mentor(13, "Emma Thompson", "emma.thompson@example.com", "#Emma_Thompson_Password0", "+40754321813", "111 Pine St, Beijing, China", LocalDate.of(2000, 12, 22), getMockedRole1(), EmploymentType.FULL_TIME, Position.TESTING, Grade.SENIOR, getMockedMentor2(), getMockedStudies1(), getMockedExperiences5(), 1, 5, true);
    }

    public static Mentor getMockedMentor14() {
        return new Mentor(14, "Liam Brown", "liam.brown@example.com", "#Liam_Brown_Password0", "+40789012614", "333 Oak St, Cape Town, South Africa", LocalDate.of(1997, 9, 4), getMockedRole1(), EmploymentType.FULL_TIME, Position.TESTING, Grade.SENIOR, getMockedMentor2(), getMockedStudies2(), getMockedExperiences6(), 1, 5, true);
    }

    public static Mentor getMockedMentor15() {
        return new Mentor(15, "Olivia Wilson", "olivia.wilson@example.com", "#Olivia_Wilson_Password0", "+40723145615", "555 Elm St, Buenos Aires, Argentina", LocalDate.of(2001, 4, 7), getMockedRole1(), EmploymentType.FULL_TIME, Position.TESTING, Grade.SENIOR, getMockedMentor2(), getMockedStudies3(), getMockedExperiences7(), 1, 5, false);
    }

    public static Mentor getMockedMentor16() {
        return new Mentor(16, "Noah Taylor", "noah.taylor@example.com", "#Noah_Taylor_Password0", "+40787654316", "777 Pine St, Rio de Janeiro, Brazil", LocalDate.of(1996, 11, 19), getMockedRole1(), EmploymentType.FULL_TIME, Position.TESTING, Grade.SENIOR, getMockedMentor2(), getMockedStudies4(), getMockedExperiences8(), 1, 5, true);
    }

    public static Mentor getMockedMentor17() {
        return new Mentor(17, "Ava Johnson", "ava.johnson@example.com", "#Ava_Johnson_Password0", "+40754321817", "999 Oak St, Mexico City, Mexico", LocalDate.of(2002, 6, 2), getMockedRole1(), EmploymentType.FULL_TIME, Position.DESIGN, Grade.SENIOR, getMockedMentor2(), getMockedStudies5(), getMockedExperiences1(), 1, 5, true);
    }

    public static Mentor getMockedMentor18() {
        return new Mentor(18, "William Davis", "william.davis@example.com", "#William_Davis_Password0", "+40789012618", "111 Elm St, Vancouver, Canada", LocalDate.of(1993, 2, 25), getMockedRole1(), EmploymentType.FULL_TIME, Position.DESIGN, Grade.SENIOR, getMockedMentor2(), getMockedStudies6(), getMockedExperiences2(), 1, 5, false);
    }

    public static Mentor getMockedMentor19() {
        return new Mentor(19, "Sophia Martinez", "sophia.martinez@example.com", "#Sophia_Martinez_Password0", "+40723145619", "333 Oak St, Paris, France", LocalDate.of(2003, 9, 8), getMockedRole1(), EmploymentType.PART_TIME, Position.DESIGN, Grade.SENIOR, getMockedMentor3(), getMockedStudies1(), getMockedExperiences3(), 1, 5, true);
    }

    public static Mentor getMockedMentor20() {
        return new Mentor(20, "Isabella Anderson", "isabella.anderson@example.com", "#Isabella_Anderson_Password0", "+40787654320", "555 Elm St, London, UK", LocalDate.of(1999, 6, 22), getMockedRole1(), EmploymentType.PART_TIME, Position.DESIGN, Grade.SENIOR, getMockedMentor3(), getMockedStudies2(), getMockedExperiences4(), 1, 5, true);
    }

    public static Mentor getMockedMentor21() {
        return new Mentor(21, "Mason Thompson", "mason.thompson@example.com", "#Mason_Thompson_Password0", "+40754321821", "777 Pine St, Berlin, Germany", LocalDate.of(2002, 12, 20), getMockedRole1(), EmploymentType.PART_TIME, Position.DATA_ANALYST, Grade.SENIOR, getMockedMentor3(), getMockedStudies3(), getMockedExperiences5(), 1, 5, false);
    }

    public static Mentor getMockedMentor22() {
        return new Mentor(22, "Charlotte Thompson", "charlotte.thompson@example.com", "#Charlotte_Thompson_Password0", "+40789012622", "999 Oak St, Moscow, Russia", LocalDate.of(1998, 10, 18), getMockedRole1(), EmploymentType.PART_TIME, Position.DATA_ANALYST, Grade.SENIOR, getMockedMentor3(), getMockedStudies4(), getMockedExperiences6(), 1, 5, true);
    }

    public static Mentor getMockedMentor23() {
        return new Mentor(23, "Elijah Smith", "elijah.smith@example.com", "#Elijah_Smith_Password0", "+40723145623", "111 Elm St, Athens, Greece", LocalDate.of(2003, 5, 3), getMockedRole1(), EmploymentType.PART_TIME, Position.DATA_ANALYST, Grade.SENIOR, getMockedMentor3(), getMockedStudies5(), getMockedExperiences7(), 1, 5, true);
    }

    public static Mentor getMockedMentor24() {
        return new Mentor(24, "Amelia Johnson", "amelia.johnson@example.com", "#Amelia_Johnson_Password0", "+40787654324", "333 Pine St, Madrid, Spain", LocalDate.of(1998, 12, 14), getMockedRole1(), EmploymentType.PART_TIME, Position.DATA_ANALYST, Grade.SENIOR, getMockedMentor3(), getMockedStudies6(), getMockedExperiences8(), 1, 5, false);
    }

    public static Mentor getMockedMentor25() {
        return new Mentor(25, "Harper Wilson", "harper.wilson@example.com", "#Harper_Wilson_Password0", "+40754321825", "555 Oak St, Tokyo, Japan", LocalDate.of(2001, 7, 27), getMockedRole1(), EmploymentType.PART_TIME, Position.MACHINE_LEARNING, Grade.SENIOR, getMockedMentor4(), getMockedStudies1(), getMockedExperiences1(), 1, 5, true);
    }

    public static Mentor getMockedMentor26() {
        return new Mentor(26, "Daniel Thompson", "daniel.thompson@example.com", "#Daniel_Thompson_Password0", "+40789012626", "777 Elm St, Seoul, South Korea", LocalDate.of(2001, 2, 9), getMockedRole1(), EmploymentType.PART_TIME, Position.MACHINE_LEARNING, Grade.SENIOR, getMockedMentor4(), getMockedStudies2(), getMockedExperiences2(), 1, 5, true);
    }

    public static Mentor getMockedMentor27() {
        return new Mentor(27, "Liam Thompson", "liam.thompson@example.com", "#Liam_Thompson_Password0", "+40723145627", "999 Oak St, Beijing, China", LocalDate.of(2000, 9, 23), getMockedRole1(), EmploymentType.PART_TIME, Position.MACHINE_LEARNING, Grade.SENIOR, getMockedMentor4(), getMockedStudies3(), getMockedExperiences3(), 1, 5, false);
    }

    public static Mentor getMockedMentor28() {
        return new Mentor(28, "Grace Martinez", "grace.martinez@example.com", "#Grace_Martinez_Password0", "+40787654328", "111 Elm St, Cape Town, South Africa", LocalDate.of(2002, 6, 6), getMockedRole1(), EmploymentType.PART_TIME, Position.MACHINE_LEARNING, Grade.SENIOR, getMockedMentor4(), getMockedStudies4(), getMockedExperiences4(), 1, 5, true);
    }

    public static Mentor getMockedMentor29() {
        return new Mentor(29, "Isabella White", "isabella.white@example.com", "#Isabella_White_Password0", "+40754321829", "333 Pine St, Buenos Aires, Argentina", LocalDate.of(2002, 1, 19), getMockedRole1(), EmploymentType.PART_TIME, Position.BUSINESS_ANALYST, Grade.SENIOR, getMockedMentor4(), getMockedStudies5(), getMockedExperiences5(), 1, 5, true);
    }

    public static Mentor getMockedMentor30() {
        return new Mentor(30, "Logan Thompson", "logan.thompson@example.com", "#Logan_Thompson_Password0", "+40789012630", "555 Elm St, Rio de Janeiro, Brazil", LocalDate.of(2003, 7, 2), getMockedRole1(), EmploymentType.PART_TIME, Position.BUSINESS_ANALYST, Grade.SENIOR, getMockedMentor4(), getMockedStudies6(), getMockedExperiences6(), 1, 5, false);
    }

    public static Mentor getMockedMentor31() {
        return new Mentor(31, "Evelyn Brown", "evelyn.brown@example.com", "#Evelyn_Brown_Password0", "+40723145631", "777 Oak St, Mexico City, Mexico", LocalDate.of(1999, 3, 16), getMockedRole1(), EmploymentType.PART_TIME, Position.BUSINESS_ANALYST, Grade.SENIOR, getMockedMentor5(), getMockedStudies1(), getMockedExperiences7(), 1, 5, true);
    }

    public static Mentor getMockedMentor32() {
        return new Mentor(32, "Henry Davis", "henry.davis@example.com", "#Henry_Davis_Password0", "+40787654332", "999 Elm St, Vancouver, Canada", LocalDate.of(1998, 10, 29), getMockedRole1(), EmploymentType.PART_TIME, Position.BUSINESS_ANALYST, Grade.SENIOR, getMockedMentor5(), getMockedStudies2(), getMockedExperiences8(), 1, 5, true);
    }

    public static Mentor getMockedMentor33() {
        return new Mentor(33, "Sofia Smith", "sofia.smith@example.com", "#Sofia_Smith_Password0", "+40754321833", "111 Oak St, Paris, France", LocalDate.of(2000, 6, 11), getMockedRole1(), EmploymentType.PART_TIME, Position.SCRUM_MASTER, Grade.SENIOR, getMockedMentor5(), getMockedStudies3(), getMockedExperiences1(), 1, 5, false);
    }

    public static Mentor getMockedMentor34() {
        return new Mentor(34, "Jack Wilson", "jack.wilson@example.com", "#Jack_Wilson_Password0", "+40789012634", "333 Elm St, London, UK", LocalDate.of(1997, 1, 24), getMockedRole1(), EmploymentType.PART_TIME, Position.SCRUM_MASTER, Grade.SENIOR, getMockedMentor5(), getMockedStudies4(), getMockedExperiences2(), 1, 5, true);
    }

    public static Mentor getMockedMentor35() {
        return new Mentor(35, "Emily Anderson", "emily.anderson@example.com", "#Emily_Anderson_Password0", "+40723145635", "555 Pine St, Berlin, Germany", LocalDate.of(1995, 8, 6), getMockedRole1(), EmploymentType.PART_TIME, Position.SCRUM_MASTER, Grade.SENIOR, getMockedMentor5(), getMockedStudies5(), getMockedExperiences3(), 1, 5, true);
    }

    public static Mentor getMockedMentor36() {
        return new Mentor(36, "Benjamin Thompson", "benjamin.thompson@example.com", "#Benjamin_Thompson_Password0", "+40787654336", "777 Elm St, Moscow, Russia", LocalDate.of(1996, 3, 20), getMockedRole1(), EmploymentType.PART_TIME, Position.SCRUM_MASTER, Grade.SENIOR, getMockedMentor5(), getMockedStudies6(), getMockedExperiences4(), 1, 5, false);
    }
}
