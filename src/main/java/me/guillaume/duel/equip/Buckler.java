package me.guillaume.duel.equip;

import me.guillaume.duel.Player;

/**
 *  cancel all the damages of a blow, one time out of two
 *  is destroyed after blocking 3 blow from an axe
 */
public class Buckler extends Equip {

    public Buckler() {
        super("buckler");
        equipEnabled = true;
        blowsLeft = 3;
        weaponThatDestroysMe = "axe";
        handsUsed = 1;
        damage = 0;
    }

    @Override
    public boolean isValidBlow(Player<?> enemy) {
        if (equipEnabled && blowsLeft > 0) {
            equipEnabled = false;
            if (enemy.equip().stream().anyMatch(e -> e.name.equals(weaponThatDestroysMe)))
                blowsLeft--;
            return false;
        }
        equipEnabled = true;
        return true;
    }
}
