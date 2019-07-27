package ru.vdovgopyaty.cloud.common.messages;

public class FileRequest extends Message {
    private String name;

    public String getName() {
        return name;
    }

    public FileRequest(String name) {
        this.name = name;
    }
}
