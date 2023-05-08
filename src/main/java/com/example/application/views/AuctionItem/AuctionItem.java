package com.example.application.views.AuctionItem;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import jakarta.annotation.security.PermitAll;

@PageTitle("Auction Item")
@Route(value = "auctionItem")
@PermitAll

public class AuctionItem extends VerticalLayout {
    public AuctionItem() {
        setSpacing(true);

        H1 header = new H1("Item Title");
        header.addClassNames(Margin.Top.LARGE, Margin.Bottom.SMALL);
        add(header);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        H4 description = new H4("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        add(description);

        TextArea details = new TextArea();
        details.setReadOnly(true);
        details.setLabel("Auction details");
        details.setValue("Starting date: X  Starting hour: Y\nEnding date: X  Ending hour: Y\nMinimum accepted price: Z\nStarting price: 50% of minimum accepted price\nLast Bid: n/a");
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
