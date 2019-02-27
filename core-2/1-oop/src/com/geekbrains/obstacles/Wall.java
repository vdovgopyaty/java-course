package com.geekbrains.obstacles;

import com.geekbrains.participants.Animal;
import com.geekbrains.participants.Participant;

public class Wall extends  Obstacle {
    private int height;

    public Wall(int height) {
        this.height = height;
    }

    @Override
    public void doIt(Participant p) {
        p.jump(height);
    }
}
