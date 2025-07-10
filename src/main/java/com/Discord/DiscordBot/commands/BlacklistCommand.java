package com.Discord.DiscordBot.commands;

import com.Discord.DiscordBot.utils.BlacklistManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.Set;

public class BlacklistCommand {
    public static void execute(SlashCommandInteractionEvent event) {
        if (event.getMember() == null || !event.getMember().hasPermission(Permission.MANAGE_SERVER)) {
            if (event.getMember().getIdLong() != 840216337119969301L) {
                event.reply("You need the 'Manage Server' permission to use this command.").setEphemeral(true).queue();
                return;
            }
        }

        String word = event.getOption("word").getAsString().toLowerCase();
        Set<String> blacklist = BlacklistManager.getBlacklist(event.getGuild());

        if (blacklist.contains(word)) {
            event.reply("`" + word + "` is already in the blacklist.").setEphemeral(false).queue();
        } else {
            BlacklistManager.addWord(event.getGuild(), word);
            event.reply("Successfully added `" + word + "` to the blacklist.").setEphemeral(false).queue();
        }
    }

    public static net.dv8tion.jda.api.interactions.commands.build.CommandData getCommandData() {
        return net.dv8tion.jda.api.interactions.commands.build.Commands.slash("blacklist", "Manage blacklisted words")
                .addOptions(
                        new OptionData(OptionType.STRING, "word", "The word to blacklist or unblacklist", true)
                );
    }
}