package plugin.midorin.info.chrooni.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import plugin.midorin.info.chrooni.ChrooniPlugin;
import plugin.midorin.info.chrooni.chrooni.Game;

public class Main implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender.hasPermission("chrooni.commands.game"))
        {
            if (args.length >= 1)
            {
                switch (args[0])
                {
                    case "start":
                    {
                        getGame().start();
                        break;
                    }
                    case "finish":
                    {
                        getGame().finish();
                        break;
                    }
                    case "reset":
                    {
                        getGame().reset();
                        break;
                    }
                    case "oni":
                    {

                        break;
                    }
                    default:
                    {
                        sender.sendMessage(ChatColor.RED + "そのサブコマンドは存在しません。");
                        break;
                    }
                }
            }
            else sender.sendMessage(ChatColor.RED + "引数が足りません。");
        }
        else sender.sendMessage(ChatColor.RED + "権限がありません。");
        return true;
    }

    public Game getGame()
    {
        return ChrooniPlugin.game;
    }

}
