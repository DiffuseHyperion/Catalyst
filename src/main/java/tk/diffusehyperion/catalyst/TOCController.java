package tk.diffusehyperion.catalyst;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

import static tk.diffusehyperion.catalyst.MainApplication.scene;

public class TOCController {
    // very new to javafx, no idea how to implement a 10 second cooldown before exiting, if any mcw people see this send a pr lol
    @FXML
    protected void returnToMain() {
        try {
            Parent pane = new FXMLLoader(MainApplication.class.getResource("main.fxml")).load();
            scene.setRoot(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
