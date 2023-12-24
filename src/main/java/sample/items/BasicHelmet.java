package sample.items;

import lombok.Getter;
import me.hysong.libhyextended.objects.dataobj2.DataObject2;
import me.hysong.libhyextended.objects.dataobj2.JSONCodable;
import me.hysong.libhyextended.objects.dataobj2.NotJSONCodable;
import sample.objects.EquipmentItem;

import java.util.Random;

@JSONCodable
@Getter
public class BasicHelmet extends DataObject2 implements EquipmentItem {

    @NotJSONCodable private static final int STAT_MAXIMUM = 10;

    private final int defense;
    private final int attack;
    private final int speed;
    private final int health;
    private final int mana;
    private final int stamina;
    private final int strength;
    private final int critChance;
    private final int critAmplifier;

    public BasicHelmet() {
        defense = new Random().nextInt(STAT_MAXIMUM) + 1;
        attack = new Random().nextInt(STAT_MAXIMUM) + 1;
        speed = new Random().nextInt(STAT_MAXIMUM) + 1;
        health = new Random().nextInt(STAT_MAXIMUM) + 1;
        mana = new Random().nextInt(STAT_MAXIMUM) + 1;
        stamina = new Random().nextInt(STAT_MAXIMUM) + 1;
        strength = new Random().nextInt(STAT_MAXIMUM) + 1;
        critChance = new Random().nextInt(STAT_MAXIMUM) + 1;
        critAmplifier = new Random().nextInt(STAT_MAXIMUM*3) + 1;
    }
}
