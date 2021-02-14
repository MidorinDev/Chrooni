package plugin.midorin.info.angling;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items
{
    public static void setup()
    {
        itemMeta1.setDisplayName(ChatColor.AQUA + "釣り竿");
        fishing_rod.setItemMeta(itemMeta1);
    }
    public static ItemStack fishing_rod = new ItemStack(Material.FISHING_ROD);
    public static ItemMeta itemMeta1 = fishing_rod.getItemMeta();
}
