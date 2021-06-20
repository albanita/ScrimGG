package com.igalda.scrimgg.dom;

public class Champ {
    private float armor;
    private float armorPerLevel;
    private float attackDamage;
    private float attackDamagePerLevel;
    private float attackRange;
    private float attackSpeedPerLevel;
    private String bigImgURL;
    private float crit;
    private float critPerLevel;
    private float hp;
    private float hpPerLevel;
    private float hpRegen;
    private float hpRegenPerLevel;
    private float id;
    private String imageURL;
    private float moveSpeed;
    private float mp;
    private float mpPerLevel;
    private float mpRegen;
    private float mpRegenPerLevel;
    private String name;
    private float spellBlock;
    private float spellBlockPerLevel;

    public Champ(float armor, float armorPerLevel, float attackDamage, float attackDamagePerLevel, float attackRange, float attackSpeedPerLevel, String bigImgURL, float crit, float critPerLevel, float hp, float hpPerLevel, float hpRegen, float hpRegenPerLevel, float id, String imageURL, float moveSpeed, float mp, float mpPerLevel, float mpRegen, float mpRegenPerLevel, String name, float spellBlock, float spellBlockPerLevel) {
        this.armor = armor;
        this.armorPerLevel = armorPerLevel;
        this.attackDamage = attackDamage;
        this.attackDamagePerLevel = attackDamagePerLevel;
        this.attackRange = attackRange;
        this.attackSpeedPerLevel = attackSpeedPerLevel;
        this.bigImgURL = bigImgURL;
        this.crit = crit;
        this.critPerLevel = critPerLevel;
        this.hp = hp;
        this.hpPerLevel = hpPerLevel;
        this.hpRegen = hpRegen;
        this.hpRegenPerLevel = hpRegenPerLevel;
        this.id = id;
        this.imageURL = imageURL;
        this.moveSpeed = moveSpeed;
        this.mp = mp;
        this.mpPerLevel = mpPerLevel;
        this.mpRegen = mpRegen;
        this.mpRegenPerLevel = mpRegenPerLevel;
        this.name = name;
        this.spellBlock = spellBlock;
        this.spellBlockPerLevel = spellBlockPerLevel;
    }

    public float getArmor() {
        return armor;
    }

    public float getArmorPerLevel() {
        return armorPerLevel;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public float getAttackDamagePerLevel() {
        return attackDamagePerLevel;
    }

    public float getAttackRange() {
        return attackRange;
    }

    public float getAttackSpeedPerLevel() {
        return attackSpeedPerLevel;
    }

    public String getBigImgURL() {
        return bigImgURL;
    }

    public float getCrit() {
        return crit;
    }

    public float getCritPerLevel() {
        return critPerLevel;
    }

    public float getHp() {
        return hp;
    }

    public float getHpPerLevel() {
        return hpPerLevel;
    }

    public float getHpRegen() {
        return hpRegen;
    }

    public float getHpRegenPerLevel() {
        return hpRegenPerLevel;
    }

    public float getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getMp() {
        return mp;
    }

    public float getMpPerLevel() {
        return mpPerLevel;
    }

    public float getMpRegen() {
        return mpRegen;
    }

    public float getMpRegenPerLevel() {
        return mpRegenPerLevel;
    }

    public String getName() {
        return name;
    }

    public float getSpellBlock() {
        return spellBlock;
    }

    public float getSpellBlockPerLevel() {
        return spellBlockPerLevel;
    }
}
