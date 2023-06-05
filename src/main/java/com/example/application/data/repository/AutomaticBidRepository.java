package com.example.application.data.repository;

import com.example.application.data.entity.AutomaticBid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutomaticBidRepository extends JpaRepository<AutomaticBid, Long> {
    List<AutomaticBid> findByAuctionId(Long auctionId);
}
