package com.trizu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trizu.domain.House;
import com.trizu.domain.Pin;

@Repository
public interface PinRepository extends JpaRepository<Pin, Long>{

}
