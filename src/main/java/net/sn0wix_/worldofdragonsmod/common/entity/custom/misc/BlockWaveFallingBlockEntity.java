package net.sn0wix_.worldofdragonsmod.common.entity.custom.misc;

import com.mojang.logging.LogUtils;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.entity.ModEntities;
import net.sn0wix_.worldofdragonsmod.common.util.ModDamageSources;
import net.sn0wix_.worldofdragonsmod.common.util.blockWaves.BlockWave;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.function.Predicate;

public class BlockWaveFallingBlockEntity extends Entity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private BlockState block = Blocks.SAND.getDefaultState();
    public int timeFalling;
    public boolean dropItem = true;
    private boolean destroyedOnLanding;
    private boolean hurtEntities;
    private int fallHurtMax = 40;
    private float fallHurtAmount;
    private Entity owner;
    private float damage;
    @Nullable
    public NbtCompound blockEntityData;
    protected static final TrackedData<BlockPos> BLOCK_POS = DataTracker.registerData(BlockWaveFallingBlockEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
    private BlockWave parentWave;

    public BlockWaveFallingBlockEntity(EntityType<? extends BlockWaveFallingBlockEntity> entityType, World world) {
        super(entityType, world);
    }

    private BlockWaveFallingBlockEntity(World world, double x, double y, double z, BlockState block, Entity owner, float damage, BlockWave parentWave) {
        this(ModEntities.BLOCK_WAVE_FALLING_BLOCK, world);
        this.block = block;
        this.parentWave = parentWave;
        this.intersectionChecked = true;
        this.setPosition(x, y, z);
        this.setVelocity(Vec3d.ZERO);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.setFallingBlockPos(this.getBlockPos());
        this.owner = owner;
        this.damage = damage;
        this.parentWave = parentWave;
    }

    /**
     * Spawns a falling block entity at {@code pos} from the block {@code state}.
     *
     * @return the spawned entity
     */
    public static BlockWaveFallingBlockEntity spawnFromBlock(World world, BlockPos pos, BlockState state, Entity owner, float damage, BlockWave parentWave) {
        BlockWaveFallingBlockEntity fallingBlockEntity = new BlockWaveFallingBlockEntity(world, (double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5, state.contains(Properties.WATERLOGGED) ? state.with(Properties.WATERLOGGED, false) : state, owner, damage, parentWave);
        world.spawnEntity(fallingBlockEntity);
        return fallingBlockEntity;
    }

    @Override
    public void pushAwayFrom(Entity entity) {
    }

    @Override
    protected void pushOutOfBlocks(double x, double y, double z) {
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    public void setFallingBlockPos(BlockPos pos) {
        this.dataTracker.set(BLOCK_POS, pos);
    }

    public BlockPos getFallingBlockPos() {
        return this.dataTracker.get(BLOCK_POS);
    }

    @Override
    protected Entity.MoveEffect getMoveEffect() {
        return Entity.MoveEffect.NONE;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(BLOCK_POS, BlockPos.ORIGIN);
    }

    @Override
    public boolean canHit() {
        return !this.isRemoved();
    }

    @Override
    public void tick() {
        if (damage != 0) {
            getWorld().getOtherEntities(this, getBoundingBox()).forEach(entity -> {
                if (!entity.equals(owner) && !(entity instanceof BlockWaveFallingBlockEntity) && !parentWave.movedEntities.contains(entity)) {
                    parentWave.movedEntities.add(entity);
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.damage(ModDamageSources.of(getWorld(), ModDamageSources.BLOCK_WAVE, this), damage);
                    }

                    entity.addVelocity(new Vec3d(0, 0.5, 0));
                    entity.velocityDirty = true;
                    entity.velocityModified = true;
                }
            });
        }

        if (this.block.isAir()) {
            this.discard();
            return;
        }
        Block block = this.block.getBlock();
        ++this.timeFalling;
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
        }
        this.move(MovementType.SELF, this.getVelocity());
        if (!this.getWorld().isClient) {
            BlockHitResult blockHitResult;
            BlockPos blockPos = this.getBlockPos();
            boolean bl = this.block.getBlock() instanceof ConcretePowderBlock;
            boolean bl2 = bl && this.getWorld().getFluidState(blockPos).isIn(FluidTags.WATER);
            double d = this.getVelocity().lengthSquared();
            if (bl && d > 1.0 && (blockHitResult = this.getWorld().raycast(new RaycastContext(new Vec3d(this.prevX, this.prevY, this.prevZ), this.getPos(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.SOURCE_ONLY, this))).getType() != HitResult.Type.MISS && this.getWorld().getFluidState(blockHitResult.getBlockPos()).isIn(FluidTags.WATER)) {
                blockPos = blockHitResult.getBlockPos();
                bl2 = true;
            }
            if (this.isOnGround() || bl2) {
                BlockState blockState = this.getWorld().getBlockState(blockPos);
                this.setVelocity(this.getVelocity().multiply(0.7, -0.5, 0.7));
                if (!blockState.isOf(Blocks.MOVING_PISTON)) {
                    if (!this.destroyedOnLanding) {
                        boolean bl5;
                        boolean bl3 = blockState.canReplace(new AutomaticItemPlacementContext(this.getWorld(), blockPos, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                        boolean bl4 = FallingBlock.canFallThrough(this.getWorld().getBlockState(blockPos.down())) && (!bl || !bl2);
                        boolean bl6 = bl5 = this.block.canPlaceAt(this.getWorld(), blockPos) && !bl4;
                        if (bl3 && bl5) {
                            if (this.block.contains(Properties.WATERLOGGED) && this.getWorld().getFluidState(blockPos).getFluid() == Fluids.WATER) {
                                this.block = this.block.with(Properties.WATERLOGGED, true);
                            }
                            if (this.getWorld().setBlockState(blockPos, this.block, Block.NOTIFY_ALL)) {
                                BlockEntity blockEntity;
                                ((ServerWorld) this.getWorld()).getChunkManager().threadedAnvilChunkStorage.sendToOtherNearbyPlayers(this, new BlockUpdateS2CPacket(blockPos, this.getWorld().getBlockState(blockPos)));
                                this.discard();
                                if (block instanceof LandingBlock) {
                                    ((LandingBlock) (block)).onLanding(this.getWorld(), blockPos, this.block, blockState, null);
                                }
                                if (this.blockEntityData != null && this.block.hasBlockEntity() && (blockEntity = this.getWorld().getBlockEntity(blockPos)) != null) {
                                    NbtCompound nbtCompound = blockEntity.createNbt();
                                    for (String string : this.blockEntityData.getKeys()) {
                                        nbtCompound.put(string, this.blockEntityData.get(string).copy());
                                    }
                                    try {
                                        blockEntity.readNbt(nbtCompound);
                                    } catch (Exception exception) {
                                        LOGGER.error("Failed to load block entity from falling block", exception);
                                    }
                                    blockEntity.markDirty();
                                }
                            } else if (this.dropItem && this.getWorld().getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                                this.discard();
                                this.onDestroyedOnLanding(block, blockPos);
                                this.dropItem(block);
                            }
                        } else {
                            this.discard();
                            if (this.dropItem && this.getWorld().getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                                this.onDestroyedOnLanding(block, blockPos);
                                this.dropItem(block);
                            }
                        }
                    } else {
                        this.discard();
                        this.onDestroyedOnLanding(block, blockPos);
                    }
                }
            } else if (!(this.getWorld().isClient || (this.timeFalling <= 100 || blockPos.getY() > this.getWorld().getBottomY() && blockPos.getY() <= this.getWorld().getTopY()) && this.timeFalling <= 600)) {
                if (this.dropItem && this.getWorld().getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                    this.dropItem(block);
                }
                this.discard();
            }
        }
        this.setVelocity(this.getVelocity().multiply(0.98));
    }

    public void onDestroyedOnLanding(Block block, BlockPos pos) {
        if (block instanceof LandingBlock) {
            ((LandingBlock) (block)).onDestroyedOnLanding(this.getWorld(), pos, null);
        }
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        DamageSource damageSource2;
        if (!this.hurtEntities) {
            return false;
        }
        int i = MathHelper.ceil(fallDistance - 1.0f);
        if (i < 0) {
            return false;
        }
        Predicate<Entity> predicate = EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.and(EntityPredicates.VALID_LIVING_ENTITY);
        Block block = this.block.getBlock();
        if (block instanceof LandingBlock) {
            LandingBlock landingBlock = (LandingBlock) ((Object) block);
            damageSource2 = landingBlock.getDamageSource(this);
        } else {
            damageSource2 = this.getDamageSources().fallingBlock(this);
        }
        DamageSource damageSource22 = damageSource2;
        float f = Math.min(MathHelper.floor((float) i * this.fallHurtAmount), this.fallHurtMax);
        this.getWorld().getOtherEntities(this, this.getBoundingBox(), predicate).forEach(entity -> entity.damage(damageSource22, f));
        boolean bl = this.block.isIn(BlockTags.ANVIL);
        if (bl && f > 0.0f && this.random.nextFloat() < 0.05f + (float) i * 0.05f) {
            BlockState blockState = AnvilBlock.getLandingState(this.block);
            if (blockState == null) {
                this.destroyedOnLanding = true;
            } else {
                this.block = blockState;
            }
        }
        return false;
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.put("BlockState", NbtHelper.fromBlockState(this.block));
        nbt.putInt("Time", this.timeFalling);
        nbt.putBoolean("DropItem", this.dropItem);
        nbt.putBoolean("HurtEntities", this.hurtEntities);
        nbt.putFloat("FallHurtAmount", this.fallHurtAmount);
        nbt.putInt("FallHurtMax", this.fallHurtMax);
        if (this.blockEntityData != null) {
            nbt.put("TileEntityData", this.blockEntityData);
        }
        nbt.putBoolean("CancelDrop", this.destroyedOnLanding);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.block = NbtHelper.toBlockState(this.getWorld().createCommandRegistryWrapper(RegistryKeys.BLOCK), nbt.getCompound("BlockState"));
        this.timeFalling = nbt.getInt("Time");
        if (nbt.contains("HurtEntities", NbtElement.NUMBER_TYPE)) {
            this.hurtEntities = nbt.getBoolean("HurtEntities");
            this.fallHurtAmount = nbt.getFloat("FallHurtAmount");
            this.fallHurtMax = nbt.getInt("FallHurtMax");
        } else if (this.block.isIn(BlockTags.ANVIL)) {
            this.hurtEntities = true;
        }
        if (nbt.contains("DropItem", NbtElement.NUMBER_TYPE)) {
            this.dropItem = nbt.getBoolean("DropItem");
        }
        if (nbt.contains("TileEntityData", NbtElement.COMPOUND_TYPE)) {
            this.blockEntityData = nbt.getCompound("TileEntityData").copy();
        }
        this.destroyedOnLanding = nbt.getBoolean("CancelDrop");
        if (this.block.isAir()) {
            this.block = Blocks.SAND.getDefaultState();
        }
    }

    public Entity getOwner() {
        return owner;
    }

    public void setHurtEntities(float fallHurtAmount, int fallHurtMax) {
        this.hurtEntities = true;
        this.fallHurtAmount = fallHurtAmount;
        this.fallHurtMax = fallHurtMax;
    }

    public void setDestroyedOnLanding() {
        this.destroyedOnLanding = true;
    }

    @Override
    public boolean doesRenderOnFire() {
        return false;
    }

    @Override
    public void populateCrashReport(CrashReportSection section) {
        super.populateCrashReport(section);
        section.add("Immitating BlockState", this.block.toString());
    }

    public BlockState getBlockState() {
        return this.block;
    }

    @Override
    protected Text getDefaultName() {
        return Text.translatable("entity.minecraft.falling_block_type", this.block.getBlock().getName());
    }

    @Override
    public boolean entityDataRequiresOperator() {
        return true;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this, Block.getRawIdFromState(this.getBlockState()));
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        this.block = Block.getStateFromRawId(packet.getEntityData());
        this.intersectionChecked = true;
        double d = packet.getX();
        double e = packet.getY();
        double f = packet.getZ();
        this.setPosition(d, e, f);
        this.setFallingBlockPos(this.getBlockPos());
    }
}
