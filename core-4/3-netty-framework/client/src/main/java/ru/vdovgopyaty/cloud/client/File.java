package ru.vdovgopyaty.cloud.client;

public class File {
    private String name;
    private String size;
    private String local;
    private String remote;

    public File(String name, long size, boolean local, boolean remote) {
        this.name = name;
        this.size = size + " B";
        this.local = local ? "+" : "-";
        this.remote = remote ? "+" : "-";
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String isLocal() {
        return local;
    }

    public String isRemote() {
        return remote;
    }
}
