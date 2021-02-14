package plugin.midorin.info.angling.commands;

import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import plugin.midorin.info.angling.Game;
import plugin.midorin.info.angling.util.Messages;
import plugin.midorin.info.angling.util.NPCManager;

import java.util.Map;

public class Ang implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        Player p = (Player) sender;
        if (sender.isOp() || sender.hasPermission("angling.commands"))
        {
            try
            {
                if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("help1"))
                    Game.help1(sender);
                else if (args[0].equalsIgnoreCase("start"))
                    Game.start_count();
                else if (args[0].equalsIgnoreCase("finish"))
                    Game.finish();
                else if (args[0].equalsIgnoreCase("reset"))
                    Game.reset();
                else if (args[0].equalsIgnoreCase("spawn"))
                {
                    Player target = Bukkit.getPlayer(args[1]);
                    Pair<String, String> data = NPCManager.getSkin(target.getUniqueId());
                    NPCManager.create
                            (
                                    p.getLocation(),
                                    target.getName(),
                                    data.getLeft(),
                                    data.getRight()
                            );
                    NPCManager.show(p);
                    sender.sendMessage("npc spawned");
                }
            }
            catch (Exception ex) { Game.help1(sender); }
        }
        else Messages.sendPermissionError(sender);
        return true;
    }
}
