package com.example.seichi_plugin;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

// プレイヤーがブロックを設置した時に呼ばれるリスナ
public class blockPlaceListener implements Listener {

    //設置した場所から10×10マスを設置したブロックと同じブロックで埋めます
    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent evt) {

        // 設置したブロックの座標取得
        Location loc = evt.getBlock().getLocation();
        double curposX = loc.getX();
        double curposZ = loc.getZ();
        double curposY = loc.getY();

        // x, z方向に埋める
        for (int x = -5; x < 5; x++) {
            for (int z = -5; z < 5; z++) {
                Location dLoc = new Location(loc.getWorld(), curposX + x, curposY, curposZ + z);
                Block b = dLoc.getBlock();

                // ブロックがすでにあったら何もしない(空のときだけ設定)
                if(!b.isEmpty()){
                   continue;
                }

                // 設置したものと同じブロックで埋める
                b.setBlockData(evt.getBlock().getBlockData());
            }
        }
    }
}
