package com.example.application.data.services;

import com.example.application.data.entity.Auction;
import com.example.application.data.repository.AuctionRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;

    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    public List<Auction> findAll(){
        return auctionRepository.findAll();
    }

    public List<Auction> listSortedByOldest(List<Auction> auctions ){

        Collections.sort(auctions, new Comparator<Auction>() {
            public int compare(Auction a1, Auction a2) {
                if(a2.getFromLD().getYear() == a1.getFromLD().getYear())
                    if(a2.getFromLD().getDayOfYear() == a1.getFromLD().getDayOfYear())
                        return a2.getFromLT().getHour() - a1.getFromLT().getHour();
                    else
                        return a2.getFromLD().getDayOfYear() - a1.getFromLD().getDayOfYear();
                else
                    return a2.getFromLD().getYear() - a1.getFromLD().getYear();
            }
        });
        return auctions;
    }

    public List<Auction> listSortedByNewest(List<Auction> auctions){
        Collections.sort(auctions, new Comparator<Auction>() {
            public int compare(Auction a1, Auction a2) {
                if(a1.getFromLD().getYear() == a2.getFromLD().getYear())
                    if(a1.getFromLD().getDayOfYear() == a2.getFromLD().getDayOfYear())
                        return a1.getFromLT().getHour() - a2.getFromLT().getHour();
                    else
                        return a1.getFromLD().getDayOfYear() - a2.getFromLD().getDayOfYear();
                else
                    return a1.getFromLD().getYear() - a2.getFromLD().getYear();
            }
        });

        return auctions;
    }

    public void delete(Auction auction){
        auctionRepository.delete(auction);
    }

    public void save(Auction auction){
        if(auction != null)
            auctionRepository.save(auction);
    }

    public void update(Auction auction){
        Optional<Auction> auctionToBeUpdated = auctionRepository.findById(auction.getId());
        if(auctionToBeUpdated.isPresent()) {
            Auction existingAuction = auctionToBeUpdated.get();
            existingAuction.setCurrentPrice(auction.getCurrentPrice());
            existingAuction.setLastBidderUsername(auction.getLastBidderUsername());
            auctionRepository.save(existingAuction);
        }
    }

    public Optional<Auction> findById(Long id){
        return auctionRepository.findById(id);
    }

    public List<Auction> findByUsername(String auctionerUsername){
        return auctionRepository.findByAuctionerUsername(auctionerUsername);
    }
}
