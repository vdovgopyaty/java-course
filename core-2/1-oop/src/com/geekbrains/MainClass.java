package com.geekbrains;

import com.geekbrains.participants.*;
import com.geekbrains.obstacles.*;

public class MainClass {
    public static void main(String[] args) {
        Obstacle[] obstacles = {new Cross(200), new Water(20), new Wall(2), new Cross(500)};
        Course c = new Course(obstacles);

        Participant[] participants = {new Human("Bob"), new Human("Alice"), new Cat("Barsik"), new Dog("Bobik")};
        Team team = new Team("Team 1", participants);

        c.doIt(team);
        team.showResults();
    }
}
