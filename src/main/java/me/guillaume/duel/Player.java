package me.guillaume.duel;

public class Player {

    private int hitPoints;
    private String weapon;
    private int hands;
    private int damage;

    public Player(int hitPoints, String weapon, int hands, int damage) {
        this.hitPoints = hitPoints;
        this.weapon = weapon;
        this.hands = hands;
        this.damage = damage;
    }

    public void engage(Player enemy) {
        blow(enemy);
    }

    private void blow(Player enemy) {
        enemy.hitPoints(Math.max(enemy.hitPoints - damage, 0));
        if (enemy.hitPoints>0) enemy.blow(this);
    }

    public int hitPoints() {
        return hitPoints;
    }

    public void hitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

}
