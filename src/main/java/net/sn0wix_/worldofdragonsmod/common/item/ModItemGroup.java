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
    private static final ArrayList<Item> SPAWN_EGGS = new ArrayList<>(16);

    public static final ItemGroup WORLD_OF_DRAGONS_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(WorldOfDragons.MOD_ID,
            "world_of_dragons_item_group"), FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.worldofdragonsmod.world_of_dragons_item_group"))
            .icon(() -> new ItemStack(Items.DRAGON_HEAD)).entries(ModItemGroup::addEntries).build());

    public static final ItemGroup ADMIN_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, new Identifier(WorldOfDragons.MOD_ID,
            "world_of_dragons_admin_item_group"), FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.worldofdragonsmod.admin_item_group"))
            .icon(() -> new ItemStack(Items.COMMAND_BLOCK)).entries(ModItemGroup::addItemsToAdminGroup).build());

    public static final ItemGroup WORLD_OF_DRAGONS_SPAWN_EGGS = Registry.register(Registries.ITEM_GROUP, new Identifier(WorldOfDragons.MOD_ID,
            "world_of_dragons_spawn_eggs"), FabricItemGroup.builder()
            .displayName(Text.translatable("itemGroup.worldofdragonsmod.world_of_dragons_spawn_eggs"))
            .icon(() -> new ItemStack(ModItems.GOBLIN_SPAWN_EGG)).entries(ModItemGroup::addSpawnEggs).build());


    private static void addSpawnEggs(ItemGroup.DisplayContext displayContext, ItemGroup.Entries entries) {
        SPAWN_EGGS.forEach(entries::add);
    }

    private static void addEntries(ItemGroup.DisplayContext displayContext, ItemGroup.Entries entries) {
        ITEMS.forEach(entries::add);
    }

    private static void addItemsToAdminGroup(ItemGroup.DisplayContext displayContext, ItemGroup.Entries entries) {
        entries.add(new ItemStack(Items.COMMAND_BLOCK));
        entries.add(new ItemStack(Items.CHAIN_COMMAND_BLOCK));
        entries.add(new ItemStack(Items.REPEATING_COMMAND_BLOCK));
        entries.add(new ItemStack(Items.BARRIER));
        entries.add(new ItemStack(Items.DEBUG_STICK));
        entries.add(new ItemStack(Items.LIGHT));
        entries.add(new ItemStack(Items.COMMAND_BLOCK_MINECART));
        entries.add(new ItemStack(Items.STRUCTURE_BLOCK));
        entries.add(new ItemStack(Items.JIGSAW));
        entries.add(new ItemStack(Items.STRUCTURE_VOID));
    }

    public static void registerItemGroup() {
        WorldOfDragons.LOGGER.info("Registering item group for " + WorldOfDragons.MOD_ID);
    }

    public static void addToGroup(Item item) {
        ITEMS.add(item);
    }

    public static void addSpawnEgg(Item item) {
        SPAWN_EGGS.add(item);
    }
}
