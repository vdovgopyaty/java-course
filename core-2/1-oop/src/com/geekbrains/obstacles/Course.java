package com.geekbrains.obstacles;

import com.geekbrains.participants.Participant;
import com.geekbrains.participants.Team;

public class Course {
    private Obstacle[] obstacles;

    public Course(Obstacle[] obstacles) {
        this.obstacles = obstacles;
    }

    public void doIt(Team team) {
        for (Participant p : team.getMembers()) {
            for (Obstacle o : obstacles) {
                o.doIt(p);
                if (!p.isActive()) {
                    break;
                }
            }
        }
    }

}
