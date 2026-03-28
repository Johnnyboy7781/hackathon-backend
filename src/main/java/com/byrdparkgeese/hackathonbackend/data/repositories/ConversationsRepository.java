package com.byrdparkgeese.hackathonbackend.data.repositories;

import com.byrdparkgeese.hackathonbackend.data.entities.ConversationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationsRepository extends JpaRepository<ConversationsEntity, Long> {

}
