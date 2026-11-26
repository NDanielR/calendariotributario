package com.dasther.calendariotributario.model.dto.person;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PersonCreateDto(
        
    @NotNull 
    @Positive 
    Long idCard,
        
    @NotBlank 
    @Size(max = 255) 
    String name,
        
    @Size(max = 255) 
    String userNameDian,
        
    String passwordDian,
      
    @Size(max = 255) 
    String userNameTownHall,
        
    String passwordTownHall) {

    /*@Override
    public String toString() {
        return "PersonCreateDto[credentials=REDACTED]";
    }*/
} 
