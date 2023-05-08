package com.example.application.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(int startingPrice) {
        this.startingPrice = startingPrice;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public long getLastBidderId() {
        return lastBidderId;
    }

    public void setLastBidderId(long lastBidderId) {
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

    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return super.hashCode();
    }
}
