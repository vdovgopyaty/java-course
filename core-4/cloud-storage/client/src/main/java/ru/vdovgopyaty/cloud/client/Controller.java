package ru.vdovgopyaty.cloud.client;

import ru.vdovgopyaty.cloud.common.FileMessage;
import ru.vdovgopyaty.cloud.common.FileRequest;
import ru.vdovgopyaty.cloud.common.Message;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private final String STORAGE_FOLDER = "clientStorage";

    public TextField fileNameInput;
    public TableView<File> fileTable;
    public TableColumn<File, String> fileNameColumn;
    public TableColumn<File, Long> fileSizeColumn;
    public TableColumn<File, Boolean> localFlagColumn;
    public TableColumn<File, Boolean> remoteFlagColumn;
    public TextField fileNameUploadInput;

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

        fileNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        fileSizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
        localFlagColumn.setCellValueFactory(new PropertyValueFactory<>("local"));
        remoteFlagColumn.setCellValueFactory(new PropertyValueFactory<>("remote"));
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
                fileTable.getItems().clear();
                Files.list(Paths.get(STORAGE_FOLDER))
                        .map(path -> {
                            long size = 0;
                            try {
                                size = Files.size(path);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return new File(path.getFileName().toString(), size, true, false);
                        }) // TODO
                        .forEach(file -> fileTable.getItems().add(file));
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

    public void uploadFile(ActionEvent actionEvent) {
        if (fileNameUploadInput.getLength() > 0) {
            Path path = Paths.get(STORAGE_FOLDER + "/" + fileNameUploadInput.getText());

            try {
                Network.send(new FileMessage(path));
            } catch (IOException e) {
                e.printStackTrace();
            }

            fileNameUploadInput.clear();
        }
    }
}
