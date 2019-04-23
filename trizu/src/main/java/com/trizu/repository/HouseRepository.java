package com.trizu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trizu.domain.House;
import com.trizu.domain.Pin;

@Repository
public interface HouseRepository  extends JpaRepository<House, Long> {

	@Query("SELECT h FROM House h join h.users u WHERE u.username = :username")
    public List<House> getHousesByUsername(@Param("username") String username);
	
	@Query("Select CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM House h WHERE h.houseid = :houseid")
	public boolean existByIp(@Param("houseid") String houseid);
	
	@Query("SELECT h FROM House h WHERE h.houseid = :houseid")
	public House getHouseByHouseid(@Param("houseid")String houseid);
	
	
}