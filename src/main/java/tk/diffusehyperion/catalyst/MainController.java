package tk.diffusehyperion.catalyst;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

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
        try {
            Parent pane = new FXMLLoader(MainApplication.class.getResource("toc.fxml")).load();
            scene.setRoot(pane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

        // linux version
        ProcessBuilder pb = new ProcessBuilder("sudo", "./wax.sh", shimFile.getAbsolutePath());
        pb.directory(Path.of(sourceDirectory.getAbsolutePath(), "/wax").toFile());
        try {
            Process p = pb.start();
            p.onExit().thenRun(() -> {
                if (p.exitValue() != 0) {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Something went wrong while building the image file!");
                    a.show();
                } else {
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setContentText("sh1mmer file successfully built! Flash the original shim file onto your usb now to use sh1mmer.");
                    a.show();
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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