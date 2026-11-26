package com.dasther.calendariotributario.model.entity;

import jakarta.persistence.Column;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotNull
    @Positive
    @Column(name = "id_card", nullable = false, updatable = false)
    private Long idCard;

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
