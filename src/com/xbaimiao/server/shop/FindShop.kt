package com.xbaimiao.server.shop

import com.xbaimiao.miao.Miao
import com.xbaimiao.miao.command.CommandHandle
import com.xbaimiao.miao.command.MainCommand
import com.xbaimiao.miao.item.Chinese.getChineseName
import com.xbaimiao.miao.item.ItemBuilder
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.maxgamer.quickshop.api.QuickShopAPI

/**
 * @Auther: 小白
 * @Date: 2020/12/28 13:23
 * @Description:
 */
@MainCommand("findShop")
class FindShop : CommandHandle {

    companion object {
        val map = HashMap<String, ShopData>()
    }

    private val shopApi = QuickShopAPI.getShopAPI()

    override fun onCommand(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${Miao.prefix}此命令只能由玩家执行")
            return true
        }
        val shops = shopApi.getPlayerAllShops(sender.uniqueId)
        if (shops.isEmpty()){
            sender.closeInventory()
            sender.sendMessage("${Miao.prefix}你还没有创建商店")
            return true
        }
        val gui = Bukkit.createInventory(Owner(), 54, "§8查找商店")
        var a = 0
        shops.forEach {
            gui.setItem(a++,
                ItemBuilder(Material.CHEST)
                    .setDisplayName("§6◆ §a§n${it.item.type.getChineseName()} §6◆")
                    .setLore(
                        ArrayList<String>().also { list ->
                            list.add("")
                            list.add("§a◎ §7坐标: X${it.location.x} Y${it.location.y} Z${it.location.z}")
                            list.add("§a◎ §7世界: ${it.location.world.name}")
                            list.add("")
                            list.add("§3◎ §7点击传送至该商店")
                        }
                    ).build()
            )
        }
        map[sender.name] = ShopData(shops)
        sender.openInventory(gui)
        return true
    }

    override fun onTabComplete(sender: CommandSender, args: Array<out String>): MutableList<String>? {
        return null
    }
}