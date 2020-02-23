package me.guillaume.duel;

public class Highlander extends Player<Highlander> {

    public Highlander() {
        super(150, "greatSword");
    }

    public Highlander(String skill) {
        this();
        super.equip(skill);
    }
}
