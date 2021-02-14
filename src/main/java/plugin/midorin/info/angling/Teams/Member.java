package plugin.midorin.info.angling.Teams;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import plugin.midorin.info.angling.Scoreboards;
import plugin.midorin.info.angling.util.Messages;

public class Member
{
    public static final String TEAM = "member";
    public static Team team;
    public static void setup()
    {
        team = Scoreboards.board.getTeam(TEAM);
        if (team == null)
        {
            team = Scoreboards.board.registerNewTeam(TEAM);
            team.setPrefix(ChatColor.GREEN.toString());
            team.setSuffix(ChatColor.RESET.toString());
            team.setDisplayName(ChatColor.GREEN + "member");
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
            team.setAllowFriendlyFire(false);
        }
    }
    public static void addPlayer(Player player, boolean message)
    {
        if (team.hasEntry(player.getName())) return;
        team.addEntry(player.getName());
        if (message)
            Bukkit.broadcastMessage(Messages.PREFIX + ChatColor.GRAY + player.getName() + "がゲームに参加しました。");
    }
    public static void removePlayer(Player player, boolean message)
    {
        if (!team.hasEntry(player.getName())) return;
        team.removeEntry(player.getName());
        if (message)
            Bukkit.broadcastMessage(Messages.PREFIX + ChatColor.GRAY + player.getName() + "がゲームから離脱しました。");
    }
}
