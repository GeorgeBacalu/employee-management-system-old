package com.project.ems.mock;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.enums.Grade;
import java.time.LocalDate;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.ems.employee.enums.EmploymentType.FULL_TIME;
import static com.project.ems.employee.enums.EmploymentType.PART_TIME;
import static com.project.ems.employee.enums.Grade.JUNIOR;
import static com.project.ems.employee.enums.Grade.MID;
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
import static com.project.ems.mock.AuthorityMock.getMockedAuthorities;
import static com.project.ems.mock.AuthorityMock.getMockedAuthoritiesIds;
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
        return List.of(getMockedEmployee1(), getMockedEmployee2(), getMockedEmployee3(), getMockedEmployee4(), getMockedEmployee5(), getMockedEmployee6(), getMockedEmployee7(), getMockedEmployee8(), getMockedEmployee9(),
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

    public static List<EmployeeDto> getMockedEmployeeDtosPage1() {
        return List.of(getMockedEmployeeDto1(), getMockedEmployeeDto2());
    }

    public static List<EmployeeDto> getMockedEmployeeDtosPage2() {
        return List.of(getMockedEmployeeDto3(), getMockedEmployeeDto4());
    }

    public static List<EmployeeDto> getMockedEmployeeDtosPage3() {
        return List.of(getMockedEmployeeDto5(), getMockedEmployeeDto6());
    }

    public static List<Employee> getMockedEmployeesFirstPage() { return List.of(getMockedEmployee1(), getMockedEmployee2(), getMockedEmployee3(), getMockedEmployee4(), getMockedEmployee5(), getMockedEmployee6(), getMockedEmployee7(), getMockedEmployee8(), getMockedEmployee9(), getMockedEmployee10()); }

    public static List<EmployeeDto> getMockedEmployeeDtosFirstPage() { return List.of(getMockedEmployeeDto1(), getMockedEmployeeDto2(), getMockedEmployeeDto3(), getMockedEmployeeDto4(), getMockedEmployeeDto5(), getMockedEmployeeDto6(), getMockedEmployeeDto7(), getMockedEmployeeDto8(), getMockedEmployeeDto9(), getMockedEmployeeDto10()); }

    public static Employee getMockedEmployee1() { return Employee.builder().id(1).name("Abigail Johnson").email("abigail.johnson@example.com").password("$2a$10$7UUcqTY7jdmZDBOBDiZ3g.HqGB6D06nJP/AwvPFV7rwo9uOaZNIlO").mobile("+40754321837").address("999 Oak St, Athens, Greece").birthday(LocalDate.of(2000, 10, 2)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(FRONTEND).grade(JUNIOR).mentor(getMockedMentor1()).studies(getMockedStudies1()).experiences(getMockedExperiences1()).build(); }

    public static Employee getMockedEmployee2() { return Employee.builder().id(2).name("Michael Davis").email("michael.davis@example.com").password("$2a$10$3auV5ZcsQ3BsY8.3oUrgPucCvU0eznOnu6rMzSCXBuAtk/PYijS/m").mobile("+40789012638").address("111 Oak St, Madrid, Spain").birthday(LocalDate.of(1994, 5, 16)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(FRONTEND).grade(JUNIOR).mentor(getMockedMentor2()).studies(getMockedStudies2()).experiences(getMockedExperiences2()).build(); }

    public static Employee getMockedEmployee3() { return Employee.builder().id(3).name("Mia Wilson").email("mia.wilson@example.com").password("$2a$10$MuET5mkpKltmeMVvuHd6V.7259YpfoILdK2.nddroh9faRLqLpHEm").mobile("+40723145639").address("333 Elm St, Tokyo, Japan").birthday(LocalDate.of(1990, 12, 29)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(FRONTEND).grade(Grade.MID).mentor(getMockedMentor3()).studies(getMockedStudies3()).experiences(getMockedExperiences3()).build(); }

    public static Employee getMockedEmployee4() { return Employee.builder().id(4).name("James Lee").email("james.lee@example.com").password("$2a$10$4FbH/OxBf07GkqQF4jZ0lewyz6848Bd9/FQDZlFpkWxVKCJoe2lFu").mobile("+40787654340").address("555 Pine St, Seoul, South Korea").birthday(LocalDate.of(1991, 8, 11)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(FRONTEND).grade(SENIOR).mentor(getMockedMentor4()).studies(getMockedStudies4()).experiences(getMockedExperiences4()).build(); }

    public static Employee getMockedEmployee5() { return Employee.builder().id(5).name("Charlotte Thompson").email("charlotte.thompson@example.com").password("$2a$10$cBB9kPr6TTTuSoXGvemMpOM22GFC3luTSifaHUiGVSe1DD5/alV5O").mobile("+40754321841").address("777 Elm St, Beijing, China").birthday(LocalDate.of(1993, 3, 24)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(BACKEND).grade(JUNIOR).mentor(getMockedMentor5()).studies(getMockedStudies5()).experiences(getMockedExperiences5()).build(); }

    public static Employee getMockedEmployee6() { return Employee.builder().id(6).name("Ethan Smith").email("ethan.smith@example.com").password("$2a$10$FvZzJA.PCFlv5b4NqYQBSuMjwDnFt2FGtjqXkhyNcDDPrp/gf5Oxe").mobile("+40789012642").address("999 Oak St, Cape Town, South Africa").birthday(LocalDate.of(1989, 11, 6)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(BACKEND).grade(JUNIOR).mentor(getMockedMentor6()).studies(getMockedStudies6()).experiences(getMockedExperiences6()).build(); }

    public static Employee getMockedEmployee7() { return Employee.builder().id(7).name("Amelia Johnson").email("amelia.johnson@example.com").password("$2a$10$6d32lzlX2hB83fWixR5jeOroXywO1SVvUnvdyaaZ9wk4Pp5F7Yq0y").mobile("+40723145643").address("111 Elm St, Buenos Aires, Argentina").birthday(LocalDate.of(1994, 6, 19)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(BACKEND).grade(MID).mentor(getMockedMentor7()).studies(getMockedStudies1()).experiences(getMockedExperiences7()).build(); }

    public static Employee getMockedEmployee8() { return Employee.builder().id(8).name("Emily Davis").email("emily.davis@example.com").password("$2a$10$n0mHfbplLnUpRNFbXEYD5OaQBeCjZzUsuRJ5H/S8ofINjKXECaZ.G").mobile("+40787654344").address("333 Elm St, Rio de Janeiro, Brazil").birthday(LocalDate.of(1998, 1, 1)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(BACKEND).grade(SENIOR).mentor(getMockedMentor8()).studies(getMockedStudies2()).experiences(getMockedExperiences8()).build(); }

    public static Employee getMockedEmployee9() { return Employee.builder().id(9).name("Henry Wilson").email("henry.wilson@example.com").password("$2a$10$AZ3jGKUs6D8lfhHUr3rx3uQ/0T476Fcht5TbiaDAFXbye450e9LyC").mobile("+40754321845").address("555 Pine St, Mexico City, Mexico").birthday(LocalDate.of(2001, 8, 14)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(DEVOPS).grade(JUNIOR).mentor(getMockedMentor9()).studies(getMockedStudies3()).experiences(getMockedExperiences1()).build(); }

    public static Employee getMockedEmployee10() { return Employee.builder().id(10).name("Scarlett Thompson").email("scarlett.thompson@example.com").password("$2a$10$oRPHmqBWyxqUUTp9/D7H5.yfGDfdec0/RiY.WzB7BhMPPcBkE6cSu").mobile("+40789012646").address("777 Elm St, Vancouver, Canada").birthday(LocalDate.of(2002, 3, 28)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(DEVOPS).grade(JUNIOR).mentor(getMockedMentor10()).studies(getMockedStudies4()).experiences(getMockedExperiences2()).build(); }

    public static Employee getMockedEmployee11() { return Employee.builder().id(11).name("Jacob Brown").email("jacob.brown@example.com").password("$2a$10$OO1Z6TYDthCdybZU/rceSOQfFAcsxvkZT2yBO9ucYgqENtQX.50va").mobile("+40723145647").address("999 Pine St, Paris, France").birthday(LocalDate.of(1999, 11, 10)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(DEVOPS).grade(MID).mentor(getMockedMentor11()).studies(getMockedStudies5()).experiences(getMockedExperiences3()).build(); }

    public static Employee getMockedEmployee12() { return Employee.builder().id(12).name("Ava Smith").email("ava.smith@example.com").password("$2a$10$Oo7exvPPj/LhlunzWwSHhuyTEPwd13A6cpZae9f9LtamdtJNAFoRO").mobile("+40787654348").address("111 Pine St, London, UK").birthday(LocalDate.of(1986, 6, 23)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(DEVOPS).grade(SENIOR).mentor(getMockedMentor12()).studies(getMockedStudies6()).experiences(getMockedExperiences4()).build(); }

    public static Employee getMockedEmployee13() { return Employee.builder().id(13).name("Oliver Johnson").email("oliver.johnson@example.com").password("$2a$10$94fTudR/Hf9H8ikS6Ce/dO.i1VuvD/1zSWpVFJGQJn0b6JSwRlJZG").mobile("+40754321849").address("333 Elm St, Berlin, Germany").birthday(LocalDate.of(1988, 2, 5)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(TESTING).grade(JUNIOR).mentor(getMockedMentor13()).studies(getMockedStudies1()).experiences(getMockedExperiences5()).build(); }

    public static Employee getMockedEmployee14() { return Employee.builder().id(14).name("Sophia Wilson").email("sophia.wilson@example.com").password("$2a$10$5wChcsu5xUGj4u/H1yDIbe7xk8lAz8riAOe2BqGdj70K/HNcKjTRm").mobile("+40789012650").address("555 Elm St, Moscow, Russia").birthday(LocalDate.of(1994, 9, 19)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(TESTING).grade(JUNIOR).mentor(getMockedMentor14()).studies(getMockedStudies2()).experiences(getMockedExperiences6()).build(); }

    public static Employee getMockedEmployee15() { return Employee.builder().id(15).name("William Davis").email("william.davis@example.com").password("$2a$10$257Ppu30VNArsiC3urYLGuts6H7dZqZtZslzBvkXZK0lC59Rz//U2").mobile("+40723145651").address("777 Pine St, Athens, Greece").birthday(LocalDate.of(1996, 4, 3)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(TESTING).grade(MID).mentor(getMockedMentor15()).studies(getMockedStudies3()).experiences(getMockedExperiences7()).build(); }

    public static Employee getMockedEmployee16() { return Employee.builder().id(16).name("Mia Johnson").email("mia.johnson@example.com").password("$2a$10$LFMAamKO7DYmBGppJpxwDuLD4btdjty5jCokyUX/VIH6Hcml31rai").mobile("+40787654352").address("999 Oak St, Madrid, Spain").birthday(LocalDate.of(1998, 11, 16)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(TESTING).grade(SENIOR).mentor(getMockedMentor16()).studies(getMockedStudies4()).experiences(getMockedExperiences8()).build(); }

    public static Employee getMockedEmployee17() { return Employee.builder().id(17).name("James Lee").email("james.lee@example.com").password("$2a$10$wqh8ddUuBBncfB//NDGGxuVO.ABJce5F9YwCT2QBUW2SQVenRKak2").mobile("+40754321853").address("111 Elm St, Tokyo, Japan").birthday(LocalDate.of(1997, 6, 29)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(DESIGN).grade(JUNIOR).mentor(getMockedMentor17()).studies(getMockedStudies5()).experiences(getMockedExperiences1()).build(); }

    public static Employee getMockedEmployee18() { return Employee.builder().id(18).name("Charlotte Brown").email("charlotte.brown@example.com").password("$2a$10$WfHpWQ4nIFeQ.jaVEV0Ga.IXMZQewk6PFogdeJHAhCcrUfxjyDfrG").mobile("+40789012654").address("333 Pine St, Seoul, South Korea").birthday(LocalDate.of(2000, 2, 12)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(FULL_TIME).position(DESIGN).grade(JUNIOR).mentor(getMockedMentor18()).studies(getMockedStudies6()).experiences(getMockedExperiences2()).build(); }

    public static Employee getMockedEmployee19() { return Employee.builder().id(19).name("Ethan Smith").email("ethan.smith@example.com").password("$2a$10$SuJZKKj2BG/Snz3Csehvq.yIh7sPpA./pJccTEvwzwDDH6dH5Txxm").mobile("+40723145655").address("555 Elm St, Beijing, China").birthday(LocalDate.of(1998, 9, 25)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(DESIGN).grade(MID).mentor(getMockedMentor19()).studies(getMockedStudies1()).experiences(getMockedExperiences3()).build(); }

    public static Employee getMockedEmployee20() { return Employee.builder().id(20).name("Emily Davis").email("emily.davis@example.com").password("$2a$10$GudEvtjLoJSxTiZoCOYqmO4.OjWPQyCj1C6gWfYEpsjReeHl2LxjC").mobile("+40787654356").address("777 Elm St, Cape Town, South Africa").birthday(LocalDate.of(2001, 7, 9)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(DESIGN).grade(SENIOR).mentor(getMockedMentor20()).studies(getMockedStudies2()).experiences(getMockedExperiences4()).build(); }

    public static Employee getMockedEmployee21() { return Employee.builder().id(21).name("Jacob Thompson").email("jacob.thompson@example.com").password("$2a$10$2tlMlGt5xxn5wwx3wID52eGMdCjlGVtiCunQdsN0qYsdiIPAG1mX2").mobile("+40754321857").address("999 Pine St, Buenos Aires, Argentina").birthday(LocalDate.of(1996, 2, 22)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(DATA_ANALYST).grade(JUNIOR).mentor(getMockedMentor21()).studies(getMockedStudies3()).experiences(getMockedExperiences5()).build(); }

    public static Employee getMockedEmployee22() { return Employee.builder().id(22).name("Sophia Wilson").email("sophia.wilson@example.com").password("$2a$10$WrJOjHuzHZZmiYauvD.CLuXCusKkhemkIXCe/s9bApcg3.VbcALTS").mobile("+40789012658").address("111 Elm St, Rio de Janeiro, Brazil").birthday(LocalDate.of(1995, 10, 7)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(DATA_ANALYST).grade(JUNIOR).mentor(getMockedMentor22()).studies(getMockedStudies4()).experiences(getMockedExperiences6()).build(); }

    public static Employee getMockedEmployee23() { return Employee.builder().id(23).name("Oliver Johnson").email("oliver.johnson@example.com").password("$2a$10$2SL7v1wglwp.BpoljPgKke9gRzn46wyanmPRH1Qt7vQyllKRRbBbS").mobile("+40723145659").address("333 Elm St, Mexico City, Mexico").birthday(LocalDate.of(1998, 7, 21)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(DATA_ANALYST).grade(MID).mentor(getMockedMentor23()).studies(getMockedStudies5()).experiences(getMockedExperiences7()).build(); }

    public static Employee getMockedEmployee24() { return Employee.builder().id(24).name("Scarlett Wilson").email("scarlett.wilson@example.com").password("$2a$10$/8XJPbpHCcBvLjL/0OUdN.FC8L/96z5kysz0dk7A3RUoVxgXCIrmC").mobile("+40787654360").address("555 Elm St, Vancouver, Canada").birthday(LocalDate.of(2001, 5, 5)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(DATA_ANALYST).grade(SENIOR).mentor(getMockedMentor24()).studies(getMockedStudies6()).experiences(getMockedExperiences8()).build(); }

    public static Employee getMockedEmployee25() { return Employee.builder().id(25).name("William Davis").email("william.davis@example.com").password("$2a$10$491zZkJmr8sEpEYoFY9zlOGWwSC5GkEd8lseuJY8M1Kt8v0VfcjB6").mobile("+40754321861").address("777 Pine St, Paris, France").birthday(LocalDate.of(1999, 12, 17)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(MACHINE_LEARNING).grade(JUNIOR).mentor(getMockedMentor25()).studies(getMockedStudies1()).experiences(getMockedExperiences1()).build(); }

    public static Employee getMockedEmployee26() { return Employee.builder().id(26).name("Mia Johnson").email("mia.johnson@example.com").password("$2a$10$p92501aT9fP1OSFYjH84guX9Fryv7UTb0pIAuHQ8KzIs7jzzBHkuG").mobile("+40789012662").address("999 Oak St, London, UK").birthday(LocalDate.of(2002, 9, 30)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(MACHINE_LEARNING).grade(JUNIOR).mentor(getMockedMentor26()).studies(getMockedStudies2()).experiences(getMockedExperiences2()).build(); }

    public static Employee getMockedEmployee27() { return Employee.builder().id(27).name("James Lee").email("james.lee@example.com").password("$2a$10$vfsknQZqRgxXc2PPGh4NYubY6Sz9u/OlV2NdS0koy1coxetYJBTZK").mobile("+40723145663").address("111 Elm St, Berlin, Germany").birthday(LocalDate.of(1996, 5, 14)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(MACHINE_LEARNING).grade(MID).mentor(getMockedMentor27()).studies(getMockedStudies3()).experiences(getMockedExperiences3()).build(); }

    public static Employee getMockedEmployee28() { return Employee.builder().id(28).name("Charlotte Brown").email("charlotte.brown@example.com").password("$2a$10$9OE8rwchWegMy/FAcvMYU.vOSj0dNoIMzzzaiuRBgiyhkJkWU0Q8i").mobile("+40787654364").address("333 Elm St, Moscow, Russia").birthday(LocalDate.of(2002, 12, 27)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(MACHINE_LEARNING).grade(SENIOR).mentor(getMockedMentor28()).studies(getMockedStudies4()).experiences(getMockedExperiences4()).build(); }

    public static Employee getMockedEmployee29() { return Employee.builder().id(29).name("Elijah Roberts").email("elijah.roberts@example.com").password("$2a$10$XILV0oXm68X3a.Ym23xlG.Gk1tcQVR1ZhruRVXV7JJ62qGB4yc7u2").mobile("+40754321865").address("555 Pine St, Sydney, Australia").birthday(LocalDate.of(2003, 1, 27)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(BUSINESS_ANALYST).grade(JUNIOR).mentor(getMockedMentor29()).studies(getMockedStudies5()).experiences(getMockedExperiences5()).build(); }

    public static Employee getMockedEmployee30() { return Employee.builder().id(30).name("Amelia Walker").email("amelia.walker@example.com").password("$2a$10$dxOTaCQO.7RJK.cAsJ97iu9J4kLMdJXusmvSG2IYq8P454GAO/SSa").mobile("+40789012666").address("777 Oak St, Rome, Italy").birthday(LocalDate.of(1992, 8, 12)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(BUSINESS_ANALYST).grade(JUNIOR).mentor(getMockedMentor30()).studies(getMockedStudies6()).experiences(getMockedExperiences6()).build(); }

    public static Employee getMockedEmployee31() { return Employee.builder().id(31).name("Daniel Green").email("daniel.green@example.com").password("$2a$10$H5Esf6nFvsfooqzGbN4MoOG44HRT9YrgfeiuPwtZPJwce4VFq3q9e").mobile("+40723145667").address("999 Elm St, Moscow, Russia").birthday(LocalDate.of(1996, 3, 27)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(BUSINESS_ANALYST).grade(MID).mentor(getMockedMentor31()).studies(getMockedStudies1()).experiences(getMockedExperiences7()).build(); }

    public static Employee getMockedEmployee32() { return Employee.builder().id(32).name("Liam Hall").email("liam.hall@example.com").password("$2a$10$hHaSEzjoEsDuc./b.tuAguHhMrhAWfabZMlHVAwmmlv2t5LzqhjaW").mobile("+40787654368").address("111 Oak St, Athens, Greece").birthday(LocalDate.of(1998, 11, 9)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(BUSINESS_ANALYST).grade(SENIOR).mentor(getMockedMentor32()).studies(getMockedStudies2()).experiences(getMockedExperiences8()).build(); }

    public static Employee getMockedEmployee33() { return Employee.builder().id(33).name("Sophia Young").email("sophia.young@example.com").password("$2a$10$hXkNymRKKR/ecaWDFpAjCuA4F.mFSIsMRG5pm3EcEpC52RTE/bmS2").mobile("+40754321869").address("333 Pine St, Madrid, Spain").birthday(LocalDate.of(1995, 6, 23)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(SCRUM_MASTER).grade(JUNIOR).mentor(getMockedMentor33()).studies(getMockedStudies3()).experiences(getMockedExperiences1()).build(); }

    public static Employee getMockedEmployee34() { return Employee.builder().id(34).name("Noah Clark").email("noah.clark@example.com").password("$2a$10$XDO5A5bCk.bDuJm1eKEF6./FIu51X2g9voB44GdXfb/6ICnH5MWmG").mobile("+40789012670").address("555 Elm St, Tokyo, Japan").birthday(LocalDate.of(1997, 2, 5)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(SCRUM_MASTER).grade(JUNIOR).mentor(getMockedMentor34()).studies(getMockedStudies4()).experiences(getMockedExperiences2()).build(); }

    public static Employee getMockedEmployee35() { return Employee.builder().id(35).name("Olivia Hill").email("olivia.hill@example.com").password("$2a$10$BU7Fl.RH3Bf66WjaQYTjq.vP5cLtK3WKlX4f3dfeEbuI..IgxKl3u").mobile("+40723145671").address("777 Oak St, Seoul, South Korea").birthday(LocalDate.of(2001, 9, 20)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(SCRUM_MASTER).grade(MID).mentor(getMockedMentor35()).studies(getMockedStudies5()).experiences(getMockedExperiences3()).build(); }

    public static Employee getMockedEmployee36() { return Employee.builder().id(36).name("Michaela Allen").email("michaela.allen@example.com").password("$2a$10$jtNJLdz.PNuBgZWbBz/APOOjjipUSn9Qw6p1VmEM1PjYCBXZHI.Lm").mobile("+40787654372").address("999 Pine St, Beijing, China").birthday(LocalDate.of(1998, 5, 6)).role(getMockedRole1()).authorities(getMockedAuthorities()).employmentType(PART_TIME).position(SCRUM_MASTER).grade(SENIOR).mentor(getMockedMentor36()).studies(getMockedStudies6()).experiences(getMockedExperiences4()).build(); }

    public static EmployeeDto getMockedEmployeeDto1() { return EmployeeDto.builder().id(1).name("Abigail Johnson").email("abigail.johnson@example.com").password("$2a$10$7UUcqTY7jdmZDBOBDiZ3g.HqGB6D06nJP/AwvPFV7rwo9uOaZNIlO").mobile("+40754321837").address("999 Oak St, Athens, Greece").birthday(LocalDate.of(2000, 10, 2)).roleId(1).authoritiesIds(getMockedAuthoritiesIds()).employmentType(FULL_TIME).position(FRONTEND).grade(JUNIOR).mentorId(1).studiesIds(List.of(1, 2)).experiencesIds(List.of(1, 2)).build(); }

    public static EmployeeDto getMockedEmployeeDto2() { return EmployeeDto.builder().id(2).name("Michael Davis").email("michael.davis@example.com").password("$2a$10$3auV5ZcsQ3BsY8.3oUrgPucCvU0eznOnu6rMzSCXBuAtk/PYijS/m").mobile("+40789012638").address("111 Oak St, Madrid, Spain").birthday(LocalDate.of(1994, 5, 16)).roleId(1).authoritiesIds(getMockedAuthoritiesIds()).employmentType(FULL_TIME).position(FRONTEND).grade(JUNIOR).mentorId(2).studiesIds(List.of(3, 4)).experiencesIds(List.of(3, 4)).build(); }

    public static EmployeeDto getMockedEmployeeDto3() { return EmployeeDto.builder().id(3).name("Mia Wilson").email("mia.wilson@example.com").password("$2a$10$MuET5mkpKltmeMVvuHd6V.7259YpfoILdK2.nddroh9faRLqLpHEm").mobile("+40723145639").address("333 Elm St, Tokyo, Japan").birthday(LocalDate.of(1990, 12, 29)).roleId(1).authoritiesIds(getMockedAuthoritiesIds()).employmentType(FULL_TIME).position(FRONTEND).grade(MID).mentorId(3).studiesIds(List.of(5, 6)).experiencesIds(List.of(5, 6)).build(); }

    public static EmployeeDto getMockedEmployeeDto4() { return EmployeeDto.builder().id(4).name("James Lee").email("james.lee@example.com").password("$2a$10$4FbH/OxBf07GkqQF4jZ0lewyz6848Bd9/FQDZlFpkWxVKCJoe2lFu").mobile("+40787654340").address("555 Pine St, Seoul, South Korea").birthday(LocalDate.of(1991, 8, 11)).roleId(1).authoritiesIds(getMockedAuthoritiesIds()).employmentType(FULL_TIME).position(FRONTEND).grade(SENIOR).mentorId(4).studiesIds(List.of(7, 8)).experiencesIds(List.of(7, 8)).build(); }

    public static EmployeeDto getMockedEmployeeDto5() { return EmployeeDto.builder().id(5).name("Charlotte Thompson").email("charlotte.thompson@example.com").password("$2a$10$cBB9kPr6TTTuSoXGvemMpOM22GFC3luTSifaHUiGVSe1DD5/alV5O").mobile("+40754321841").address("777 Elm St, Beijing, China").birthday(LocalDate.of(1993, 3, 24)).roleId(1).authoritiesIds(getMockedAuthoritiesIds()).employmentType(FULL_TIME).position(BACKEND).grade(JUNIOR).mentorId(5).studiesIds(List.of(9, 10)).experiencesIds(List.of(9, 10)).build(); }

    public static EmployeeDto getMockedEmployeeDto6() { return EmployeeDto.builder().id(6).name("Ethan Smith").email("ethan.smith@example.com").password("$2a$10$FvZzJA.PCFlv5b4NqYQBSuMjwDnFt2FGtjqXkhyNcDDPrp/gf5Oxe").mobile("+40789012642").address("999 Oak St, Cape Town, South Africa").birthday(LocalDate.of(1989, 11, 6)).roleId(1).authoritiesIds(getMockedAuthoritiesIds()).employmentType(FULL_TIME).position(BACKEND).grade(JUNIOR).mentorId(6).studiesIds(List.of(11, 12)).experiencesIds(List.of(11, 12)).build(); }

    public static EmployeeDto getMockedEmployeeDto7() { return EmployeeDto.builder().id(7).name("Amelia Johnson").email("amelia.johnson@example.com").password("$2a$10$6d32lzlX2hB83fWixR5jeOroXywO1SVvUnvdyaaZ9wk4Pp5F7Yq0y").mobile("+40723145643").address("111 Elm St, Buenos Aires, Argentina").birthday(LocalDate.of(1994, 6, 19)).roleId(1).authoritiesIds(getMockedAuthoritiesIds()).employmentType(FULL_TIME).position(BACKEND).grade(MID).mentorId(7).studiesIds(List.of(1, 2)).experiencesIds(List.of(13, 14)).build(); }

    public static EmployeeDto getMockedEmployeeDto8() { return EmployeeDto.builder().id(8).name("Emily Davis").email("emily.davis@example.com").password("$2a$10$n0mHfbplLnUpRNFbXEYD5OaQBeCjZzUsuRJ5H/S8ofINjKXECaZ.G").mobile("+40787654344").address("333 Elm St, Rio de Janeiro, Brazil").birthday(LocalDate.of(1998, 1, 1)).roleId(1).authoritiesIds(getMockedAuthoritiesIds()).employmentType(FULL_TIME).position(BACKEND).grade(SENIOR).mentorId(8).studiesIds(List.of(3, 4)).experiencesIds(List.of(15, 16)).build(); }

    public static EmployeeDto getMockedEmployeeDto9() { return EmployeeDto.builder().id(9).name("Henry Wilson").email("henry.wilson@example.com").password("$2a$10$AZ3jGKUs6D8lfhHUr3rx3uQ/0T476Fcht5TbiaDAFXbye450e9LyC").mobile("+40754321845").address("555 Pine St, Mexico City, Mexico").birthday(LocalDate.of(2001, 8, 14)).roleId(1).authoritiesIds(getMockedAuthoritiesIds()).employmentType(FULL_TIME).position(DEVOPS).grade(JUNIOR).mentorId(9).studiesIds(List.of(5, 6)).experiencesIds(List.of(1, 2)).build(); }

    public static EmployeeDto getMockedEmployeeDto10() { return EmployeeDto.builder().id(10).name("Scarlett Thompson").email("scarlett.thompson@example.com").password("$2a$10$oRPHmqBWyxqUUTp9/D7H5.yfGDfdec0/RiY.WzB7BhMPPcBkE6cSu").mobile("+40789012646").address("777 Elm St, Vancouver, Canada").birthday(LocalDate.of(2002, 3, 28)).authoritiesIds(getMockedAuthoritiesIds()).roleId(1).employmentType(FULL_TIME).position(DEVOPS).grade(JUNIOR).mentorId(10).studiesIds(List.of(7, 8)).experiencesIds(List.of(3, 4)).build(); }
}
