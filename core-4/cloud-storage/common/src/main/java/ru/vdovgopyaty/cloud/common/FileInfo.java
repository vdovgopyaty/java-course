package ru.vdovgopyaty.cloud.common;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileInfo implements Serializable {
    private String name;
    private long size;
    private boolean local;
    private boolean remote;

    public FileInfo(String name, long size, boolean local, boolean remote) {
        this.name = name;
        this.size = size;
        this.local = local;
        this.remote = remote;
    }

    public FileInfo(String name, long size) {
        this(name, size, false, true);
    }

    public FileInfo(Path path) throws IOException {
        this(path.getFileName().toString(), Files.size(path));
    }

    public String getName() {
        return name;
    }

    public String getStringSize() {
        return size + " B";
    }

    public String getLocal() {
        return local ? "+" : "-";
    }

    public String getRemote() {
        return remote ? "+" : "-";
    }

    public long getSize() {
        return size;
    }

    public void setLocal(boolean value) {
        this.local = value;
    }

    public boolean isLocal() {
        return local;
    }

    public boolean isRemote() {
        return remote;
    }
}
