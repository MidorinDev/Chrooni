package plugin.midorin.info.angling.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_12_R1.*;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class NPCManager
{
    private static EntityPlayer ep;

    public static void create(Location location, String name, String skin, String signature)
    {
        MinecraftServer ms = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer ws = ((CraftWorld) location.getWorld()).getHandle();

        GameProfile gp = new GameProfile(UUID.randomUUID(), ChatColor.translateAlternateColorCodes('&', name));
        gp.getProperties().put("textures", new Property("textures", skin, signature));

        ep = new EntityPlayer(ms, ws, gp, new PlayerInteractManager(ws));
        ep.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public static void show(Player player)
    {
        PlayerConnection pc = ((CraftPlayer) player).getHandle().playerConnection;
        pc.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ep));
        pc.sendPacket(new PacketPlayOutNamedEntitySpawn(ep));
    }

    public static String getSkin(UUID player, String key)
    {
        String strPlayer = player.toString().replace("-", "");
        String url = "https://sessionserver.mojang.com/session/minecraft/profile/" + strPlayer + "?unsigned=false";
        try
        {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                return null;

            try (InputStream stream = connection.getInputStream();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));)
            {
                StringBuilder o = new StringBuilder();
                String l;
                while ((l = bufferedReader.readLine()) != null)
                    o.append(l);
                JsonNode tree = new ObjectMapper().readTree(o.toString());
                return tree.get("properties").get(key).asText();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static Pair<String, String> getSkin(UUID player)
    {
        String strPlayer = player.toString().replace("-", "");
        String url = "https://sessionserver.mojang.com/session/minecraft/profile/" + strPlayer + "?unsigned=false";
        try
        {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK)
                return Pair.of("", "");

            try (InputStream stream = connection.getInputStream();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));)
            {
                StringBuilder o = new StringBuilder();
                String l;
                while ((l = bufferedReader.readLine()) != null)
                    o.append(l);
                JsonNode tree = new ObjectMapper().readTree(o.toString());
                return Pair.of(tree.get("properties").get(0).get("value").asText(), tree.get("properties").get(0).get("signature").asText());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Pair.of("", "");
        }
    }
}
