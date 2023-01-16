package com.example.application.views;

import com.example.application.security.SecurityService;
import com.example.application.views.list.ListView;
import com.example.application.views.packageList.PackageList;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class MainLayout extends AppLayout {
    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Wolszyn | Courier system");
        logo.addClassNames("text-l", "m-m");

        Image baner = new Image("images/logo.png", "logo");


        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, baner);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);

        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);

    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("Client list", ListView.class);
        listLink.setHighlightCondition(HighlightConditions.sameLocation());
        Button logout = new Button("Log out", e -> securityService.logout());

        RouterLink packageList = new RouterLink("Package list", PackageList.class);
        packageList.setHighlightCondition(HighlightConditions.sameLocation());
        if (securityService.getAuthenticatedUser().getUsername() == "admin") {
            addToDrawer(new VerticalLayout(
                    listLink,
                    new RouterLink("Dashboard", DashboardView.class),
                    packageList,
                    logout
            ));
        } else if (securityService.getAuthenticatedUser().getUsername()== "kurier") {
            addToDrawer(new VerticalLayout(
                    listLink,
                    packageList,
                    logout
            ));
        } else {
            addToDrawer(new VerticalLayout(
                    packageList,
                    logout
            ));
        }
    }
}