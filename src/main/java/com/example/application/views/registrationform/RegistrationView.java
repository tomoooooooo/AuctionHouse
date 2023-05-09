package com.example.application.views.registrationform;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "register")
@PageTitle("Register | AuctionHouse")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    public RegistrationView() {
        RegistrationForm registrationForm = new RegistrationForm();
        // Center the RegistrationForm
        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);

        add(registrationForm);

     //  RegistrationFormBinder registrationFormBinder = new RegistrationFormBinder();
      // registrationFormBinder.addBindingAndValidation();
    }
}
