package com.project.ems.mock;

import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.project.ems.experience.enums.ExperienceType.APPRENTICESHIP;
import static com.project.ems.experience.enums.ExperienceType.INTERNSHIP;
import static com.project.ems.experience.enums.ExperienceType.TRAINING;
import static com.project.ems.experience.enums.ExperienceType.VOLUNTEERING;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExperienceMock {

    public static List<Experience> getMockedExperiences() { return Stream.of(getMockedExperiences1(), getMockedExperiences2(), getMockedExperiences3(), getMockedExperiences4(), getMockedExperiences5(), getMockedExperiences6(), getMockedExperiences7(), getMockedExperiences8()).flatMap(List::stream).toList(); }

    public static List<Experience> getMockedExperiences1() {
        return new ArrayList<>(List.of(getMockedExperience1(), getMockedExperience2()));
    }

    public static List<Experience> getMockedExperiences2() {
        return new ArrayList<>(List.of(getMockedExperience3(), getMockedExperience4()));
    }

    public static List<Experience> getMockedExperiences3() {
        return new ArrayList<>(List.of(getMockedExperience5(), getMockedExperience6()));
    }

    public static List<Experience> getMockedExperiences4() {
        return new ArrayList<>(List.of(getMockedExperience7(), getMockedExperience8()));
    }

    public static List<Experience> getMockedExperiences5() {
        return new ArrayList<>(List.of(getMockedExperience9(), getMockedExperience10()));
    }

    public static List<Experience> getMockedExperiences6() {
        return new ArrayList<>(List.of(getMockedExperience11(), getMockedExperience12()));
    }

    public static List<Experience> getMockedExperiences7() {
        return new ArrayList<>(List.of(getMockedExperience13(), getMockedExperience14()));
    }

    public static List<Experience> getMockedExperiences8() {
        return new ArrayList<>(List.of(getMockedExperience15(), getMockedExperience16()));
    }

    public static List<Experience> getMockedExperiencesPage1() {
        return getMockedExperiences1();
    }

    public static List<Experience> getMockedExperiencesPage2() {
        return getMockedExperiences2();
    }

    public static List<Experience> getMockedExperiencesPage3() {
        return getMockedExperiences3();
    }

    public static List<ExperienceDto> getMockedExperienceDtosPage1() {
        return List.of(getMockedExperienceDto1(), getMockedExperienceDto2());
    }

    public static List<ExperienceDto> getMockedExperienceDtosPage2() {
        return List.of(getMockedExperienceDto3(), getMockedExperienceDto4());
    }

    public static List<ExperienceDto> getMockedExperienceDtosPage3() {
        return List.of(getMockedExperienceDto5(), getMockedExperienceDto6());
    }

    public static List<Experience> getMockedExperiencesFirstPage() { return List.of(getMockedExperience1(), getMockedExperience2(),getMockedExperience3(), getMockedExperience4(), getMockedExperience5(), getMockedExperience6(), getMockedExperience7(), getMockedExperience8(), getMockedExperience9(), getMockedExperience10()); }

    public static List<ExperienceDto> getMockedExperienceDtosFirstPage() { return List.of(getMockedExperienceDto1(), getMockedExperienceDto2(), getMockedExperienceDto3(), getMockedExperienceDto4(), getMockedExperienceDto5(), getMockedExperienceDto6(), getMockedExperienceDto7(), getMockedExperienceDto8(), getMockedExperienceDto9(), getMockedExperienceDto10()); }

    public static Experience getMockedExperience1() { return Experience.builder().id(1).title("Software Engineering Intern").organization("Google").description("Worked as a Software Engineering Intern at Google, gaining hands-on experience in developing scalable software solutions.").type(INTERNSHIP).startedAt(LocalDate.of(2019, 6, 1)).finishedAt(LocalDate.of(2019, 8, 31)).build(); }

    public static Experience getMockedExperience2() { return Experience.builder().id(2).title("Data Science Intern").organization("Facebook").description("Completed a Data Science Internship at Facebook, working on data analysis and machine learning projects to gain insights and drive business decisions.").type(INTERNSHIP).startedAt(LocalDate.of(2020, 6, 1)).finishedAt(LocalDate.of(2020, 8, 31)).build(); }

    public static Experience getMockedExperience3() { return Experience.builder().id(3).title("Backend Developer Intern").organization("Amazon").description("Interned as a Backend Developer at Amazon, contributing to the development of high-performance backend systems for e-commerce applications.").type(INTERNSHIP).startedAt(LocalDate.of(2019, 5, 1)).finishedAt(LocalDate.of(2019, 7, 31)).build(); }

    public static Experience getMockedExperience4() { return Experience.builder().id(4).title("Frontend Developer Intern").organization("Microsoft").description("Participated in a Frontend Developer Internship at Microsoft, collaborating on the development of user-friendly and responsive web interfaces.").type(INTERNSHIP).startedAt(LocalDate.of(2020, 5, 15)).finishedAt(LocalDate.of(2020, 8, 15)).build(); }

    public static Experience getMockedExperience5() { return Experience.builder().id(5).title("Machine Learning Trainee").organization("Apple").description("Underwent comprehensive training in Machine Learning at Apple, exploring advanced algorithms and applying them to real-world data analysis challenges.").type(TRAINING).startedAt(LocalDate.of(2018, 6, 15)).finishedAt(LocalDate.of(2018, 9, 15)).build(); }

    public static Experience getMockedExperience6() { return Experience.builder().id(6).title("Web Development Trainee").organization("IBM").description("Completed a rigorous training program in Web Development at IBM, acquiring skills in front-end and back-end development and working on industry projects.").type(TRAINING).startedAt(LocalDate.of(2017, 6, 1)).finishedAt(LocalDate.of(2017, 12, 31)).build(); }

    public static Experience getMockedExperience7() { return Experience.builder().id(7).title("Software Developer Trainee").organization("Intel").description("Engaged in a Software Developer Training program at Intel, learning best practices in software engineering and gaining practical experience in software development lifecycle.").type(TRAINING).startedAt(LocalDate.of(2018, 6, 1)).finishedAt(LocalDate.of(2018, 8, 31)).build(); }

    public static Experience getMockedExperience8() { return Experience.builder().id(8).title("Mobile Application Developer Trainee").organization("Uber").description("Participated in a Mobile Application Development Training program at Uber, learning mobile development frameworks and building innovative mobile applications.").type(TRAINING).startedAt(LocalDate.of(2019, 9, 1)).finishedAt(LocalDate.of(2019, 11, 30)).build(); }

    public static Experience getMockedExperience9() { return Experience.builder().id(9).title("Cloud Computing Apprentice").organization("Oracle").description("Completed an apprenticeship program in Cloud Computing at Oracle, gaining expertise in cloud infrastructure and deploying scalable solutions.").type(APPRENTICESHIP).startedAt(LocalDate.of(2019, 1, 1)).finishedAt(LocalDate.of(2019, 6, 30)).build(); }

    public static Experience getMockedExperience10() { return Experience.builder().id(10).title("Full Stack Developer Apprentice").organization("Airbnb").description("Engaged in an apprenticeship program as a Full Stack Developer at Airbnb, developing end-to-end web applications and learning agile development methodologies.").type(APPRENTICESHIP).startedAt(LocalDate.of(2018, 1, 15)).finishedAt(LocalDate.of(2018, 7, 15)).build(); }

    public static Experience getMockedExperience11() { return Experience.builder().id(11).title("Cyber-security Apprentice").organization("Cisco").description("Participated in an apprenticeship program in Cyber-security at Cisco, learning about network security, vulnerability assessments, and threat mitigation strategies.").type(APPRENTICESHIP).startedAt(LocalDate.of(2019, 2, 1)).finishedAt(LocalDate.of(2019, 8, 31)).build(); }

    public static Experience getMockedExperience12() { return Experience.builder().id(12).title("Database Administration Apprentice").organization("Salesforce").description("Completed an apprenticeship program in Database Administration at Salesforce, gaining expertise in managing and optimizing database systems.").type(APPRENTICESHIP).startedAt(LocalDate.of(2020, 3, 1)).finishedAt(LocalDate.of(2020, 9, 30)).build(); }

    public static Experience getMockedExperience13() { return Experience.builder().id(13).title("Open Source Contributor").organization("Mozilla").description("Contributed to open source projects at Mozilla, collaborating with developers worldwide to enhance software functionality and address issues.").type(VOLUNTEERING).startedAt(LocalDate.of(2017, 9, 1)).finishedAt(LocalDate.of(2018, 3, 31)).build(); }

    public static Experience getMockedExperience14() { return Experience.builder().id(14).title("Code Mentor").organization("Codecademy").description("Volunteered as a Code Mentor at Codecademy, providing guidance and support to individuals learning to code and helping them overcome programming challenges.").type(VOLUNTEERING).startedAt(LocalDate.of(2020, 2, 1)).finishedAt(LocalDate.of(2020, 6, 30)).build(); }

    public static Experience getMockedExperience15() { return Experience.builder().id(15).title("Hackathon Organizer").organization("TechCrunch").description("Organized hackathons at TechCrunch, bringing together developers, designers, and innovators to collaborate and create innovative solutions within a limited timeframe.").type(VOLUNTEERING).startedAt(LocalDate.of(2019, 1, 1)).finishedAt(LocalDate.of(2019, 6, 30)).build(); }

    public static Experience getMockedExperience16() { return Experience.builder().id(16).title("Teaching Assistant").organization("Coursera").description("Served as a Teaching Assistant for online courses on Coursera, providing guidance and support to learners, grading assignments, and facilitating discussions.").type(VOLUNTEERING).startedAt(LocalDate.of(2018, 9, 1)).finishedAt(LocalDate.of(2018, 12, 31)).build(); }

    public static ExperienceDto getMockedExperienceDto1() { return ExperienceDto.builder().id(1).title("Software Engineering Intern").organization("Google").description("Worked as a Software Engineering Intern at Google, gaining hands-on experience in developing scalable software solutions.").type(INTERNSHIP).startedAt(LocalDate.of(2019, 6, 1)).finishedAt(LocalDate.of(2019, 8, 31)).build(); }

    public static ExperienceDto getMockedExperienceDto2() { return ExperienceDto.builder().id(2).title("Data Science Intern").organization("Facebook").description("Completed a Data Science Internship at Facebook, working on data analysis and machine learning projects to gain insights and drive business decisions.").type(INTERNSHIP).startedAt(LocalDate.of(2020, 6, 1)).finishedAt(LocalDate.of(2020, 8, 31)).build(); }

    public static ExperienceDto getMockedExperienceDto3() { return ExperienceDto.builder().id(3).title("Backend Developer Intern").organization("Amazon").description("Interned as a Backend Developer at Amazon, contributing to the development of high-performance backend systems for e-commerce applications.").type(INTERNSHIP).startedAt(LocalDate.of(2019, 5, 1)).finishedAt(LocalDate.of(2019, 7, 31)).build(); }

    public static ExperienceDto getMockedExperienceDto4() { return ExperienceDto.builder().id(4).title("Frontend Developer Intern").organization("Microsoft").description("Participated in a Frontend Developer Internship at Microsoft, collaborating on the development of user-friendly and responsive web interfaces.").type(INTERNSHIP).startedAt(LocalDate.of(2020, 5, 15)).finishedAt(LocalDate.of(2020, 8, 15)).build(); }

    public static ExperienceDto getMockedExperienceDto5() { return ExperienceDto.builder().id(5).title("Machine Learning Trainee").organization("Apple").description("Underwent comprehensive training in Machine Learning at Apple, exploring advanced algorithms and applying them to real-world data analysis challenges.").type(TRAINING).startedAt(LocalDate.of(2018, 6, 15)).finishedAt(LocalDate.of(2018, 9, 15)).build(); }

    public static ExperienceDto getMockedExperienceDto6() { return ExperienceDto.builder().id(6).title("Web Development Trainee").organization("IBM").description("Completed a rigorous training program in Web Development at IBM, acquiring skills in front-end and back-end development and working on industry projects.").type(TRAINING).startedAt(LocalDate.of(2017, 6, 1)).finishedAt(LocalDate.of(2017, 12, 31)).build(); }

    public static ExperienceDto getMockedExperienceDto7() { return ExperienceDto.builder().id(7).title("Software Developer Trainee").organization("Intel").description("Engaged in a Software Developer Training program at Intel, learning best practices in software engineering and gaining practical experience in software development lifecycle.").type(TRAINING).startedAt(LocalDate.of(2018, 6, 1)).finishedAt(LocalDate.of(2018, 8, 31)).build(); }

    public static ExperienceDto getMockedExperienceDto8() { return ExperienceDto.builder().id(8).title("Mobile Application Developer Trainee").organization("Uber").description("Participated in a Mobile Application Development Training program at Uber, learning mobile development frameworks and building innovative mobile applications.").type(TRAINING).startedAt(LocalDate.of(2019, 9, 1)).finishedAt(LocalDate.of(2019, 11, 30)).build(); }

    public static ExperienceDto getMockedExperienceDto9() { return ExperienceDto.builder().id(9).title("Cloud Computing Apprentice").organization("Oracle").description("Completed an apprenticeship program in Cloud Computing at Oracle, gaining expertise in cloud infrastructure and deploying scalable solutions.").type(APPRENTICESHIP).startedAt(LocalDate.of(2019, 1, 1)).finishedAt(LocalDate.of(2019, 6, 30)).build(); }

    public static ExperienceDto getMockedExperienceDto10() { return ExperienceDto.builder().id(10).title("Full Stack Developer Apprentice").organization("Airbnb").description("Engaged in an apprenticeship program as a Full Stack Developer at Airbnb, developing end-to-end web applications and learning agile development methodologies.").type(APPRENTICESHIP).startedAt(LocalDate.of(2018, 1, 15)).finishedAt(LocalDate.of(2018, 7, 15)).build(); }
}
