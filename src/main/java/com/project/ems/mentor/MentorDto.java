package com.project.ems.mentor;

import com.project.ems.employee.enums.EmploymentType;
import com.project.ems.employee.enums.Grade;
import com.project.ems.employee.enums.Position;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentorDto {

    @Positive(message = "Mentor ID must be positive")
    private Integer id;

    @NotBlank(message = "Mentor name must not be blank")
    private String name;

    @NotBlank(message = "Mentor email must not be blank")
    @Email
    private String email;

    @NotBlank(message = "Mentor password must not be blank")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+-=()])(?=\\S+$).{8,25}$")
    private String password;

    @NotBlank(message = "Mentor mobile must not be blank")
    @Pattern(regexp = "^(00|\\+?40|0)(7\\d{2}|\\d{2}[13]|[2-37]\\d|8[02-9]|9[0-2])\\s?\\d{3}\\s?\\d{3}$")
    private String mobile;

    @NotBlank(message = "Mentor address must not be blank")
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotNull(message = "Role ID must not be null")
    @Positive(message = "Role ID must be positive")
    private Integer roleId;

    @NotNull(message = "Employment type must not be null")
    private EmploymentType employmentType;

    @NotNull(message = "Mentor position must not be null")
    private Position position;

    @NotNull(message = "Mentor grade must not be null")
    private Grade grade;

    @Positive(message = "Supervising mentor ID must be positive")
    private Integer supervisingMentorId;

    private List<
          @NotNull(message = "Study ID must not be null")
          @Positive(message = "Study ID must be positive")
          Integer> studiesIds;

    private List<
          @NotNull(message = "Experience ID must not be null")
          @Positive(message = "Experience ID must be positive")
          Integer> experiencesIds;

    @NotNull(message = "Mentor's number of trainees must not be null")
    @Positive(message = "Mentor's number of trainees must be positive")
    private Integer nrTrainees;

    @NotNull(message = "Mentor's maximum number of trainees must not be null")
    @Positive(message = "Mentor's maximum number of trainees must be positive")
    private Integer maxTrainees;

    @NotNull(message = "Mentor's open for training status must not be null")
    private Boolean isTrainingOpen;
}
