package com.dasther.calendariotributario.model.entity;

import jakarta.persistence.Column;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "people")
@Getter
@Setter
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    private String userNameDian;

    @JsonIgnore
    @Column(columnDefinition = "text")
    private String passwordDian;

    private String userNameTownHall;

    @JsonIgnore
    @Column(columnDefinition = "text")
    private String passwordTownHall;
}
