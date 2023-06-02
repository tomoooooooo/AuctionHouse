package com.example.application.views.AuctionItem;

import com.example.application.data.entity.Auction;
import com.example.application.data.services.AuctionService;
import com.example.application.security.SecurityService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import jakarta.annotation.security.PermitAll;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@PageTitle("Auction Item")
@Route(value = "auctionItem/")
@PermitAll

public class AuctionItem extends VerticalLayout implements HasUrlParameter<String> {

    private final AuctionService auctionService;
    private final SecurityService securityService;
    private H1 header;
    private List<Auction> auctionsList;
    private Auction auction;
    private Image img;
    private H4 description;
    private TextArea details;
    private TextField status;
    private NumberField euroField;
    private Button bid;
    private Notification notification;

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        for(Auction a : auctionsList)
            if(a.getId() == Long.parseLong(parameter)) {
                auction = new Auction(
                        a.getId(),
                        a.getTitle(),
                        a.getDescription(),
                        a.getStartingPrice(),
                        a.getCurrentPrice(),
                        a.getLastBidderUsername(),
                        a.getAuctionerUsername(),
                        a.getImage(),
                        a.getFromLD(),
                        a.getToLD(),
                        a.getFromLT(),
                        a.getToLT(),
                        a.isAccepted()
                        );
            }
        header.setText("Title: " + auction.getTitle());

        InputStream imageStream = new ByteArrayInputStream(auction.getImage());
        StreamResource imageResource = new StreamResource("StreamedImage",
                () -> imageStream);
        img.setSrc(imageResource);
        img.setAlt("No image found :(");

        description.setText(auction.getDescription());
        details.setValue("Starting date: " + auction.getFromLD() + "  Starting hour: "+ auction.getFromLT() +"\n" +
                "Ending date: "+ auction.getToLD() +"  Ending hour: "+ auction.getToLT() +"\n" +
                "Starting price: " + auction.getStartingPrice() + "\n" +
                "Last Bid: "+ auction.getCurrentPrice());

        if((auction.getFromLD().isBefore(LocalDate.now()) && auction.getToLD().isAfter(LocalDate.now()))
        || (auction.getFromLD().isEqual(LocalDate.now()) && auction.getFromLT().isBefore(LocalTime.now()))
        || (auction.getToLD().isEqual(LocalDate.now()) && auction.getToLT().isAfter(LocalTime.now()))) {
            status.setValue("Ongoing");
            status.getStyle().set("background-color", "green");
            euroField.setReadOnly(false);
            if(auction.getCurrentPrice() == 0) {
                euroField.setMin(auction.getStartingPrice() * 1.01);
                euroField.setValue(auction.getStartingPrice() * 1.01);
            } else {
                euroField.setMin(auction.getCurrentPrice() * 1.01);
                euroField.setValue(auction.getCurrentPrice() * 1.01);
            }
        }else if(auction.getToLD().isBefore(LocalDate.now()) || (auction.getToLD().isEqual(LocalDate.now()) && auction.getToLT().isBefore(LocalTime.now()))){
            status.setValue("Closed auction");
            status.getStyle().set("background-color", "red");
        }

        if(securityService.getAuthenticatedUser().getUsername().equals(auction.getAuctionerUsername()))
        {
            euroField.setHelperText("You can't bid for your own auction!");
            euroField.setReadOnly(true);
            euroField.setValue(0.0);
        }

        bid.addClickListener(event -> {
            if(auction.getLastBidderUsername().equals(securityService.getAuthenticatedUser().getUsername())){
                notification = Notification.show("You are the last bidder, you can't place a bid until someone else places a bid!");
            }
            else {
                if (auction.getCurrentPrice() >= euroField.getValue())
                    notification = Notification.show("Your bid is too low!");
                else {
                    notification = Notification.show("You bidded for " + euroField.getValue() + " successfully!");
                    auction.setLastBidderUsername(securityService.getAuthenticatedUser().getUsername());
                    auction.setCurrentPrice(euroField.getValue());
                    auctionService.update(auction);

                    details.setValue("Starting date: " + auction.getFromLD() + "  Starting hour: " + auction.getFromLT() + "\n" +
                            "Ending date: " + auction.getToLD() + "  Ending hour: " + auction.getToLT() + "\n" +
                            "Starting price: " + auction.getStartingPrice() + "\n" +
                            "Last Bid: " + auction.getCurrentPrice());
                }
            }


        });
    }


    public AuctionItem(AuctionService auctionService, SecurityService securityService) {
        this.auctionService = auctionService;
        this.securityService = securityService;


        auctionsList = auctionService.findAll();

        setSpacing(true);

        header = new H1();
        header.addClassNames(Margin.Top.LARGE, Margin.Bottom.SMALL);
        add(header);


        img = new Image();
        img.setWidth("200px");
        add(img);

        description = new H4();
        add(description);

        details = new TextArea();
        details.setReadOnly(true);
        details.setLabel("Auction details");
        details.setWidthFull();

        status = new TextField();
        status.setReadOnly(true);
        status.setValue("Waiting for auction");
        status.getStyle().set("background-color", "red");
        status.getStyle().set("margin-top", "60px");
        status.setWidth("200px");
        status.setHeight("32px");

        Button favorite = new Button("Add Favorite!");
        favorite.addClickListener(e ->{
            notification = Notification
                    .show("Added to favorite!");
        });
        favorite.getStyle().set("margin-top", "60px");

        HorizontalLayout horizontaldetails = new HorizontalLayout(details, status, favorite);
        horizontaldetails.setWidth("800px");
        add(horizontaldetails);

        euroField = new NumberField();
        euroField.setLabel("Your offer");
        euroField.setValue(0.0);
        euroField.setReadOnly(true);
        Div euroSuffix = new Div();
        euroSuffix.setText("€");
        euroField.setSuffixComponent(euroSuffix);
        add(euroField);

        bid = new Button("BID!");
        bid.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_CONTRAST);

        Button back = new Button("BACK");
        back.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
        back.addClickListener(e -> {
            UI.getCurrent().navigate("");
        });

        Button automatic = new Button("Automatic Bid");
        automatic.addClickListener(e -> {
            UI.getCurrent().navigate("automaticBid");
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout(back, automatic, bid);
        add(horizontalLayout);


        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");

    }


}
