package net.sn0wix_.worldofdragonsmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

import static net.sn0wix_.worldofdragonsmod.WorldOfDragonsMain.MOD_ID;

public class ModItemGroup {
    public static final ItemGroup WORLD_OF_DRAGONS_ITEM_GROUP = FabricItemGroup.builder(new Identifier(MOD_ID, "world_of_dragons_item_group"))
            .icon(() -> new ItemStack(Items.DRAGON_HEAD)).build();
}
