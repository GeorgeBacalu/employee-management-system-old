package com.project.ems.mentor;

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
public class MentorDto extends PersonDto {

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
    private Boolean openForTraining;
}
