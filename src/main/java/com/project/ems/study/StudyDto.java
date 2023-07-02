package com.project.ems.study;

import com.project.ems.study.enums.StudyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @Positive(message = "Study ID must be positive")
    private Integer id;

    @NotBlank(message = "Study title must not be blank")
    private String title;

    @NotBlank(message = "Study institution must not be blank")
    private String institution;

    @NotBlank(message = "Study description must not be blank")
    private String description;

    @NotNull(message = "Study type must not be null")
    private StudyType type;

    @NotNull(message = "Started at must not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startedAt;

    @NotNull(message = "Finished at must not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishedAt;
}
