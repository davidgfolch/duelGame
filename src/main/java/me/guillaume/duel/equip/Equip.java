package me.guillaume.duel.equip;

import me.guillaume.duel.Player;

public class Equip {

    public static final int ATTACK = 1;
    public static final int REST = 0;

    String name;
    int damage;
    boolean equipEnabled;
    Integer blowsLeft = null;
    String weaponThatDestroysMe;  //weapon that destroys equip after n blows (blowsLeft)
    int damageVariationReceived = 0;
    int damageVariationDelivered = 0;
    int damageVariationDeliveredMultiplier = 0;
    int handsUsed;
    int hitsActivationPercentage = 0;

    int attacksRestsPosition = 0;
    int[] attacksRestsCadence;

    Equip(String name) {
        this.name=name;
    }

    public static Equip equip(String name) {
        switch (name) {
            case "axe": return new Axe();
            case "sword": return new Sword();
            case "greatSword": return new GreatSword();
            case "buckler": return new Buckler();
            case "armor": return new Armor();
            case "Vicious": return new Vicious();
            case "Veteran": return new Veteran();
            default:
                throw new RuntimeException("equip not implemented: " + name);
        }
    }

    public boolean isValidBlow(Player<?> enemy) {
        return true;
    }

    public int damageVariationReceived() {
        return damageVariationReceived;
    }

    public int handsUsed() {
        return handsUsed;
    }

    public int totalDamage() {
        if (damage != 0 || damageVariationDelivered != 0) { //is weapon (or armor)
            if (blowsLeft == null || blowsLeft > 0) {
                if (blowsLeft != null) blowsLeft--;
                return damage + damageVariationDelivered;
            }
        }
        return damage;
    }

    public int totalDamagePercentage(int damage, Player<?> player) {
        if (damageVariationDeliveredMultiplier == 0 ||
                (hitsActivationPercentage > 0 &&
                        player.hitPoints() > 0 &&  //avoid 0 division, should not happen, but just in case
                        hitsActivationPercentage < player.hitPoints() * 100 / player.hitPointsInitial()))
            return 0;
        return damage * damageVariationDeliveredMultiplier;
    }

    public int[] attacksRestsCadence() {
        return attacksRestsCadence;
    }

    public boolean canAttack() {
        if (attacksRestsPosition > attacksRestsCadence.length - 1) attacksRestsPosition = 0;
        return attacksRestsCadence[attacksRestsPosition++] == ATTACK;
    }
}
