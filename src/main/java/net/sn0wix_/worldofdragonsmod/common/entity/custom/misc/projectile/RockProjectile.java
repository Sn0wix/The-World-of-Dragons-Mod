package net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.item.ModItems;
import org.jetbrains.annotations.Nullable;

public class RockProjectile extends SnowballEntity {
    public final boolean isLava;

    public RockProjectile(EntityType<? extends SnowballEntity> entityType, World world, boolean isLava) {
        super(entityType, world);
        this.isLava = isLava;
    }

    public RockProjectile(World world, PlayerEntity user, boolean isLava) {
        super(world, user);
        this.isLava = isLava;
    }

    public RockProjectile(World world, double x, double y, double z, boolean isLava) {
        super(world, x, y, z);
        this.isLava = isLava;
    }

    @Override
    public void tick() {
        super.tick();
        if (isLava) {
            setOnFire(true);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), 1);
        if (isLava) {
            entity.setOnFireFor(2);
        }
    }

    @Override
    public ItemStack getStack() {
        return new ItemStack(isLava ? ModItems.LAVA_ROCK : ModItems.THE_ROCK);
    }

    @Nullable
    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(isLava ? ModItems.LAVA_ROCK : ModItems.THE_ROCK);
    }
}




/*
⡇⢸⡇⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⢟⣩⠷⡬⠉⠻⢿⣿⣿⣿⣿⣿⣿⣿⣷⠸⣭⢯⡝⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⡇⢸⡇⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⣵⢇⠤⠛⣀⡁⠀⠀⠹⣿⣿⣿⣿⣿⣿⣿⣘⠷⣮⡝⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⡅⢸⠇⠀⢸⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⢴⣷⢺⡏⠑⠀⠂⠀⠀⠀⢸⣿⣿⣿⣿⣿⣿⢌⡟⣶⠹⡇⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⠄⢸⡃⠀⢸⣻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡟⠡⠀⠀⣴⠀⠀⠀⠀⠀⡆⣿⣿⣿⣿⣿⣿⠈⡿⣜⣫⠇⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⠂⢸⡅⠀⢼⢹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⣋⣷⣟⣤⣾⣿⠧⠐⢀⠊⡁⠣⠀⣿⣿⣿⣿⣿⠈⣿⢼⡱⢯⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⠀⢼⡃⠀⢾⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⠉⣿⣿⣫⣧⠔⠀⠀⢄⡀⠄⠄⢨⣿⣿⣿⣿⣿⠰⣟⢮⡳⣹⢺⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⠀⢸⡇⠀⢾⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣏⣼⣇⢃⣩⠄⠀⠀⠀⢐⠀⡰⣾⣿⣿⣿⣿⣿⠰⣟⣧⣛⢹⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⠀⢸⡃⠀⡻⢼⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣟⢿⣿⣿⡗⠄⠀⠀⠠⠀⠁⢻⣿⣿⣿⣿⣿⠇⣿⠶⣭⢳⣸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⠀⢸⡀⠀⣏⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠟⠉⢰⣿⣿⣮⡁⠀⠀⠀⠀⠀⠀⠀⠄⢉⠛⠻⢿⣿⡇⣿⣻⢧⣻⡚⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⠀⢸⠀⠀⡷⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠫⣑⡜⠀⠀⠈⢿⣿⣿⣿⣗⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢱⣊⠣⣙⢿⣟⡶⡇⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⠀⢸⠀⢈⡇⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⢟⡫⠌⠇⠻⡉⠀⠀⠀⠈⢿⣿⣿⠟⡀⠀⠀⡀⠂⠀⠀⠀⠀⠀⠠⣌⠳⠌⠻⡽⢷⡏⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⠀⢼⠂⢨⣇⣿⣿⣿⣿⣿⣿⣿⡿⢟⣫⣇⡄⠛⢌⠀⠀⠄⢠⠀⠀⠀⢀⠈⠻⣿⠀⠐⢤⠃⢀⢂⡐⠨⠀⠀⠀⠐⢈⠥⠨⠔⠈⣂⠌⢝⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⠀⢸⠂⢰⢸⣿⣿⣿⣿⣿⣿⢟⣵⡾⠟⢣⠴⢿⡗⡈⠆⠀⠄⠀⠀⠀⠀⢠⠈⠀⠀⠀⣿⡿⢿⣣⣜⠂⠀⠀⠀⢨⣴⣿⣿⣿⣻⡔⠘⣄⢃⠈⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⠂⢹⡀⢸⢸⣿⣿⣿⣿⣿⡟⣼⣯⠭⠌⠟⠩⠌⠔⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠠⢋⡴⠿⠟⠁⠀⠀⠀⠀⢺⣿⣿⣿⣷⢯⣷⡀⢩⠆⡈⠄⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⠡⢸⡀⢺⢸⣿⡿⠛⣿⣿⢿⣿⣷⣆⣀⠀⠀⠀⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⠿⠹⣿⣯⠷⠸⡅⢨⠐⠀⠀⠈⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⡂⢼⡁⢎⣼⣿⣧⠀⠹⣛⣾⣿⠿⠻⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⣀⣀⣀⣀⣠⣤⣤⣤⣤⣤⣄⡀⢀⠀⠀⠀⠉⠛⡄⠂⣡⣬⣤⣤⢀⠀⠻⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⡅⢺⠀⡎⣾⣿⢃⣼⣺⣿⣿⣿⠟⠃⠀⠀⠀⠀⢀⠀⠀⠀⢀⣴⣿⣿⣿⣿⣿⠿⠿⠿⠿⠿⠿⠿⠿⠿⠻⠟⠻⠓⠀⠀⠀⠀⢾⣾⣿⣿⣿⣿⣇⠒⢢⠀⠑⠏⡼⣸⣿⣿⣿⣿⣿
⡌⣽⠀⠱⣾⠏⡟⣿⣾⣿⣿⠟⡡⠂⠀⠄⠈⣰⡆⠀⠀⠀⣠⣽⣿⣷⣶⣶⣶⣶⡶⢶⣶⣶⣶⡶⠶⠖⠒⠺⢶⠀⠀⠀⠀⠀⠈⢻⣿⣿⣿⣿⣿⣧⡀⢇⠀⠀⢷⡇⣿⣿⣿⣿⣿
⠖⡼⠀⢀⣭⡾⢃⣿⠟⠉⡑⠀⠄⠡⠈⢀⣴⣿⣿⡀⣠⣾⣿⣿⣷⣶⣶⣿⣾⣿⡿⠷⣷⡾⠿⠿⠿⢿⢿⣿⣶⡄⠀⠀⠀⠀⣸⣷⡙⣿⣿⣿⣿⠟⢣⠈⠆⠈⠈⠀⢻⣿⣿⣿⣿
⡍⢞⢠⣿⣿⡇⠌⢻⠀⠀⠀⠌⠂⠀⢠⣾⣿⣿⣿⣿⢿⢿⣛⣻⣍⣩⣤⣶⣶⣶⣶⣿⣷⣿⣿⣿⣤⣤⣶⢶⠏⠀⠀⠀⠀⢰⡿⣇⣿⣌⢿⣿⣿⡆⠸⣌⢿⣧⠀⢀⡒⡈⢻⣿⣿
⠜⢊⣼⣿⣿⡷⣴⠞⠀⠠⠀⠀⢀⣴⣿⣿⣿⣿⣿⣵⣿⣿⠿⠟⠩⠝⠛⠛⠛⠙⢋⠉⠉⢉⠉⢉⣉⣁⣠⣤⡄⠀⠀⠀⠀⣾⣻⣯⢻⣿⡶⡙⢁⣤⡴⣿⣾⣿⡇⢰⣚⡐⡀⠻⣿
⠃⣾⣿⣿⡸⠰⠃⡎⠐⠀⠁⢠⣿⣿⣿⣿⣿⣿⣿⣿⢰⣶⣿⣿⡿⠿⠿⣿⠿⠿⠿⠛⠛⣿⠿⣛⣉⣉⣙⣏⠀⠀⠀⠀⠀⣡⠣⢿⣽⣿⣇⣿⣦⢻⣿⣿⣿⡿⠓⠈⣸⣵⠄⠀⠘
⢰⣿⣛⣛⡇⠂⢀⣠⣤⡀⢠⣿⣿⣿⠿⠿⠿⠿⠿⠿⢠⣿⣶⣶⣶⣶⣾⣿⣿⣿⣷⣦⣤⣶⣾⣿⣿⡿⠟⣛⣋⡀⠀⠀⠐⠙⠲⣭⣜⣻⣟⡻⠿⠷⣹⣷⢟⡀⠀⣀⣿⣿⣮⠈⡠
⣿⣿⣿⣿⣿⣶⠈⠹⠿⠋⢸⡇⠨⢐⢊⡐⠌⠒⠈⣀⣤⣿⣯⣭⡽⢽⣿⣿⣛⣛⣛⣿⣿⣛⣫⡭⠶⠾⠿⠿⢛⠃⠀⠀⠀⣃⠀⠀⠈⢿⣿⡿⠋⠁⠸⠤⣤⣾⠿⡛⠋⠉⠀⠀⠀
⢷⣤⣤⣛⠿⠃⠀⠐⣠⡄⢸⣧⠀⠀⢂⠌⠈⠂⠀⠉⢹⣿⣿⣿⣿⣿⣶⣶⣄⣬⣵⣶⣶⣽⣶⣾⣿⣶⡾⠿⠿⠷⠀⠀⠀⢃⠀⠀⠀⠸⣿⠇⠀⠀⠀⣹⠟⡃⠐⣀⡠⠤⠖⡞⠖
⣜⠻⢿⣿⣿⠖⠀⠀⠉⢡⣿⣿⣧⠀⠀⠀⠀⠀⠀⠄⠀⠉⢉⣉⠉⢉⣭⣭⣭⣤⣍⡩⢍⣭⣥⣤⣤⣤⣤⣤⣶⣾⣷⡆⠀⢁⠀⠀⠀⠀⠃⠀⠀⢀⣾⣿⣗⠏⠙⠁⠀⠀⠀⢀⠀
⣑⣿⣶⡶⣦⡄⠀⠀⣴⣿⣿⠉⠁⠉⡄⠂⠐⠀⠘⠀⢰⣶⣿⣿⣶⣾⣇⣙⣛⣛⣛⣓⣚⣛⣛⣛⣛⣛⣛⣛⣋⣭⣍⠀⠀⠈⠄⠂⡔⣲⣶⣶⣶⡛⠙⠹⣝⡵⠤⠀⠆⠂⠑⠊⠀
⣿⣭⣭⣍⣃⠀⠀⢰⣿⣿⣿⣶⡞⠯⢔⣚⣫⣭⣭⣴⣽⣟⡿⠿⠿⠿⠿⣿⣿⣿⣿⡿⠿⠿⠿⠿⠿⠿⠿⠿⠟⢿⣯⣷⣶⣶⣶⣤⣤⣍⣚⣹⣿⣿⣿⣿⣿⣦⡄⣀⣀⠠⡄⠎⠀
⠈⡘⠟⠛⠋⠀⠀⠋⢷⡻⢝⣥⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣿⣾⣥⣴⣶⣶⣶⣶⣶⣷⣾⣷⣿⣾⣿⣿⣿⣷⣷⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⣋⠀⠀⠀⠀⠀⠀⠘
⡂⡜⠶⣰⠄⠀⠀⠀⠈⢓⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣯⣽⣿⣿⢿⣟⣛⣿⣻⡻⣿⣿⣿⣍⣭⣭⣦⣶⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⢿⣿⣿⡏⠂⠅⠃⠀⠀⠀⠀⠀⠀
*/