package com.example.application.data.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table
@Entity
@NoArgsConstructor
public class Favourite {

    @Id
    @SequenceGenerator(name = "favouriteIdGenerator", initialValue = 1000, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favouriteIdGenerator")
    private Long id;

    private String username;
    private Long auctionId;

    public Favourite(String username, Long auctionId) {
        this.username = username;
        this.auctionId = auctionId;
    }
}
