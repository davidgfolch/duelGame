package me.guillaume.duel;

/**
 * Equips
 * ------
 * <p>
 * BUCKLER:
 * - cancel all the damages of a blow, one time out of two
 * - is destroyed after blocking 3 blow from an axe
 * <p>
 * ARMOR:
 * - reduces all received damages by 3 & reduces delivered damages by one
 */
public class Equip {

    private String name;
    private boolean equipEnabled;
    private int blockingBlowsLeft;
    private String weapon;  //weapon that destroys equip after n blows (blockingBlowsLeft)
    private int damageVariationReceived = 0;
    private int damageVariationDelivered = 0;

    public Equip(String name) {
        this.name = name;
        switch (name) {
            case "buckler":
                equipEnabled = true;
                blockingBlowsLeft = 3;
                weapon = "axe";
                break;
            case "armor":
                damageVariationReceived = -3;
                damageVariationDelivered = -1;
                break;
            default:
                throw new RuntimeException("equip not implemented");
        }
    }

    public boolean isValidBlow(Player<?> enemy) {
        if ("buckler".equals(name)) {
            if (equipEnabled && blockingBlowsLeft > 0) {
                equipEnabled = false;
                if (enemy.equip() != null && weapon.equals(enemy.weapon()))
                    blockingBlowsLeft -= 1;
                return false;
            }
            equipEnabled = true;
        }
        return true;
    }

    public int damageVariationDelivered() {
        return damageVariationDelivered;
    }

    public int damageVariationReceived() {
        return damageVariationReceived;
    }
}
