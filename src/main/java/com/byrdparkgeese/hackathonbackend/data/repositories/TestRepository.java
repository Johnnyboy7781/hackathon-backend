package com.byrdparkgeese.hackathonbackend.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.byrdparkgeese.hackathonbackend.data.entities.TestEntity;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {}
