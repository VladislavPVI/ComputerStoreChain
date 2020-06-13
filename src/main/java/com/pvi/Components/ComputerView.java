package com.pvi.Components;

import com.pvi.domain.Account;
import com.pvi.domain.Computer;
import com.pvi.domain.Customer;
import com.pvi.repos.ComputerRepo;
import com.pvi.repos.CustomerRepo;
import com.pvi.repos.ItemRepo;
import com.pvi.repos.OrderRepo;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Set;

@Route(value = "computer",layout = MainView.class)
@Secured("CUSTOMER")
@PageTitle("Computer | PvIComputers")

public class ComputerView extends VerticalLayout {

    private final ComputerRepo computerRepo;
    final Grid<Computer> grid;
    final Grid<Computer> gridCompare;
    private Set<Computer> cart;
    private Set<Computer> compare;
    private Button cartButton;
    private Button compareButton;
    private Dialog compareDialog;
    private CartDialog cartDialog;
    private Customer customer;


    public ComputerView(@Autowired ComputerRepo computerRepo, @Autowired ItemRepo itemRepo, @Autowired CustomerRepo customerRepo, @Autowired OrderRepo orderRepo) {
        this.computerRepo = computerRepo;
        this.grid = new Grid<>(Computer.class);
        this.gridCompare = new Grid<>(Computer.class);
        this.cart = new HashSet<>();
        this.compare = new HashSet<>();
        this.compareDialog = new Dialog();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        this.customer = customerRepo.findByAccount((Account) authentication.getPrincipal());

        cartButton = new Button(new Icon(VaadinIcon.CART));

        compareButton = new Button(new Icon(VaadinIcon.ABACUS));
        compareButton.addClickListener(event -> {
            listCompCompare();
            compareDialog.add(new Text("Compare"), gridCompare);
            compareDialog.setWidth("1300px");
            compareDialog.open();
        });


        cartButton.addClickListener(event -> {
            if (!cart.isEmpty()){
                cartDialog = new CartDialog(cart,itemRepo,customer,orderRepo);
                cartDialog.open();
            }

        });


        HorizontalLayout buttons = new HorizontalLayout(cartButton, compareButton);
        buttons.setWidthFull();
        buttons.setJustifyContentMode(JustifyContentMode.END);

        listComp();

        setHeightFull();
        add(buttons, grid);
        setHorizontalComponentAlignment(Alignment.CENTER, grid);


    }


    public void listComp() {
        grid.setItems(computerRepo.findAll());
        //"cpu", "gpu", "ram", "hdd", "os"
        grid.setColumns("name", "brand", "price");
        grid.getColumnByKey("price").setHeader("Price (RUB)");
        grid.addComponentColumn(this::addToCart);
        grid.addComponentColumn(this::compare);
        grid.setWidth("1000px");

        grid.getColumns().forEach(column -> column.setAutoWidth(true));


    }

    public void listCompCompare() {
        gridCompare.setItems(compare);
        gridCompare.setColumns("name", "brand", "cpu", "gpu", "ram", "hdd", "os", "price");
        gridCompare.getColumnByKey("price").setHeader("Price (RUB)");
        // gridCompare.addComponentColumn(this::addToCart);
        // gridCompare.addComponentColumn(this::compare);
        gridCompare.getColumns().forEach(column -> column.setAutoWidth(true));


    }


    private Button addToCart(Computer p) {

        Button buttonADD = new Button("Add to cart");
        buttonADD.addClickListener(e -> {
            if (buttonADD.getText().equals("Add to cart")) {
                cart.add(p);
                cartButton.setText(String.valueOf(cart.size()));
                buttonADD.setText("Delete from cart");

            } else {
                cart.remove(p);
                buttonADD.setText("Add to cart");
                cartButton.setText(String.valueOf(cart.size()));

            }
        });
        return buttonADD;

    }

    private Button compare(Computer p) {
        Button button = new Button("Add to compare");
        button.addClickListener(e -> {
            if (button.getText().equals("Add to compare")) {
                compare.add(p);
                button.setText("Delete from compare");
                compareButton.setText(String.valueOf(compare.size()));

            } else {
                button.setText("Add to compare");
                compare.remove(p);
                compareButton.setText(String.valueOf(compare.size()));

            }
        });
        return button;
    }

}
