package net.sn0wix_.worldofdragonsmod.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import software.bernie.geckolib.animatable.GeoEntity;

import java.util.ArrayList;

public class TriggerAnimCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("triggerAnim").then(CommandManager.argument("targets", EntityArgumentType.entities()).then(CommandManager.argument("controller", StringArgumentType.word()).then(CommandManager.argument("animation", StringArgumentType.word()).executes(TriggerAnimCommand::run)))));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String controller = StringArgumentType.getString(context, "controller");
        String anim = StringArgumentType.getString(context, "animation");
        ArrayList<String> entityNames = new ArrayList<>();

        EntityArgumentType.getEntities(context, "targets").forEach(entity -> {
            if (entity instanceof GeoEntity geo) {
                geo.triggerAnim(controller, anim);
                entityNames.add(entity.getName().getString());
            }
        });

        context.getSource().sendFeedback(() -> Text.literal("Triggered animation " + anim + " for entities " + entityNames), false);
        return 1;
    }
}
