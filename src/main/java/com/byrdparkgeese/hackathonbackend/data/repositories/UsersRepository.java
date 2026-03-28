package com.byrdparkgeese.hackathonbackend.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.byrdparkgeese.hackathonbackend.data.entities.UsersEntity;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {}
