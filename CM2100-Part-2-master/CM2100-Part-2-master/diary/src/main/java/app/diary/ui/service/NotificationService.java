package app.diary.ui.service;

import javafx.scene.control.Alert;
import org.springframework.stereotype.Service;

/**
 * CM2100 Advanced Software Design and Development Assessment Part 2 - Coursework (Deadline: 13/01/2020, 16:00)
 *
 * @author Ross Morrison 1105864
 */

@Service
public class NotificationService {
    private final Alert basicAlert = new Alert(Alert.AlertType.NONE);

    public void showErrorNotification(String notification){
        basicAlert.setAlertType(Alert.AlertType.ERROR);
        basicAlert.setTitle(null);
        basicAlert.setContentText(notification);
        basicAlert.show();
    }

    public void showSuccessMessage(String notification) {
        basicAlert.setAlertType(Alert.AlertType.INFORMATION);
        basicAlert.setTitle(null);
        basicAlert.setContentText(notification);
        basicAlert.show();
    }
}
