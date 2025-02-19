package com.example.banto.Repositorys;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.banto.Entitys.GroupBuys;

public interface GroupBuyRepository extends JpaRepository<GroupBuys, Integer> {

	@Query("SELECT gb FROM GroupBuys gb ORDER BY gb.startDate DESC LIMIT 1")
	Optional<GroupBuys> findLatest();
}
