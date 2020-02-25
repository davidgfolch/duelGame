package me.guillaume.duel;

import me.guillaume.duel.equip.Equip;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player<T> {

    private final Class<T> claz;

    private int hitPointsInitial;
    private int hitPoints;

    private List<Equip> equip = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public Player(int hitPoints, String weapon) {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        claz = (Class<T>) type.getActualTypeArguments()[0];
        this.hitPoints = hitPoints;
        this.hitPointsInitial = hitPoints;
        this.equip(weapon);
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
        final int damage = equip.stream().mapToInt(Equip::totalDamage).sum();
        final int damagePercentageAdded = equip.stream().mapToInt(e -> e.totalDamagePercentage(damage,this)).sum();
        final int enemyDefenseVariation = enemy.equip.stream().mapToInt(Equip::damageVariationReceived).sum();
        return (damagePercentageAdded==0?damage:damagePercentageAdded) + enemyDefenseVariation;
    }

    private boolean isValidBlow(Player<?> enemy) {
        return enemy.equip.stream().allMatch(e->e.isValidBlow(this));
    }

    private boolean canAttack() {
        List<Equip> equipsWithCadence = equip.stream().filter(e -> e.attacksRestsCadence() != null).collect(Collectors.toList());
        if (equipsWithCadence.isEmpty())
            return true;
        return equipsWithCadence.stream().allMatch(Equip::canAttack);
    }

    public int hitPointsInitial() {
        return hitPointsInitial;
    }

    public int hitPoints() {
        return hitPoints;
    }

    public void hitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public T equip(String equip) {
        this.equip.add(Equip.equip(equip));
        checkEquipAndHands();
        return claz.cast(this);
    }

    private void checkEquipAndHands() {
        List<Equip> equipsUsingHands = equip.stream().filter(e -> e.handsUsed() > 0).collect(Collectors.toList());
        if (!equipsUsingHands.isEmpty()) {
            int hands = 2;
            while (equipsUsingHands.stream().mapToInt(Equip::handsUsed).sum()> hands) {
                equip.remove(equipsUsingHands.get(0));
                equipsUsingHands.remove(0);
            }
        }
    }

    public List<Equip> equip() {
        return equip;
    }

}
