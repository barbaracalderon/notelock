package com.fmt.notelock.datasource.repositories;

import com.fmt.notelock.datasource.entities.CadernoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CadernoRepository extends JpaRepository<CadernoEntity, Long> {
}
