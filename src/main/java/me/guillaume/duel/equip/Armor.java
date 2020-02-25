package me.guillaume.duel.equip;

/**
 * reduces all received damages by 3 & reduces delivered damages by one
 */
public class Armor extends Equip {

    public Armor() {
        super("armor");
        damageVariationReceived = -3;
        damageVariationDelivered = -1;
        handsUsed = 0;
        damage = 0;
    }
}
