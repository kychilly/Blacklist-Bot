package com.Discord.DiscordBot.commands;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CommandManager extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equalsIgnoreCase("blacklist")) {
            BlacklistCommand.execute(event);
        } else if (command.equalsIgnoreCase("removeblacklist")) {
            RemoveBlacklistCommand.execute(event);
        } else if (command.equalsIgnoreCase("viewblacklist")) {
            ViewBlacklistCommand.execute(event);
        }

    }

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();

        //blacklisting commands
        commandData.add(BlacklistCommand.getCommandData());
        commandData.add(RemoveBlacklistCommand.getCommandData());
        commandData.add(ViewBlacklistCommand.getCommandData());
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        List<CommandData> commandData = new ArrayList<>();

        // Blacklisting commands
        commandData.add(BlacklistCommand.getCommandData());
        commandData.add(RemoveBlacklistCommand.getCommandData());
        commandData.add(ViewBlacklistCommand.getCommandData());

        // Update commands for the newly joined guild
        event.getGuild().updateCommands()
                .addCommands(commandData)
                .queue(
                        success -> System.out.println("✅ Blacklist commands registered in " + event.getGuild().getName()),
                        error -> System.err.println("❌ Failed to register commands in " + event.getGuild().getName() + ": " + error.getMessage())
                );
    }
}