package com.example.application.views.packageList;

import com.example.application.data.entity.Package;
import com.example.application.data.entity.Status;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class PackageForm extends FormLayout {
    private Package pack;
    TextField weight = new TextField("Weight");
    TextField height = new TextField("Height");
    TextField width = new TextField("Width");
    TextField length = new TextField("Length");
    ComboBox<Status> status = new ComboBox<>("Status");
    Binder<Package> binder = new BeanValidationBinder<>(Package.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public void setPack(Package pack) {
        this.pack = pack;
        binder.readBean(pack);
    }

    public PackageForm(List<Status> statuses) {
        addClassName("package-form");
        binder.bindInstanceFields(this);
        status.setItems(statuses);
        status.setItemLabelGenerator(Status::getName);

        add(weight,
                height,
                width,
                length,
                status,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new PackageForm.DeleteEvent(this, pack)));
        close.addClickListener(event -> fireEvent(new PackageForm.CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(pack);
            fireEvent(new PackageForm.SaveEvent(this, pack));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class PackageFormEvent extends ComponentEvent<PackageForm> {
        private Package pack;

        protected PackageFormEvent(PackageForm source, Package pack) {
            super(source, false);
            this.pack = pack;
        }

        public Package getPack() {
            return pack;
        }
    }

    public static class SaveEvent extends PackageForm.PackageFormEvent {
        SaveEvent(PackageForm source, Package pack) {
            super(source, pack);
        }
    }

    public static class DeleteEvent extends PackageForm.PackageFormEvent {
        DeleteEvent(PackageForm source, Package pack) {
            super(source, pack);
        }

    }

    public static class CloseEvent extends PackageForm.PackageFormEvent {
        CloseEvent(PackageForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
