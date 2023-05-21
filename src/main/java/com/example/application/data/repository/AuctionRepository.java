package com.example.application.data.repository;

import com.example.application.data.entity.Auction;
import com.example.application.data.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByUsername(String username);
}
