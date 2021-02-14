package plugin.midorin.info.angling;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import plugin.midorin.info.angling.util.Messages;

public class Game
{
    public static int before = 5;
    public static int countup = 0;
    public static int m = 0;
    public static BukkitRunnable timer;
    public static boolean now = false;

    public static void help1(CommandSender sender)
    {
    }
    public static void start_count()
    {
        BukkitRunnable task1 = new BukkitRunnable()
        {
            public void run()
            {
                if (before == 0)
                {
                    this.cancel();
                    start();
                }
                else
                {
                    for (Player ap : Bukkit.getOnlinePlayers())
                    {
                        ap.sendTitle(ChatColor.GREEN + " " + before + " ", ChatColor.GRAY + "開始まで・・・", 10, 10, 10);
                        ap.getLocation().getWorld().playSound(ap.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    }
                }
                before--;
            }
        };
        task1.runTaskTimer(Angling.getPlugin(), 0L, 20L);
    }
    public static void start()
    {
        now = true;
        Scoreboards.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        for (Player ap : Bukkit.getOnlinePlayers())
        {
            Location loc = ap.getPlayer().getLocation();
            loc.getWorld().playSound(loc, Sound.ENTITY_ENDERDRAGON_AMBIENT, 50, 1);
            ap.sendTitle(ChatColor.GOLD + "ゲーム開始！", ChatColor.GRAY + "Angling", 10, 10, 10);
            ap.setGameMode(GameMode.ADVENTURE);
            ap.getInventory().addItem(new ItemStack(Items.fishing_rod));
        }
        System.out.println(m);
        timer = new BukkitRunnable()
        {
            public void run()
            {
                if (!(countup == 0) && (countup % 60) == 0) m=m+1;
                if (countup >= 60) Scoreboards.setTime(m, countup - m * 60);
                else Scoreboards.setTime(m, countup);
                Scoreboards.objective.getScoreboard().resetScores(Scoreboards.players.getEntry());
                Scoreboards.players = Scoreboards.objective.getScore(ChatColor.RED + "  参加人数:  " + ChatColor.WHITE + "0人");
                Scoreboards.players.setScore(-4);

                if (countup == 600)
                {
                    finish();
                }
                countup++;
            }
        };
        timer.runTaskTimer(Angling.getPlugin(), 0L, 20L);
    }
    public static void finish()
    {
        for (Player ap : Bukkit.getOnlinePlayers())
        {
            ap.sendTitle(ChatColor.GOLD + "ゲーム終了", ChatColor.GRAY + "Angling", 10, 10 ,10);
            ap.getLocation().getWorld().playSound(ap.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 50, 1);
            ap.setGameMode(GameMode.SPECTATOR);
        }
        timer.cancel();
    }
    public static void reset()
    {
        if (timer != null) timer.cancel();
        now = false;
        Scoreboards.reset();
        before = 5;
        countup = 0;
        m = 0;
        //if (BlockSeek.players != null) BlockSeek.players.clear();
    }
}
