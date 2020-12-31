package com.xbaimiao.server.islands

import com.xbaimiao.miao.Miao
import com.xbaimiao.miao.command.CommandHandle
import com.xbaimiao.miao.command.MainCommand
import com.xbaimiao.miao.item.SkullBuilder
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @Auther: 小白
 * @Date: 2020/12/28 14:05
 * @Description:
 */
@MainCommand("islandAddPlayer")
class AddPlayer : CommandHandle {

    override fun onCommand(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player){
            sender.sendMessage("${Miao.prefix}此命令只能由玩家执行")
            return true
        }
        val gui = Bukkit.createInventory(Owner(), 54, "§8添加权限")
        var a = 0
        Bukkit.getOnlinePlayers().forEach {
            gui.setItem(a++,
                SkullBuilder()
                    .setOwnerPlayer(it)
                    .setDisplayName(it.name)
                    .setLore(
                        ArrayList<String>().also { list ->
                            list.add("")
                            list.add("§a${Miao.menuPrefix} §6按住Shift + 左击 §7给该玩家添加权限")
                        }
                    )
                    .build()
            )
        }
        sender.openInventory(gui)
        return true
    }

    override fun onTabComplete(sender: CommandSender, args: Array<out String>): MutableList<String>? {
        return null
    }

}