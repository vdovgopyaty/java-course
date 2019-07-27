package ru.vdovgopyaty.cloud.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.vdovgopyaty.cloud.common.FileInfo;
import ru.vdovgopyaty.cloud.common.messages.*;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class Controller implements Initializable {

    private Stage stage;
    private final String STORAGE_FOLDER = "clientStorage";
    private String focusFileName = "";
    private ArrayList<FileInfo> remoteFileList = new ArrayList<>();

    public TableView<FileInfo> fileTable;
    public TableColumn<FileInfo, String> fileNameColumn;
    public TableColumn<FileInfo, Long> fileSizeColumn;
    public TableColumn<FileInfo, Boolean> localFlagColumn;
    public TableColumn<FileInfo, Boolean> remoteFlagColumn;
    public Button downloadFile;
    public Button deleteLocalFile;
    public Button deleteFile;

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
                    } else if (msg instanceof FilesInfoMessage) {
                        FilesInfoMessage filesInfoMessage = (FilesInfoMessage) msg;
                        refreshFileList(filesInfoMessage);
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

        downloadFile.setDisable(true);
        deleteLocalFile.setDisable(true);
        deleteFile.setDisable(true);

        fileTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            downloadFile.setDisable(obs.getValue().isLocal());
            deleteLocalFile.setDisable(!obs.getValue().isLocal());
            deleteFile.setDisable(false);

            if (obs.getValue().getName() != null) {
                focusFileName = obs.getValue().getName();
            } else {
                focusFileName = "";
            }
        });

        Platform.runLater(() -> fileTable.requestFocus());

        Network.send(new FilesInfoRequest());
    }

    public void setStageAndSetupListeners(Stage stage) {
        this.stage = stage;
    }

    public static void render(Runnable r) {
        if (Platform.isFxApplicationThread()) {
            r.run();
        } else {
            Platform.runLater(r);
        }
    }

    public void refreshFileList(FilesInfoMessage remoteFileList) {
        render(() -> {
            this.remoteFileList.clear();
            this.remoteFileList.addAll(remoteFileList.getFiles());
            refreshLocalFileList();
        });
    }

    private void refreshLocalFileList() {
        ArrayList<FileInfo> localFiles = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(STORAGE_FOLDER))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            localFiles.add(new FileInfo(file));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<FileInfo> mergedFileList = mergeFileLists(remoteFileList, localFiles);

        fileTable.getItems().clear();
        fileTable.getItems().addAll(mergedFileList);
    }

    private ArrayList<FileInfo> mergeFileLists(ArrayList<FileInfo> remoteFiles, ArrayList<FileInfo> localFiles) {
        remoteFiles.forEach(remoteFile -> {
            localFiles.forEach(localFile -> {
                if (remoteFile.getName().equals(localFile.getName())) {
                    remoteFile.setLocal(true);
                }
            });
        });
        return remoteFiles;
    }

    private void deleteLocalFile(String fileName) {
        try {
            Files.delete(Paths.get(STORAGE_FOLDER + "/" + fileName));
            refreshLocalFileList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        Path filePath = fileChooser.showOpenDialog(stage).toPath();
        Path newFilePath = Paths.get(STORAGE_FOLDER + "/" + filePath.getFileName());
        try {
            Files.copy(filePath, newFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Network.send(new FileMessage(newFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downloadFile(ActionEvent actionEvent) {
        if (!focusFileName.equals("")) {
            Network.send(new FileRequest(focusFileName));
        }
    }

    public void deleteLocalFile(ActionEvent actionEvent) {
        if (!focusFileName.equals("")) {
            deleteLocalFile(focusFileName);
        }
    }

    public void deleteFile(ActionEvent actionEvent) {
        if (!focusFileName.equals("")) {
            Network.send(new DeleteFileMessage(focusFileName));
            deleteLocalFile(focusFileName);
        }
    }
}
