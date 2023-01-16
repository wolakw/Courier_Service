package com.example.application.views.packageList;

import com.example.application.data.entity.Package;
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
@Route(value = "package", layout = MainLayout.class)
@PageTitle("Wolszyn | Packages")
public class PackageList extends VerticalLayout {
    Grid<Package> grid = new Grid<>(Package.class);
    TextField filterText = new TextField();
    PackageForm form;
    CrmService service;

    public PackageList(CrmService service) {
        this.service = service;
        addClassName("package-view");
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
        form = new PackageForm(service.findAllStatuses());
        form.setWidth("25em");
        form.addListener(PackageForm.SaveEvent.class, this::savePackage);
        form.addListener(PackageForm.DeleteEvent.class, this::deletePackage);
        form.addListener(PackageForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassNames("package-grid");
        grid.setSizeFull();
        grid.setColumns("id","weight", "height", "width", "length");
        grid.addColumn(pack -> pack.getStatus().getName()).setHeader("Status");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editPackage(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by id");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button editButton = new Button("Edytuj");
        editButton.addClickListener(click -> addPackage());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, editButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    public void editPackage(Package pack) {
        if (pack == null) {
            closeEditor();
        } else {
            form.setPack(pack);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void savePackage(PackageForm.SaveEvent event) {
        service.savePackage(event.getPack());
        updateList();
        closeEditor();
    }

    private void deletePackage(PackageForm.DeleteEvent event) {
        service.deletePackage(event.getPack());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setPack(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addPackage() {
        grid.asSingleSelect().clear();
        editPackage(new Package());
    }


    private void updateList() {
        grid.setItems(service.findAllPackages(filterText.getValue()));
    }
}
