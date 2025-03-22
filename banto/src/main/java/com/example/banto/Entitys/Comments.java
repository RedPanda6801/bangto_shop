package com.example.banto.Entitys;

import java.time.LocalDateTime;
import java.util.ArrayList;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(
		name = "comment_seq",
		sequenceName = "comment_seq",
		allocationSize = 1
)
public class Comments {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
	private Integer id;
	
	@Column(name="CONTENT", nullable=false)
	private String content;
	
	@Column(name="STAR",  nullable=false)
	private Integer star;
	
	@Column(name = "WRITE_DATE", nullable=false, insertable = false, columnDefinition = "date default sysdate")
	private LocalDateTime writeDate;
	
	@Column(name="IMG", nullable=true)
    private String img;

	@ManyToOne
	@JoinColumn(name="BUYER_PK")
	private Users user;
	
	@ManyToOne
	@JoinColumn(name="ITEM_PK")
	private Items item;
}
