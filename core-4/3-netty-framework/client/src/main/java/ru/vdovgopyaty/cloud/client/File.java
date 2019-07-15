package ru.vdovgopyaty.cloud.client;

public class File {
    private String name;
    private long size;
    private boolean local;
    private boolean remote;

    public File(String name, long size, boolean local, boolean remote) {
        this.name = name;
        this.size = size;
        this.local = local;
        this.remote = remote;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public boolean isLocal() {
        return local;
    }

    public boolean isRemote() {
        return remote;
    }
}
