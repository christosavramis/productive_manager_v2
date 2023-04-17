package com.example.application.ui.views;

import com.example.application.backend.data.AuditType;
import com.example.application.backend.data.entities.Audit;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import org.springframework.stereotype.Component;

@Component
public class NotificationManager {

    public void notificationError(Audit audit) {
        notificationError(audit.getMessage(), matchVariantWithLogLevel(audit.getAuditType()));
    }

    public void notificationError(String string, NotificationVariant notificationVariant){
        Text notificationText = new Text(string);
        Notification notificationError = new Notification();
        notificationError.addThemeVariants(notificationVariant);
        Div text = new Div(notificationText);
        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> {
            notificationError.close();
        });

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        notificationError.add(layout);
        notificationError.setDuration(4000);
        notificationError.open();
    }

    private NotificationVariant matchVariantWithLogLevel(AuditType auditType) {
        NotificationVariant notificationVariant;
            switch (auditType) {
                case INFO:
                    notificationVariant = NotificationVariant.LUMO_PRIMARY;
                    break;
                case WARN:
                case DEBUG:
                case ERROR:
                    notificationVariant = NotificationVariant.LUMO_ERROR;
                    break;
                case TRACE:
                default:
                    notificationVariant = NotificationVariant.LUMO_CONTRAST;
            }
            return notificationVariant;
    }

}
