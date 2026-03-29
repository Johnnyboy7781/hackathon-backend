package com.byrdparkgeese.hackathonbackend.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepliesByType extends JpaRepository<RepliesByType, Long> {

}
