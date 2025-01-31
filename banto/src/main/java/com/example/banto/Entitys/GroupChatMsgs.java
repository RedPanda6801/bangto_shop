package com.example.banto.Entitys;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class GroupChatMsgs{
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="MSG", nullable=false)
	private String msg;
	
	@Column(name = "TIMESTAMP", columnDefinition = "date default sysdate")
	private LocalDateTime timestamp;
	
	@Column(name="A_WRITE_DATE", nullable=true)
	private LocalDateTime aWriteDate;;
	
	@ManyToOne
	@JoinColumn(name="BUYER_PK")
	private Users user;
	
	@ManyToOne
	@JoinColumn(name="ROOM_PK")
	private GroupChatRooms room;
}
