package com.project.ems.mock;

import com.project.ems.experience.Experience;
import com.project.ems.experience.enums.ExperienceType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExperienceMock {

    public static List<Experience> getMockedExperiences() {
        return Stream.of(getMockedExperiences1(), getMockedExperiences2(), getMockedExperiences3(), getMockedExperiences4(), getMockedExperiences5(), getMockedExperiences6(), getMockedExperiences7(), getMockedExperiences8())
              .flatMap(List::stream)
              .toList();
    }

    public static List<Experience> getMockedFilteredExperiences() {
        return Stream.of(getMockedExperiences1(), getMockedExperiences2()).flatMap(List::stream).toList();
    }

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

    public static Experience getMockedExperience1() {
        return new Experience(1, "Software Engineering Intern", "Google", "Worked as a Software Engineering Intern at Google, gaining hands-on experience in developing scalable software solutions.", ExperienceType.INTERNSHIP, LocalDate.of(2019, 6, 1), LocalDate.of(2019, 8, 31));
    }

    public static Experience getMockedExperience2() {
        return new Experience(2, "Data Science Intern", "Facebook", "Completed a Data Science Internship at Facebook, working on data analysis and machine learning projects to gain insights and drive business decisions.", ExperienceType.INTERNSHIP, LocalDate.of(2020, 6, 1), LocalDate.of(2020, 8, 31));
    }

    public static Experience getMockedExperience3() {
        return new Experience(3, "Backend Developer Intern", "Amazon", "Interned as a Backend Developer at Amazon, contributing to the development of high-performance backend systems for e-commerce applications.", ExperienceType.INTERNSHIP, LocalDate.of(2019, 5, 1), LocalDate.of(2019, 7, 31));
    }

    public static Experience getMockedExperience4() {
        return new Experience(4, "Frontend Developer Intern", "Microsoft", "Participated in a Frontend Developer Internship at Microsoft, collaborating on the development of user-friendly and responsive web interfaces.", ExperienceType.INTERNSHIP, LocalDate.of(2020, 5, 15), LocalDate.of(2020, 8, 15));
    }

    public static Experience getMockedExperience5() {
        return new Experience(5, "Machine Learning Trainee", "Apple", "Underwent comprehensive training in Machine Learning at Apple, exploring advanced algorithms and applying them to real-world data analysis challenges.", ExperienceType.TRAINING, LocalDate.of(2018, 6, 15), LocalDate.of(2018, 9, 15));
    }

    public static Experience getMockedExperience6() {
        return new Experience(6, "Web Development Trainee", "IBM", "Completed a rigorous training program in Web Development at IBM, acquiring skills in front-end and back-end development and working on industry projects.", ExperienceType.TRAINING, LocalDate.of(2017, 6, 1), LocalDate.of(2017, 12, 31));
    }

    public static Experience getMockedExperience7() {
        return new Experience(7, "Software Developer Trainee", "Intel", "Engaged in a Software Developer Training program at Intel, learning best practices in software engineering and gaining practical experience in software development lifecycle.", ExperienceType.TRAINING, LocalDate.of(2018, 6, 1), LocalDate.of(2018, 8, 31));
    }

    public static Experience getMockedExperience8() {
        return new Experience(8, "Mobile Application Developer Trainee", "Uber", "Participated in a Mobile Application Development Training program at Uber, learning mobile development frameworks and building innovative mobile applications.", ExperienceType.TRAINING, LocalDate.of(2019, 9, 1), LocalDate.of(2019, 11, 30));
    }

    public static Experience getMockedExperience9() {
        return new Experience(9, "Cloud Computing Apprentice", "Oracle", "Completed an apprenticeship program in Cloud Computing at Oracle, gaining expertise in cloud infrastructure and deploying scalable solutions.", ExperienceType.APPRENTICESHIP, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 6, 30));
    }

    public static Experience getMockedExperience10() {
        return new Experience(10, "Full Stack Developer Apprentice", "Airbnb", "Engaged in an apprenticeship program as a Full Stack Developer at Airbnb, developing end-to-end web applications and learning agile development methodologies.", ExperienceType.APPRENTICESHIP, LocalDate.of(2018, 1, 15), LocalDate.of(2018, 7, 15));
    }

    public static Experience getMockedExperience11() {
        return new Experience(11, "Cyber-security Apprentice", "Cisco", "Participated in an apprenticeship program in Cyber-security at Cisco, learning about network security, vulnerability assessments, and threat mitigation strategies.", ExperienceType.APPRENTICESHIP, LocalDate.of(2019, 2, 1), LocalDate.of(2019, 8, 31));
    }

    public static Experience getMockedExperience12() {
        return new Experience(12, "Database Administration Apprentice", "Salesforce", "Completed an apprenticeship program in Database Administration at Salesforce, gaining expertise in managing and optimizing database systems.", ExperienceType.APPRENTICESHIP, LocalDate.of(2020, 3, 1), LocalDate.of(2020, 9, 30));
    }

    public static Experience getMockedExperience13() {
        return new Experience(13, "Open Source Contributor", "Mozilla", "Contributed to open source projects at Mozilla, collaborating with developers worldwide to enhance software functionality and address issues.", ExperienceType.VOLUNTEERING, LocalDate.of(2017, 9, 1), LocalDate.of(2018, 3, 31));
    }

    public static Experience getMockedExperience14() {
        return new Experience(14, "Code Mentor", "Codecademy", "Volunteered as a Code Mentor at Codecademy, providing guidance and support to individuals learning to code and helping them overcome programming challenges.", ExperienceType.VOLUNTEERING, LocalDate.of(2020, 2, 1), LocalDate.of(2020, 6, 30));
    }

    public static Experience getMockedExperience15() {
        return new Experience(15, "Hackathon Organizer", "TechCrunch", "Organized hackathons at TechCrunch, bringing together developers, designers, and innovators to collaborate and create innovative solutions within a limited timeframe.", ExperienceType.VOLUNTEERING, LocalDate.of(2019, 1, 1), LocalDate.of(2019, 6, 30));
    }

    public static Experience getMockedExperience16() {
        return new Experience(16, "Teaching Assistant", "Coursera", "Served as a Teaching Assistant for online courses on Coursera, providing guidance and support to learners, grading assignments, and facilitating discussions.", ExperienceType.VOLUNTEERING, LocalDate.of(2018, 9, 1), LocalDate.of(2018, 12, 31));
    }
}
