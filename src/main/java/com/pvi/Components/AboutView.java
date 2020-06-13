package com.pvi.Components;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "",layout = MainView.class)
@PageTitle("About | PvIComputers")
public class AboutView extends VerticalLayout {
    public AboutView() {
        TextArea text = new TextArea();
        text.setValue("The company’s infrastructure includes the Pvi Computers online store, more than 650 stores and order points in 360 cities of Russia. The product portfolio includes about 800 global brands and 70,000 items of computer, digital, household appliances, and other categories of goods, including garden, car electronics, office furniture, and office equipment. The portfolio of services includes computer assembly, equipment installation, a selection of consumables, electronic keys and signatures, protection of purchases and property, as well as other digital services.");
        text.setReadOnly(true);
        text.setWidthFull();
        TextArea text1 = new TextArea();
        text1.setValue("Pvi Computers stores are equipped with terminals for self-ordering, cash desks and dispensing windows. Call-center of the company operates 7 days a week and provides round-the-clock support to customers. The number of visits to Pvi Computers per month is more than 35 million people.");
        text1.setReadOnly(true);
        text1.setWidthFull();
        TextArea text2 = new TextArea();
        text2.setValue("Pvi Computers holds a leading position in the e-commerce market: In 2015, it ranked 2nd in the Forbes magazine's TOP-20 Russian Online Stores rating. In 2015 - 4th place in the Forbes magazine ranking of “20 most expensive Runet companies” In 2016 - 8th place in the Forbes magazine ranking of “20 most expensive Runet companies” In 2016 - 3rd place in terms of online sales in the Data Insight and Ruward E-commerce Index TOP-100 ranking In 2017 - 2nd place in terms of online sales in the Data Insight and Ruward E-commerce Index TOP-100 ranking In 2019 - 1st place in the study Data Insight “Online Market of Household Appliances and Electronics” (period: July 2018-June 2019). In 2019 - 7th place in the ranking of \"20 most expensive Runet companies\" according to Forbes. The only online retailer in the ranking that specializes in the sale of electronics and household appliances. In 2020 - the 2nd place in the Data Insight TOP-100 rating of the largest Russian stores E-Commerce Index In 2020 - the 8th place in the Forbes rating of \"10 main Runet sellers\" In 2012, 2013, 2014, 2015, 2016 In 2018 and 2019, Pvi Computers was awarded the title of Attractive Employer based on a study by Superjob.ru among companies operating in Russia.");
        text2.setReadOnly(true);
        text2.setWidthFull();

        Image image = new Image("https://i.dlpng.com/static/png/6379876_preview.png", "DummyImage");
        image.setHeight("354px");
        image.setWidth("460px");


        add(new HorizontalLayout(new H3("Pvi Computers - one of the largest chains of computer equipment stores, is part of the Merlion Group of Companies. In the market since 2008"), image), text,text1,text2);

    }

}
