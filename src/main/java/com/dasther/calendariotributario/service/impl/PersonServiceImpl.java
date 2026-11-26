package com.dasther.calendariotributario.service.impl;

import com.dasther.calendariotributario.service.EncryptionService;
import com.dasther.calendariotributario.service.PersonService;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dasther.calendariotributario.model.dto.person.PersonCreateDto;
import com.dasther.calendariotributario.model.dto.person.PersonResponseDto;
import com.dasther.calendariotributario.model.dto.person.PersonUpdateDto;
import com.dasther.calendariotributario.mapper.PersonMapper;
import com.dasther.calendariotributario.repository.PersonRepository;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {
    private final PersonRepository repository;
    private final EncryptionService encryption;
    private final PersonMapper mapper;

    public PersonServiceImpl(PersonRepository repository, EncryptionService encryption, PersonMapper mapper) {
        this.repository = repository;
        this.encryption = encryption;
        this.mapper = mapper;
    }

    @Override
    public List<PersonResponseDto> getAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public PersonResponseDto getById(long id) {
        var person = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe una persona con esa cedula"));

        return mapper.toResponse(person);
    }

    @Override
    public PersonResponseDto create(PersonCreateDto dto) {

        var person = mapper.toEntity(dto);
        person.setPasswordDian(encryption.encrypt(dto.passwordDian()));
        person.setPasswordTownHall(encryption.encrypt(dto.passwordTownHall()));

        var saved = repository.save(person);
        return mapper.toResponse(saved);
    }

    @Override
    public PersonResponseDto update(PersonUpdateDto dto) {
        
        var actualPerson = repository.findById(dto.idCard())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe una persona con esa cedula"));

        var person = mapper.toEntity(dto);
        person.setPasswordDian(actualPerson.getPasswordDian());
        person.setPasswordTownHall(actualPerson.getPasswordTownHall());

        person = repository.save(person);
        return mapper.toResponse(person);
    }

    @Override
    public void delete(Long id) {
        var person = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe una persona con esa cedula"));

        repository.delete(person);
    }
    
}
