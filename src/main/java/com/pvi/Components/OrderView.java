package com.pvi.Components;

import com.pvi.domain.*;
import com.pvi.repos.CustomerRepo;
import com.pvi.repos.OrderRepo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;


@Route(value = "orders",layout = MainView.class)
@Secured("CUSTOMER")
@PageTitle("My Orders | PvIComputers")
public class OrderView extends VerticalLayout {
    private final Grid<Orders> grid;
    private Dialog d;
    private Grid<Item> itemGrid;
    private List<Orders> byCustomer;
    private OrderRepo orderRepo;


    public OrderView(@Autowired OrderRepo orderRepo, @Autowired CustomerRepo customerRepo) {
        this.orderRepo = orderRepo;
        grid = new Grid<>(Orders.class);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerRepo.findByAccount((Account) authentication.getPrincipal());
        byCustomer = orderRepo.findByCustomer(customer);
        grid.setItems(byCustomer);
//        //"cpu", "gpu", "ram", "hdd", "os"
//        grid.setColumns("name", "brand", "price");
//        grid.getColumnByKey("price").setHeader("Price (RUB)");
//        grid.addComponentColumn(this::addToCart);
//        grid.addComponentColumn(this::compare);
//        grid.setWidth("1000px");


       // grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.removeColumnByKey("id");
        grid.removeColumnByKey("items");
        grid.removeColumnByKey("dateOfComplete");

        grid.addComponentColumn(this::showItems).setHeader("Items");
        grid.addComponentColumn(this::deleteOrder);

        grid.setHeightByRows(true);
        grid.setThemeName("wrap-cell-content");

        add(grid);
       // setHeightFull();

    }
    private Button showItems(Orders o) {

        Button button = new Button("Show items");
        button.addClickListener(e -> {
            itemGrid = new Grid<>(Item.class);
            itemGrid.setItems(o.getItems());
            itemGrid.setHeightByRows(true);
            itemGrid.setWidthFull();
            itemGrid.getColumns().forEach(column -> column.setAutoWidth(true));
            d = new Dialog();
            d.setWidth("800px");
            d.add(itemGrid);
            d.open();
        });
        return button;

    }

    private Button deleteOrder(Orders o) {

        Button button = new Button("Delete");
        button.addClickListener(e -> {
         byCustomer.remove(o);
         grid.getDataProvider().refreshAll();
         orderRepo.delete(o);
        });
        return button;

    }


}
