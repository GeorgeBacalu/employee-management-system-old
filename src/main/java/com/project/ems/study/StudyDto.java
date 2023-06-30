package com.project.ems.study;

import com.project.ems.study.enums.StudyType;
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
public class StudyDto {

    private Integer id;

    private String title;

    private String institution;

    private String description;

    private StudyType type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startedAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishedAt;
}
