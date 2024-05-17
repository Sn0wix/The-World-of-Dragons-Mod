package net.sn0wix_.worldofdragonsmod.client.renderersAndModels.entity.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.BlockWaveFallingBlockEntity;

@Environment(value = EnvType.CLIENT)
public class BlockWaveFallingBlockEntityRenderer
        extends EntityRenderer<BlockWaveFallingBlockEntity> {
    private final BlockRenderManager blockRenderManager;

    public BlockWaveFallingBlockEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0.5f;
        this.blockRenderManager = context.getBlockRenderManager();
    }

    @Override
    public void render(BlockWaveFallingBlockEntity fallingBlockEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        BlockState blockState = fallingBlockEntity.getBlockState();
        if (blockState.getRenderType() != BlockRenderType.MODEL) {
            return;
        }
        World world = fallingBlockEntity.getWorld();
        if (blockState == world.getBlockState(fallingBlockEntity.getBlockPos()) || blockState.getRenderType() == BlockRenderType.INVISIBLE) {
            return;
        }
        matrixStack.push();
        BlockPos blockPos = BlockPos.ofFloored(fallingBlockEntity.getX(), fallingBlockEntity.getBoundingBox().maxY, fallingBlockEntity.getZ());
        matrixStack.translate(-0.5, 0.0, -0.5);
        this.blockRenderManager.getModelRenderer().render(world, this.blockRenderManager.getModel(blockState), blockState, blockPos, matrixStack, vertexConsumerProvider.getBuffer(RenderLayers.getMovingBlockLayer(blockState)), false, Random.create(), blockState.getRenderingSeed(fallingBlockEntity.getFallingBlockPos()), OverlayTexture.DEFAULT_UV);
        matrixStack.pop();
        super.render(fallingBlockEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(BlockWaveFallingBlockEntity fallingBlockEntity) {
        return PlayerScreenHandler.BLOCK_ATLAS_TEXTURE;
    }
}
