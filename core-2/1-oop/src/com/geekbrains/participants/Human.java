package com.geekbrains.participants;

public class Human implements Participant {
    String name;

    int maxRunDistance;
    int maxJumpHeight;
    int maxSwimDistance;

    boolean onDistance;

    public String getName() {
        return name;
    }

    @Override
    public boolean isActive() {
        return onDistance;
    }

    public Human(String name) {
        this.name = name;
        this.maxRunDistance = 10000;
        this.maxJumpHeight = 50;
        this.maxSwimDistance = 2000;
        this.onDistance = true;
    }

    @Override
    public void run(int distance) {
        if (distance <= maxRunDistance) {
            System.out.println(name + " справился с кроссом");
        } else {
            System.out.println(name + " не справился с кроссом");
            onDistance = false;
        }
    }

    @Override
    public void jump(int height) {
        if (height <= maxJumpHeight) {
            System.out.println(name + " перепрыгнул препятствие");
        } else {
            System.out.println(name + " не смог перепрыгнуть препятствие");
            onDistance = false;
        }
    }

    @Override
    public void swim(int distance) {
        if (distance <= maxSwimDistance) {
            System.out.println(name + " переплыл препятствие");
        } else {
            System.out.println(name + " не смог переплыть препятствие");
            onDistance = false;
        }
    }
}
