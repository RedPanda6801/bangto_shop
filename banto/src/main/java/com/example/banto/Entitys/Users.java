package com.example.banto.Entitys;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.banto.DTOs.UserDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="EMAIL", nullable=false, unique=true)
    private String email;

    @Column(name="PW", nullable=true)
    private String pw;

    @Column(name="NAME", nullable=false)
    private String name;

    @Column(name="ADDR", nullable=true)
    private String addr;

    @Column(name="PHONE", nullable=true)
    private String phone;

    @Column(name = "REG_DATE", nullable=false, insertable = false, columnDefinition = "date default sysdate")
    private LocalDateTime regDate;

    @Column(name="SNS_AUTH", nullable=false)
    private Boolean snsAuth;

    // 참조 객체
    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SellerAuths> sellers;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comments> comments;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Favorites> favorites;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupChatRooms> groupChatRooms;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupChatMsgs> msgs;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Carts> carts;

    public static Users toEntity(UserDTO dto) {
        return Users.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .pw(dto.getPw())
                .name(dto.getName())
                .addr(dto.getAddr())
                .phone(dto.getPhone())
                .regDate(dto.getRegDate())
                .snsAuth(dto.getSnsAuth())
                .build();
    }
    
    public Users setSeller(int id, String email, String name, String addr, String phone) {
    	return Users.builder()
    			.id(id)
    			.email(email)
    			.name(name)
    			.addr(addr)
    			.phone(phone)
    			.build();
    }
}
