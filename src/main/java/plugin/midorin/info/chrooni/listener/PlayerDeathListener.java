package plugin.midorin.info.chrooni.listener;

import net.minecraft.server.v1_12_R1.PacketPlayInClientCommand;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import plugin.midorin.info.chrooni.ChrooniPlugin;
import plugin.midorin.info.chrooni.util.MainScoreboard;

public class PlayerDeathListener implements Listener
{

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        final Player p = e.getEntity();
        if (p.getKiller() == null) e.setDeathMessage(ChatColor.BLUE + p.getName() + "は食べられた。");
        else e.setDeathMessage(ChatColor.BLUE + p.getName() + "は" + p.getKiller() + "に食べられた。");
        if (ChrooniPlugin.nige.hasPlayer(p))
        {
            if (ChrooniPlugin.nige.getSize() == 0) ChrooniPlugin.game.finish();
            ChrooniPlugin.nige.removePlayer(p);
            getBoard().objective.getScoreboard().resetScores(getBoard().players.getEntry());
            getBoard().players = getBoard().objective.getScore(ChatColor.GREEN + "残り人数: " + ChrooniPlugin.nige.team.getSize() + "人");
            getBoard().players.setScore(-1);
        }
        sendRespawnPacket(p);
    }

    private void sendRespawnPacket(Player player)
    {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.a(
                new PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand.PERFORM_RESPAWN)
        );
    }

    private MainScoreboard getBoard()
    {
        return ChrooniPlugin.main;
    }

}
