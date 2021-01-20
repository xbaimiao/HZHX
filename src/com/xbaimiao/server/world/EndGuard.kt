package com.xbaimiao.server.world

import com.xbaimiao.miao.Miao
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

/**
 * @Auther: 小白
 * @Date: 2020/12/30 14:06
 * @Description:
 */
object EndGuard : Listener {

    private val end = Bukkit.getWorld("world_the_end")

    private fun isLocation(location: Location): Boolean {
        val z = location.z.toInt()
        val x = location.x.toInt()
        if (z in -15..15 && x in -15..15){
            return true
        }
        return false
    }

    @EventHandler
    fun place(event: BlockPlaceEvent) {
        if (event.player.world != end) {
            return
        }
        if (isLocation(event.block.location)){
            event.isCancelled = true
            event.player.sendMessage("${Miao.prefix}末地出生点不能造成破坏")
        }
    }

    @EventHandler
    fun d(event: BlockBreakEvent) {
        if (event.player.world != end) {
            return
        }
        if (isLocation(event.block.location)){
            event.isCancelled = true
            event.player.sendMessage("${Miao.prefix}末地出生点不能造成破坏")
        }
    }

}