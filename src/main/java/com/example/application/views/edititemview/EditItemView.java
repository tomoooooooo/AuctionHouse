package com.example.application.views.edititemview;

import com.example.application.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import jakarta.annotation.security.PermitAll;

import java.io.InputStream;

@PageTitle("Edit auction item")
@Route(value = "edit")
@PermitAll
public class EditItemView  extends VerticalLayout{

    int charLimit = 140;
    private Span status;

    private void setStatus(String value) {
        status.setText("Status: " + value);
        status.setVisible(true);
    };

    public EditItemView(){
        TextArea details = new TextArea();
        details.setReadOnly(true);
        details.setLabel("Current item details");
        details.setValue("Title: X\nDescription: Y\nStarting date: X  Starting hour: Y\nEnding date: X  Ending hour: Y\nMinimum accepted price: Z\nStarting price: 50% of minimum accepted price\nLast Bid: n/a");
        details.setWidthFull();
        add(details);

        TextField title = new TextField("Title");


        TextArea textArea = new TextArea();
        textArea.setLabel("Description");
        textArea.setMaxLength(charLimit);
        textArea.setValueChangeMode(ValueChangeMode.EAGER);
        textArea.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + charLimit);
        });
        textArea.setValue("");


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

        Button submitButton = new Button("Edit");
        submitButton.addClickListener(e -> {
            HorizontalLayout layout = new HorizontalLayout();
            layout.setAlignItems(FlexComponent.Alignment.CENTER);
            layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

            status = new Span();
            status.setVisible(false);

            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setHeader("Are you sure you want to save the added information?");
            dialog.setText(
                    "Please check if all the introduced data is correct.");


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
                Notification notification = Notification
                        .show("Data saved!");
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

        Button back = new Button("Back");
        back.addClickListener(e -> {
            UI.getCurrent().navigate("2");
        });
        submitButton.getStyle().set("background-color", "white");
        submitButton.getStyle().set("color", "black");

        HorizontalLayout horizontalLayout = new HorizontalLayout(back, submitButton
        );
        add(horizontalLayout);

        FormLayout formLayout = new FormLayout();
        formLayout.add(title, textArea, dateFrom, from, dateTo, to, euroField, horizontalLayout);
        // tag::snippet[]
        formLayout.setColspan(title, 3);
        formLayout.setColspan(textArea, 3);
        formLayout.setColspan(from, 2);
        formLayout.setColspan(to, 2);
        formLayout.setColspan(horizontalLayout, 3);


        // end::snippet[]
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 3));
        add(formLayout);
    }
}

