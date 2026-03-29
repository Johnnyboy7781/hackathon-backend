package com.byrdparkgeese.hackathonbackend.data.repositories;

import com.byrdparkgeese.hackathonbackend.data.entities.RepliesByTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepliesByTypeRepository extends JpaRepository<RepliesByTypeEntity, Long> {

    RepliesByTypeEntity findByCategory(String category);

}
