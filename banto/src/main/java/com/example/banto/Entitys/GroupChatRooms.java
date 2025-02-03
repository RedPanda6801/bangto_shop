package com.example.banto.Entitys;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupChatRooms {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    // @OneToMany 관계에서 mappedBy="room" 필드가 정확하게 매핑되어야 합니다.
    @OneToMany(mappedBy="room", cascade = CascadeType.ALL)  // cascade 설정 추가
    private List<GroupChatMsgs> msgs;

    @ManyToOne
    @JoinColumn(name="BUYER_PK")
    private Users user;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="GROUP_BUY_ITEM_PK")
    private GroupBuyItems groupBuyItem;

}
