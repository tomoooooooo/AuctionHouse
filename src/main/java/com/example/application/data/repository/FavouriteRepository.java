package com.example.application.data.repository;

import com.example.application.data.entity.Favourite;
import com.example.application.data.services.FavouriteService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {

    List<Favourite> findByUsername(String username);
    Favourite findByUsernameAndAuctionId(String username, Long auctionId);
}
