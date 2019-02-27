package com.geekbrains.obstacles;

import com.geekbrains.participants.Animal;
import com.geekbrains.participants.Participant;

public class Water extends Obstacle {
    private int length;

    public Water(int length) {
        this.length = length;
    }

    @Override
    public void doIt(Participant p) {
        p.swim(length);
    }
}
