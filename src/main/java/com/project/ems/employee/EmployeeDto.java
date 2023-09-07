package com.project.ems.employee;

import com.project.ems.employee.enums.EmploymentType;
import com.project.ems.employee.enums.Grade;
import com.project.ems.employee.enums.Position;
import com.project.ems.person.PersonDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto extends PersonDto {

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
