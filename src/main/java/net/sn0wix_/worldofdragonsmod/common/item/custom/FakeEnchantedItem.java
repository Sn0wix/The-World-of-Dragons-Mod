package net.sn0wix_.worldofdragonsmod.common.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FakeEnchantedItem extends Item {
    public FakeEnchantedItem(Settings settings) {
        super(settings);
    }

    public boolean hasGlint(ItemStack itemStack) {
        return true;
    }
}
