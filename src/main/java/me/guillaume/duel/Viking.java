package me.guillaume.duel;

/**
 * A Viking has 120 hit points and use a 1 hand axe that does 6 dmg
 */
public class Viking extends Player<Viking> {
    public Viking() {
        super(120, "axe",1,6);
    }
}
