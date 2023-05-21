package com.example.application.views.registrationform;

import com.example.application.data.services.UserService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "register")
@PageTitle("Register | AuctionHouse")
@AnonymousAllowed
public class RegistrationView extends VerticalLayout {

    private final UserService userService;

    public RegistrationView(UserService userService) {
        this.userService = userService;
        RegistrationForm registrationForm = new RegistrationForm(this.userService);
        // Center the RegistrationForm
        setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);

        add(registrationForm);

      // RegistrationFormBinder registrationFormBinder = new RegistrationFormBinder();
      // registrationFormBinder.addBindingAndValidation();
    }
}
