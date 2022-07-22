package net.buycraft.plugin.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import net.buycraft.plugin.velocity.BuycraftPlugin;
import net.buycraft.plugin.velocity.util.ColorAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;


public class ForceCheckSubcommand implements Subcommand {
    private final BuycraftPlugin plugin;

    public ForceCheckSubcommand(final BuycraftPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSource sender, String[] args) {
        if (args.length != 0) {
            sender.sendMessage(Component.text(plugin.getI18n().get("no_params")).color(TextColor.fromHexString(ColorAPI.color("&c"))));
            return;
        }

        if (plugin.getApiClient() == null) {
            sender.sendMessage(Component.text(plugin.getI18n().get("need_secret_key")).color(TextColor.fromHexString(ColorAPI.color("&c"))));
            return;
        }

        if (plugin.getDuePlayerFetcher().inProgress()) {
            sender.sendMessage(Component.text(plugin.getI18n().get("already_checking_for_purchases")).color(TextColor.fromHexString(ColorAPI.color("&c"))));
            return;
        }

        plugin.getPlatform().executeAsync(() -> plugin.getDuePlayerFetcher().run(false));
        sender.sendMessage(Component.text(plugin.getI18n().get("forcecheck_queued")).color(TextColor.fromHexString(ColorAPI.color("&a"))));
    }

    @Override
    public String getDescription() {
        return plugin.getI18n().get("usage_forcecheck");
    }
}
