package com.dasther.calendariotributario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dasther.calendariotributario.model.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
