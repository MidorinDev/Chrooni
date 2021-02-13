package plugin.midorin.info.chrooni;

public class ChrooniPlugin extends AbstractChrooniPlugin
{
    public static ChrooniPlugin plugin;

    @Override
    public void onEnable()
    {
        super.onEnable();
    }

    @Override
    public void onDisable()
    {
        super.onDisable();
    }

    public static ChrooniPlugin getPlugin()
    {
        return plugin;
    }
}
