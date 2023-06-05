package com.example.application.views.registrationform;

import com.example.application.data.entity.UserRole;
import com.example.application.data.entity.Users;
import com.example.application.data.services.UserService;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
@Setter
public class RegistrationForm extends FormLayout{

    private final UserService userService;

    private H3 title;
    private TextField firstName;
    private TextField lastName;
    private TextField userName;
    private EmailField email;
    private PasswordField password;
    private PasswordField passwordConfirm;
    private Checkbox allowMarketing;
    private Span errorMessageField;
    private Button submitButton;



    public RegistrationForm(UserService userService){
        this.userService = userService;
        setSizeFull();
        title = new H3("Signup form");
        firstName = new TextField("First name");
        lastName = new TextField("Last name");
        userName = new TextField("User name");
        email = new EmailField("Email");
        password = new PasswordField("Password");
        passwordConfirm = new PasswordField("Confirm password");

        setRequiredIndicatorVisible(firstName, lastName, email, userName, password, passwordConfirm);

        errorMessageField = new Span();

        submitButton = new Button("Join the community");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener(e -> {
            Users user = new Users(
                    firstName.getValue(),
                    lastName.getValue(),
                    email.getValue(),
                    userName.getValue(),
                    password.getValue(),
                    UserRole.USER
            );
            Optional<Users> uUsername = userService.findByUsername(userName.getValue());
            Optional<Users> uEmail = userService.findByEmail(email.getValue());
            if(uUsername.isPresent() || uEmail.isPresent()) {
                Notification notification = Notification.show("User with email or username already exists!");
            }
            else{
                userService.saveUser(user);
                UI.getCurrent().navigate("");
            }

        });

        add(title, firstName, lastName, email, userName, password, passwordConfirm, errorMessageField, submitButton);

        // Max width of the Form
        setMaxWidth("500px");

        // Allow the form layout to be responsive.
        // On device widths 0-490px we have one column.
        // Otherwise, we have two columns.
        setResponsiveSteps(
                new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
                new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP));

        // These components always take full width
        setColspan(title, 2);
        setColspan(email, 2);
        setColspan(userName, 2);
        setColspan(errorMessageField, 2);
        setColspan(submitButton, 2);
    }

    public PasswordField getPasswordField() {
        return password;
    }

    public PasswordField getPasswordConfirmField() {
        return passwordConfirm;
    }

    private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
        Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
    }

}