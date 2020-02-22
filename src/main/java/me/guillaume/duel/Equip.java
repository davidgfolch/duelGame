package me.guillaume.duel;

/**
 * Equips
 * ------
 *
 * buckler:
 * - cancel all the damages of a blow, one time out of two
 * - is destroyed after blocking 3 blow from an axe
 */
public class Equip {

    private String name;
    private boolean equipEnabled;
    private int blockingBlowsLeft;
    private String weapon;  //weapon that destroys equip after n blows (blockingBlowsLeft)

    public Equip(String name) {
        this.name=name;
        switch (name) {
            case "buckler":
                equipEnabled=true;
                blockingBlowsLeft =3;
                weapon="axe";
                break;
            default: throw new RuntimeException("equip not implemented");
        }
    }

    public <T> boolean isValidBlow(Player<?> attacker) {
        switch (name) {
            case "buckler":
                if (equipEnabled && blockingBlowsLeft >0) {
                    equipEnabled=false;
                    if (attacker.equip()!=null && weapon.equals(attacker.weapon()))
                        blockingBlowsLeft-=1;
                    return false;
                }
                equipEnabled=true;
                return true;
            default: throw new RuntimeException("equip not implemented");
        }
    }
}
