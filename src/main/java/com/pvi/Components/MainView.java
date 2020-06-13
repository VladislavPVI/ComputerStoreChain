package com.pvi.Components;

import com.pvi.domain.Account;
import com.pvi.domain.Role;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import org.springframework.security.core.context.SecurityContextHolder;
// Import the main style sheets which set all the
// global custom properties



@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")

public class MainView extends AppLayout {

    public MainView() {
        createHeader();
        createDrawer();


        }

        private void createHeader(){
        H1 title = new H1("PvI Computers");
        title.addClassName("logo");
            Anchor logout = new Anchor("logout", "Log out");

            HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), title, logout);
            header.expand(title);
            header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
            header.setWidth("100%");
            header.addClassName("header");
            addToNavbar(header);


        }
        private void createDrawer(){

            Account principal = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Tabs tabs = new Tabs();


            if (principal.getRoles().contains(Role.CUSTOMER)){
                RouterLink about = new RouterLink("About", AboutView.class);
                RouterLink computers = new RouterLink("Computers", ComputerView.class);
                RouterLink myorders = new RouterLink("My Orders", OrderView.class);

                Tab tab1 = new Tab(VaadinIcon.INFO.create(),about);
                Tab tab2 = new Tab(VaadinIcon.DESKTOP.create(),computers);
                Tab tab3 = new Tab(VaadinIcon.PACKAGE.create(),myorders);

                tabs.add(tab1, tab2, tab3);

            }
            else {
                RouterLink about = new RouterLink("About", AboutView.class);
                RouterLink computers = new RouterLink("Computers Edit", ComputerEditView.class);
                RouterLink myorders = new RouterLink("Orders Edit", OrderViewManager.class);
                RouterLink customer = new RouterLink("Customers Edit", CustomerView.class);

                Tab tab1 = new Tab(VaadinIcon.INFO.create(),about);
                Tab tab2 = new Tab(VaadinIcon.DESKTOP.create(),computers);
                Tab tab3 = new Tab(VaadinIcon.PACKAGE.create(),myorders);
                Tab tab4 = new Tab(VaadinIcon.USER.create(),customer);

                tabs.add(tab1, tab2, tab3,tab4);

            }

            tabs.setOrientation(Tabs.Orientation.VERTICAL);

            addToDrawer(tabs);
        }

}
