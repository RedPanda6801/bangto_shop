package com.example.banto.Repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.banto.Entitys.Wallets;

public interface WalletRepository extends JpaRepository<Wallets, Integer> {

}
