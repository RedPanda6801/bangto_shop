package com.example.banto.Repositorys;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.banto.Entitys.GroupItemPays;

public interface GroupBuyPayRepository extends JpaRepository<GroupItemPays, Integer> {
	
	@Query("SELECT p FROM GroupItemPays p WHERE p.groupBuyItem.item.id = :itemId")
	public List<GroupItemPays> findByItemId(@Param("itemId") Integer itemId);
}
