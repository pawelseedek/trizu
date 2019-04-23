package com.trizu.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trizu.domain.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    
	User findByUsername(String username);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN 'true' ELSE 'false' END FROM User u WHERE u.username = :username")
    public Boolean existsByUsername(@Param("username") String username);
	
	@Transactional
	@Modifying
	@Query("UPDATE User u SET u.password= ?1 where u.username= ?2")
	public void changePassword(String password, String username);

	
}