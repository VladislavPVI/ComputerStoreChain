package com.pvi.Components;

import com.pvi.domain.*;
import com.pvi.repos.OrderRepo;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.WeakHashMap;


@Route(value = "allorders",layout = MainView.class)
@Secured("MANAGER")
@PageTitle("All Orders | PvIComputers")
public class OrderViewManager extends VerticalLayout {
    final Grid<Orders> grid;

    public OrderViewManager(@Autowired OrderRepo orderRepo) {
        grid = new Grid<>();
        List<Orders> all = orderRepo.findAll();
        grid.setItems(all);

        Button saveButton = new Button("SAVE",
                e -> {orderRepo.saveAll(all);Notification.show("Save to DB");});

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickShortcut(Key.ENTER);

        HorizontalLayout saveTotallay = new HorizontalLayout(saveButton);
        saveTotallay.setWidthFull();
        saveTotallay.setJustifyContentMode(JustifyContentMode.END);


        Binder<Orders> binder = new Binder<>(Orders.class);
        Editor<Orders> editor = grid.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        Grid.Column<Orders> customer = grid.addColumn(Orders::getCustomer)
                .setHeader("Customer");

        Grid.Column<Orders> address = grid.addColumn(Orders::getAddress)
                .setHeader("Address");

        Grid.Column<Orders> dateOfCreate = grid.addColumn(Orders::getDateOfCreate)
                .setHeader("Date of Create");

        Grid.Column<Orders> dateOFDel = grid.addColumn(Orders::getDateOfDelivery)
                .setHeader("Date of Delivery");

        Grid.Column<Orders> dateOFComp = grid.addColumn(Orders::getDateOfComplete)
                .setHeader("Date of Complete");

        Grid.Column<Orders> delMethod = grid.addColumn(Orders::getDeliveryMethod)
                .setHeader("Delivery Method");

        Grid.Column<Orders> payMethod = grid.addColumn(Orders::getPaymentMethod)
                .setHeader("Payment Method");

        Grid.Column<Orders> total = grid.addColumn(Orders::getTotal)
                .setHeader("TOTAL (RUB)");

        Grid.Column<Orders> confirm  = grid.addColumn(Orders::isConfirm)
                .setHeader("Confirm order");

        Grid.Column<Orders> paymentConfirm  = grid.addColumn(Orders::isPaymentConfirm)
                .setHeader("Confirm payment");

        Grid.Column<Orders> completed  = grid.addColumn(Orders::isCompleted)
                .setHeader("Completed?");

        Div validationStatus = new Div();
        validationStatus.setId("validation");

        TextField addressField = new TextField();
        binder.forField(addressField)
                .withValidator(new StringLengthValidator("First name length must be between 3 and 50.", 3, 50))
                .withStatusLabel(validationStatus).bind("address");
        address.setEditorComponent(addressField);

        DatePicker dateOfDelField = new DatePicker();
        binder.forField(dateOfDelField)
               .withStatusLabel(validationStatus).bind("dateOfDelivery");
        dateOFDel.setEditorComponent(dateOfDelField);

        DatePicker dateOfCompField = new DatePicker();
        binder.forField(dateOfCompField)
                .withStatusLabel(validationStatus).bind("dateOfComplete");
        dateOFComp.setEditorComponent(dateOfCompField);

        ComboBox<DeliveryMethod> deliveryMethodComboBox = new ComboBox<>();
        deliveryMethodComboBox.setItems(DeliveryMethod.values());
        binder.forField(deliveryMethodComboBox)
                .withStatusLabel(validationStatus).bind("deliveryMethod");
        delMethod.setEditorComponent(deliveryMethodComboBox);

        IntegerField totalField = new IntegerField();
        binder.forField(totalField)
                .withStatusLabel(validationStatus).bind("total");
        total.setEditorComponent(totalField);

        Checkbox checkboxConfirm = new Checkbox();
        binder.forField(checkboxConfirm)
                .withStatusLabel(validationStatus).bind("confirm");
        confirm.setEditorComponent(checkboxConfirm);

        Checkbox checkboxConfirmPay = new Checkbox();
        binder.forField(checkboxConfirmPay)
                .withStatusLabel(validationStatus).bind("paymentConfirm");
        paymentConfirm.setEditorComponent(checkboxConfirmPay);

        Checkbox checkboxComplited = new Checkbox();
        binder.forField(checkboxComplited)
                .withStatusLabel(validationStatus).bind("completed");
        completed.setEditorComponent(checkboxComplited);

        Collection<Button> editButtons = Collections
                .newSetFromMap(new WeakHashMap<>());

        Grid.Column<Orders> editorColumn = grid.addComponentColumn(person -> {
            Button edit = new Button("Edit");
            edit.addClassName("edit");
            edit.addClickListener(e -> {
                editor.editItem(person);
                checkboxConfirm.focus();
            });
            edit.setEnabled(!editor.isOpen());
            editButtons.add(edit);
            return edit;
        });

        editor.addOpenListener(e -> editButtons.stream()
                .forEach(button -> button.setEnabled(!editor.isOpen())));
        editor.addCloseListener(e -> editButtons.stream()
                .forEach(button -> button.setEnabled(!editor.isOpen())));

        Button save = new Button("Save", e -> editor.save());
        save.addClassName("save");

        Button cancel = new Button("Cancel", e -> editor.cancel());
        cancel.addClassName("cancel");

// Add a keypress listener that listens for an escape key up event.
// Note! some browsers return key as Escape and some as Esc
        grid.getElement().addEventListener("keyup", event -> editor.cancel())
                .setFilter("event.key === 'Escape' || event.key === 'Esc'");

        Div buttons = new Div(save, cancel);
        editorColumn.setEditorComponent(buttons);



        grid.setHeightByRows(true);
        grid.setThemeName("wrap-cell-content");

        add(saveTotallay,grid);

    }



}
