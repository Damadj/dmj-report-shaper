package dmj.report.shaper.controller;

import dmj.report.shaper.core.ExcelLogic;
import dmj.report.shaper.model.CRMReport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class ShaperController {

    @FXML
    private Button btnFirstFile;
    @FXML
    private Button btnSecondFile;
    @FXML
    private Button btnMonthFile;
    @FXML
    private Button btnStart;
    @FXML
    private Button btnStart1;
    @FXML
    TextField firstFileText;
    @FXML
    TextField secondFileText;
    @FXML
    TextField monthFileText;
    @FXML
    ChoiceBox<String> months;

    private File firstFile;
    private File secondFile;
    private File monthFile;

    @FXML
    private void handleButtonClicks(ActionEvent mouseEvent) {
        if (mouseEvent.getSource() == btnFirstFile) {
            chooseFirstFile();
        } else if (mouseEvent.getSource() == btnSecondFile) {
            chooseSecondFile();
        } else if (mouseEvent.getSource() == btnMonthFile) {
            chooseMonthFile();
        } else if (mouseEvent.getSource() == btnStart) {
            if (firstFile == null || secondFile == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setHeaderText("Не выбран файл");
                alert.showAndWait();
            } else {
                startShapingReport();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("");
                alert.setHeaderText("Готово");
                alert.showAndWait();
            }
        } else if (mouseEvent.getSource() == btnStart1) {
            if (monthFile == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("WARNING");
                alert.setHeaderText("Не выбран файл");
                alert.showAndWait();
            } else {
                startShapingMonthlyReport();
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("");
                alert.setHeaderText("Готово");
                alert.showAndWait();
            }
        }
    }

    private void chooseFirstFile() {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        firstFile = fileChooser.showOpenDialog(stage);
        firstFileText.setText(firstFile.getName());
    }

    private void chooseSecondFile() {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        secondFile = fileChooser.showOpenDialog(stage);
        secondFileText.setText(secondFile.getName());
    }

    private void chooseMonthFile() {
        FileChooser fileChooser = new FileChooser();
        Stage stage = new Stage();
        monthFile = fileChooser.showOpenDialog(stage);
        monthFileText.setText(monthFile.getName());
    }

    private void startShapingReport() {
        ExcelLogic logic = new ExcelLogic();
        List<CRMReport> reports = logic.parseCompaniesReport(firstFile.getPath());
        logic.shapeFinalReport(secondFile.getPath(), reports);
    }

    private void startShapingMonthlyReport() {
        ExcelLogic logic = new ExcelLogic();

        logic.shapeMonthlyReport(monthFile.getPath(), months.getValue());
    }


}

