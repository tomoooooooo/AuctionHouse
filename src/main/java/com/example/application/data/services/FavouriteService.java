package com.example.application.data.services;

import com.example.application.data.entity.Auction;
import com.example.application.data.entity.Favourite;
import com.example.application.data.repository.AuctionRepository;
import com.example.application.data.repository.FavouriteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavouriteService {
    private final FavouriteRepository favouriteRepository;
    private final AuctionRepository auctionRepository;

    public FavouriteService(FavouriteRepository favouriteRepository, AuctionRepository auctionRepository) {
        this.favouriteRepository = favouriteRepository;
        this.auctionRepository = auctionRepository;
    }

    public List<Auction> findAll(String username){
        List<Favourite> favourites = favouriteRepository.findByUsername(username);
        List<Auction> auctionList = new ArrayList<>();
        for(Favourite f : favourites) {
            Optional<Auction> auction = auctionRepository.findById(f.getAuctionId());
            auction.ifPresent(auctionList::add);
        }
        return auctionList;
    }

    public Favourite findByUsernameAndAuctionId(String username, Long auctionId){
        return favouriteRepository.findByUsernameAndAuctionId(username, auctionId);
    }

    public void delete(Favourite favourite){
        favouriteRepository.delete(favourite);
    }

    public void save(Favourite favourite){
        if(favourite != null)
            favouriteRepository.save(favourite);
    }
}
