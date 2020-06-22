package app.diary.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;

/**
 * CM2100 Advanced Software Design and Development Assessment Part 2 - Coursework (Deadline: 13/01/2020, 16:00)
 *
 * @author Ross Morrison 1105864
 */

@Controller
public class PreloaderController {
    @FXML
    private Text loaderText;

    public void setLoaderText(String loaderText){
        this.loaderText.setText(loaderText);
    }
}
