package com.pvi.Components;

import com.pvi.domain.Computer;
import com.pvi.domain.Customer;
import com.pvi.domain.Item;
import com.pvi.repos.ItemRepo;
import com.pvi.repos.OrderRepo;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.*;

public class CartDialog extends Dialog {

    private Map<Long, List<Item>> allComputers;
    private List<Cart> positions;
    private List<Item> out;
    private TextField totalCart;
    private Grid<Cart> cartGrid;

    public CartDialog(Set<Computer> cart, ItemRepo itemRepo, Customer customer, OrderRepo orderRepo) {
        H3 title = new H3("CART");
        int total = 0;
        positions = new ArrayList<>();
        allComputers = new HashMap();
        for (Computer computer : cart) {
            List<Item> items = itemRepo.getAvailableComp(computer.getId());
            total = total + computer.getPrice();
            allComputers.put(computer.getId(), items);
            positions.add(new Cart(computer.getId(), computer.toString(), computer.getPrice(), items.size()));
        }
        totalCart = new TextField();
        totalCart.setReadOnly(true);
        totalCart.setValue(String.valueOf(total));
        totalCart.setWidth("125px");

        Button buy = new Button("BUY",
                e -> {
            out = new ArrayList<>();
            for (Cart c : positions){
                int count = c.getQuantity();
                List<Item> items = allComputers.get(c.getArticul());
                for (int i =0; i<count;i++) {
                    out.add(items.get(i));
                }
            }
            OrderDialog orderDialog = new OrderDialog(out,customer,orderRepo);
            orderDialog.open();
            close();
                });

        buy.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buy.addClickShortcut(Key.ENTER);

        cartGrid = new Grid<>(Cart.class);
        cartGrid.setItems(positions);
        cartGrid.setColumns("articul", "computer", "available", "unitPrice");
        cartGrid.getColumnByKey("unitPrice").setHeader("Unit Price (RUB)");
        cartGrid.addComponentColumn(this::addQuantity).setHeader("Quantity");
        cartGrid.addColumn("total").setHeader("TOTAL (RUB)");
        cartGrid.setThemeName("wrap-cell-content");
        cartGrid.setHeightByRows(true);
        cartGrid.getColumns().forEach(column -> column.setAutoWidth(true));

        HorizontalLayout horizontalLayoutTotal = new HorizontalLayout(buy, totalCart);
        horizontalLayoutTotal.setWidthFull();
        horizontalLayoutTotal.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        add(title, cartGrid, horizontalLayoutTotal);
        setWidth("1350px");

    }

    private IntegerField addQuantity(Cart cart) {
        IntegerField numberField = new IntegerField();
        numberField.setWidth("100px");
        numberField.setValue(cart.getQuantity());
        numberField.setHasControls(true);
        numberField.setMin(0);
        numberField.setMax(cart.available);
        numberField.addValueChangeListener(event -> {
            cart.setQuantity(event.getValue());
            cart.updateTotal();
            updateTotal();
            cartGrid.getDataProvider().refreshItem(cart);
        });
        return numberField;

    }


    private void updateTotal() {
        int total = 0;
        for (Cart cart : positions)
            total = total + cart.getTotal();
        totalCart.setValue(String.valueOf(total));
    }

    public class Cart {
        private Long articul;
        private String computer;
        private int quantity;
        private int unitPrice;
        private int available;
        private int total;

        public Cart(Long articul, String computer, int unitPrice, int available) {
            this.articul = articul;
            this.computer = computer;
            this.quantity = 1;
            this.unitPrice = unitPrice;
            this.total = unitPrice;
            this.available = available;
        }

        public void updateTotal() {
            this.total = this.unitPrice * this.quantity;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public Long getArticul() {
            return articul;
        }

        public void setArticul(Long articul) {
            this.articul = articul;
        }

        public String getComputer() {
            return computer;
        }

        public void setComputer(String computer) {
            this.computer = computer;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(int unitPrice) {
            this.unitPrice = unitPrice;
        }

        public int getAvailable() {
            return available;
        }

        public void setAvailable(int available) {
            this.available = available;
        }
    }
}
