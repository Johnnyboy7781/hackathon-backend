package com.byrdparkgeese.hackathonbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.byrdparkgeese.hackathonbackend.entities.Test;

@Repository
public interface TestRepo extends JpaRepository<Test, Long> {}
