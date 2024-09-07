module edu.school21.smartcalc {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.school21.smartcalc to javafx.fxml;
    exports edu.school21.smartcalc;
    exports edu.school21.smartcalc.view;
    opens edu.school21.smartcalc.view to javafx.fxml;
}