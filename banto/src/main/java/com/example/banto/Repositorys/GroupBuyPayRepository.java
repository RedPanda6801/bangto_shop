package com.example.banto.Repositorys;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.banto.Entitys.GroupItemPays;

public interface GroupBuyPayRepository extends JpaRepository<GroupItemPays, Integer> {
	
	@Query("SELECT p FROM GroupItemPays p WHERE p.item.id = :itemId")
	public List<GroupItemPays> findByItemId(@Param("itemId") Integer itemId);

	@Query(value = "SELECT * FROM group_item_pays p WHERE p.buyer_pk = :userId AND EXTRACT(YEAR FROM p.sold_date) = :year",
			countQuery = "SELECT count(*) FROM group_item_pays p WHERE p.buyer_pk = :userId AND EXTRACT(YEAR FROM p.sold_date) = :year",
			nativeQuery = true)
	public List<GroupItemPays> findByUserIdAndYear(@Param("userId") Integer userId, @Param("year") Integer year);
}
