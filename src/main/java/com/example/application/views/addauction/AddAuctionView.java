package com.example.application.views.addauction;

import com.example.application.security.RegistrationFormBinder;
import com.example.application.views.MainLayout;
import com.example.application.views.registrationform.RegistrationForm;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import jakarta.annotation.security.PermitAll;

@PageTitle("Add Auction")
@Route(value = "addauction", layout = MainLayout.class)
@PermitAll
public class AddAuctionView extends VerticalLayout {

    public AddAuctionView() {
        setSpacing(true);
        FormLayoutColspan registrationForm = new FormLayoutColspan();
        // Center the RegistrationForm
        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);

        add(registrationForm);

    }

}
