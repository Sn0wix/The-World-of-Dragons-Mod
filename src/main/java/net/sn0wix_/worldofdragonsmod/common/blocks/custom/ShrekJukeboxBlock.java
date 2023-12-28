package net.sn0wix_.worldofdragonsmod.common.blocks.custom;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.blocks.ModBlockEntities;
import net.sn0wix_.worldofdragonsmod.common.blocks.entity.ShrekJukeboxBlockEntity;
import org.jetbrains.annotations.Nullable;

public class ShrekJukeboxBlock extends BlockWithEntity {
    public static final BooleanProperty HAS_RECORD = Properties.HAS_RECORD;

    public ShrekJukeboxBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(HAS_RECORD, false));

    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(itemStack);
        if (nbtCompound != null && nbtCompound.contains("RecordItem")) {
            world.setBlockState(pos, state.with(HAS_RECORD, true), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ShrekJukeboxBlockEntity jukeboxBlockEntity) {
            if (state.get(HAS_RECORD)) {
                jukeboxBlockEntity.dropRecord();
                return ActionResult.success(world.isClient);
            }else {
                if (!world.isClient) {
                    jukeboxBlockEntity.setStack(player.getStackInHand(hand));
                    //player.getStackInHand(hand).decrement(1);
                    //jukeboxBlockEntity.startPlaying();
                }
                return ActionResult.success(world.isClient);
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ShrekJukeboxBlockEntity) {
            ShrekJukeboxBlockEntity jukeboxBlockEntity = (ShrekJukeboxBlockEntity) blockEntity;
            jukeboxBlockEntity.dropRecord();
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        ShrekJukeboxBlockEntity jukeboxBlockEntity;
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ShrekJukeboxBlockEntity && (jukeboxBlockEntity = (ShrekJukeboxBlockEntity) blockEntity).isPlayingRecord()) {
            return 15;
        }
        return 0;
    }

    @Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        ShrekJukeboxBlockEntity jukeboxBlockEntity;
        Object object = world.getBlockEntity(pos);
        if (object instanceof ShrekJukeboxBlockEntity && (object = (jukeboxBlockEntity = (ShrekJukeboxBlockEntity) object).getStack().getItem()) instanceof MusicDiscItem) {
            MusicDiscItem musicDiscItem = (MusicDiscItem) object;
            return musicDiscItem.getComparatorOutput();
        }
        return 0;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HAS_RECORD);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShrekJukeboxBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        if (state.get(HAS_RECORD).booleanValue()) {
            return ShrekJukeboxBlock.checkType(type, ModBlockEntities.SHREK_JUKEBOX_ENTITY, ShrekJukeboxBlockEntity::tick);
        }
        return null;
    }
}
