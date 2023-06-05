package com.example.application.views.wonauctions;

import com.example.application.data.AuctionsViewCard;
import com.example.application.data.entity.Auction;
import com.example.application.data.services.AuctionService;
import com.example.application.security.SecurityService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import jakarta.annotation.security.PermitAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@PageTitle("WonAuctions")
@Route(value = "won", layout = MainLayout.class)
@PermitAll
public class WonAuctionsView extends Main implements HasComponents, HasStyle {

    private final AuctionService auctionService;
    private final SecurityService securityService;

    private OrderedList imageContainer;
    private List<Auction> auctions;
    Select<String> sortBy;

    public WonAuctionsView(AuctionService auctionService, SecurityService securityService) {
        this.auctionService = auctionService;
        this.securityService = securityService;
        constructUI();

        auctions = auctionService.findAll();
        String user = securityService.getAuthenticatedUser().getUsername();
        LocalDate nowDate = LocalDate.now();
        LocalTime nowTime = LocalTime.now();

        for(Auction a : auctions)
        {
            if(Objects.equals(a.getLastBidderUsername(), user))
                if (a.getToLD().isBefore(nowDate) || (a.getToLD().isEqual(nowDate) && a.getToLT().isBefore(nowTime)))
                    imageContainer.add(new AuctionsViewCard(a));
        }


        sortBy.addValueChangeListener(event -> {
            if(event.getValue().equals("Newest first")) {
                auctions = auctionService.listSortedByNewest(auctions);
                imageContainer.removeAll();
                for(Auction a : auctions)
                {
                    imageContainer.add(new AuctionsViewCard(a));
                }
            }
            else if(event.getValue().equals("Oldest first")) {
                imageContainer.removeAll();
                auctions = auctionService.listSortedByOldest(auctions);
                for (Auction a : auctions) {
                    imageContainer.add(new AuctionsViewCard(a));
                }
            }
        });
    }

    private void constructUI() {
        addClassNames("won-auctions-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);

        HorizontalLayout container = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        VerticalLayout headerContainer = new VerticalLayout();
        H2 header = new H2("Won Auctions");
        header.addClassNames("header");
        Paragraph description = new Paragraph("Here you can find the items you won in auctions!");
        description.addClassName("description");
        headerContainer.add(header, description);

        HorizontalLayout container2 = new HorizontalLayout();
        container2.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);

        Label selectLabel = new Label("Sort by");
        selectLabel.addClassName("label");
        container2.add(selectLabel);

        sortBy = new Select<>();
        sortBy.setLabel("Sort by");
        sortBy.setClassName("my-select");
        sortBy.setItems("Newest first", "Oldest first");
        sortBy.setValue("Newest first");


        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);

        container.add(headerContainer, container2, sortBy);
        add(container, imageContainer);
    }
}
