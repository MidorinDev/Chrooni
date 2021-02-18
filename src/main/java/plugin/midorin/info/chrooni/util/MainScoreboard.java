package plugin.midorin.info.chrooni.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class MainScoreboard
{
    public Scoreboard board = Bukkit.getScoreboardManager().getMainScoreboard();
    public Objective objective;
    public Score time;
    public Score players;

    public MainScoreboard()
    {
        objective = board.registerNewObjective("chrooni_board", "dummy");
        time = objective.getScore(ChatColor.GREEN + "残り時間: 0秒");
        players = objective.getScore(ChatColor.GOLD + "残り人数: 0人");
    }

    public void setup(String title)
    {
        objective.setDisplayName(title);
        time.setScore(0);
        players.setScore(-1);
    }

    public void reset()
    {
        objective.getScoreboard().resetScores(time.getEntry());
        objective.getScoreboard().resetScores(players.getEntry());
        objective.unregister();
    }

    public Scoreboard getBoard()
    {
        return board;
    }
}
