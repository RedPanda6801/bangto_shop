package com.example.banto.Repositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.banto.Entitys.GroupBuyItems;

public interface GroupBuyItemRepository extends JpaRepository<GroupBuyItems, Integer> {

	// 카테고리 별로 정렬
	@Query("SELECT gi FROM GroupBuyItems gi WHERE gi.event.id = :eventId ORDER BY gi.item.category")
	public List<GroupBuyItems> findAllByEventId(@Param("eventId") Integer eventId);
}
