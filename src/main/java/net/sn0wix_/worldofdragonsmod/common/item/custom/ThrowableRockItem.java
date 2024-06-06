package net.sn0wix_.worldofdragonsmod.common.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SnowballItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.sn0wix_.worldofdragonsmod.common.entity.custom.misc.projectile.RockProjectile;

public class ThrowableRockItem extends SnowballItem {
    public final boolean isLava;

    public ThrowableRockItem(Settings settings, boolean isLava) {
        super(settings);
        this.isLava = isLava;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (isLava) {
            target.setOnFireFor(2);
            if (!(attacker instanceof PlayerEntity player && player.isCreative())) {
                stack.decrement(1);
            }
        }
        return isLava;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        user.getItemCooldownManager().set(this, 10);
        if (!world.isClient) {
            RockProjectile rock = new RockProjectile(world, user, isLava);
            rock.setItem(itemStack);
            rock.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 1.5f, 1.0f);
            world.spawnEntity(rock);
        }

        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }


        return TypedActionResult.success(itemStack, world.isClient());
    }
}

/*
⣿⣿⣿⣿⣿⣿⣿⣷⣿⣿⣿⣿⣿⣿⣿⣷⣷⣶⣶⣷⣷⣷⣶⣶⣶⣶⣶⣶⣶⣶⣦⣄⠻⣶⣶⣶⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣬⠻⣿⣿⡿⣿⡿⠿⠛⣻⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡈⠶⠖⠛⣋⣉⣉⣭⣤⣤⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⣿⣿⠋⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⠘⣛⣉⣭⣥⣴⣶⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⣿⡿⠁⢲⣿⣿⣿⣿⣿⣿⣿⣿⡟⠁⠔⠒⠈⣉⡉⠛⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣇⠹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣉⣉⣩⣥⣤⣤⠆⣰⣿⣿⣿⣿⣿⣿⣿⣿⣋⣤⣤⣶⢾⣿⣿⣦⣄⠀⠈⠻⢿⣿⣿⣿⣿⣿⣿⣿⣿⡆⢻⣿⣿⢸⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⣿⣿⣿⡟⢰⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠋⠀⠀⢈⣈⡀⠙⠛⠷⢦⡀⠀⡹⠋⠛⢻⣿⣿⣿⣿⡇⢸⣿⣇⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⣿⠟⠛⢿⠇⢻⣿⣿⣿⣿⣿⣿⣿⡿⠛⠀⠀⠠⠖⠛⠋⠛⠳⠀⠀⠀⠙⠆⠀⡾⠀⠀⠻⠿⢿⣿⡇⢸⣿⣗⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⡟⠀⣴⣄⡀⢾⣿⣿⣿⣿⣿⣿⣿⣿⡇⢀⣀⣤⡙⠂⠀⠀⡄⠀⠀⠀⠀⣴⣾⣧⠀⠀⠀⠀⠀⠀⠃⠘⣿⣹⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⡇⠀⣾⣿⣿⣦⣹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣭⣀⠀⠐⠃⠀⢀⣠⣼⣿⣿⣿⠀⠀⠀⠀⠀⢤⠀⢠⣿⣽⣿⣿⣿⣿⣿⣿⣿⣿⣿
⣿⡇⠘⠋⠀⢠⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠅⣠⣼⣿⣿⣿⣿⣿⣿⣦⠀⠀⠀⠀⠀⠀⡾⠿⠽⠿⠿⠿⠟⠿⠿⡿⡿⡿
⣿⣿⡀⣀⠀⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⢹⣦⣤⣤⣦⡄⠉⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⢸⣿⣧⣼⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⢡⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⢠⡀⠀⠹⣿⠇⣸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡻⢃⣾⡿⠿⢿⣿⣿⣿⠙⠻⣿⠟⢻⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠈⠓⡄⠀⢀⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⠘⠏⠀⢀⣀⣙⡛⠋⠀⠀⠈⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⠀⠇⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣤⣤⣤⣭⠀⠀⠀⢀⣠⣆⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⠀⣇⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⢩⣿⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⢀⠁⠀⠇⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠋⠹⠿⠿⠛⠋⠉⠉⠁⠀⠈⠙⠋⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⢸⠀⠀⢤⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⡁⢠⣤⣤⣄⣤⣤⣤⣀⣀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⣬⡾⠓⠀⣸⣿⣿⣿⣿⣿⡟⢿⣿⣿⣿⣿⣿⡏⢀⠄⣸⣿⣿⣿⣿⣿⠟⠉⠉⠉⠉⠀⠀⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⡯⠐⣡⣾⣿⣿⣿⣿⣿⣿⠻⣎⠻⣿⣿⣿⣿⣇⢘⣠⣹⣿⣿⣿⣿⣧⣀⡀⣀⣀⠀⢀⠀⣼⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⢁⣼⣿⣿⣿⣿⣿⣿⣿⣿⣧⡙⢷⣽⣿⣮⡻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡟⠀⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣭⢻⣿⣿⣮⣻⣿⣿⣿⣿⣿⣿⣿⡿⠟⠛⠙⠙⠃⢰⣌⠣⡀⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣾⣿⣿⣿⡟⠈⠙⠛⠿⠿⠿⠋⠀⠀⠀⠀⠀⢀⣄⣹⣇⠱⠘⣿⣷⣦⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀
⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠻⣿⣿⣿⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⠟⢻⣿⠀⡆⢸⡇⢿⣿⣷⣄⡀⠀⠀⠀⠀⠀⠀
*/