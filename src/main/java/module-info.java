module tk.diffusehyperion.catalyst {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;


    opens tk.diffusehyperion.catalyst to javafx.fxml;
    exports tk.diffusehyperion.catalyst;
}