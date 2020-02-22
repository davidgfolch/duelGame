package me.guillaume.duel;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class Player<T> {

    public static final int ATTACK = 1;
    public static final int REST = 0;

    private final Class<T> claz;

    private int hitPoints;
    private String weapon;
    private int hands;
    private int damage;
    private int attacksRestsPosition = 0;
    private int[] attacksRestsCadence;

    private List<Equip> equip = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public Player(int hitPoints, String weapon, int hands, int damage) {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.claz = (Class<T>) type.getActualTypeArguments()[0];
        this.hitPoints = hitPoints;
        this.weapon = weapon;
        this.hands = hands;
        this.damage = damage;
    }

    public Player(int hitPoints, String weapon, int hands, int damage, int[] attacksRestsCadence) {
        this(hitPoints, weapon, hands, damage);
        this.attacksRestsCadence = attacksRestsCadence;
    }

    public void engage(Player<?> enemy) {
        blow(enemy);
    }

    private void blow(Player<?> enemy) {
        if (canAttack() && isValidBlow(enemy)) {
            enemy.hitPoints(Math.max(enemy.hitPoints - getBlowDamage(enemy), 0));
        }
        if (enemy.hitPoints > 0) {
            enemy.blow(this);
        }
    }

    private int getBlowDamage(Player<?> enemy) {
        int damageVariation = equip.stream().mapToInt(Equip::damageVariationDelivered).sum();
        int enemyDefenseVariation = enemy.equip.stream().mapToInt(Equip::damageVariationReceived).sum();
        return damage + damageVariation + enemyDefenseVariation; //todo what if resultDamage is less than 0 ?? (not specified in test)
    }

    private boolean isValidBlow(Player<?> enemy) {
        return enemy.equip.stream().allMatch(e->e.isValidBlow(this));
    }

    private boolean canAttack() {
        if (attacksRestsCadence == null)
            return true;
        if (attacksRestsPosition>attacksRestsCadence.length-1) attacksRestsPosition=0;
        return attacksRestsCadence[attacksRestsPosition++] == ATTACK;
    }

    public int hitPoints() {
        return hitPoints;
    }

    public void hitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public T equip(String equip) {
        this.equip.add(new Equip(equip));
        return claz.cast(this);
    }

    public List<Equip> equip() {
        return equip;
    }

    public String weapon() {
        return weapon;
    }

}
