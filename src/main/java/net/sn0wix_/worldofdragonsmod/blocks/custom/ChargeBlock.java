package net.sn0wix_.worldofdragonsmod.blocks.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.item.ModItems;

public class ChargeBlock extends Block {
    public ChargeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient && entity instanceof PlayerEntity){
            PlayerEntity player = (PlayerEntity) entity;

            if (player.getInventory().getMainHandStack().getItem().equals(ModItems.CHARGE_BATTERY)){
                ItemStack itemStack = player.getInventory().getMainHandStack();
                itemStack.damage(-1, player, (e) -> e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND));
            }
        }
        super.onSteppedOn(world, pos, state, entity);
    }
}
