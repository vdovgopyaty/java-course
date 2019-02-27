package main.java.im;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller {
    public TextArea imHistory;
    public TextField imMessage;
    public Button imSend;

    public void sendMessage(ActionEvent actionEvent) {
        String text = imMessage.getText();
        if (!text.equals("")) {

            Date date = new Date();
            DateFormat format = new SimpleDateFormat("HH:mm");

            imHistory.appendText(format.format(date) + "\n" + imMessage.getText() + "\n\n");
            imMessage.clear();
        }
        imMessage.requestFocus();
    }
}
