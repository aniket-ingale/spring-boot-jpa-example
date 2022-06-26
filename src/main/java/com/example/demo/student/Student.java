package com.example.demo.student;

import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;

@NoArgsConstructor
@AllArgsConstructor

@Setter
@ToString
@Entity
@Table

public class Student {
    @Id
    @SequenceGenerator(
            name="student_sequence",
            sequenceName = "student_Sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )

    @Getter
    private Long id;

    @Getter
    private String name;

    @Getter
    private String email;

    @Getter
    private LocalDate dob;

    public Integer getAge() {
        return Period.between(dob, LocalDate.now()).getYears();
    }

    @Getter
    @Transient
    private Integer age;

    public Student(String name, String email, LocalDate dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;
    }
}
