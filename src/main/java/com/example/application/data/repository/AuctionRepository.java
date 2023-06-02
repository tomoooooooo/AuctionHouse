package com.example.application.data.repository;

import com.example.application.data.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByAuctionerUsername(String auctionerUsername);
}
