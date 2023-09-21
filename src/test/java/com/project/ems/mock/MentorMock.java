package com.project.ems.mock;

import com.project.ems.mentor.Mentor;
import com.project.ems.mentor.MentorDto;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.ems.employee.enums.EmploymentType.FULL_TIME;
import static com.project.ems.employee.enums.EmploymentType.PART_TIME;
import static com.project.ems.employee.enums.Grade.SENIOR;
import static com.project.ems.employee.enums.Position.BACKEND;
import static com.project.ems.employee.enums.Position.BUSINESS_ANALYST;
import static com.project.ems.employee.enums.Position.DATA_ANALYST;
import static com.project.ems.employee.enums.Position.DESIGN;
import static com.project.ems.employee.enums.Position.DEVOPS;
import static com.project.ems.employee.enums.Position.FRONTEND;
import static com.project.ems.employee.enums.Position.MACHINE_LEARNING;
import static com.project.ems.employee.enums.Position.SCRUM_MASTER;
import static com.project.ems.employee.enums.Position.TESTING;
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
        return List.of(getMockedMentor1(), getMockedMentor2(), getMockedMentor3(), getMockedMentor4(), getMockedMentor5(), getMockedMentor6(), getMockedMentor7(), getMockedMentor8(), getMockedMentor9(),
              getMockedMentor10(), getMockedMentor11(), getMockedMentor12(), getMockedMentor13(), getMockedMentor14(), getMockedMentor15(), getMockedMentor16(), getMockedMentor17(), getMockedMentor18(),
              getMockedMentor19(), getMockedMentor20(), getMockedMentor21(), getMockedMentor22(), getMockedMentor23(), getMockedMentor24(), getMockedMentor25(), getMockedMentor26(), getMockedMentor27(),
              getMockedMentor28(), getMockedMentor29(), getMockedMentor30(), getMockedMentor31(), getMockedMentor32(), getMockedMentor33(), getMockedMentor34(), getMockedMentor35(), getMockedMentor36());
    }

    public static List<Mentor> getMockedMentorsPage1() {
        return List.of(getMockedMentor1(), getMockedMentor2());
    }

    public static List<Mentor> getMockedMentorsPage2() {
        return List.of(getMockedMentor3(), getMockedMentor4());
    }

    public static List<Mentor> getMockedMentorsPage3() {
        return List.of(getMockedMentor5(), getMockedMentor6());
    }

    public static List<MentorDto> getMockedMentorDtosPage1() {
        return List.of(getMockedMentorDto1(), getMockedMentorDto2());
    }

    public static List<MentorDto> getMockedMentorDtosPage2() {
        return List.of(getMockedMentorDto3(), getMockedMentorDto4());
    }

    public static List<MentorDto> getMockedMentorDtosPage3() {
        return List.of(getMockedMentorDto5(), getMockedMentorDto6());
    }

    public static List<Mentor> getMockedMentorsFirstPage() { return List.of(getMockedMentor1(), getMockedMentor2(), getMockedMentor3(), getMockedMentor4(), getMockedMentor5(), getMockedMentor6(), getMockedMentor7(), getMockedMentor8(), getMockedMentor9(), getMockedMentor10()); }

    public static List<MentorDto> getMockedMentorDtosFirstPage() { return List.of(getMockedMentorDto1(), getMockedMentorDto2(), getMockedMentorDto3(), getMockedMentorDto4(), getMockedMentorDto5(), getMockedMentorDto6(), getMockedMentorDto7(), getMockedMentorDto8(), getMockedMentorDto9(), getMockedMentorDto10()); }

    public static Mentor getMockedMentor1() { return Mentor.builder().id(1).name("John Doe").email("john.doe@example.com").password("$2a$10$2cEzAx1UKlGGlWai6VasvOUdVBg.NCug8XsdOyhwRdvLYYgFW.O/W").mobile("+40721543701").address("123 Main St, Boston, USA").birthday(LocalDate.of(1980, 2, 15)).role(getMockedRole2()).employmentType(FULL_TIME).position(FRONTEND).grade(SENIOR).supervisingMentor(null).studies(getMockedStudies1()).experiences(getMockedExperiences1()).nrTrainees(7).maxTrainees(10).openForTraining(true).build(); }

    public static Mentor getMockedMentor2() { return Mentor.builder().id(2).name("Jane Smith").email("jane.smith@example.com").password("$2a$10$tltXH0bchhhvzf6sWpZOduMfTdNFzORnJjo2quB9e8qTFttHj8RsG").mobile("+40756321802").address("456 Oak St, London, UK").birthday(LocalDate.of(1982, 7, 10)).role(getMockedRole2()).employmentType(FULL_TIME).position(FRONTEND).grade(SENIOR).supervisingMentor(null).studies(getMockedStudies2()).experiences(getMockedExperiences2()).nrTrainees(7).maxTrainees(10).openForTraining(true).build(); }

    public static Mentor getMockedMentor3() { return Mentor.builder().id(3).name("Michael Johnson").email("michael.johnson@example.com").password("$2a$10$IeJe8nsYE4MCiuIXTEk7uetLN/T5ynAKtApxJwVMBmDC6Bk3W3W3y").mobile("+40789712303").address("789 Pine St, Madrid, Spain").birthday(LocalDate.of(1990, 11, 20)).role(getMockedRole2()).employmentType(FULL_TIME).position(FRONTEND).grade(SENIOR).supervisingMentor(null).studies(getMockedStudies3()).experiences(getMockedExperiences3()).nrTrainees(7).maxTrainees(10).openForTraining(true).build(); }

    public static Mentor getMockedMentor4() { return Mentor.builder().id(4).name("Laura Brown").email("laura.brown@example.com").password("$2a$10$OvqxdzK0hef5A.jcW3PQOewRrZnafHtpAiAFn.aF8KW1ktaRvb5NO").mobile("+40734289604").address("333 Elm St, Paris, France").birthday(LocalDate.of(1985, 8, 25)).role(getMockedRole2()).employmentType(FULL_TIME).position(FRONTEND).grade(SENIOR).supervisingMentor(null).studies(getMockedStudies4()).experiences(getMockedExperiences4()).nrTrainees(7).maxTrainees(10).openForTraining(false).build(); }

    public static Mentor getMockedMentor5() { return Mentor.builder().id(5).name("Robert Davis").email("robert.davis@example.com").password("$2a$10$vpnwsCU8bOzYq2l8CljiBeOcZZRfVjeo4g96XnH2KYbqNRQYfZQh.").mobile("+40754321805").address("555 Oak St, Berlin, Germany").birthday(LocalDate.of(1988, 5, 12)).role(getMockedRole1()).employmentType(FULL_TIME).position(BACKEND).grade(SENIOR).supervisingMentor(null).studies(getMockedStudies5()).experiences(getMockedExperiences5()).nrTrainees(7).maxTrainees(10).openForTraining(false).build(); }

    public static Mentor getMockedMentor6() { return Mentor.builder().id(6).name("Emily Wilson").email("emily.wilson@example.com").password("$2a$10$GNYbCsERVLU1Vw7L9obxAOX9spb8QgGSc8SSzAfQwGVIE/UMZ.ItK").mobile("+40789012606").address("777 Pine St, Sydney, Australia").birthday(LocalDate.of(1995, 9, 8)).role(getMockedRole1()).employmentType(FULL_TIME).position(BACKEND).grade(SENIOR).supervisingMentor(null).studies(getMockedStudies6()).experiences(getMockedExperiences6()).nrTrainees(7).maxTrainees(10).openForTraining(false).build(); }

    public static Mentor getMockedMentor7() { return Mentor.builder().id(7).name("Michaela Taylor").email("michaela.taylor@example.com").password("$2a$10$ZRHHzDuEirPWk/GA22HQ1u/Rm6sNV4VPO9WhxYfgKSuCvF73AMIO6").mobile("+40723145607").address("999 Elm St, Rome, Italy").birthday(LocalDate.of(1983, 12, 7)).role(getMockedRole1()).employmentType(FULL_TIME).position(BACKEND).grade(SENIOR).supervisingMentor(getMockedMentor1()).studies(getMockedStudies1()).experiences(getMockedExperiences7()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor8() { return Mentor.builder().id(8).name("David Anderson").email("david.anderson@example.com").password("$2a$10$A8YW129edU79eHirdlT.2u4cQQy5bCgJIBie2YrDyYfoUSi4i5/GO").mobile("+40787654308").address("111 Oak St, Moscow, Russia").birthday(LocalDate.of(1992, 4, 23)).role(getMockedRole1()).employmentType(FULL_TIME).position(BACKEND).grade(SENIOR).supervisingMentor(getMockedMentor1()).studies(getMockedStudies2()).experiences(getMockedExperiences8()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor9() { return Mentor.builder().id(9).name("Sophia Garcia").email("sophia.garcia@example.com").password("$2a$10$uO2oGV6PM5aBrAmqR98LQu2FtC/iaFoXx2nsyNXuOhNbaEOzXKbx2").mobile("+40754321809").address("333 Pine St, Athens, Greece").birthday(LocalDate.of(1998, 7, 30)).role(getMockedRole1()).employmentType(FULL_TIME).position(DEVOPS).grade(SENIOR).supervisingMentor(getMockedMentor1()).studies(getMockedStudies3()).experiences(getMockedExperiences1()).nrTrainees(1).maxTrainees(5).openForTraining(false).build(); }

    public static Mentor getMockedMentor10() { return Mentor.builder().id(10).name("Joseph Wilson").email("joseph.wilson@example.com").password("$2a$10$aoNj5vm.RdmMDffhpCgpu.mCmhgbanqS1R1CHeFR1iSjv/cASQa4W").mobile("+40789012610").address("555 Elm St, Madrid, Spain").birthday(LocalDate.of(1991, 3, 14)).role(getMockedRole1()).employmentType(FULL_TIME).position(DEVOPS).grade(SENIOR).supervisingMentor(getMockedMentor1()).studies(getMockedStudies4()).experiences(getMockedExperiences2()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor11() { return Mentor.builder().id(11).name("Olivia Martinez").email("olivia.martinez@example.com").password("$2a$10$Gq9twxRqP68mHkvNCJIPt.Gh0Jhb28SrbBIczDhiBOcVCvH1VHC3C").mobile("+40723145611").address("777 Oak St, Tokyo, Japan").birthday(LocalDate.of(1999, 10, 17)).role(getMockedRole1()).employmentType(FULL_TIME).position(DEVOPS).grade(SENIOR).supervisingMentor(getMockedMentor1()).studies(getMockedStudies5()).experiences(getMockedExperiences3()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor12() { return Mentor.builder().id(12).name("Daniel Thompson").email("daniel.thompson@example.com").password("$2a$10$oskNRX/aOXL5UunrCXbIkeJI3AKhmwNJENNNnj9/YXsofKQLVSTbW").mobile("+40787654312").address("999 Elm St, Seoul, South Korea").birthday(LocalDate.of(1994, 6, 9)).role(getMockedRole1()).employmentType(FULL_TIME).position(DEVOPS).grade(SENIOR).supervisingMentor(getMockedMentor1()).studies(getMockedStudies6()).experiences(getMockedExperiences4()).nrTrainees(1).maxTrainees(5).openForTraining(false).build(); }

    public static Mentor getMockedMentor13() { return Mentor.builder().id(13).name("Emma Thompson").email("emma.thompson@example.com").password("$2a$10$WsezewgzAH68WmkfpXJuF..k4FxuZb5ZDsNfdPC4.VXCPc2p4Y95m").mobile("+40754321813").address("111 Pine St, Beijing, China").birthday(LocalDate.of(2000, 12, 22)).role(getMockedRole1()).employmentType(FULL_TIME).position(TESTING).grade(SENIOR).supervisingMentor(getMockedMentor2()).studies(getMockedStudies1()).experiences(getMockedExperiences5()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor14() { return Mentor.builder().id(14).name("Liam Brown").email("liam.brown@example.com").password("$2a$10$xeXGbNuNk/3KcL826T4d8.08P4VXykQvwZ0YQRuVIlmJU49Lv2vGy").mobile("+40789012614").address("333 Oak St, Cape Town, South Africa").birthday(LocalDate.of(1997, 9, 4)).role(getMockedRole1()).employmentType(FULL_TIME).position(TESTING).grade(SENIOR).supervisingMentor(getMockedMentor2()).studies(getMockedStudies2()).experiences(getMockedExperiences6()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor15() { return Mentor.builder().id(15).name("Olivia Wilson").email("olivia.wilson@example.com").password("$2a$10$w7EDq9QbQziqsW6RX6t3iO.KnCJnaE.lsjsnKWMDhs9lwqiBQ4rXC").mobile("+40723145615").address("555 Elm St, Buenos Aires, Argentina").birthday(LocalDate.of(2001, 4, 7)).role(getMockedRole1()).employmentType(FULL_TIME).position(TESTING).grade(SENIOR).supervisingMentor(getMockedMentor2()).studies(getMockedStudies3()).experiences(getMockedExperiences7()).nrTrainees(1).maxTrainees(5).openForTraining(false).build(); }

    public static Mentor getMockedMentor16() { return Mentor.builder().id(16).name("Noah Taylor").email("noah.taylor@example.com").password("$2a$10$6yV39WUU4bzgOsrN75xueutjZ4ThW374fIzcEb6QmexZlpAbJP4Xq").mobile("+40787654316").address("777 Pine St, Rio de Janeiro, Brazil").birthday(LocalDate.of(1996, 11, 19)).role(getMockedRole1()).employmentType(FULL_TIME).position(TESTING).grade(SENIOR).supervisingMentor(getMockedMentor2()).studies(getMockedStudies4()).experiences(getMockedExperiences8()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor17() { return Mentor.builder().id(17).name("Ava Johnson").email("ava.johnson@example.com").password("$2a$10$3O3sqTFrFnsrBvBNxgNtMub6j0SzvUvEdTnlA5hPaJkerYS.YUgzG").mobile("+40754321817").address("999 Oak St, Mexico City, Mexico").birthday(LocalDate.of(2002, 6, 2)).role(getMockedRole1()).employmentType(FULL_TIME).position(DESIGN).grade(SENIOR).supervisingMentor(getMockedMentor2()).studies(getMockedStudies5()).experiences(getMockedExperiences1()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor18() { return Mentor.builder().id(18).name("William Davis").email("william.davis@example.com").password("$2a$10$yikPyGgUSjfBRGRa/m2i5O8kKvJue0Fk1aeVzpGUR10hhGbaCpPai").mobile("+40789012618").address("111 Elm St, Vancouver, Canada").birthday(LocalDate.of(1993, 2, 25)).role(getMockedRole1()).employmentType(FULL_TIME).position(DESIGN).grade(SENIOR).supervisingMentor(getMockedMentor2()).studies(getMockedStudies6()).experiences(getMockedExperiences2()).nrTrainees(1).maxTrainees(5).openForTraining(false).build(); }

    public static Mentor getMockedMentor19() { return Mentor.builder().id(19).name("Sophia Martinez").email("sophia.martinez@example.com").password("$2a$10$/AdEGcsi9Nf3OTEFhTDoGOmRfQz/FcdxnxQhqyiiHGrbNYYtlqbHK").mobile("+40723145619").address("333 Oak St, Paris, France").birthday(LocalDate.of(2003, 9, 8)).role(getMockedRole1()).employmentType(PART_TIME).position(DESIGN).grade(SENIOR).supervisingMentor(getMockedMentor3()).studies(getMockedStudies1()).experiences(getMockedExperiences3()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor20() { return Mentor.builder().id(20).name("Isabella Anderson").email("isabella.anderson@example.com").password("$2a$10$wfSRBt.m9sSC.RmOe8lzJOsJlX7ajGujrdE1wmhRVEY753.DzYRD.").mobile("+40787654320").address("555 Elm St, London, UK").birthday(LocalDate.of(1999, 6, 22)).role(getMockedRole1()).employmentType(PART_TIME).position(DESIGN).grade(SENIOR).supervisingMentor(getMockedMentor3()).studies(getMockedStudies2()).experiences(getMockedExperiences4()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor21() { return Mentor.builder().id(21).name("Mason Thompson").email("mason.thompson@example.com").password("$2a$10$ERa1AxKeQw1/eDMOS/ObleC/o7KWhKqsDNZmFUPMI5830cWZr/VLy").mobile("+40754321821").address("777 Pine St, Berlin, Germany").birthday(LocalDate.of(2002, 12, 20)).role(getMockedRole1()).employmentType(PART_TIME).position(DATA_ANALYST).grade(SENIOR).supervisingMentor(getMockedMentor3()).studies(getMockedStudies3()).experiences(getMockedExperiences5()).nrTrainees(1).maxTrainees(5).openForTraining(false).build(); }

    public static Mentor getMockedMentor22() { return Mentor.builder().id(22).name("Charlotte Thompson").email("charlotte.thompson@example.com").password("$2a$10$0UjFhZHVhp2HBgjyedohUu0U6LJxARKlgExfUeAPyn4qTZFDqPOg2").mobile("+40789012622").address("999 Oak St, Moscow, Russia").birthday(LocalDate.of(1998, 10, 18)).role(getMockedRole1()).employmentType(PART_TIME).position(DATA_ANALYST).grade(SENIOR).supervisingMentor(getMockedMentor3()).studies(getMockedStudies4()).experiences(getMockedExperiences6()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor23() { return Mentor.builder().id(23).name("Elijah Smith").email("elijah.smith@example.com").password("$2a$10$FDghGPE0AshKfF5R8EUPteMsPkAeeuxgU0iEKu31X7uo1FPqGqXn2").mobile("+40723145623").address("111 Elm St, Athens, Greece").birthday(LocalDate.of(2003, 5, 3)).role(getMockedRole1()).employmentType(PART_TIME).position(DATA_ANALYST).grade(SENIOR).supervisingMentor(getMockedMentor3()).studies(getMockedStudies5()).experiences(getMockedExperiences7()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor24() { return Mentor.builder().id(24).name("Amelia Johnson").email("amelia.johnson@example.com").password("$2a$10$dkLjeeYMeprqj.fKHC.O/uoTdaPYmUj3iSWEOgqywxDKauEIQMmTO").mobile("+40787654324").address("333 Pine St, Madrid, Spain").birthday(LocalDate.of(1998, 12, 14)).role(getMockedRole1()).employmentType(PART_TIME).position(DATA_ANALYST).grade(SENIOR).supervisingMentor(getMockedMentor3()).studies(getMockedStudies6()).experiences(getMockedExperiences8()).nrTrainees(1).maxTrainees(5).openForTraining(false).build(); }

    public static Mentor getMockedMentor25() { return Mentor.builder().id(25).name("Harper Wilson").email("harper.wilson@example.com").password("$2a$10$tM.taQiLPy0Dv.9eu.lq6OKzr1c5KvNj4jE0Wv.fH4LL895CyRsY.").mobile("+40754321825").address("555 Oak St, Tokyo, Japan").birthday(LocalDate.of(2001, 7, 27)).role(getMockedRole1()).employmentType(PART_TIME).position(MACHINE_LEARNING).grade(SENIOR).supervisingMentor(getMockedMentor4()).studies(getMockedStudies1()).experiences(getMockedExperiences1()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor26() { return Mentor.builder().id(26).name("Daniel Thompson").email("daniel.thompson@example.com").password("$2a$10$NcaZuOZx20.pU8K95t0uBeuOi7L7LsqHNBZP1yPDnKpzyPiRbjqu6").mobile("+40789012626").address("777 Elm St, Seoul, South Korea").birthday(LocalDate.of(2001, 2, 9)).role(getMockedRole1()).employmentType(PART_TIME).position(MACHINE_LEARNING).grade(SENIOR).supervisingMentor(getMockedMentor4()).studies(getMockedStudies2()).experiences(getMockedExperiences2()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor27() { return Mentor.builder().id(27).name("Liam Thompson").email("liam.thompson@example.com").password("$2a$10$HNmZy.y.aQHrFfJHuTfj4O3eZmmSEyGzjIpBAxh6NuqjUZIEfudJC").mobile("+40723145627").address("999 Oak St, Beijing, China").birthday(LocalDate.of(2000, 9, 23)).role(getMockedRole1()).employmentType(PART_TIME).position(MACHINE_LEARNING).grade(SENIOR).supervisingMentor(getMockedMentor4()).studies(getMockedStudies3()).experiences(getMockedExperiences3()).nrTrainees(1).maxTrainees(5).openForTraining(false).build(); }

    public static Mentor getMockedMentor28() { return Mentor.builder().id(28).name("Grace Martinez").email("grace.martinez@example.com").password("$2a$10$sUhx0SNgqZJWZRKAnObyRu3QxkeNJ1PVwAM1dPMtPEBLd2UhXOFvW").mobile("+40787654328").address("111 Elm St, Cape Town, South Africa").birthday(LocalDate.of(2002, 6, 6)).role(getMockedRole1()).employmentType(PART_TIME).position(MACHINE_LEARNING).grade(SENIOR).supervisingMentor(getMockedMentor4()).studies(getMockedStudies4()).experiences(getMockedExperiences4()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor29() { return Mentor.builder().id(29).name("Isabella White").email("isabella.white@example.com").password("$2a$10$DNt/zlBmhtAebwXf4RCyZ.hIIk3c2guAz9NcVt0qk/Ax3M9fK3Ef2").mobile("+40754321829").address("333 Pine St, Buenos Aires, Argentina").birthday(LocalDate.of(2002, 1, 19)).role(getMockedRole1()).employmentType(PART_TIME).position(BUSINESS_ANALYST).grade(SENIOR).supervisingMentor(getMockedMentor4()).studies(getMockedStudies5()).experiences(getMockedExperiences5()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor30() { return Mentor.builder().id(30).name("Logan Thompson").email("logan.thompson@example.com").password("$2a$10$qNkwGS7qKC9i2bOeB5SWJeVhFyWpk71NN6XCBAqNdnK1lVnQfygOG").mobile("+40789012630").address("555 Elm St, Rio de Janeiro, Brazil").birthday(LocalDate.of(2003, 7, 2)).role(getMockedRole1()).employmentType(PART_TIME).position(BUSINESS_ANALYST).grade(SENIOR).supervisingMentor(getMockedMentor4()).studies(getMockedStudies6()).experiences(getMockedExperiences6()).nrTrainees(1).maxTrainees(5).openForTraining(false).build(); }

    public static Mentor getMockedMentor31() { return Mentor.builder().id(31).name("Evelyn Brown").email("evelyn.brown@example.com").password("$2a$10$NDktEzYFJBMVc104nC9TJeQgabUxhdp4sXbNdjvESyOP6tii0k1.e").mobile("+40723145631").address("777 Oak St, Mexico City, Mexico").birthday(LocalDate.of(1999, 3, 16)).role(getMockedRole1()).employmentType(PART_TIME).position(BUSINESS_ANALYST).grade(SENIOR).supervisingMentor(getMockedMentor5()).studies(getMockedStudies1()).experiences(getMockedExperiences7()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor32() { return Mentor.builder().id(32).name("Henry Davis").email("henry.davis@example.com").password("$2a$10$ofOqGSbZwN/gKCyQ1dXhiOgG9KirJ39QwAvg2bfMdJSLabMWTL.qi").mobile("+40787654332").address("999 Elm St, Vancouver, Canada").birthday(LocalDate.of(1998, 10, 29)).role(getMockedRole1()).employmentType(PART_TIME).position(BUSINESS_ANALYST).grade(SENIOR).supervisingMentor(getMockedMentor5()).studies(getMockedStudies2()).experiences(getMockedExperiences8()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor33() { return Mentor.builder().id(33).name("Sofia Smith").email("sofia.smith@example.com").password("$2a$10$e/zTP55VBCo7VUPSiHkdB.q6w5L2pZS0STtujevXPlG1zGgz641AW").mobile("+40754321833").address("111 Oak St, Paris, France").birthday(LocalDate.of(2000, 6, 11)).role(getMockedRole1()).employmentType(PART_TIME).position(SCRUM_MASTER).grade(SENIOR).supervisingMentor(getMockedMentor5()).studies(getMockedStudies3()).experiences(getMockedExperiences1()).nrTrainees(1).maxTrainees(5).openForTraining(false).build(); }

    public static Mentor getMockedMentor34() { return Mentor.builder().id(34).name("Jack Wilson").email("jack.wilson@example.com").password("$2a$10$RTe09cmsz4R9JGssv2b/buTnVvmIYHGHD/vGfiglxjZARNCPMik6W").mobile("+40789012634").address("333 Elm St, London, UK").birthday(LocalDate.of(1997, 1, 24)).role(getMockedRole1()).employmentType(PART_TIME).position(SCRUM_MASTER).grade(SENIOR).supervisingMentor(getMockedMentor5()).studies(getMockedStudies4()).experiences(getMockedExperiences2()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor35() { return Mentor.builder().id(35).name("Emily Anderson").email("emily.anderson@example.com").password("$2a$10$E2lv05QvDikcobayv6ACoOHZHc1xVEDF1ffkLLiE29Jm941IJQ/rq").mobile("+40723145635").address("555 Pine St, Berlin, Germany").birthday(LocalDate.of(1995, 8, 6)).role(getMockedRole1()).employmentType(PART_TIME).position(SCRUM_MASTER).grade(SENIOR).supervisingMentor(getMockedMentor5()).studies(getMockedStudies5()).experiences(getMockedExperiences3()).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static Mentor getMockedMentor36() { return Mentor.builder().id(36).name("Benjamin Thompson").email("benjamin.thompson@example.com").password("$2a$10$4lnj3fxxnHMA36oeYBjDNuCHH8TbN3fMX0OB1ia6RpseCpQTG2qWe").mobile("+40787654336").address("777 Elm St, Moscow, Russia").birthday(LocalDate.of(1996, 3, 20)).role(getMockedRole1()).employmentType(PART_TIME).position(SCRUM_MASTER).grade(SENIOR).supervisingMentor(getMockedMentor5()).studies(getMockedStudies6()).experiences(getMockedExperiences4()).nrTrainees(1).maxTrainees(5).openForTraining(false).build(); }

    public static MentorDto getMockedMentorDto1() { return MentorDto.builder().id(1).name("John Doe").email("john.doe@example.com").password("$2a$10$2cEzAx1UKlGGlWai6VasvOUdVBg.NCug8XsdOyhwRdvLYYgFW.O/W").mobile("+40721543701").address("123 Main St, Boston, USA").birthday(LocalDate.of(1980, 2, 15)).roleId(2).employmentType(FULL_TIME).position(FRONTEND).grade(SENIOR).supervisingMentorId(null).studiesIds(List.of(1, 2)).experiencesIds(List.of(1, 2)).nrTrainees(7).maxTrainees(10).openForTraining(true).build(); }

    public static MentorDto getMockedMentorDto2() { return MentorDto.builder().id(2).name("Jane Smith").email("jane.smith@example.com").password("$2a$10$tltXH0bchhhvzf6sWpZOduMfTdNFzORnJjo2quB9e8qTFttHj8RsG").mobile("+40756321802").address("456 Oak St, London, UK").birthday(LocalDate.of(1982, 7, 10)).roleId(2).employmentType(FULL_TIME).position(FRONTEND).grade(SENIOR).supervisingMentorId(null).studiesIds(List.of(3, 4)).experiencesIds(List.of(3, 4)).nrTrainees(7).maxTrainees(10).openForTraining(true).build(); }

    public static MentorDto getMockedMentorDto3() { return MentorDto.builder().id(3).name("Michael Johnson").email("michael.johnson@example.com").password("$2a$10$IeJe8nsYE4MCiuIXTEk7uetLN/T5ynAKtApxJwVMBmDC6Bk3W3W3y").mobile("+40789712303").address("789 Pine St, Madrid, Spain").birthday(LocalDate.of(1990, 11, 20)).roleId(2).employmentType(FULL_TIME).position(FRONTEND).grade(SENIOR).supervisingMentorId(null).studiesIds(List.of(5, 6)).experiencesIds(List.of(5, 6)).nrTrainees(7).maxTrainees(10).openForTraining(true).build(); }

    public static MentorDto getMockedMentorDto4() { return MentorDto.builder().id(4).name("Laura Brown").email("laura.brown@example.com").password("$2a$10$OvqxdzK0hef5A.jcW3PQOewRrZnafHtpAiAFn.aF8KW1ktaRvb5NO").mobile("+40734289604").address("333 Elm St, Paris, France").birthday(LocalDate.of(1985, 8, 25)).roleId(2).employmentType(FULL_TIME).position(FRONTEND).grade(SENIOR).supervisingMentorId(null).studiesIds(List.of(7, 8)).experiencesIds(List.of(7, 8)).nrTrainees(7).maxTrainees(10).openForTraining(false).build(); }

    public static MentorDto getMockedMentorDto5() { return MentorDto.builder().id(5).name("Robert Davis").email("robert.davis@example.com").password("$2a$10$vpnwsCU8bOzYq2l8CljiBeOcZZRfVjeo4g96XnH2KYbqNRQYfZQh.").mobile("+40754321805").address("555 Oak St, Berlin, Germany").birthday(LocalDate.of(1988, 5, 12)).roleId(1).employmentType(FULL_TIME).position(BACKEND).grade(SENIOR).supervisingMentorId(null).studiesIds(List.of(9, 10)).experiencesIds(List.of(9, 10)).nrTrainees(7).maxTrainees(10).openForTraining(false).build(); }

    public static MentorDto getMockedMentorDto6() { return MentorDto.builder().id(6).name("Emily Wilson").email("emily.wilson@example.com").password("$2a$10$GNYbCsERVLU1Vw7L9obxAOX9spb8QgGSc8SSzAfQwGVIE/UMZ.ItK").mobile("+40789012606").address("777 Pine St, Sydney, Australia").birthday(LocalDate.of(1995, 9, 8)).roleId(1).employmentType(FULL_TIME).position(BACKEND).grade(SENIOR).supervisingMentorId(null).studiesIds(List.of(11, 12)).experiencesIds(List.of(11, 12)).nrTrainees(7).maxTrainees(10).openForTraining(false).build(); }

    public static MentorDto getMockedMentorDto7() { return MentorDto.builder().id(7).name("Michaela Taylor").email("michaela.taylor@example.com").password("$2a$10$ZRHHzDuEirPWk/GA22HQ1u/Rm6sNV4VPO9WhxYfgKSuCvF73AMIO6").mobile("+40723145607").address("999 Elm St, Rome, Italy").birthday(LocalDate.of(1983, 12, 7)).roleId(1).employmentType(FULL_TIME).position(BACKEND).grade(SENIOR).supervisingMentorId(1).studiesIds(List.of(1, 2)).experiencesIds(List.of(13, 14)).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static MentorDto getMockedMentorDto8() { return MentorDto.builder().id(8).name("David Anderson").email("david.anderson@example.com").password("$2a$10$A8YW129edU79eHirdlT.2u4cQQy5bCgJIBie2YrDyYfoUSi4i5/GO").mobile("+40787654308").address("111 Oak St, Moscow, Russia").birthday(LocalDate.of(1992, 4, 23)).roleId(1).employmentType(FULL_TIME).position(BACKEND).grade(SENIOR).supervisingMentorId(1).studiesIds(List.of(3, 4)).experiencesIds(List.of(15, 16)).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }

    public static MentorDto getMockedMentorDto9() { return MentorDto.builder().id(9).name("Sophia Garcia").email("sophia.garcia@example.com").password("$2a$10$uO2oGV6PM5aBrAmqR98LQu2FtC/iaFoXx2nsyNXuOhNbaEOzXKbx2").mobile("+40754321809").address("333 Pine St, Athens, Greece").birthday(LocalDate.of(1998, 7, 30)).roleId(1).employmentType(FULL_TIME).position(DEVOPS).grade(SENIOR).supervisingMentorId(1).studiesIds(List.of(5, 6)).experiencesIds(List.of(1, 2)).nrTrainees(1).maxTrainees(5).openForTraining(false).build(); }

    public static MentorDto getMockedMentorDto10() { return MentorDto.builder().id(10).name("Joseph Wilson").email("joseph.wilson@example.com").password("$2a$10$aoNj5vm.RdmMDffhpCgpu.mCmhgbanqS1R1CHeFR1iSjv/cASQa4W").mobile("+40789012610").address("555 Elm St, Madrid, Spain").birthday(LocalDate.of(1991, 3, 14)).roleId(1).employmentType(FULL_TIME).position(DEVOPS).grade(SENIOR).supervisingMentorId(1).studiesIds(List.of(7, 8)).experiencesIds(List.of(3, 4)).nrTrainees(1).maxTrainees(5).openForTraining(true).build(); }
}
