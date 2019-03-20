package com.napptilus.crewmanager.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.napptilus.crewmanager.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value = "SELECT * FROM USERS WHERE name = ?1", countQuery = "SELECT count(*) FROM USERS WHERE name = ?1", nativeQuery = true)
	List<User> findByName(String name, Pageable pageable);
}
