package _.HealPlugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class HealPlugin extends JavaPlugin {

    private boolean adminOnly;
    private double healValue;

    public HealPlugin() {
        adminOnly = getConfig().getBoolean("AdminOnly");
        healValue = getConfig().getDouble("HealValue");
    }

    @Override
    public void onEnable() {
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();

        System.out.println("HealPlugin has been enabled");
        Objects.requireNonNull(this.getCommand("heal")).setExecutor(new HealCommand(this));
    }

    @Override
    public void onDisable() {
        System.out.println("HealPlugin has been disabled");
    }

    public double getHealValue() {
        return healValue;
    }

    public void setHealValue(double healValue) {
        this.healValue = healValue;
    }

    public boolean getAdminOnly() {
        return adminOnly;
    }
}
