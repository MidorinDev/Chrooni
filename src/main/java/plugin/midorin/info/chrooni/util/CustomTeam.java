package plugin.midorin.info.chrooni.util;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import plugin.midorin.info.chrooni.ChrooniPlugin;

public class CustomTeam
{
    public final String TEAM;
    public Scoreboard board;
    public Team team;

    public CustomTeam(String team_name, Scoreboard scoreboard)
    {
        this.TEAM = team_name;
        this.board = scoreboard;
    }

    public void setup(String name, String prefix, String suffix)
    {
        team = board.getTeam(TEAM);
        if (team == null)
        {
            team = board.registerNewTeam(TEAM);
            team.setPrefix(prefix);
            team.setPrefix(suffix);
            team.setDisplayName(name);
            team.setAllowFriendlyFire(false);
        }
    }

    public void setup(String name, String prefix, String suffix, Team.Option option, Team.OptionStatus optionStatus)
    {
        team = board.getTeam(TEAM);
        if (team == null)
        {
            team = board.registerNewTeam(TEAM);
            team.setPrefix(prefix);
            team.setPrefix(suffix);
            team.setDisplayName(name);
            team.setOption(option, optionStatus);
            team.setAllowFriendlyFire(false);
        }
    }

    public void reset()
    {
        team.unregister();
    }

    public void addPlayer(Player player)
    {
        team.addEntry(player.getName());
    }

    public void removePlayer(Player player)
    {
        team.removeEntry(player.getName());
    }

    public boolean hasPlayer(Player player)
    {
        return team.hasEntry(player.getName());
    }

    public int getSize()
    {
        return team.getSize();
    }
}
