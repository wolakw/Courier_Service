package com.example.application.views.courierlist;

import com.example.application.data.entity.Courier;
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

public class CourierForm extends FormLayout {
    private Courier courier;
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    //ComboBox<Package> packages = new ComboBox<>("Packages");
    Binder<Courier> binder = new BeanValidationBinder<>(Courier.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");
    Button assign = new Button("Assign package");

    public void setCourier(Courier courier) {
        this.courier = courier;
        binder.readBean(courier);
    }

    public CourierForm() {
        addClassName("courier-form");
        binder.bindInstanceFields(this);
        //packages.setItems(courier.getPackages());

        add(firstName,
                lastName,
                //packages,
                createButtonsLayout(),
                createAssignLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);


        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new CourierForm.DeleteEvent(this, courier)));
        close.addClickListener(event -> fireEvent(new CourierForm.CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));
        return new HorizontalLayout(save, delete, close);
    }

    private HorizontalLayout createAssignLayout() {
        assign.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        assign.addClickListener(event -> fireEvent(new CourierForm.AssignEvent(this, courier)));
        return new HorizontalLayout(assign);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(courier);
            fireEvent(new CourierForm.SaveEvent(this, courier));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class CourierFormEvent extends ComponentEvent<CourierForm> {
        private Courier courier;

        protected CourierFormEvent(CourierForm source, Courier courier) {
            super(source, false);
            this.courier = courier;
        }

        public Courier getCourier() {
            return courier;
        }
    }

    public static class SaveEvent extends CourierForm.CourierFormEvent {
        SaveEvent(CourierForm source, Courier courier) {
            super(source, courier);
        }
    }

    public static class DeleteEvent extends CourierForm.CourierFormEvent {
        DeleteEvent(CourierForm source, Courier courier) {
            super(source, courier);
        }

    }

    public static class CloseEvent extends CourierForm.CourierFormEvent {
        CloseEvent(CourierForm source) {
            super(source, null);
        }
    }

    public static class AssignEvent extends CourierForm.CourierFormEvent {
        AssignEvent(CourierForm source, Courier courier) {
            super(source, courier);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
