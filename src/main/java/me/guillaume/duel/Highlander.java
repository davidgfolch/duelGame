package me.guillaume.duel;

/**
 * an Highlander as 150 hit points and fight with a Great Sword
 * a Great Sword is a two handed sword that deliver 12 damages, but can attack only 2 every 3 (attack, attack, rest)
 */
public class Highlander extends Player<Swordsman> {

    public Highlander() {
        super(150, "greatSword", 2, 12, new int[]{ATTACK, ATTACK, REST});
    }
}
