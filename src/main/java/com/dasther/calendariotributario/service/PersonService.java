package com.dasther.calendariotributario.service;

import java.util.List;

import com.dasther.calendariotributario.model.dto.person.PersonCreateDto;
import com.dasther.calendariotributario.model.dto.person.PersonResponseDto;
import com.dasther.calendariotributario.model.dto.person.PersonUpdateDto;


public interface PersonService {

    //listar usuarios
    List<PersonResponseDto> getAll();
    
    //crear usuario
    PersonResponseDto create(PersonCreateDto dto);

    //actualizar usuario
    PersonResponseDto update(PersonUpdateDto dto);

}
