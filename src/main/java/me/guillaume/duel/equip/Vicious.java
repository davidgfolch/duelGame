package me.guillaume.duel.equip;

/**
 * a vicious Player, put poison on his weapon, this adds 20 damages on 2 first blows
 */
public class Vicious extends Equip {

    public Vicious() {
        super("Vicious");
        blowsLeft = 2;
        damageVariationDelivered = 20;
        handsUsed = 0;
    }
}
