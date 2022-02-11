package me.loremente.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.UUID;

public class SidebarListener implements Listener {

    private HashMap<UUID, Integer> blocksBroken = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("testboard", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.GREEN + "Scoreboard");

        Score website = obj.getScore(ChatColor.YELLOW + "www.stocazzo.com");
        website.setScore(1);

        Score space = obj.getScore("");
        space.setScore(2);

        Team blocksBroken = board.registerNewTeam("Blocchi rotti");
        blocksBroken.addEntry(ChatColor.BOLD.toString());
        blocksBroken.setPrefix(ChatColor.BLUE + "Blocchi rotti: ");
        blocksBroken.setSuffix(ChatColor.YELLOW + "0");
        obj.getScore(ChatColor.BOLD.toString()).setScore(3);

        player.setScoreboard(board);

        this.blocksBroken.put(player.getUniqueId(),0);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        Player player = e.getPlayer();

        int amount = blocksBroken.get(player.getUniqueId());
        amount++;

        blocksBroken.put(player.getUniqueId(), amount);
        player.getScoreboard().getTeam("blocchirotti").setSuffix(ChatColor.YELLOW.toString() + amount);
    }
}
