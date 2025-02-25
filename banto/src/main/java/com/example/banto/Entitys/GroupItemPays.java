package com.example.banto.Entitys;

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
public class GroupItemPays {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="AMOUNT", nullable=false)
    private Integer amount;
    
    @Column(name="PAY", nullable=false)
    private Integer pay;
    
    @Column(name="DELEVER_INFO", nullable=false)
    private DeliverType deleverInfo;
    
    @ManyToOne
    @JoinColumn(name="OPTION_PK")
    private Options Option;
    
    @ManyToOne
    @JoinColumn(name="BUYER_PK")
    private Users user;

    @ManyToOne
    @JoinColumn(name="GROUP_ITEM_PK")
    private GroupBuyItems groupBuyItem;

}
