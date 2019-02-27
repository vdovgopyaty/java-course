package com.geekbrains.participants;

public class Team {
    private final int MEMBERS_NUM = 4;

    private String name;
    private Participant[] members;
    private boolean active;
    private int membersNumWhoDidIt;

    public Team(String name, Participant[] members) {
        this.name = name;
        this.active = false;
        this.membersNumWhoDidIt = 0;
        if (members.length == MEMBERS_NUM) {
            this.members = members;
        } else {
            System.out.println("В команде должно быть " + MEMBERS_NUM + " участника");
        }
    }

    public void showResults() {
        System.out.println("---------- РЕЗУЛЬТАТЫ КОМАНДЫ " + this.name.toUpperCase() + " ----------");
        for (Participant member: members) {
            if (member.isActive()) {
                System.out.println("Участник " + member.getName() + " прошел полосу препятствий");
            } else {
                System.out.println("Участник " + member.getName() + " не прошел полосу препятствий");
            }
        }
    }

    public void showResults(boolean winnersOnly) {
        if (isActive()) {
            System.out.println("Следующие участники команды " + this.name + " прошли полосу предятствий:");
            for (Participant member: members) {
                if (member.isActive()) {
                    System.out.println(member.getName());
                }
            }
        } else {
            System.out.println("Ни один участник команды " + this.name + " не прошел полосу препятствий");
        }
    }

    public boolean isActive() {
        if (!this.active) {
            if (this.membersNumWhoDidIt == MEMBERS_NUM) {
                for (Participant member: members) {
                    if (member.isActive()) {
                        this.active = true;
                        break;
                    }
                }
            } else {
                return true;
            }
        }
        return active;
    }

    public String getName() {
        return name;
    }

    public Participant[] getMembers() {
        return members;
    }
}
