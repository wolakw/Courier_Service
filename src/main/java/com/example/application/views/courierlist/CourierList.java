package com.example.application.views.courierlist;

import com.example.application.data.entity.Courier;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PermitAll
@Route(value = "courier", layout = MainLayout.class)
@PageTitle("Wolszyn | Couriers")
public class CourierList extends VerticalLayout {
    Grid<Courier> grid = new Grid<>(Courier.class);
    TextField filterText = new TextField();
    CourierForm form;
    CrmService service;

    public CourierList(CrmService service) {
        this.service = service;
        addClassName("courier-view");
        setSizeFull();
        configureGrid();
        configureForm();

        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private HorizontalLayout getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new CourierForm();
        form.setWidth("25em");
        form.addListener(CourierForm.SaveEvent.class, this::saveCourier);
        form.addListener(CourierForm.DeleteEvent.class, this::deleteCourier);
        form.addListener(CourierForm.CloseEvent.class, e -> closeEditor());
        form.addListener(CourierForm.AssignEvent.class, this::assignPackage);
    }

    private void configureGrid() {
        grid.addClassNames("courier-grid");
        grid.setSizeFull();
        grid.setColumns("id","firstName", "lastName");
        //grid.addColumn(courier -> courier.getPackages()).setHeader("Packages");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editCourier(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button editButton = new Button("Add or edit");
        editButton.addClickListener(click -> addCourier());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, editButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editCourier(Courier courier) {
        if (courier == null) {
            closeEditor();
        } else {
            form.setCourier(courier);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveCourier(CourierForm.SaveEvent event) {
        service.saveCourier(event.getCourier());
        updateList();
        closeEditor();
    }

    private void deleteCourier(CourierForm.DeleteEvent event) {
        service.deleteCourier(event.getCourier());
        updateList();
        closeEditor();
    }


    private void closeEditor() {
        form.setCourier(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addCourier() {
        grid.asSingleSelect().clear();
        editCourier(new Courier());
    }

    private void assignPackage(CourierForm.AssignEvent event) {
        service.assignPackage(event.getCourier());
    }


    private void updateList() {
        grid.setItems(service.findAllCouriers(filterText.getValue()));
    }
}
