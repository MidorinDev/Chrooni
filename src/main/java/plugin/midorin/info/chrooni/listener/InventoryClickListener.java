package plugin.midorin.info.chrooni.listener;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClickListener implements Listener
{

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e)
    {
        if (e.getWhoClicked().getGameMode() == GameMode.ADVENTURE && e.getSlotType() == InventoryType.SlotType.ARMOR)
            e.setCancelled(true);
    }

}