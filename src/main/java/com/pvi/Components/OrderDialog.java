package com.pvi.Components;


import com.pvi.domain.*;
import com.pvi.repos.OrderRepo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDialog extends Dialog {

    private int totalPay = 0;
    private boolean paybool = false;
    private Grid<Item> itemGrid;
    private ComboBox<DeliveryMethod> deliveryMethodComboBox = new ComboBox<>("Delivery");
    private ComboBox<PaymentMethod> paymentMethodComboBox  = new ComboBox<>("Payment");

    public OrderDialog(List<Item> out, Customer customer, OrderRepo orderRepo) {
        H3 title = new H3("NEW ORDER");
        itemGrid = new Grid<>(Item.class);
        itemGrid.setItems(out);
        itemGrid.setHeightByRows(true);
        itemGrid.getColumns().forEach(column -> column.setAutoWidth(true));
        for (Item item : out)
            totalPay = totalPay + item.getComputer().getPrice();

        TextField fullname = new TextField("Full name");
        fullname.setValue(customer.getFullname());
        fullname.setReadOnly(true);
        fullname.setWidthFull();

        TextField total = new TextField("Total (RUB)");
        total.setValue(String.valueOf(totalPay));
        total.setReadOnly(true);
        total.setWidthFull();

        deliveryMethodComboBox.setItems(DeliveryMethod.values());
        paymentMethodComboBox.setItems(PaymentMethod.values());
        paymentMethodComboBox.setWidthFull();

        HorizontalLayout horizontalLayoutPay = new HorizontalLayout(total, paymentMethodComboBox);
        horizontalLayoutPay.setWidthFull();


        Button buy = new Button("PAY",
                e -> {Notification.show("Thank you for payment!"); paybool=true;});
        buy.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buy.setWidthFull();

        DatePicker delDate = new DatePicker("Date of delivery");

        TextField addressText = new TextField("Address");

        Button makeOrder = new Button("MAKE AN ORDER",
            e -> {Orders order = new Orders(customer,out,LocalDate.now(),delDate.getValue(),deliveryMethodComboBox.getValue(),paymentMethodComboBox.getValue(),totalPay,addressText.getValue(),paybool); orderRepo.save(order); Notification.show("Complete!");close();});

        makeOrder.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        makeOrder.setWidthFull();

        deliveryMethodComboBox.setWidthFull();
        addressText.setWidthFull();
        delDate.setWidthFull();
        HorizontalLayout horizontalLayoutD = new HorizontalLayout(deliveryMethodComboBox, addressText, delDate);
        horizontalLayoutD.setWidthFull();

        VerticalLayout verticalLayout = new VerticalLayout(fullname,horizontalLayoutPay,buy,horizontalLayoutD,makeOrder);
        add(title, itemGrid,verticalLayout);
        setWidth("750px");
      //  setHeightFull();
    }
}
