package com.pvi.Components;

import com.pvi.domain.Customer;
import com.pvi.repos.CustomerRepo;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

@Route(value = "customers",layout = MainView.class)
@Secured("MANAGER")
@PageTitle("Customers | PvIComputers")
public class CustomerView  extends VerticalLayout {
    final Grid<Customer> grid;

    public CustomerView(@Autowired CustomerRepo customerRepo) {
        grid = new Grid<>();
        List<Customer> all = customerRepo.findAll();
        grid.setItems(all);

        Button saveButton = new Button("SAVE",
                e -> {customerRepo.saveAll(all);Notification.show("Save to DB");});

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickShortcut(Key.ENTER);

        HorizontalLayout saveTotallay = new HorizontalLayout(saveButton);
        saveTotallay.setWidthFull();
        saveTotallay.setJustifyContentMode(JustifyContentMode.END);

        Binder<Customer> binder = new Binder<>(Customer.class);
        Editor<Customer> editor = grid.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        Grid.Column<Customer> fullname = grid.addColumn(Customer::getFullname)
                .setHeader("Customer");

        Grid.Column<Customer> email = grid.addColumn(Customer::getEmail)
                .setHeader("Email");

        Grid.Column<Customer> bd = grid.addColumn(Customer::getBirthd)
                .setHeader("Birthday");

        Div validationStatus = new Div();
        validationStatus.setId("validation");

        TextField fullnameField = new TextField();
        binder.forField(fullnameField)
                .withValidator(new StringLengthValidator("First name length must be between 3 and 50.", 3, 50))
                .withStatusLabel(validationStatus).bind("fullname");
        fullname.setEditorComponent(fullnameField);

        TextField emailField = new TextField();
        binder.forField(emailField)
                .withValidator(new StringLengthValidator("First name length must be between 3 and 50.", 3, 50))
                .withStatusLabel(validationStatus).bind("email");
        email.setEditorComponent(emailField);

        DatePicker dateOfBD = new DatePicker();
        binder.forField(dateOfBD)
                .withStatusLabel(validationStatus).bind("birthd");
        bd.setEditorComponent(dateOfBD);

        Collection<Button> editButtons = Collections
                .newSetFromMap(new WeakHashMap<>());

        Grid.Column<Customer> editorColumn = grid.addComponentColumn(person -> {
            Button edit = new Button("Edit");
            edit.addClassName("edit");
            edit.addClickListener(e -> {
                editor.editItem(person);
                fullnameField.focus();
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
