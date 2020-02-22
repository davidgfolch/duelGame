package me.guillaume.duel;

import java.lang.reflect.ParameterizedType;

public class Player<T> {

    private final Class<T> claz;

    private int hitPoints;
    private String weapon;
    private int hands;
    private int damage;

    private Equip equip;

    @SuppressWarnings("unchecked")
    public Player(int hitPoints, String weapon, int hands, int damage) {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.claz = (Class<T>) type.getActualTypeArguments()[0];
        this.hitPoints = hitPoints;
        this.weapon = weapon;
        this.hands = hands;
        this.damage = damage;
    }

    public void engage(Player<?> enemy) {
        blow(enemy);
    }

    private void blow(Player<?> enemy) {
        if (enemy.equip==null || enemy.equip.isValidBlow(this))
            enemy.hitPoints(Math.max(enemy.hitPoints - damage, 0));
        if (enemy.hitPoints>0) enemy.blow(this);
    }

    public int hitPoints() {
        return hitPoints;
    }

    public void hitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public T equip(String equip) {
        this.equip=new Equip(equip);
        return claz.cast(this);
    }

    public Equip equip() {
        return equip;
    }

    public String weapon() {
        return weapon;
    }
}
