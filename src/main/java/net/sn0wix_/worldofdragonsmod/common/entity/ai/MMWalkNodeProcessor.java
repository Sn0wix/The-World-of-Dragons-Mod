package net.sn0wix_.worldofdragonsmod.common.entity.ai;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShapes;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

//Code is ported from forge version of Mowzie's mobs: https://github.com/BobMowzie/MowziesMobs
public class MMWalkNodeProcessor extends LandPathNodeMaker {
    @Override
    public PathNode getStart() {
        int y;
        Box bounds = this.entity.getBoundingBox();
        if (this.canSwim() && this.entity.isSubmergedInWater()) {
            y = (int) bounds.minY;
            BlockPos.Mutable pos = new BlockPos.Mutable(MathHelper.floor(this.entity.getX()), y, MathHelper.floor(this.entity.getZ()));
            for (Block block = this.cachedWorld.getBlockState(pos).getBlock(); block == Blocks.WATER; block = this.cachedWorld.getBlockState(pos).getBlock()) {
                pos.setY(++y);
            }
        } else if (this.entity.isOnGround()) {
            y = MathHelper.floor(bounds.minY + 0.5D);
        } else {
            y = MathHelper.floor(this.entity.getY());
            BlockPos.Mutable pos = new BlockPos.Mutable(MathHelper.floor(this.entity.getX()), y, MathHelper.floor(this.entity.getZ()));
            while (y > 0 && (this.cachedWorld.getBlockState(pos).isAir() || this.cachedWorld.getBlockState(pos).getBlock().getCollisionShape(this.cachedWorld.getBlockState(pos),this.cachedWorld, pos, ShapeContext.absent()) != VoxelShapes.empty())) {
                pos.setY(y--);
            }
            y++;
        }
        // account for node size
        float r = this.entity.getWidth() * 0.5F;
        int x = MathHelper.floor(this.entity.getX() - r);
        int z = MathHelper.floor(this.entity.getZ() - r);
        if (this.entity.getPathfindingPenalty(this.getPathType(this.entity, x, y, z)) < 0.0F) {
            Set<BlockPos> diagonals = Sets.newHashSet();
            diagonals.add(new BlockPos((int) (bounds.minX - r), y, (int) (bounds.minZ - r)));
            diagonals.add(new BlockPos((int) (bounds.minX - r), y, (int) (bounds.maxZ - r)));
            diagonals.add(new BlockPos((int) (bounds.maxX - r), y, (int) (bounds.minZ - r)));
            diagonals.add(new BlockPos((int) (bounds.maxX - r), y, (int) (bounds.maxZ - r)));
            for (BlockPos p : diagonals) {
                PathNodeType pathnodetype = this.getPathType(this.entity, p.getX(), p.getY(), p.getZ());
                if (this.entity.getPathfindingPenalty(pathnodetype) >= 0.0F) {
                    return this.getNode(p.getX(), p.getY(), p.getZ());
                }
            }
        }
        return this.getNode(x, y, z);
    }

    /*@Override
    public int getSuccessors(PathNode[] pathOptions, PathNode currentPoint, PathNode targetPoint, float maxDistance) {
        int optionCount = 0;
        int step = 0;
        PathNodeType pathnodetype = this.getPathType(this.entity, currentPoint.x, currentPoint.y + 1, currentPoint.z);
        if (this.entity.getPathfindingPenalty(pathnodetype) >= 0.0F) {
            step = MathHelper.floor(Math.max(1.0F, this.entity.getStepHeight()));
        }
        BlockPos under = (new BlockPos(currentPoint.x, currentPoint.y, currentPoint.z)).down();
        double floor = currentPoint.y - (1.0D - this.cachedWorld.getBlockState(under).getCollisionShape(this.cachedWorld, under).getBoundingBox().maxY);
        PathNode south = this.getNode(currentPoint.x, currentPoint.y, currentPoint.z + 1, step, floor, Direction.SOUTH);
        PathNode west = this.getNode(currentPoint.x - 1, currentPoint.y, currentPoint.z, step, floor, Direction.WEST);
        PathNode east = this.getNode(currentPoint.x + 1, currentPoint.y, currentPoint.z, step, floor, Direction.EAST);
        PathNode north = this.getNode(currentPoint.x, currentPoint.y, currentPoint.z - 1, step, floor, Direction.NORTH);
        if (south != null && !south.visited && south.getDistance(targetPoint) < maxDistance) {
            pathOptions[optionCount++] = south;
        }
        if (west != null && !west.visited && west.getDistance(targetPoint) < maxDistance) {
            pathOptions[optionCount++] = west;
        }
        if (east != null && !east.visited && east.getDistance(targetPoint) < maxDistance) {
            pathOptions[optionCount++] = east;
        }
        if (north != null && !north.visited && north.getDistance(targetPoint) < maxDistance) {
            pathOptions[optionCount++] = north;
        }
        boolean northPassable = north == null || north.type == PathNodeType.OPEN || north.penalty != 0.0F;
        boolean southPassable = south == null || south.type == PathNodeType.OPEN || south.penalty != 0.0F;
        boolean eastPassable = east == null || east.type == PathNodeType.OPEN || east.penalty != 0.0F;
        boolean westPassable = west == null || west.type == PathNodeType.OPEN || west.penalty != 0.0F;
        if (northPassable && westPassable) {
            PathNode northwest = this.getNode(currentPoint.x - 1, currentPoint.y, currentPoint.z - 1, step, floor, Direction.NORTH);
            if (northwest != null && !northwest.visited && northwest.getDistance(targetPoint) < maxDistance) {
                pathOptions[optionCount++] = northwest;
            }
        }
        if (northPassable && eastPassable) {
            PathNode northeast = this.getNode(currentPoint.x + 1, currentPoint.y, currentPoint.z - 1, step, floor, Direction.NORTH);
            if (northeast != null && !northeast.visited && northeast.getDistance(targetPoint) < maxDistance) {
                pathOptions[optionCount++] = northeast;
            }
        }
        if (southPassable && westPassable) {
            PathNode southwest = this.getNode(currentPoint.x - 1, currentPoint.y, currentPoint.z + 1, step, floor, Direction.SOUTH);
            if (southwest != null && !southwest.visited && southwest.getDistance(targetPoint) < maxDistance) {
                pathOptions[optionCount++] = southwest;
            }
        }
        if (southPassable && eastPassable) {
            PathNode southeast = this.getNode(currentPoint.x + 1, currentPoint.y, currentPoint.z + 1, step, floor, Direction.SOUTH);
            if (southeast != null && !southeast.visited && southeast.getDistance(targetPoint) < maxDistance) {
                pathOptions[optionCount++] = southeast;
            }
        }
        return optionCount;
    }*/

    @Nullable
    private PathNode getNode(int x, int y, int z, int step, double floor, Direction dir) {
        PathNode result = null;
        BlockPos pos = new BlockPos(x, y, z);
        BlockPos under = pos.down();
        double dirFloor = (double) y - (1.0D - this.cachedWorld.getBlockState(under).getCollisionShape(this.cachedWorld, under).getMax(Direction.Axis.Y));
        if (dirFloor - floor > 1.125D) {
            return null;
        }
        PathNodeType atNode = this.getPathType(this.entity, x, y, z);
        float malus = this.entity.getPathfindingPenalty(atNode);
        double r = this.entity.getWidth() / 2.0D;
        if (malus >= 0.0F) {
            result = this.getNode(x, y, z);
            result.type = atNode;
            result.penalty = Math.max(result.penalty, malus);
        }
        if (atNode == PathNodeType.WALKABLE) {
            return result;
        }
        if (result == null && step > 0 && atNode != PathNodeType.FENCE && atNode != PathNodeType.TRAPDOOR) {
            result = this.getNode(x, y + 1, z, step - 1, floor, dir);
            if (result != null && (result.type == PathNodeType.OPEN || result.type == PathNodeType.WALKABLE) && this.entity.getYaw() < 1.0F) {
                double px = (x - dir.getOffsetX()) + 0.5D;
                double pz = (z - dir.getOffsetZ()) + 0.5D;
                Box axisalignedbb = new Box(px - r, y + 0.001D, pz - r, px + r, y + this.entity.getHeight(), pz + r);
                Box floorShape = this.cachedWorld.getBlockState(pos).getCollisionShape(this.cachedWorld, pos).getBoundingBox();
                Box collision = axisalignedbb.expand(0.0D, floorShape.maxY - 0.002D, 0.0D);
                if (this.entity.getWorld().canCollide(this.entity, collision)) {
                    result = null;
                }
            }
        }
        if (atNode == PathNodeType.OPEN) {
            // account for node size
            Box collision = new Box(
                    x - r + this.entityBlockXSize * 0.5D, y + 0.001D, z - r + this.entityBlockZSize * 0.5D,
                    x + r + this.entityBlockXSize * 0.5D, y + this.entity.getHeight(), z + r + this.entityBlockZSize * 0.5D
            );
            if (this.entity.getWorld().canCollide(entity, collision)) {
                return null;
            }
            if (this.entity.getWidth() >= 1.0F) {
                PathNodeType down = this.getPathType(this.entity, x, y - 1, z);
                if (down == PathNodeType.BLOCKED) {
                    result = this.getNode(x, y, z);
                    result.type = PathNodeType.WALKABLE;
                    result.penalty = Math.max(result.penalty, malus);
                    return result;
                }
            }
            int fallDistance = 0;
            while (y-- > 0 && atNode == PathNodeType.OPEN) {
                if (fallDistance++ >= this.entity.getSafeFallDistance()) {
                    return null;
                }
                atNode = this.getPathType(this.entity, x, y, z);
                malus = this.entity.getPathfindingPenalty(atNode);
                if (atNode != PathNodeType.OPEN && malus >= 0.0F) {
                    result = this.getNode(x, y, z);
                    result.type = atNode;
                    result.penalty = Math.max(result.penalty, malus);
                    break;
                }
                if (malus < 0.0F) {
                    return null;
                }
            }
        }
        return result;
    }

    private PathNodeType getPathType(MobEntity living, int x, int y, int z) {
        return this.getNodeType(this.cachedWorld, x, y, z, living);
    }
}
