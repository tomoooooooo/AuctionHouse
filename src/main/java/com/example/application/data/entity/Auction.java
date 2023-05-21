package com.example.application.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Auction {

    @Id
    @SequenceGenerator(name = "auctionidgenerator", initialValue = 10000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auctionidgenerator")
    private Long id;

    @NotBlank
    private String title;

    private String description;

    private double startingPrice;
    private int currentPrice;
    private long lastBidderId;
    private String username;


    public Auction(String title, String description, double startingPrice, String username) {
        this.title = title;
        this.description = description;
        this.startingPrice = startingPrice;
        this.username = username;
    }
}
