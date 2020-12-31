package com.xbaimiao.server.shop

import com.xbaimiao.miao.Miao
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType

/**
 * @Auther: 小白
 * @Date: 2020/12/28 13:35
 * @Description:
 */
class Listeners : Listener {

    @EventHandler
    fun click(event: InventoryClickEvent) {
        if (event.inventory.holder is Owner) {
            event.isCancelled = true
            val player = event.whoClicked as Player
            val slot = event.slot

            val clickInv = event.clickedInventory ?: return
            if (clickInv.type != InventoryType.CHEST) {
                return
            }
            if (slot !in 0..52) {
                return
            }
            if (event.currentItem == null || event.currentItem!!.type == Material.AIR) {
                return
            }
            val location = FindShop.map[player.name]!!.getLocation(slot) ?: return

            player.teleport(location)
            player.sendMessage("${Miao.prefix}已将你传送至商店附近")
        }

    }

}