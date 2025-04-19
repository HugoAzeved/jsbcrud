package com.jsbcrud.www.repository;

import com.jsbcrud.www.model.Thing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThingRepository extends JpaRepository<Thing, Long> {
    List<Thing> findByStatusOrderByDateDesc(Thing.Status status);
}