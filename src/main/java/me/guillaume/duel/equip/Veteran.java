package me.guillaume.duel.equip;

/**
 * a veteran goes Berserk once his hit points are under 30% of his initial total
 * once Berserk, he doubles his damages.
 */
public class Veteran extends Equip {

    public Veteran() {
        super("Veteran");
        hitsActivationPercentage = 30;
        damageVariationDeliveredMultiplier = 2;
        handsUsed = 0;
    }

}
