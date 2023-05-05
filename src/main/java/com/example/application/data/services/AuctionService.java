package com.example.application.data.services;

import com.example.application.data.entity.Auction;
import com.example.application.data.repository.AuctionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;

    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    public List<Auction> findAll(){
        return auctionRepository.findAll();
    }

    public void delete(Auction auction){
        auctionRepository.delete(auction);
    }

    public void save(Auction auction){
        if(auction != null)
            auctionRepository.save(auction);
    }
}
