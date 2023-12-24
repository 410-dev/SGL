package sample;

import lombok.Getter;
import lombok.Setter;
import me.hysong.libhyextended.objects.dataobj2.JSONCodable;
import sample.objects.EquipmentItem;

@JSONCodable
@Getter
@Setter
public class Player {
    private int level = 1;
    private int experience = 0;

    private int baseHealthMax = 100;
    private int currentHealth = 100;
    private int currentHealthMax = 100;

    private int baseDefenseMax = 100;
    private int currentDefense = 100;
    private int currentDefenseMax = 100;

    private int baseAttackMax = 100;
    private int currentAttack = 100;
    private int currentAttackMax = 100;

    private int criticalChance = 0;
    private int criticalAmplifier = 0;

    private int baseSpeedMax = 100;
    private int currentSpeed = 100;
    private int currentSpeedMax = 100;

    private int baseManaMax = 100;
    private int currentMana = 100;
    private int currentManaMax = 100;

    private int baseStaminaMax = 100;
    private int currentStamina = 100;
    private int currentStaminaMax = 100;

    private int baseStrengthMax = 100;
    private int currentStrength = 100;
    private int currentStrengthMax = 100;

    private EquipmentItem helmet = null;
    private EquipmentItem chestplate = null;
    private EquipmentItem leggings = null;
    private EquipmentItem boots = null;

    private EquipmentItem weapon = null;

    public void heal() {
        currentHealth = currentHealthMax;
        currentDefense = currentDefenseMax;
        currentAttack = currentAttackMax;
        currentSpeed = currentSpeedMax;
        currentMana = currentManaMax;
        currentStamina = currentStaminaMax;
        currentStrength = currentStrengthMax;
    }

    public void updateStats() {
        updateDefense();
        updateAttack();
        updateSpeed();
        updateMana();
        updateStamina();
        updateStrength();
        updateHP();
        updateCriticalChance();
        updateCriticalAmplifier();
    }

    public void updateCriticalChance() {
        criticalChance = 0;
        if (helmet != null) criticalChance += helmet.getCritChance();
        if (chestplate != null) criticalChance += chestplate.getCritChance();
        if (leggings != null) criticalChance += leggings.getCritChance();
        if (boots != null) criticalChance += boots.getCritChance();
        if (weapon != null) criticalChance += weapon.getCritChance();
    }

    public void updateCriticalAmplifier() {
        criticalAmplifier = 0;
        if (helmet != null) criticalAmplifier += helmet.getCritAmplifier();
        if (chestplate != null) criticalAmplifier += chestplate.getCritAmplifier();
        if (leggings != null) criticalAmplifier += leggings.getCritAmplifier();
        if (boots != null) criticalAmplifier += boots.getCritAmplifier();
        if (weapon != null) criticalAmplifier += weapon.getCritAmplifier();
    }

    public void updateDefense() {
        currentDefenseMax = baseDefenseMax;
        if (helmet != null) currentDefenseMax += helmet.getDefense();
        if (chestplate != null) currentDefenseMax += chestplate.getDefense();
        if (leggings != null) currentDefenseMax += leggings.getDefense();
        if (boots != null) currentDefenseMax += boots.getDefense();
        if (weapon != null) currentDefenseMax += weapon.getDefense();
    }

    public void updateAttack() {
        currentAttackMax = baseAttackMax;
        if (helmet != null) currentAttackMax += helmet.getAttack();
        if (chestplate != null) currentAttackMax += chestplate.getAttack();
        if (leggings != null) currentAttackMax += leggings.getAttack();
        if (boots != null) currentAttackMax += boots.getAttack();
        if (weapon != null) currentAttackMax += weapon.getAttack();
    }

    public void updateSpeed() {
        currentSpeedMax = baseSpeedMax;
        if (helmet != null) currentSpeedMax += helmet.getSpeed();
        if (chestplate != null) currentSpeedMax += chestplate.getSpeed();
        if (leggings != null) currentSpeedMax += leggings.getSpeed();
        if (boots != null) currentSpeedMax += boots.getSpeed();
        if (weapon != null) currentSpeedMax += weapon.getSpeed();
    }

    public void updateMana() {
        currentManaMax = baseManaMax;
        if (helmet != null) currentManaMax += helmet.getMana();
        if (chestplate != null) currentManaMax += chestplate.getMana();
        if (leggings != null) currentManaMax += leggings.getMana();
        if (boots != null) currentManaMax += boots.getMana();
        if (weapon != null) currentManaMax += weapon.getMana();
    }

    public void updateStamina() {
        currentStaminaMax = baseStaminaMax;
        if (helmet != null) currentStaminaMax += helmet.getStamina();
        if (chestplate != null) currentStaminaMax += chestplate.getStamina();
        if (leggings != null) currentStaminaMax += leggings.getStamina();
        if (boots != null) currentStaminaMax += boots.getStamina();
        if (weapon != null) currentStaminaMax += weapon.getStamina();
    }

    public void updateStrength() {
        currentStrengthMax = baseStrengthMax;
        if (helmet != null) currentStrengthMax += helmet.getStrength();
        if (chestplate != null) currentStrengthMax += chestplate.getStrength();
        if (leggings != null) currentStrengthMax += leggings.getStrength();
        if (boots != null) currentStrengthMax += boots.getStrength();
        if (weapon != null) currentStrengthMax += weapon.getStrength();
    }

    public void updateHP() {
        currentHealthMax = baseHealthMax;
        if (helmet != null) currentHealthMax += helmet.getHealth();
        if (chestplate != null) currentHealthMax += chestplate.getHealth();
        if (leggings != null) currentHealthMax += leggings.getHealth();
        if (boots != null) currentHealthMax += boots.getHealth();
        if (weapon != null) currentHealthMax += weapon.getHealth();
    }

    public void putHelmet(EquipmentItem helmet) {
        this.helmet = helmet;
        updateStats();
    }

    public void putChestplate(EquipmentItem chestplate) {
        this.chestplate = chestplate;
        updateStats();
    }

    public void putLeggings(EquipmentItem leggings) {
        this.leggings = leggings;
        updateStats();
    }

    public void putBoots(EquipmentItem boots) {
        this.boots = boots;
        updateStats();
    }

    public void putWeapon(EquipmentItem weapon) {
        this.weapon = weapon;
        updateStats();
    }

}
