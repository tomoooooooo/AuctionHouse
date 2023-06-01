package com.example.application.views.AuctionItem;

import com.example.application.data.entity.Auction;
import com.example.application.data.services.AuctionService;
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
import java.util.List;

@PageTitle("Auction Item")
@Route(value = "auctionItem/")
@PermitAll

public class AuctionItem extends VerticalLayout implements HasUrlParameter<String> {

    private final AuctionService auctionService;
    private H1 header;
    private List<Auction> auctionsList;
    private Auction auction;
    private Image img;
    private H4 description;
    private TextArea details;

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter String parameter) {
        for(Auction a : auctionsList)
            if(a.getId() == Long.parseLong(parameter))
                auction = new Auction(
                        a.getTitle(),
                        a.getDescription(),
                        a.getStartingPrice(),
                        a.getUsername(),
                        a.getImage()
                );
        header.setText("Title: " + auction.getTitle());

        InputStream imageStream = new ByteArrayInputStream(auction.getImage());
        StreamResource imageResource = new StreamResource("StreamedImage",
                () -> imageStream);
        img.setSrc(imageResource);
        img.setAlt("No image found :(");

        description.setText(auction.getDescription());
        details.setValue("Starting date: X  Starting hour: Y\n" +
                "Ending date: X  Ending hour: Y\n" +
                "Starting price: " + auction.getStartingPrice() + "\n" +
                "Last Bid: n/a");
    }


    public AuctionItem(AuctionService auctionService) {
        this.auctionService = auctionService;
        auctionsList = auctionService.findAll();
        setSpacing(true);

        header = new H1();
        header.addClassNames(Margin.Top.LARGE, Margin.Bottom.SMALL);
        add(header);

      /*
        Image image = new Image(, "Image");
       */
        img = new Image();
        img.setWidth("200px");
        add(img);

        description = new H4();
        add(description);

        details = new TextArea();
        details.setReadOnly(true);
        details.setLabel("Auction details");
        details.setWidthFull();

        TextField status = new TextField();
        status.setReadOnly(true);
        status.setValue("Waiting for auction");
        status.getStyle().set("background-color", "red");
        status.getStyle().set("margin-top", "60px");
        //daca e ongoing se schimba in verder si ongoing
        status.setWidth("200px");
        status.setHeight("32px");

        Button favorite = new Button("Add Favorite!");
        favorite.addClickListener(e ->{
            Notification notification = Notification
                    .show("Added to favorite!");
        });
        favorite.getStyle().set("margin-top", "60px");

        HorizontalLayout horizontaldetails = new HorizontalLayout(details, status, favorite
        );
        horizontaldetails.setWidth("800px");
        add(horizontaldetails);

        NumberField euroField = new NumberField();
        euroField.setLabel("Your offer");
        euroField.setValue(0.0);
        euroField.setReadOnly(true);
        //daca a inceput, se scoate
        Div euroSuffix = new Div();
        euroSuffix.setText("€");
        euroField.setSuffixComponent(euroSuffix);
        add(euroField);

        Button bid = new Button("BID!");
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

        HorizontalLayout horizontalLayout = new HorizontalLayout(back, automatic, bid
                );
        add(horizontalLayout);



        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");

    }


}
