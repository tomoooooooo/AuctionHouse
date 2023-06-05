package com.example.application.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@NoArgsConstructor
public class AutomaticBid {

    @Id
    @SequenceGenerator(name = "automaticBidIdGenerator", initialValue = 1000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "automaticBidIdGenerator")
    private Long id;

    private Long auctionId;
    private String username;
    private Double maximumPrice;

    public AutomaticBid(Long auctionId, String username, Double maximumPrice) {
        this.auctionId = auctionId;
        this.username = username;
        this.maximumPrice = maximumPrice;
    }
}
