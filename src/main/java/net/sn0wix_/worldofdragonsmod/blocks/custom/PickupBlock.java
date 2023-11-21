package net.sn0wix_.worldofdragonsmod.blocks.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.blocks.entity.PickupBlockEntity;
import org.jetbrains.annotations.Nullable;

public class PickupBlock extends BlockWithEntity {
    public PickupBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient()) {
            if (player.isCreative()) {
                if (world.getBlockEntity(pos) instanceof PickupBlockEntity pickupBlockEntity) {
                    pickupBlockEntity.setStack(player.getStackInHand(hand));
                    return ActionResult.SUCCESS;
                }
            }else {
                world.breakBlock(pos, true, player);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PickupBlockEntity(pos, state);
    }
}
