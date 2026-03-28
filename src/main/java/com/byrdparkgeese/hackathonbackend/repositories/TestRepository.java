package com.byrdparkgeese.hackathonbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.byrdparkgeese.hackathonbackend.entities.TestEntity;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {}
