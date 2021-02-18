package plugin.midorin.info.chrooni.chrooni;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Team;
import plugin.midorin.info.chrooni.ChrooniPlugin;
import plugin.midorin.info.chrooni.util.CustomTeam;
import plugin.midorin.info.chrooni.util.MainScoreboard;

import java.util.ArrayList;
import java.util.List;

public class Game
{
    public int before, countdown;
    public BukkitRunnable timer;
    public List<Location> spawnpoints = new ArrayList<Location>();

    public Game(int before, int countdown)
    {
        this.before = before;
        this.countdown = countdown;
    }

    public void help()
    {
    }

    public void start()
    {
        BukkitRunnable task = new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if (before == 0)
                {
                    this.cancel();
                    ChrooniPlugin.main.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                    for (Player players : Bukkit.getOnlinePlayers())
                    {
                        players.playSound(players.getLocation(), Sound.ENTITY_ENDERDRAGON_AMBIENT, 10, 1);
                        players.sendTitle(
                                ChatColor.BLUE + "Chro" + ChatColor.WHITE + "鬼ごっこ",
                                ChatColor.WHITE + "START", 0, 10, 30
                        );
                        //players.setGameMode(GameMode.ADVENTURE);
                    }
                    Bukkit.broadcastMessage(ChatColor.AQUA + "Chro鬼ごっこ開始");
                    timer = new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            getBoard().objective.getScoreboard().resetScores(getBoard().time.getEntry());
                            getBoard().time = getBoard().objective.getScore(ChatColor.GREEN + "残り時間: " + countdown + "秒");
                            getBoard().time.setScore(0);
                            if (countdown == 0)
                            {
                                this.cancel();
                                for (Player players : Bukkit.getOnlinePlayers())
                                {
                                    players.sendTitle(
                                            ChatColor.BLUE + "Chro" + ChatColor.WHITE + "鬼ごっこ",
                                            ChatColor.WHITE + "終了", 0, 10, 30
                                    );
                                    players.playSound(players.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
                                }
                                Bukkit.broadcastMessage(ChatColor.AQUA + "Chro鬼ごっこ終了");
                            }
                            countdown--;
                        }
                    };
                    timer.runTaskTimer(ChrooniPlugin.getPlugin(), 0L, 20L);
                }
                else
                {
                    for (Player players : Bukkit.getOnlinePlayers())
                    {
                        players.sendTitle(ChatColor.LIGHT_PURPLE + "--->>--- " + before + " ---<<---", "", 10, 10, 10);
                        players.playSound(players.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    }
                }
                before--;
            }
        };
        task.runTaskTimer(ChrooniPlugin.getPlugin(), 0L, 20L);
    }

    public void finish()
    {
        timer.cancel();
        for (Player players : Bukkit.getOnlinePlayers())
        {
            players.sendTitle(
                    ChatColor.BLUE + "Chro" + ChatColor.WHITE + "鬼ごっこ",
                    ChatColor.WHITE + "終了", 10, 10, 30
            );
            players.playSound(players.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 10, 1);
            Bukkit.broadcastMessage(ChatColor.AQUA + "Chro鬼ごっこ終了");
        }
    }

    public void reset()
    {
        if (timer != null && !timer.isCancelled()) timer.cancel();
        getBoard().reset();
        ChrooniPlugin.oni.reset();
        ChrooniPlugin.nige.reset();
        before = 3;
        countdown = 900;

        ChrooniPlugin.main = new MainScoreboard();
        ChrooniPlugin.main.setup("Chrooni");
        ChrooniPlugin.oni = new CustomTeam("oni", ChrooniPlugin.main.getBoard());
        ChrooniPlugin.oni.setup(
                ChatColor.RED + "Oni",
                ChatColor.RED.toString(),
                ChatColor.RESET.toString(),
                Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS
        );
        ChrooniPlugin.nige = new CustomTeam("nige", ChrooniPlugin.main.getBoard());
        ChrooniPlugin.nige.setup(
                ChatColor.GRAY + "Nige",
                ChatColor.GRAY.toString(),
                ChatColor.RESET.toString(),
                Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS
        );
        ChrooniPlugin.game = new Game(3, 900);
        Bukkit.broadcastMessage(ChatColor.BLUE + "Chro" + ChatColor.WHITE + "鬼: ゲームのリセットが完了しました。");
    }

    public MainScoreboard getBoard()
    {
        return ChrooniPlugin.main;
    }
}
