package com.xbaimiao.server.islands

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType

/**
 * @Auther: 小白
 * @Date: 2020/12/28 14:21
 * @Description:
 */
class IslandsListeners : Listener {

    @EventHandler
    fun click(event: InventoryClickEvent) {

        if (event.inventory.holder is Owner) {
            event.isCancelled = true
            val player = event.whoClicked as Player
            val slot = event.slot
            if (event.click != ClickType.SHIFT_LEFT) {
                return
            }

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
            val name = event.currentItem!!.itemMeta!!.displayName
            player.chat("/islands:trust $name")
            player.closeInventory()
        }

    }

}