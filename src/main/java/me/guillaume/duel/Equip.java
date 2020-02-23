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

    public static final int ATTACK = 1;
    public static final int REST = 0;

    private final String name;
    private int damage;
    private boolean equipEnabled;
    private Integer blowsLeft = null;
    private String weaponThatDestroysMe;  //weapon that destroys equip after n blows (blowsLeft)
    private int damageVariationReceived = 0;
    private int damageVariationDelivered = 0;
    private int damageVariationDeliveredMultiplier = 0;
    private final int handsUsed;
    private int hitsActivationPercentage = 0;

    private int attacksRestsPosition = 0;
    private int[] attacksRestsCadence;

    public Equip(String name) {
        this.name = name;
        switch (name) {
            case "axe": //1 hand axe that does 6 dmg
                handsUsed = 1;
                damage = 6;
                break;
            case "sword": //one hand sword does 5 dmg
                handsUsed = 1;
                damage = 5;
                break;
            case "greatSword": //a Great Sword is a two handed sword that deliver 12 damages, but can attack only 2 every 3 (attack, attack, rest)
                handsUsed = 2;
                damage = 12;
                attacksRestsCadence = new int[]{ATTACK, ATTACK, REST};
                break;
            case "buckler":
                equipEnabled = true;
                blowsLeft = 3;
                weaponThatDestroysMe = "axe";
                handsUsed = 1;
                damage = 0;
                break;
            case "armor":
                damageVariationReceived = -3;
                damageVariationDelivered = -1;
                handsUsed = 0;
                damage = 0;
                break;
            case "Vicious": //a vicious Player, put poison on his weapon, this adds 20 damages on 2 first blows
                blowsLeft = 2;
                damageVariationDelivered = 20;
                handsUsed = 0;
                break;
            case "Veteran": // a veteran goes Berserk once his hit points are under 30% of his initial total
                //once Berserk, he doubles his damages.
                hitsActivationPercentage = 30;
                damageVariationDeliveredMultiplier = 2;
                handsUsed = 0;
                break;
            default:
                throw new RuntimeException("equip not implemented: " + name);
        }
    }

    public boolean isValidBlow(Player<?> enemy) {
        if ("buckler".equals(name)) {
            if (equipEnabled && blowsLeft > 0) {
                equipEnabled = false;
                if (enemy.equip().stream().anyMatch(e -> e.name.equals(weaponThatDestroysMe)))
                    blowsLeft--;
                System.out.println("Not valid blow! (Buckler)");
                return false;
            }
            equipEnabled = true;
        }
        return true;
    }

    public int damageVariationReceived() {
        return damageVariationReceived;
    }

    public int handsUsed() {
        return handsUsed;
    }

    public String getName() {
        return name;
    }

    public int totalDamage(Player<?> player) {
        if (damage != 0 || damageVariationDelivered != 0) { //is weapon (or armor)
            if (blowsLeft == null || blowsLeft > 0) {
                if (blowsLeft != null) blowsLeft--;
                System.out.println(this.getName() + " damage=" + damage + ", blowsLeft=" + blowsLeft + ", damageVariationDelivered=" + damageVariationDelivered);
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
