package com.dasther.calendariotributario.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import com.dasther.calendariotributario.model.dto.person.PersonCreateDto;
import com.dasther.calendariotributario.model.dto.person.PersonResponseDto;
import com.dasther.calendariotributario.model.dto.person.PersonUpdateDto;
import com.dasther.calendariotributario.model.entity.Person;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "idCard", source = "idCard")
    @Mapping(target = "passwordDian", ignore = true)
    @Mapping(target = "passwordTownHall", ignore = true)
    Person toEntity(PersonCreateDto dto);

    @Mapping(target = "idCard", source = "idCard")
    @Mapping(target = "passwordDian", ignore = true)
    @Mapping(target = "passwordTownHall", ignore = true)
    Person toEntity(PersonUpdateDto dto);

    PersonResponseDto toResponse(Person entityPerson);
    
} 
