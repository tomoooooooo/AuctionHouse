package com.example.application.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Table
@Entity
public class Auction {

    @Id
    @SequenceGenerator(name = "auctionidgenerator", initialValue = 1000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auctionidgenerator")
    private Long id;

    @NotBlank
    private String title;

    private String description;
    private int startingPrice;
    private int currentPrice;
    private long lastBidderId;

    public Auction() {

    }

    public Auction(String title, String description, int startingPrice, int currentPrice, long lastBidderId) {
        this.title = title;
        this.description = description;
        this.startingPrice = startingPrice;
        this.currentPrice = currentPrice;
        this.lastBidderId = lastBidderId;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startingPrice=" + startingPrice +
                ", currentPrice=" + currentPrice +
                ", lastBidderId=" + lastBidderId +
                '}';
    }

}
