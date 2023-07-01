package com.project.ems.mock;

import com.project.ems.experience.Experience;
import com.project.ems.experience.enums.ExperienceType;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExperienceMock {

    public static List<Experience> getMockedExperiences() {
        return Stream.of(getMockedExperiences1(), getMockedExperiences2()).flatMap(List::stream).toList();
    }

    public static List<Experience> getMockedExperiences1() {
        return List.of(getMockedExperience1(), getMockedExperience2());
    }

    public static List<Experience> getMockedExperiences2() {
        return List.of(getMockedExperience3(), getMockedExperience4());
    }

    public static Experience getMockedExperience1() {
        return Experience.builder()
              .id(1)
              .title("Experience_title1")
              .organization("Experience_organization1")
              .description("Experience_description1")
              .type(ExperienceType.APPRENTICESHIP)
              .startedAt(LocalDate.of(2010, 1, 1))
              .finishedAt(LocalDate.of(2011, 1, 1))
              .build();
    }

    public static Experience getMockedExperience2() {
        return Experience.builder()
              .id(2)
              .title("Experience_title2")
              .organization("Experience_organization2")
              .description("Experience_description2")
              .type(ExperienceType.INTERNSHIP)
              .startedAt(LocalDate.of(2011, 1, 1))
              .finishedAt(LocalDate.of(2012, 1, 1))
              .build();
    }

    public static Experience getMockedExperience3() {
        return Experience.builder()
              .id(3)
              .title("Experience_title3")
              .organization("Experience_organization3")
              .description("Experience_description3")
              .type(ExperienceType.TRAINING)
              .startedAt(LocalDate.of(2012, 1, 1))
              .finishedAt(LocalDate.of(2013, 1, 1))
              .build();
    }

    public static Experience getMockedExperience4() {
        return Experience.builder()
              .id(4)
              .title("Experience_title4")
              .organization("Experience_organization4")
              .description("Experience_description4")
              .type(ExperienceType.VOLUNTEERING)
              .startedAt(LocalDate.of(2013, 1, 1))
              .finishedAt(LocalDate.of(2014, 1, 1))
              .build();
    }
}
