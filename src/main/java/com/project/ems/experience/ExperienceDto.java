package com.project.ems.experience;

import com.project.ems.experience.enums.ExperienceType;
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
public class ExperienceDto {

    @Positive(message = "Experience ID must be positive")
    private Integer id;

    @NotBlank(message = "Experience title must not be blank")
    private String title;

    @NotBlank(message = "Experience organization must not be blank")
    private String organization;

    @NotBlank(message = "Experience description must not be blank")
    private String description;

    @NotNull(message = "Experience type must not be null")
    private ExperienceType type;

    @NotNull(message = "Started at must not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startedAt;

    @NotNull(message = "Finished at must not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishedAt;
}
