package xyz.eyyuepguelen.xyz.depo.commands;

import com.hakan.core.command.HCommandAdapter;
import com.hakan.core.command.executors.base.BaseCommand;
import com.hakan.core.command.executors.sub.SubCommand;
import com.hakan.core.ui.inventory.HInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import xyz.eyyuepguelen.xyz.depo.guis.MainGUI;

@BaseCommand(
        name = "depo",
        description = "Depo komutu"
)
public class DepoCMD implements HCommandAdapter {
    @SubCommand()
    public void mainCommand(Player player, String[] args) {
        HInventory gui = new MainGUI("0", ChatColor.translateAlternateColorCodes('&', "&cDepo Menü"), 3, InventoryType.CHEST);
        gui.open(player);
    }
}
