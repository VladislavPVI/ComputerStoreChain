package com.pvi.Components;

import com.pvi.domain.Brand;
import com.pvi.domain.Computer;
import com.pvi.domain.Item;
import com.pvi.repos.ComputerRepo;
import com.pvi.repos.ItemRepo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
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

@Route(value = "computerEdit",layout = MainView.class)
@Secured("MANAGER")
@PageTitle("ComputerEdit | PvIComputers")
public class ComputerEditView extends VerticalLayout {
    final Grid<Computer> grid;

    public ComputerEditView(@Autowired ComputerRepo computerRepo, @Autowired ItemRepo itemRepo) {
        grid = new Grid<>();
        List<Computer> all = computerRepo.findAll();
        grid.setItems(all);

        Button saveButton = new Button("SAVE",
                e -> {
                    computerRepo.saveAll(all);
                    Notification.show("Save to DB");
                });

        Button addNewButton = new Button("ADD NEW",
                e -> {
                    Computer computer = new Computer();
                    Binder<Computer> binder = new Binder<>(Computer.class);

                    binder.setBean(computer);

                    Div validationStatus = new Div();
                    validationStatus.setId("validation");

                    ComboBox<Brand> brandComboBox = new ComboBox<>();
                    brandComboBox.setItems(Brand.values());
                    binder.forField(brandComboBox)
                            .withStatusLabel(validationStatus).bind("brand");

                    TextField nameField = new TextField();
                    binder.forField(nameField)
                            .withValidator(new StringLengthValidator("First name length must be between 3 and 50.", 3, 50))
                            .withStatusLabel(validationStatus).bind("name");
                    nameField.setLabel("Name");

                    TextField cpuField = new TextField();
                    binder.forField(cpuField)
                            .withValidator(new StringLengthValidator("First name length must be between 3 and 50.", 3, 50))
                            .withStatusLabel(validationStatus).bind("cpu");
                    cpuField.setLabel("CPU");

                    TextField gpuField = new TextField();
                    binder.forField(gpuField)
                            .withValidator(new StringLengthValidator("First name length must be between 3 and 50.", 3, 50))
                            .withStatusLabel(validationStatus).bind("gpu");
                    gpuField.setLabel("GPU");


                    TextField ramField = new TextField();
                    binder.forField(ramField)
                            .withValidator(new StringLengthValidator("First name length must be between 3 and 50.", 3, 50))
                            .withStatusLabel(validationStatus).bind("ram");
                    ramField.setLabel("RAM");

                    TextField hddField = new TextField();
                    binder.forField(hddField)
                            .withValidator(new StringLengthValidator("First name length must be between 3 and 50.", 3, 50))
                            .withStatusLabel(validationStatus).bind("hdd");

                    hddField.setLabel("HDD");

                    TextField osField = new TextField();
                    binder.forField(osField)
                            .withValidator(new StringLengthValidator("First name length must be between 3 and 50.", 3, 50))
                            .withStatusLabel(validationStatus).bind("os");

                    osField.setLabel("OS");

                    IntegerField priceField = new IntegerField();
                    binder.forField(priceField).withStatusLabel(validationStatus).bind("price");

                    priceField.setLabel("Price (RUB)");
                    priceField.setWidthFull();

                    IntegerField count = new IntegerField();

                    count.setLabel("Quantity");
                    count.setWidthFull();
                    count.setMin(0);
                    count.setHasControls(true);

                    Dialog newcomp = new Dialog();

                    Button saveComp = new Button("SAVE",
                            p -> {
                                computerRepo.save(computer);
                                for (int d = 0; d < count.getValue(); d++)
                                    itemRepo.save(new Item(computer));
                                Notification.show("Complete!");
                                newcomp.close();
                            });

                    saveComp.setWidthFull();


                    newcomp.add(new H3("NEW COMPUTER"), new VerticalLayout(brandComboBox, nameField, cpuField, gpuField, ramField, hddField, osField, priceField, count, saveComp));
                    newcomp.open();
                });

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addNewButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        HorizontalLayout saveTotallay = new HorizontalLayout(addNewButton, saveButton);
        saveTotallay.setWidthFull();
        saveTotallay.setJustifyContentMode(JustifyContentMode.END);

        Binder<Computer> binder = new Binder<>(Computer.class);
        Editor<Computer> editor = grid.getEditor();
        editor.setBinder(binder);
        editor.setBuffered(true);

        Grid.Column<Computer> brand = grid.addColumn(Computer::getBrand)
                .setHeader("Brand");

        Grid.Column<Computer> name = grid.addColumn(Computer::getName)
                .setHeader("Name");

        Grid.Column<Computer> cpu = grid.addColumn(Computer::getCpu)
                .setHeader("CPU");

        Grid.Column<Computer> gpu = grid.addColumn(Computer::getGpu)
                .setHeader("GPU");

        Grid.Column<Computer> ram = grid.addColumn(Computer::getRam)
                .setHeader("RAM");

        Grid.Column<Computer> hdd = grid.addColumn(Computer::getHdd)
                .setHeader("HDD");

        Grid.Column<Computer> os = grid.addColumn(Computer::getOs)
                .setHeader("OS");

        Grid.Column<Computer> price = grid.addColumn(Computer::getPrice)
                .setHeader("Price (RUB)");

        Div validationStatus = new Div();
        validationStatus.setId("validation");

        ComboBox<Brand> brandComboBox = new ComboBox<>();
        brandComboBox.setItems(Brand.values());
        binder.forField(brandComboBox)
                .withStatusLabel(validationStatus).bind("brand");
        brand.setEditorComponent(brandComboBox);

        TextField nameField = new TextField();
        binder.forField(nameField)
                .withValidator(new StringLengthValidator("First name length must be between 3 and 50.", 3, 50))
                .withStatusLabel(validationStatus).bind("name");
        name.setEditorComponent(nameField);

        TextField cpuField = new TextField();
        binder.forField(cpuField)
                .withValidator(new StringLengthValidator("First name length must be between 3 and 50.", 3, 50))
                .withStatusLabel(validationStatus).bind("cpu");
        cpu.setEditorComponent(cpuField);

        TextField gpuField = new TextField();
        binder.forField(gpuField)
                .withValidator(new StringLengthValidator("First name length must be between 3 and 50.", 3, 50))
                .withStatusLabel(validationStatus).bind("gpu");
        gpu.setEditorComponent(gpuField);

        TextField ramField = new TextField();
        binder.forField(ramField)
                .withValidator(new StringLengthValidator("First name length must be between 3 and 50.", 3, 50))
                .withStatusLabel(validationStatus).bind("ram");
        ram.setEditorComponent(ramField);

        TextField hddField = new TextField();
        binder.forField(hddField)
                .withValidator(new StringLengthValidator("First name length must be between 3 and 50.", 3, 50))
                .withStatusLabel(validationStatus).bind("hdd");
        hdd.setEditorComponent(hddField);

        TextField osField = new TextField();
        binder.forField(osField)
                .withValidator(new StringLengthValidator("First name length must be between 3 and 50.", 3, 50))
                .withStatusLabel(validationStatus).bind("os");
        os.setEditorComponent(osField);

        IntegerField priceField = new IntegerField();
        binder.forField(priceField).withStatusLabel(validationStatus).bind("price");
        price.setEditorComponent(priceField);

        Collection<Button> editButtons = Collections
                .newSetFromMap(new WeakHashMap<>());

        Grid.Column<Computer> editorColumn = grid.addComponentColumn(person -> {
            Button edit = new Button("Edit");
            edit.addClassName("edit");
            edit.addClickListener(e -> {
                editor.editItem(person);
                priceField.focus();
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

        add(saveTotallay, grid);


    }
}
