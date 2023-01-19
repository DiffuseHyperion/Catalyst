package tk.diffusehyperion.catalyst;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.io.File;

import static tk.diffusehyperion.catalyst.MainApplication.scene;

public class MainController {

    private File sourceDirectory;
    private File shimFile;
    private File chromebrewFile;
    private boolean viewedTOC;

    public final String SOURCEBUTTONID = "sourceButton";
    public final String SHIMBUTTONID = "shimButton";
    public final String CHROMEBREWBUTTONID = "chromebrewButton";

    @FXML
    protected void onSourceCodeSelect() {
        DirectoryChooser fc = new DirectoryChooser();
        sourceDirectory = fc.showDialog(null);
        if (sourceDirectory == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("No directory was selected! Did you select the correct directory?");
            a.show();
            return;
        }
        if (!sourceDirectory.isDirectory()) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("You did not select a directory! Did you select the correct item?");
            a.show();
            return;
        }
        changeButtonText(SOURCEBUTTONID, "Selected directory: " + sourceDirectory.getPath());
    }

    @FXML
    protected void onShimSelect() {
        shimFile = getFile(new Pair<>("Shim file", "*.bin"), "No file was selected! Did you select the correct file?");
        if (shimFile == null) {
            return;
        }
        changeButtonText(SHIMBUTTONID, "Selected file: " + shimFile.getPath());
    }

    @FXML
    protected void onChromebrewSelect() {
        chromebrewFile = getFile(new Pair<>("Chromebrew Archive", "*.tar.gz"), "No file was selected! Did you select the correct file?");
        if (chromebrewFile == null) {
            return;
        }
        changeButtonText(CHROMEBREWBUTTONID, "Selected file: " + chromebrewFile.getPath());
    }

    @FXML
    protected void viewTOC() {
        viewedTOC = true;
        Stage popupwindow = new Stage();
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("Catalyst - Terms and Conditions");

        Label label1= new Label("Catalyst - Terms and Conditions");
        Label label2= new Label("(help i dont know how to write these)");
        label2.setOpacity(0.3);
        Label label3= new Label("By using this software (Catalyst), you understand that I am not the creator of sh1mmer, which is a creation from Mercury Workshop. I am not able to provide any support reguarding the technical details of sh1mmer. I strongly encourage you to read up on both writeups on how this exploit works. https://coolelectronics.me/blog/breaking-cros-1, https://coolelectronics.me/blog/breaking-cros-2");
        label3.setWrapText(true);
        label3.setTextAlignment(TextAlignment.CENTER);
        Label label4= new Label("Instructions on how to get the files required for this software to run are available on https://sh1mmer.me, you will have to produce the files yourself. No support will be given on how to attain these files. Skid.");
        label4.setWrapText(true);
        label4.setTextAlignment(TextAlignment.CENTER);
        Label label5= new Label("I reserve the right to remove this software at my discretion. If any Mercury Workshop members are unhappy about this software, please contact me at DiffuseHyperion#0014 and provide good reasoning.");
        label5.setWrapText(true);
        label5.setTextAlignment(TextAlignment.CENTER);
        Label label6= new Label("By utilizing sh1mmer, you understand that neither me (DiffuseHyperion) nor Mercury Workshop are liable for whatever you do with your chromebook, nor the damages that you cause. Please use this exploit responsibly.");
        label6.setWrapText(true);
        label6.setTextAlignment(TextAlignment.CENTER);

        Button button1= new Button("Close this popup");
        button1.setOnAction(e -> popupwindow.close());

        VBox layout= new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(label1, label2, label3, label4, label5, label6, button1);
        layout.setAlignment(Pos.CENTER);

        Scene scene1= new Scene(layout, 600, 400);
        popupwindow.setScene(scene1);
        popupwindow.showAndWait();
    }

    @FXML
    protected void onBuildSelect() {
        if (sourceDirectory == null || shimFile == null || chromebrewFile == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("You haven't selected one of the options yet!");
            a.show();
        }
        if (!viewedTOC) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("You haven't viewed the terms and conditions yet!");
            a.show();
        }

        build();
    }

    private void build() {
        // one day i will fully turn the shell script into java, today is not that day though
        // if any mcw people see this, prs appreciated :)

    }

    private @Nullable File getFile(Pair<String, String> filter, String error) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(filter.getKey(), filter.getValue()));
        File file = fc.showOpenDialog(null);
        if (file == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText(error);
            a.show();
        }
        return file;
    }

    private void changeButtonText(String id, String text) {
        Button button = (Button) scene.lookup("#" + id);
        button.setText(text);
    }
}