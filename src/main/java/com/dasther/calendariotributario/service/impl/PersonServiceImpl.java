package com.dasther.calendariotributario.service.impl;

import com.dasther.calendariotributario.service.EncryptionService;
import com.dasther.calendariotributario.service.PersonService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dasther.calendariotributario.model.dto.person.PersonCreateDto;
import com.dasther.calendariotributario.model.dto.person.PersonResponseDto;
import com.dasther.calendariotributario.model.dto.person.PersonUpdateDto;
import com.dasther.calendariotributario.repository.PersonRepository;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
    private final PersonRepository repository;
    private final EncryptionService encryption;

    public PersonServiceImpl(PersonRepository repository, EncryptionService encryption) {
        this.repository = repository;
        this.encryption = encryption;
    }

    @Override
    public List<PersonResponseDto> getAll() {
        // TODO pendiente implementar
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public PersonResponseDto create(PersonCreateDto dto) {

        //TODO: pendiente implementar
        return null;
    }

    @Override
    public PersonResponseDto update(PersonUpdateDto dto) {
        //TODO:PENDIENTE IMPLEMENTAR
        return null;
    }
    
}
