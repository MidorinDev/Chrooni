package plugin.midorin.info.chrooni.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import plugin.midorin.info.chrooni.ChrooniPlugin;

public class PlayerInteractListener implements Listener
{

    @EventHandler
    public void onClickBlock(PlayerInteractEvent e)
    {
        final Block block = e.getClickedBlock();
        final Player p = e.getPlayer();

        if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
        if (block.getType() == Material.GOLD_BLOCK)
        {
            e.setCancelled(true);
            if (!ChrooniPlugin.nige.hasPlayer(p))
            {
                p.teleport(ChrooniPlugin.game.spawnpoints.get(0));
                ChrooniPlugin.nige.addPlayer(p);
                Bukkit.broadcastMessage(ChatColor.GOLD + p.getName() + "さんが復活しました。");
            }
        }
    }

}
