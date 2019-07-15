package ru.vdovgopyaty.cloud.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ru.vdovgopyaty.cloud.common.FileMessage;
import ru.vdovgopyaty.cloud.common.FileRequest;
import ru.vdovgopyaty.cloud.common.Message;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private final String STORAGE_FOLDER = "clientStorage";

    @FXML
    public VBox rootNode;

    @FXML
    public TextField fileNameInput;

    @FXML
    public ListView fileList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Network.start();
        Thread messageListeningThread = new Thread(() -> {
            try {
                while (true) {
                    Message msg = Network.messageListener();
                    if (msg instanceof FileMessage) {
                        FileMessage fileMessage = (FileMessage) msg;
                        Files.write(Paths.get(STORAGE_FOLDER + "/" + fileMessage.getName()), fileMessage.getData(),
                                StandardOpenOption.CREATE);
                        refreshLocalFileList();
                    }
                }
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            } finally {
                Network.stop();
            }
        });
        messageListeningThread.setDaemon(true);
        messageListeningThread.start();
        refreshLocalFileList();
    }

    public void downloadFile(ActionEvent actionEvent) {
        if (fileNameInput.getLength() > 0) {
            Network.send(new FileRequest(fileNameInput.getText()));
            fileNameInput.clear();
        }
    }

    public void refreshLocalFileList() {
        render(() -> {
            try {
                fileList.getItems().clear();
                Files.list(Paths.get(STORAGE_FOLDER))
                        .map(p -> p.getFileName().toString())
                        .forEach(o -> fileList.getItems().add(o));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void render(Runnable r) {
        if (Platform.isFxApplicationThread()) {
            r.run();
        } else {
            Platform.runLater(r);
        }
    }
}
