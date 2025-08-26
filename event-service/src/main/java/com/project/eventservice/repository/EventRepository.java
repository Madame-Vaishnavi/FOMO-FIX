package com.project.eventservice.repository;

import com.project.eventservice.enums.EventCategory;
import com.project.eventservice.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> findByCategory(EventCategory category);

    // Search by name containing the search term (case-insensitive)
    List<Event> findByNameContainingIgnoreCase(String name);

    // Search by description containing the search term (case-insensitive)
    List<Event> findByDescriptionContainingIgnoreCase(String description);

    // Search by location containing the search term (case-insensitive)
    List<Event> findByLocationContainingIgnoreCase(String location);

    // Combined search across name, description, and location
    @Query("SELECT e FROM Event e WHERE " +
            "LOWER(e.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(e.location) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Event> searchEvents(@Param("searchTerm") String searchTerm);

    // Search events by category and search term
    @Query("SELECT e FROM Event e WHERE e.category = :category AND " +
            "(LOWER(e.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(e.location) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Event> searchEventsByCategory(@Param("searchTerm") String searchTerm,
            @Param("category") EventCategory category);

    // Find upcoming events (events with date in the future)
    @Query("SELECT e FROM Event e WHERE e.date > :currentDate ORDER BY e.date ASC")
    List<Event> findUpcomingEvents(@Param("currentDate") LocalDateTime currentDate);

    // Search upcoming events
    @Query("SELECT e FROM Event e WHERE e.date > :currentDate AND " +
            "(LOWER(e.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(e.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(e.location) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "ORDER BY e.date ASC")
    List<Event> searchUpcomingEvents(@Param("searchTerm") String searchTerm,
            @Param("currentDate") LocalDateTime currentDate);
}
