package com.project.eventservice.repository;

import com.project.eventservice.enums.EventCategory;
import com.project.eventservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findByCategory(EventCategory category);
}
