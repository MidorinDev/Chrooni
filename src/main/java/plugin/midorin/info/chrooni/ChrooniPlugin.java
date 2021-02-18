package plugin.midorin.info.chrooni;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;
import plugin.midorin.info.chrooni.chrooni.Game;
import plugin.midorin.info.chrooni.command.Main;
import plugin.midorin.info.chrooni.listener.InventoryClickListener;
import plugin.midorin.info.chrooni.listener.PlayerDeathListener;
import plugin.midorin.info.chrooni.listener.PlayerInteractListener;
import plugin.midorin.info.chrooni.util.CustomTeam;
import plugin.midorin.info.chrooni.util.MainScoreboard;

public class ChrooniPlugin extends AbstractChrooniPlugin
{
    public static ChrooniPlugin plugin;
    public static MainScoreboard main;
    public static CustomTeam oni;
    public static CustomTeam nige;
    public static Game game;

    @Override
    public void onEnable()
    {
        super.onEnable();

        plugin = this;

        main = new MainScoreboard();
        main.setup("Chrooni");
        oni = new CustomTeam("oni", main.getBoard());
        oni.setup(
                ChatColor.RED + "Oni",
                ChatColor.RED.toString(),
                ChatColor.RESET.toString(),
                Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS
        );
        nige = new CustomTeam("nige", main.getBoard());
        nige.setup(
                ChatColor.GRAY + "Nige",
                ChatColor.GRAY.toString(),
                ChatColor.RESET.toString(),
                Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS
        );

        game = new Game(3, 900);

        registerListeners(
                new PlayerDeathListener(),
                new InventoryClickListener(),
                new PlayerInteractListener()
        );

        getCommand("chrooni").setExecutor(new Main());
    }

    @Override
    public void onDisable()
    {
        super.onDisable();

        main.reset();
        oni.reset();
        nige.reset();
    }

    public static ChrooniPlugin getPlugin()
    {
        return plugin;
    }
}
