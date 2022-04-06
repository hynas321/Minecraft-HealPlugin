package _.HealPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class HealCommand implements CommandExecutor {

    private final HealPlugin healPlugin;

    public HealCommand(HealPlugin healPlugin) {
        this.healPlugin = healPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (healPlugin.getAdminOnly() && !player.isOp()) {
                player.sendMessage(ChatColor.YELLOW + "HealPlugin: " + ChatColor.RED + "You do not have permissions to use this command");
                return false;
            }

            if (args.length == 0) {
                healPlayer(Objects.requireNonNull(player.getPlayer()));
                return true;
            }

            if (args.length == 1 && args[0].toString().equals("get")) {
                player.sendMessage(ChatColor.YELLOW + "HealPlugin: " + ChatColor.GREEN + "Heal value = " + healPlugin.getHealValue());
                return true;
            }
            if (args.length == 1 && args[0].toString().equals("help")) {
                printHelp(player);
                return true;
            }
            if (args.length == 1) {
                try {
                    healPlayer(Objects.requireNonNull(Bukkit.getPlayerExact(args[0])));
                    return true;
                }
                catch (Exception e) {
                    player.sendMessage(ChatColor.YELLOW + "HealPlugin: " + ChatColor.RED + "Player does not exist");
                    return false;
                }
            }

            if (args.length == 2 && args[0].toString().equals("set")) {
                try {
                    healPlugin.setHealValue(Double.parseDouble(args[1]));
                    player.sendMessage(ChatColor.YELLOW + "HealPlugin: " + ChatColor.GREEN + "New heal value set = " + healPlugin.getHealValue());
                    return true;
                }
                catch (Exception e) {
                    player.sendMessage(ChatColor.YELLOW + "HealPlugin: " + ChatColor.RED + "Incorrect heal value");
                    return false;
                }
            }

            player.sendMessage(ChatColor.YELLOW + "HealPlugin: " + ChatColor.RED + "Incorrect command");
        }
        return false;
    }

    private void healPlayer(Player player) {
        double health = player.getHealth();

        if (health != 20) {
            health = health + healPlugin.getHealValue() <= 20 ? health + healPlugin.getHealValue() : 20;
            player.setHealth(health);
            player.sendMessage(ChatColor.YELLOW + "HealPlugin: " + ChatColor.GREEN + "Health has been increased");
        } else {
            player.sendMessage(ChatColor.YELLOW + "HealPlugin: " + ChatColor.RED + "Cannot exceed maximum health");
        }
    }

    private void printHelp(Player player) {
        player.sendMessage(ChatColor.YELLOW + "HealPlugin - help, version 1.0.0");
        player.sendMessage(ChatColor.YELLOW + "/heal - heals a player that used the command");
        player.sendMessage(ChatColor.YELLOW + "/heal <player> - heals a specified player");
        player.sendMessage(ChatColor.YELLOW + "/heal get - shows heal's value");
        player.sendMessage(ChatColor.YELLOW + "/heal set <value> - sets heal's value");
    }
}
