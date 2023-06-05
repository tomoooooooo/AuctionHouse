package com.example.application.data.services;

import com.example.application.data.entity.AutomaticBid;
import com.example.application.data.repository.AutomaticBidRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutomaticBidService {
    private final AutomaticBidRepository automaticBidRepository;

    public AutomaticBidService(AutomaticBidRepository automaticBidRepository) {
        this.automaticBidRepository = automaticBidRepository;
    }

    public void save(AutomaticBid automaticBid){automaticBidRepository.save(automaticBid);}

    public void delete(AutomaticBid automaticBid){automaticBidRepository.delete(automaticBid);}

    public List<AutomaticBid> findByAuctionId(Long auctionId){return automaticBidRepository.findByAuctionId(auctionId);}

}
