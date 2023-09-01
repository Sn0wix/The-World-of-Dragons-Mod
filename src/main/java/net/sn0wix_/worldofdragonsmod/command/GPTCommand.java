package net.sn0wix_.worldofdragonsmod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.sn0wix_.worldofdragonsmod.WorldOfDragonsMain;

public class GPTCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("chat").then(CommandManager.argument("targets", EntityArgumentType.players()).then(CommandManager.argument("message", StringArgumentType.string()).executes(GPTCommand::run))));
    }

    private static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String output = WorldOfDragonsMain.sendAiMessage(StringArgumentType.getString(context, "message"));

        EntityArgumentType.getPlayers(context, "targets").forEach(serverPlayerEntity -> serverPlayerEntity.sendMessage(Text.of("<ChatGPT> " + output)));
        return 1;
    }
}
