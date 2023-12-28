package net.sn0wix_.worldofdragonsmod.common.blocks.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SingleStackInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.sn0wix_.worldofdragonsmod.common.blocks.ModBlockEntities;

public class PickupBlockEntity extends BlockEntity implements SingleStackInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);

    public PickupBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PICKUP_BLOCK_ENTITY, pos, state);
    }


    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return inventory.remove(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return false;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.put("RecordItem", this.getStack().writeNbt(new NbtCompound()));
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        if (nbt.contains("Item")) {
            this.inventory.set(0, ItemStack.fromNbt(nbt.getCompound("Item")));
        }
    }
}
