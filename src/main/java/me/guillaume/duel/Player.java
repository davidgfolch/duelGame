package me.guillaume.duel;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Player<T> {

    private final Class<T> claz;

    private int hitPointsInitial;
    private int hitPoints;
    private int hands=2;

    private List<Equip> equip = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public Player(int hitPoints, String weapon) {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        claz = (Class<T>) type.getActualTypeArguments()[0];
        System.out.println("Creating player: "+ claz.getSimpleName());
        this.hitPoints = hitPoints;
        this.hitPointsInitial = hitPoints;
        this.equip(weapon);
    }

    public void engage(Player<?> enemy) {
        blow(enemy);
    }

    private void blow(Player<?> enemy) {
        System.out.println(claz.getSimpleName()+" blows "+enemy.claz.getSimpleName());
        if (canAttack() && isValidBlow(enemy)) {
            enemy.hitPoints(Math.max(enemy.hitPoints - getBlowDamage(enemy), 0));
        }
        if (enemy.hitPoints > 0) {
            System.out.println(enemy.claz.getSimpleName()+" "+enemy.hitPoints+"/"+enemy.hitPointsInitial+" hitPoints");
            enemy.blow(this);
        } else System.out.println(enemy.claz.getSimpleName()+" is death!");
    }

    private int getBlowDamage(Player<?> enemy) {
        final int damage = equip.stream().mapToInt(e -> e.totalDamage(this)).sum();
        final int damagePercentageAdded = equip.stream().mapToInt(e -> e.totalDamagePercentage(damage,this)).sum();
        final int enemyDefenseVariation = enemy.equip.stream().mapToInt(Equip::damageVariationReceived).sum();
        final int totalDamage = (damagePercentageAdded==0?damage:damagePercentageAdded) + enemyDefenseVariation; //todo what if totalDamage is less than 0 ?? (not specified in test)
        System.out.println("Total damage: "+ totalDamage + " (Damage: "+damage+" Damage(with%): "+damagePercentageAdded+" Enemy defense: "+enemyDefenseVariation+")");
        return totalDamage;
    }

    private boolean isValidBlow(Player<?> enemy) {
        return enemy.equip.stream().allMatch(e->e.isValidBlow(this));
    }

    private boolean canAttack() {
        List<Equip> equipsWithCadence = equip.stream().filter(e -> e.attacksRestsCadence() != null).collect(Collectors.toList());
        if (equipsWithCadence.isEmpty())
            return true;
        boolean can = equipsWithCadence.stream().allMatch(Equip::canAttack);
        if (!can) System.out.println("Can't attack");
        return can;
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
        System.out.println("Equip adding: "+equip);
        this.equip.add(new Equip(equip));
        checkEquipAndHands();
        return claz.cast(this);
    }

    private void checkEquipAndHands() {
        List<Equip> equipsUsingHands = equip.stream().filter(e -> e.handsUsed() > 0).collect(Collectors.toList());
        if (!equipsUsingHands.isEmpty()) {
            while (equipsUsingHands.stream().mapToInt(Equip::handsUsed).sum()>hands) {
                System.out.println("Removing equip: "+ equipsUsingHands.get(0).getName());
                equip.remove(equipsUsingHands.get(0));
                equipsUsingHands.remove(0);
            }
            equip.forEach(e->System.out.println(e.getName()));
        }
    }

    public List<Equip> equip() {
        return equip;
    }

}
