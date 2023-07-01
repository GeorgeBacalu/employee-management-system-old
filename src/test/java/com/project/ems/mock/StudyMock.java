package com.project.ems.mock;

import com.project.ems.study.Study;
import com.project.ems.study.enums.StudyType;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyMock {

    public static List<Study> getMockedStudies() {
        return Stream.of(getMockedStudies1(), getMockedStudies2()).flatMap(List::stream).toList();
    }

    public static List<Study> getMockedStudies1() {
        return List.of(getMockedStudy1(), getMockedStudy2());
    }

    public static List<Study> getMockedStudies2() {
        return List.of(getMockedStudy3(), getMockedStudy4());
    }

    public static Study getMockedStudy1() {
        return Study.builder()
              .id(1)
              .title("Study_title1")
              .institution("Study_institution1")
              .description("Study_description1")
              .type(StudyType.BACHELORS)
              .startedAt(LocalDate.of(2010, 1, 1))
              .finishedAt(LocalDate.of(2014, 1, 1))
              .build();
    }

    public static Study getMockedStudy2() {
        return Study.builder()
              .id(2)
              .title("Study_title2")
              .institution("Study_institution2")
              .description("Study_description2")
              .type(StudyType.MASTERS)
              .startedAt(LocalDate.of(2014, 1, 1))
              .finishedAt(LocalDate.of(2018, 1, 1))
              .build();
    }

    public static Study getMockedStudy3() {
        return Study.builder()
              .id(3)
              .title("Study_title3")
              .institution("Study_institution3")
              .description("Study_description3")
              .type(StudyType.SPECIALIZED_TRAINING)
              .startedAt(LocalDate.of(2018, 1, 1))
              .finishedAt(LocalDate.of(2020, 1, 1))
              .build();
    }

    public static Study getMockedStudy4() {
        return Study.builder()
              .id(4)
              .title("Study_title4")
              .institution("Study_institution4")
              .description("Study_description4")
              .type(StudyType.SPECIALIZED_TRAINING)
              .startedAt(LocalDate.of(2020, 1, 1))
              .finishedAt(LocalDate.of(2022, 1, 1))
              .build();
    }
}
