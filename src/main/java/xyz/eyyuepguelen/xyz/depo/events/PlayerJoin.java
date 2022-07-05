package xyz.eyyuepguelen.xyz.depo.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.eyyuepguelen.xyz.depo.Depo;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent ev) {
        Depo.getInstance().createPlayerData(ev.getPlayer());
    }
}
