package sample.objects;

import me.hysong.libhyextended.objects.dataobj2.JSONCodable;

@JSONCodable
public interface EquipmentItem {
    int getDefense();
    int getAttack();
    int getSpeed();
    int getHealth();
    int getMana();
    int getStamina();
    int getStrength();
    int getCritChance();
    int getCritAmplifier();
}
