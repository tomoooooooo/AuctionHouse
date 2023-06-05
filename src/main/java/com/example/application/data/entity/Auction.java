package com.example.application.data.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Table
@Entity
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Auction {

    @Id
    @SequenceGenerator(name = "auctionidgenerator", initialValue = 10000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auctionidgenerator")
    private Long id;

    @NotBlank
    private String title;
    private String description;
    private double startingPrice;
    private double currentPrice;
    private String lastBidderUsername;
    private String auctionerUsername;
    private byte[] image;
    private LocalDate fromLD;
    private LocalDate toLD;
    private LocalTime fromLT;
    private LocalTime toLT;
    private String accepted;


    public Auction(String title, String description, double startingPrice, String auctionerUsername, byte[] image) {
        this.title = title;
        this.description = description;
        this.startingPrice = startingPrice;
        this.auctionerUsername = auctionerUsername;
        this.image = image;
        this.accepted = "waiting";
    }

    public Auction(Long id, String title, String description, double startingPrice, double currentPrice, String lastBidderUsername, String auctionerUsername, byte[] image, LocalDate fromLD, LocalDate toLD, LocalTime fromLT, LocalTime toLT, String accepted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startingPrice = startingPrice;
        this.currentPrice = currentPrice;
        this.lastBidderUsername = lastBidderUsername;
        this.auctionerUsername = auctionerUsername;
        this.image = image;
        this.fromLD = fromLD;
        this.toLD = toLD;
        this.fromLT = fromLT;
        this.toLT = toLT;
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", startingPrice=" + startingPrice +
                ", currentPrice=" + currentPrice +
                ", lastBidderUsername='" + lastBidderUsername + '\'' +
                ", auctionerUsername='" + auctionerUsername + '\'' +
                ", fromLD=" + fromLD +
                ", toLD=" + toLD +
                ", fromLT=" + fromLT +
                ", toLT=" + toLT +
                ", accepted=" + accepted +
                '}';
    }
}
