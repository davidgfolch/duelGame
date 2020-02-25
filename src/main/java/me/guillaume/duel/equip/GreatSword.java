package me.guillaume.duel.equip;

/**
 * a Great Sword is a two handed sword that deliver 12 damages, but can attack only 2 every 3 (attack, attack, rest)
 */
public class GreatSword extends Equip {

    public GreatSword() {
        super("greatSword");
        handsUsed = 2;
        damage = 12;
        attacksRestsCadence = new int[]{ATTACK, ATTACK, REST};
    }

}
