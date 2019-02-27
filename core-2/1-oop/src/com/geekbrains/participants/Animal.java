package com.geekbrains.participants;

public abstract class Animal implements Participant {
    String type;
    String name;

    int maxRunDistance;
    int maxJumpHeight;
    int maxSwimDistance;

    boolean active;

    public String getName() {
        return name;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public Animal(String type, String name, int maxRunDistance, int maxJumpHeight, int maxSwimDistance) {
        this.type = type;
        this.name = name;
        this.maxRunDistance = maxRunDistance;
        this.maxJumpHeight = maxJumpHeight;
        this.maxSwimDistance = maxSwimDistance;
        this.active = true;
    }

    @Override
    public void run(int distance) {
        if (distance <= maxRunDistance) {
            System.out.println(type + " " + name + " справился с кроссом");
        } else {
            System.out.println(type + " " + name + " не справился с кроссом");
            active = false;
        }
    }

    @Override
    public void jump(int height) {
        if (height <= maxJumpHeight) {
            System.out.println(type + " " + name + " перепрыгнул препятствие");
        } else {
            System.out.println(type + " " + name + " не смог перепрыгнуть препятствие");
            active = false;
        }
    }

    @Override
    public void swim(int distance) {
        if (maxSwimDistance == 0) {
            System.out.println(type + " " + name + " не умеет плавать");
            active = false;
            return;
        }
        if (distance <= maxSwimDistance) {
            System.out.println(type + " " + name + " переплыл препятствие");
        } else {
            System.out.println(type + " " + name + " не смог переплыть препятствие");
            active = false;
        }
    }
}
