package com.example.banto.Entitys;

import java.time.LocalDateTime;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QNAs {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(name="Q_CONTENT", nullable=false)
    private String qContent;
    
    @Column(name="A_CONTENT", nullable=true)
    private String aContent;
    
    @Column(name = "Q_WRITE_DATE", nullable=false, insertable = false, columnDefinition = "date default sysdate")
    private LocalDateTime qWriteDate;
    
    @Column(name="A_WRITE_DATE", nullable=true)
    private LocalDateTime aWriteDate;
    
    @ManyToOne
    @JoinColumn(name="WRITER_PK")
    private Users user;
    
    @ManyToOne
    @JoinColumn(name="ITEM_PK")
    private Items item;
}
