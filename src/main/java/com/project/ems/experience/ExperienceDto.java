package com.project.ems.experience;

import com.project.ems.experience.enums.ExperienceType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDto {

    private Integer id;

    private String title;

    private String organization;

    private String description;

    private ExperienceType type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startedAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishedAt;
}
