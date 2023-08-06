package com.project.ems.employee;

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
public class EmployeeDto {

    @Positive(message = "Employee ID must be positive")
    private Integer id;

    @NotBlank(message = "Employee name must not be blank")
    private String name;

    @NotBlank(message = "Employee email must not be blank")
    @Email(message = "The provided email must be valid")
    private String email;

    @NotBlank(message = "Employee password must not be blank")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+-=()])(?=\\S+$).{8,30}$", message = "The provided password must be valid")
    private String password;

    @NotBlank(message = "Employee mobile must not be blank")
    @Pattern(regexp = "^(00|\\+?40|0)(7\\d{2}|\\d{2}[13]|[2-37]\\d|8[02-9]|9[0-2])\\s?\\d{3}\\s?\\d{3}$", message = "The provided mobile must be valid")
    private String mobile;

    @NotBlank(message = "Employee address must not be blank")
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotNull(message = "Role ID must not be null")
    @Positive(message = "Role ID must be positive")
    private Integer roleId;

    @NotNull(message = "Employment type must not be null")
    private EmploymentType employmentType;

    @NotNull(message = "Employee position must not be null")
    private Position position;

    @NotNull(message = "Employee grade must not be null")
    private Grade grade;

    @NotNull(message = "Mentor ID must not be null")
    @Positive(message = "Mentor ID must be positive")
    private Integer mentorId;

    private List<
          @NotNull(message = "Study ID must not be null")
          @Positive(message = "Study ID must be positive")
          Integer> studiesIds;

    private List<
          @NotNull(message = "Experience ID must not be null")
          @Positive(message = "Experience ID must be positive")
          Integer> experiencesIds;
}
