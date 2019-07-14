package ru.vdovgopyaty.cloud.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class Controller {
    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    public void sendMessage() {
        textArea.appendText(textField.getText());
        textArea.appendText("\n");
        textField.clear();
        textField.requestFocus();
    }
}
