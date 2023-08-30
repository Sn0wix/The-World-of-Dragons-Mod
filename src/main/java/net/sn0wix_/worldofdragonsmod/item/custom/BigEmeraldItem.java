package net.sn0wix_.worldofdragonsmod.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BigEmeraldItem extends Item {
    public BigEmeraldItem(Settings settings) {
        super(settings);
    }

    public boolean hasGlint(ItemStack itemStack) {
        return true;
    }
}
