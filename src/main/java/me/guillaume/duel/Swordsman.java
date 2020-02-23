package me.guillaume.duel;

public class Swordsman extends Player<Swordsman> {

    public Swordsman() {
        super(100, "sword");
    }

    public Swordsman(String skill) {
        this();
        super.equip(skill);
    }
}
