package plugin.midorin.info.angling;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import plugin.midorin.info.angling.commands.Ang;

public class Angling extends JavaPlugin
{
    public static JavaPlugin plugin;

    @Override
    public void onEnable()
    {
        plugin = this;
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);

        getCommand("angling").setExecutor(new Ang());

        Scoreboards.setup();
        Items.setup();
    }

    @Override
    public void onDisable()
    {
        Game.reset();
    }

    public static JavaPlugin getPlugin()
    {
        return plugin;
    }
}
