package net.sn0wix_.worldofdragonsmod.common.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.ai.pathing.PathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeNavigator;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.ChunkCache;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//Code is ported from forge version of Mowzie's mobs: https://github.com/BobMowzie/MowziesMobs
public class MMPathFinder extends PathNodeNavigator {
    public MMPathFinder(PathNodeMaker processor, int maxVisitedNodes) {
        super(processor, maxVisitedNodes);
    }

    @Nullable
    @Override
    public Path findPathToAny(ChunkCache regionIn, MobEntity mob, Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
        Path path = super.findPathToAny(regionIn, mob, targetPositions, maxRange, accuracy, searchDepthMultiplier);
        return path == null ? null : new PatchedPath(path);
    }

    static class PatchedPath extends Path {
        public PatchedPath(Path original) {
            super(copyPathPoints(original), original.getTarget(), original.reachesTarget());
        }

        @Override
        public Vec3d getNodePosition(Entity entity, int index) {
            PathNode point = this.getNode(index);
            double d0 = point.x + MathHelper.floor(entity.getWidth() + 1.0F) * 0.5D;
            double d1 = point.y;
            double d2 = point.z + MathHelper.floor(entity.getWidth() + 1.0F) * 0.5D;
            return new Vec3d(d0, d1, d2);
        }

        private static List<PathNode> copyPathPoints(Path original) {
            List<PathNode> points = new ArrayList<>();
            for (int i = 0; i < original.getLength(); i++) {
                points.add(original.getNode(i));
            }
            return points;
        }
    }
}
