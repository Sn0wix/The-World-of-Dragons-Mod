package net.sn0wix_.worldofdragonsmod.common.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.sn0wix_.worldofdragonsmod.common.WorldOfDragons;

import java.util.ArrayList;

public class ModItemGroup {
    private static final ArrayList<Item> ITEMS = new ArrayList<>(64);

    public static final ItemGroup WORLD_OF_DRAGONS_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(WorldOfDragons.MOD_ID,
                    "world_of_dragons_item_group"), FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.worldofdragonsmod.world_of_dragons_item_group"))
            .icon(() -> new ItemStack(Items.DRAGON_HEAD)).entries(ModItemGroup::addEntries).build());

    private static void addEntries(ItemGroup.DisplayContext displayContext, ItemGroup.Entries entries) {
        ITEMS.forEach(entries::add);
        ITEMS.clear();
        ITEMS.trimToSize();
    }

    public static void registerItemGroup() {
        WorldOfDragons.LOGGER.info("Registering item group for " + WorldOfDragons.MOD_ID);
    }

    public static void addToGroup(Item item) {
        ITEMS.add(item);
    }
}
