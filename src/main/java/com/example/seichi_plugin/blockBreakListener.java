package com.example.seichi_plugin;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;

// プレイヤーがブロックを破壊した時に呼ばれるリスナー
public class blockBreakListener implements Listener {

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent evt){
        // for debug
        // プレイヤーの向いている方角や座標をログで見る時に使用
        // getLogger().info("sample01 PlayerMove");
        // getLogger().info("getFacing:" + evt.getPlayer().getFacing().toString());
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent evt) {

        // テンプレからコピー
        if (evt.isCancelled()) {
            return;
        }

        // 手に持っているアイテムで分岐する
        Player player = evt.getPlayer();
        switch (player.getInventory().getItemInMainHand().getData().getItemType().toString()){
            case "LEGACY_STICK":
                // 直方体で削る
                breakRectangle(evt, player);
                break;
            case "LEGACY_TORCH":
                // 逆ピラミッドの形で削る
                breakInversepyramid(evt, player);
                break;
        }
    }

    // 直方体での破壊（プレイヤーの向いている方向から、直進方向10マス、左右5マス、上方向に5マス破壊する）
    public void breakRectangle(BlockBreakEvent evt, Player player){
        // 壊したブロックの座標取得
        Location loc = evt.getBlock().getLocation();
        double curposX = loc.getX();
        double curposZ = loc.getZ();
        double curposY = loc.getY();

        switch (player.getFacing().toString()){
            case "EAST": {
                // 東向きの場合
                for (int x = 0; x < 10; x++) {
                    for (int z = -2; z <= 2; z++) {
                        for (int y = 0; y < 5; y++) {
                            Location dLoc = new Location(loc.getWorld(), curposX + x, curposY + y, curposZ + z);
                            Block b = dLoc.getBlock();
                            b.breakNaturally();
                        }
                    }
                }
                break;
            }
            case "WEST": {
                // 西向きの場合
                for (int x = 0; x > -10; x--) {
                    for (int z = -2; z <= 2; z++) {
                        for (int y = 0; y < 5; y++) {
                            Location dLoc = new Location(loc.getWorld(), curposX + x, curposY + y, curposZ + z);
                            Block b = dLoc.getBlock();
                            b.breakNaturally();
                        }
                    }
                }
                break;
            }

            case "NORTH": {
                // 北向きの場合
                for (int x = -2; x <= 2; x++) {
                    for (int z = 0; z > -10; z--) {
                        for (int y = 0; y < 5; y++) {
                            Location dLoc = new Location(loc.getWorld(), curposX + x, curposY + y, curposZ + z);
                            Block b = dLoc.getBlock();
                            b.breakNaturally();
                        }
                    }
                }
                break;
            }

            case "SOUTH": {
                // 南向きの場合
                for (int x = -2; x <= 2; x++) {
                    for (int z = 0; z < 10; z++) {
                        for (int y = 0; y < 5; y++) {
                            Location dLoc = new Location(loc.getWorld(), curposX + x, curposY + y, curposZ + z);
                            Block b = dLoc.getBlock();
                            b.breakNaturally();
                        }
                    }
                }
                break;
            }
        }
    }

    // ピラミッドを上下反転させた形で穴を掘る
    public void breakInversepyramid(BlockBreakEvent evt, Player player){

        // 壊したブロックの座標取得
        Location loc = evt.getBlock().getLocation();
        double curposX = loc.getX();
        double curposZ = loc.getZ();
        double curposY = loc.getY();

        switch (player.getFacing().toString()) {
            case "EAST": {
                // 東向きの場合
                for(int y=0; y>-10; y--) {
                    int size = 20 + y * 2;
                    int min = size / 2 * (-1);
                    int max = size / 2;

                    for (int x = 0; x < size; x++) {
                        for (int z = min; z < max; z++) {
                            Location dLoc = new Location(loc.getWorld(), curposX + x + y * (-1), curposY + y, curposZ + z);
                            Block b = dLoc.getBlock();
                            b.breakNaturally();
                        }
                    }
                }
                break;
            }
            case "WEST": {
                // 東向きの場合
                for(int y=0; y>-10; y--) {
                    int size = 20 + y * 2;
                    int min = size / 2 * (-1);
                    int max = size / 2;

                    for (int x = size * (-1) + 1; x <= 0; x++) {
                        for (int z = min; z < max; z++) {
                            Location dLoc = new Location(loc.getWorld(), curposX + x + y, curposY + y, curposZ + z);
                            Block b = dLoc.getBlock();
                            b.breakNaturally();
                        }
                    }
                }
                break;
            }
            case "NORTH": {
                // 北向きの場合
                for(int y=0; y>-10; y--) {
                    int size = 20 + y * 2;
                    int min = size / 2 * (-1);
                    int max = size / 2;

                    for (int x = min; x < max; x++) {
                        for (int z = size * (-1) + 1; z <= 0; z++) {
                            Location dLoc = new Location(loc.getWorld(), curposX + x, curposY + y, curposZ + z + y);
                            Block b = dLoc.getBlock();
                            b.breakNaturally();
                        }
                    }
                }
                break;
            }
            case "SOUTH": {
                // 南向きの場合
                for(int y=0; y>-10; y--) {
                    int size = 20 + y * 2;
                    int min = size / 2 * (-1);
                    int max = size / 2;

                    for (int x = min; x < max; x++) {
                        for (int z = 0; z < size; z++) {
                            Location dLoc = new Location(loc.getWorld(), curposX + x, curposY + y, curposZ + z + y * (-1));
                            Block b = dLoc.getBlock();
                            b.breakNaturally();
                        }
                    }
                }
                break;
            }
        }
    }
}