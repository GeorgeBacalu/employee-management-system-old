package com.project.ems.mock;

import com.project.ems.study.Study;
import com.project.ems.study.StudyDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.ems.study.enums.StudyType.BACHELORS;
import static com.project.ems.study.enums.StudyType.MASTERS;
import static com.project.ems.study.enums.StudyType.SPECIALIZED_TRAINING;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyMock {

    public static List<Study> getMockedStudies() { return Stream.of(getMockedStudies1(), getMockedStudies2(), getMockedStudies3(), getMockedStudies4(), getMockedStudies5(), getMockedStudies6()).flatMap(List::stream).toList(); }

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

    public static List<Study> getMockedStudiesPage1() {
        return getMockedStudies1();
    }

    public static List<Study> getMockedStudiesPage2() {
        return getMockedStudies2();
    }

    public static List<Study> getMockedStudiesPage3() {
        return getMockedStudies3();
    }

    public static List<StudyDto> getMockedStudyDtosPage1() {
        return List.of(getMockedStudyDto1(), getMockedStudyDto2());
    }

    public static List<StudyDto> getMockedStudyDtosPage2() {
        return List.of(getMockedStudyDto3(), getMockedStudyDto4());
    }

    public static List<StudyDto> getMockedStudyDtosPage3() {
        return List.of(getMockedStudyDto5(), getMockedStudyDto6());
    }

    public static List<Study> getMockedStudiesFirstPage() { return List.of(getMockedStudy1(), getMockedStudy2(), getMockedStudy3(), getMockedStudy4(), getMockedStudy5(), getMockedStudy6(), getMockedStudy7(), getMockedStudy8(), getMockedStudy9(), getMockedStudy10()); }

    public static List<StudyDto> getMockedStudyDtosFirstPage() { return List.of(getMockedStudyDto1(), getMockedStudyDto2(), getMockedStudyDto3(), getMockedStudyDto4(), getMockedStudyDto5(), getMockedStudyDto6(), getMockedStudyDto7(), getMockedStudyDto8(), getMockedStudyDto9(), getMockedStudyDto10()); }

    public static Study getMockedStudy1() { return Study.builder().id(1).title("Data Science Bootcamp").institution("DataCamp").description("Data Science specialized training at DataCamp").type(SPECIALIZED_TRAINING).startedAt(LocalDate.of(2022, 1, 15)).finishedAt(LocalDate.of(2022, 6, 30)).build(); }

    public static Study getMockedStudy2() { return Study.builder().id(2).title("Web Development Immersive").institution("General Assembly").description("Web Development specialized training at General Assembly").type(SPECIALIZED_TRAINING).startedAt(LocalDate.of(2021, 7, 1)).finishedAt(LocalDate.of(2022, 1, 31)).build(); }

    public static Study getMockedStudy3() { return Study.builder().id(3).title("Digital Marketing Certification").institution("HubSpot Academy").description("Digital Marketing Certification from HubSpot Academy").type(SPECIALIZED_TRAINING).startedAt(LocalDate.of(2023, 3, 1)).finishedAt(LocalDate.of(2023, 6, 30)).build(); }

    public static Study getMockedStudy4() { return Study.builder().id(4).title("UX/UI Design Workshop").institution("Interaction Design Foundation").description("UX/UI Design Workshop at Interaction Design Foundation").type(SPECIALIZED_TRAINING).startedAt(LocalDate.of(2022, 9, 1)).finishedAt(LocalDate.of(2022, 11, 30)).build(); }

    public static Study getMockedStudy5() { return Study.builder().id(5).title("Harvard University").institution("Faculty of Arts and Sciences").description("Bachelor's degree in Economics at Harvard University - Faculty of Arts and Sciences").type(BACHELORS).startedAt(LocalDate.of(2012, 8, 31)).finishedAt(LocalDate.of(2016, 6, 1)).build(); }

    public static Study getMockedStudy6() { return Study.builder().id(6).title("University of Chicago").institution("Booth School of Business").description("Bachelor's in Business Analysis at University of Chicago - Booth School of Business").type(BACHELORS).startedAt(LocalDate.of(2017, 6, 1)).finishedAt(LocalDate.of(2020, 3, 1)).build(); }

    public static Study getMockedStudy7() { return Study.builder().id(7).title("Massachusetts Institute of Technology").institution("School of Science").description("Bachelor's degree in Physics at Massachusetts Institute of Technology - School of Science").type(BACHELORS).startedAt(LocalDate.of(2013, 9, 1)).finishedAt(LocalDate.of(2017, 5, 31)).build(); }

    public static Study getMockedStudy8() { return Study.builder().id(8).title("University of Cambridge").institution("Faculty of Mathematics").description("Bachelor's degree in Mathematics at University of Cambridge - Faculty of Mathematics").type(BACHELORS).startedAt(LocalDate.of(2019, 10, 1)).finishedAt(LocalDate.of(2022, 6, 30)).build(); }

    public static Study getMockedStudy9() { return Study.builder().id(9).title("Stanford University").institution("School of Engineering").description("Master's degree in Computer Science at Stanford University - School of Engineering").type(MASTERS).startedAt(LocalDate.of(2016, 8, 31)).finishedAt(LocalDate.of(2019, 6, 1)).build(); }

    public static Study getMockedStudy10() { return Study.builder().id(10).title("University College London").institution("Faculty of Engineering").description("Master's degree in Software Engineering at University College London - Faculty of Engineering").type(MASTERS).startedAt(LocalDate.of(2020, 3, 1)).finishedAt(LocalDate.of(2023, 3, 1)).build(); }

    public static Study getMockedStudy11() { return Study.builder().id(11).title("ETH Zurich").institution("Department of Computer Science").description("Bachelor's degree in Artificial Intelligence at ETH Zurich - Department of Computer Science").type(MASTERS).startedAt(LocalDate.of(2017, 9, 1)).finishedAt(LocalDate.of(2020, 8, 31)).build(); }

    public static Study getMockedStudy12() { return Study.builder().id(12).title("California Institute of Technology").institution("Division of Engineering and Applied Science").description("Master's degree in Electrical Engineering at California Institute of Technology - Division of Engineering and Applied Science").type(MASTERS).startedAt(LocalDate.of(2018, 9, 1)).finishedAt(LocalDate.of(2021, 5, 31)).build(); }

    public static StudyDto getMockedStudyDto1() { return StudyDto.builder().id(1).title("Data Science Bootcamp").institution("DataCamp").description("Data Science specialized training at DataCamp").type(SPECIALIZED_TRAINING).startedAt(LocalDate.of(2022, 1, 15)).finishedAt(LocalDate.of(2022, 6, 30)).build(); }

    public static StudyDto getMockedStudyDto2() { return StudyDto.builder().id(2).title("Web Development Immersive").institution("General Assembly").description("Web Development specialized training at General Assembly").type(SPECIALIZED_TRAINING).startedAt(LocalDate.of(2021, 7, 1)).finishedAt(LocalDate.of(2022, 1, 31)).build(); }

    public static StudyDto getMockedStudyDto3() { return StudyDto.builder().id(3).title("Digital Marketing Certification").institution("HubSpot Academy").description("Digital Marketing Certification from HubSpot Academy").type(SPECIALIZED_TRAINING).startedAt(LocalDate.of(2023, 3, 1)).finishedAt(LocalDate.of(2023, 6, 30)).build(); }

    public static StudyDto getMockedStudyDto4() { return StudyDto.builder().id(4).title("UX/UI Design Workshop").institution("Interaction Design Foundation").description("UX/UI Design Workshop at Interaction Design Foundation").type(SPECIALIZED_TRAINING).startedAt(LocalDate.of(2022, 9, 1)).finishedAt(LocalDate.of(2022, 11, 30)).build(); }

    public static StudyDto getMockedStudyDto5() { return StudyDto.builder().id(5).title("Harvard University").institution("Faculty of Arts and Sciences").description("Bachelor's degree in Economics at Harvard University - Faculty of Arts and Sciences").type(BACHELORS).startedAt(LocalDate.of(2012, 8, 31)).finishedAt(LocalDate.of(2016, 6, 1)).build(); }

    public static StudyDto getMockedStudyDto6() { return StudyDto.builder().id(6).title("University of Chicago").institution("Booth School of Business").description("Bachelor's in Business Analysis at University of Chicago - Booth School of Business").type(BACHELORS).startedAt(LocalDate.of(2017, 6, 1)).finishedAt(LocalDate.of(2020, 3, 1)).build(); }

    public static StudyDto getMockedStudyDto7() { return StudyDto.builder().id(7).title("Massachusetts Institute of Technology").institution("School of Science").description("Bachelor's degree in Physics at Massachusetts Institute of Technology - School of Science").type(BACHELORS).startedAt(LocalDate.of(2013, 9, 1)).finishedAt(LocalDate.of(2017, 5, 31)).build(); }

    public static StudyDto getMockedStudyDto8() { return StudyDto.builder().id(8).title("University of Cambridge").institution("Faculty of Mathematics").description("Bachelor's degree in Mathematics at University of Cambridge - Faculty of Mathematics").type(BACHELORS).startedAt(LocalDate.of(2019, 10, 1)).finishedAt(LocalDate.of(2022, 6, 30)).build(); }

    public static StudyDto getMockedStudyDto9() { return StudyDto.builder().id(9).title("Stanford University").institution("School of Engineering").description("Master's degree in Computer Science at Stanford University - School of Engineering").type(MASTERS).startedAt(LocalDate.of(2016, 8, 31)).finishedAt(LocalDate.of(2019, 6, 1)).build(); }

    public static StudyDto getMockedStudyDto10() { return StudyDto.builder().id(10).title("University College London").institution("Faculty of Engineering").description("Master's degree in Software Engineering at University College London - Faculty of Engineering").type(MASTERS).startedAt(LocalDate.of(2020, 3, 1)).finishedAt(LocalDate.of(2023, 3, 1)).build(); }
}
