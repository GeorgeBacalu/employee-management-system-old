package com.project.ems.mentor;

import com.project.ems.employee.enums.EmploymentType;
import com.project.ems.employee.enums.Grade;
import com.project.ems.employee.enums.Position;
import com.project.ems.experience.Experience;
import com.project.ems.person.Person;
import com.project.ems.study.Study;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "mentors")
public class Mentor extends Person {

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Mentor supervisingMentor;

    @ManyToMany
    @JoinTable(name = "mentors_studies",
          joinColumns = @JoinColumn(name = "mentor_id"),
          inverseJoinColumns = @JoinColumn(name = "study_id"))
    @ToString.Exclude
    private List<Study> studies;

    @ManyToMany
    @JoinTable(name = "mentors_experiences",
          joinColumns = @JoinColumn(name = "mentor_id"),
          inverseJoinColumns = @JoinColumn(name = "experience_id"))
    @ToString.Exclude
    private List<Experience> experiences;

    private Integer nrTrainees;

    private Integer maxTrainees;

    private Boolean openForTraining;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Mentor mentor = (Mentor) o;
        return getId() != null && Objects.equals(getId(), mentor.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
