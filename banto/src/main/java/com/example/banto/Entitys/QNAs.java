package com.example.banto.Entitys;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SequenceGenerator(
        name = "qna_seq",
        sequenceName = "qna_seq",
        allocationSize = 1
)
public class QNAs {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qna_seq")
    private Integer id;


    @Column(name="Q_CONTENT", nullable=false)
    private String qContent;
    
    @Column(name="A_CONTENT")
    private String aContent;
    
    @Column(name = "Q_WRITE_DATE", nullable=false, insertable = false, columnDefinition = "date default sysdate")
    private LocalDateTime qWriteDate;
    
    @Column(name="A_WRITE_DATE")
    private LocalDateTime aWriteDate;

    @ManyToOne
    @JoinColumn(name="WRITER_PK")
    private Users user;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="ITEM_PK")
    private Items item;
}
