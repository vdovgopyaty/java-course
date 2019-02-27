package com.geekbrains.participants;

public interface Participant {
    boolean isActive();
    void run(int distance);
    void swim(int distance);
    void jump(int height);
    String getName();
}
