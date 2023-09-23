package net.sn0wix_.worldofdragonsmod.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BigEmeraldItem extends Item {
    public BigEmeraldItem(Settings settings) {
        super(settings);
    }

    public boolean hasGlint(ItemStack itemStack) {
        return true;
    }
}
