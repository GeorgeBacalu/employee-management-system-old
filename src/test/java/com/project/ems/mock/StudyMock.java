package com.project.ems.mock;

import com.project.ems.study.Study;
import com.project.ems.study.enums.StudyType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyMock {

    public static List<Study> getMockedStudies() {
        return Stream.of(getMockedStudies1(), getMockedStudies2(), getMockedStudies3(), getMockedStudies4(), getMockedStudies5(), getMockedStudies6()).flatMap(List::stream).toList();
    }

    public static List<Study> getMockedStudiesPage1() {
        return getMockedStudies1();
    }

    public static List<Study> getMockedStudiesPage2() {
        return getMockedStudies2();
    }

    public static List<Study> getMockedStudiesPage3() {
        return getMockedStudies3();
    }

    public static List<Study> getMockedStudies1() {
        return new ArrayList<>(List.of(getMockedStudy1(), getMockedStudy2()));
    }

    public static List<Study> getMockedStudies2() {
        return new ArrayList<>(List.of(getMockedStudy3(), getMockedStudy4()));
    }

    public static List<Study> getMockedStudies3() {
        return new ArrayList<>(List.of(getMockedStudy5(), getMockedStudy6()));
    }

    public static List<Study> getMockedStudies4() {
        return new ArrayList<>(List.of(getMockedStudy7(), getMockedStudy8()));
    }

    public static List<Study> getMockedStudies5() {
        return new ArrayList<>(List.of(getMockedStudy9(), getMockedStudy10()));
    }

    public static List<Study> getMockedStudies6() {
        return new ArrayList<>(List.of(getMockedStudy11(), getMockedStudy12()));
    }

    public static Study getMockedStudy1() {
        return new Study(1, "Data Science Bootcamp", "DataCamp", "Data Science specialized training at DataCamp", StudyType.SPECIALIZED_TRAINING, LocalDate.of(2022, 1, 15), LocalDate.of(2022, 6, 30));
    }

    public static Study getMockedStudy2() {
        return new Study(2, "Web Development Immersive", "General Assembly", "Web Development specialized training at General Assembly", StudyType.SPECIALIZED_TRAINING, LocalDate.of(2021, 7, 1), LocalDate.of(2022, 1, 31));
    }

    public static Study getMockedStudy3() {
        return new Study(3, "Digital Marketing Certification", "HubSpot Academy", "Digital Marketing Certification from HubSpot Academy", StudyType.SPECIALIZED_TRAINING, LocalDate.of(2023, 3, 1), LocalDate.of(2023, 6, 30));
    }

    public static Study getMockedStudy4() {
        return new Study(4, "UX/UI Design Workshop", "Interaction Design Foundation", "UX/UI Design Workshop at Interaction Design Foundation", StudyType.SPECIALIZED_TRAINING, LocalDate.of(2022, 9, 1), LocalDate.of(2022, 11, 30));
    }

    public static Study getMockedStudy5() {
        return new Study(5, "Harvard University", "Faculty of Arts and Sciences", "Bachelor's degree in Economics at Harvard University - Faculty of Arts and Sciences", StudyType.BACHELORS, LocalDate.of(2012, 8, 31), LocalDate.of(2016, 6, 1));
    }

    public static Study getMockedStudy6() {
        return new Study(6, "University of Chicago", "Booth School of Business", "Bachelor's in Business Analysis at University of Chicago - Booth School of Business", StudyType.BACHELORS, LocalDate.of(2017, 6, 1), LocalDate.of(2020, 3, 1));
    }

    public static Study getMockedStudy7() {
        return new Study(7, "Massachusetts Institute of Technology", "School of Science", "Bachelor's degree in Physics at Massachusetts Institute of Technology - School of Science", StudyType.BACHELORS, LocalDate.of(2013, 9, 1), LocalDate.of(2017, 5, 31));
    }

    public static Study getMockedStudy8() {
        return new Study(8, "University of Cambridge", "Faculty of Mathematics", "Bachelor's degree in Mathematics at University of Cambridge - Faculty of Mathematics", StudyType.BACHELORS, LocalDate.of(2019, 10, 1), LocalDate.of(2022, 6, 30));
    }

    public static Study getMockedStudy9() {
        return new Study(9, "Stanford University", "School of Engineering", "Master's degree in Computer Science at Stanford University - School of Engineering", StudyType.MASTERS, LocalDate.of(2016, 8, 31), LocalDate.of(2019, 6, 1));
    }

    public static Study getMockedStudy10() {
        return new Study(10, "University College London", "Faculty of Engineering", "Master's degree in Software Engineering at University College London - Faculty of Engineering", StudyType.MASTERS, LocalDate.of(2020, 3, 1), LocalDate.of(2023, 3, 1));
    }

    public static Study getMockedStudy11() {
        return new Study(11, "ETH Zurich", "Department of Computer Science", "Bachelor's degree in Artificial Intelligence at ETH Zurich - Department of Computer Science", StudyType.MASTERS, LocalDate.of(2017, 9, 1), LocalDate.of(2020, 8, 31));
    }

    public static Study getMockedStudy12() {
        return new Study(12, "California Institute of Technology", "Division of Engineering and Applied Science", "Master's degree in Electrical Engineering at California Institute of Technology - Division of Engineering and Applied Science", StudyType.MASTERS, LocalDate.of(2018, 9, 1), LocalDate.of(2021, 5, 31));
    }
}
