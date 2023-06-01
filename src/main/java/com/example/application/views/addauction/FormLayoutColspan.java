package com.example.application.views.addauction;

import com.example.application.data.entity.Auction;
import com.example.application.data.repository.AuctionRepository;
import com.example.application.security.SecurityService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.io.InputStream;

@Route("form-layout-colspan")
public class FormLayoutColspan extends Div {

    private final AuctionRepository auctionRepository;
    private final SecurityService securityService;

    int charLimit = 140;
    private Span status;

    private void setStatus(String value) {
        status.setText("Status: " + value);
        status.setVisible(true);
    };
    public FormLayoutColspan(AuctionRepository auctionRepository, SecurityService securityService){
        this.auctionRepository = auctionRepository;
        this.securityService = securityService;

        TextField title = new TextField("Title");


        TextArea description = new TextArea();
        description.setLabel("Description");
        description.setMaxLength(charLimit);
        description.setValueChangeMode(ValueChangeMode.EAGER);
        description.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + charLimit);
        });
        description.setValue("Add your description here!!");

        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload image = new Upload(buffer);
        Auction auction = new Auction();

        image.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);
            try {
                auction.setImage(inputStream.readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Do something with the file data
            // processFile(inputStream, fileName);
        });
        image.setAcceptedFileTypes(".png", ".jpg");

        DatePicker dateFrom = new DatePicker("Date from");

        TimePicker from = new TimePicker("From");

        DatePicker dateTo = new DatePicker("Date to");

        TimePicker to = new TimePicker("To");

        NumberField euroField = new NumberField();
        euroField.setLabel("Minimum accepted price:");
        euroField.setValue(10.0);
        Div euroSuffix = new Div();
        euroSuffix.setText("€");
        euroField.setSuffixComponent(euroSuffix);

        Button submitButton = new Button("Submit");
        submitButton.addClickListener(e -> {
            HorizontalLayout layout = new HorizontalLayout();
            layout.setAlignItems(FlexComponent.Alignment.CENTER);
            layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

            status = new Span();
            status.setVisible(false);

            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setHeader("Are you sure you want to submit?");
            dialog.setText("Please check if all the introduced data is correct.");

            dialog.setRejectable(true);
            dialog.setRejectText("Discard");
            dialog.addRejectListener(event -> {
                setStatus("Discarded");
                Notification notification = Notification
                        .show("Discarded");
            });

            dialog.setConfirmText("Save");
            dialog.addConfirmListener(event -> {
                setStatus("Submit");

                //Actually adding auction to db
                UserDetails user = securityService.getAuthenticatedUser();

                auction.setDescription(description.getValue());
                auction.setTitle(title.getValue());
                auction.setStartingPrice(euroField.getValue());
                auction.setUsername(user.getUsername());

                auctionRepository.save(auction);

                Notification notification = Notification
                        .show("Item added");
            });

            //Button button = new Button("Open confirm dialog");
            //button.addClickListener(event -> {
                //dialog.open();
                //status.setVisible(false);
            //});

            dialog.open();

            //layout.add(button, status);
            add(layout);

            // Center the button within the example

        });



        FormLayout formLayout = new FormLayout();
        formLayout.add(title, description, image, dateFrom, from, dateTo, to, euroField, submitButton);
        // tag::snippet[]
        formLayout.setColspan(title, 3);
        formLayout.setColspan(description, 3);
        formLayout.setColspan(image, 3);
        formLayout.setColspan(from, 2);
        formLayout.setColspan(to, 2);
        formLayout.setColspan(submitButton, 3);


        // end::snippet[]
        formLayout.setResponsiveSteps(new ResponsiveStep("0", 1),
                new ResponsiveStep("500px", 3));
        add(formLayout);
    }

}
