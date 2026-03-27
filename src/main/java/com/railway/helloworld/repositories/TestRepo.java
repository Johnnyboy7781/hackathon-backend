package com.railway.helloworld.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.railway.helloworld.entities.Test;

@Repository
public interface TestRepo extends JpaRepository<Test, Long> {}
