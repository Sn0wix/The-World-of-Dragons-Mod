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
    public RockProjectile(EntityType<? extends SnowballEntity> entityType, World world) {
        super(entityType, world);
    }

    public RockProjectile(World world, PlayerEntity user) {
        super(world, user);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), 1);
    }

    @Override
    public ItemStack getStack() {
        return new ItemStack(ModItems.THE_ROCK);
    }

    @Nullable
    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(ModItems.THE_ROCK);
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