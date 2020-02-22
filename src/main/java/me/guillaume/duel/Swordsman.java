package me.guillaume.duel;

/**
 * A Swordsman has 100 hit points and use a 1 hand sword that does 5 dmg
 */
public class Swordsman extends Player {

    public Swordsman() {
        super(100, "sword",1,5);
    }
}
