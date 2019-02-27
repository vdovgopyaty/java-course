package com.geekbrains.obstacles;

import com.geekbrains.participants.Animal;
import com.geekbrains.participants.Participant;

public class Cross extends Obstacle {
    private int length;

    public Cross(int length) {
        this.length = length;
    }

    @Override
    public void doIt(Participant p) {
        p.run(length);
    }
}
