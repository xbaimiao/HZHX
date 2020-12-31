package com.xbaimiao.server.world

import com.xbaimiao.miao.Miao
import com.xbaimiao.miao.command.CommandHandle
import com.xbaimiao.miao.command.MainCommand
import com.xbaimiao.miao.utils.Config
import com.xbaimiao.server.Main
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @Auther: 小白
 * @Date: 2020/12/24 14:16
 * @Description:
 */
@MainCommand("warps")
object Warp : CommandHandle {

    fun save() {
        config.remove()
        config = Config(Main.main, "warps.yml")
        map.forEach { (k, v) ->
            config.config.set(k, v)
        }
        config.save()
    }

    private var config = Config(Main.main, "warps.yml")

    private val map = HashMap<String, Location>().also {
        config.config.getKeys(false).forEach { k ->
            it[k] = config.config.getLocation(k)!!
        }
    }

    override fun onCommand(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (args.isNotEmpty()) {
                when (args[0]) {
                    "tp" -> {
                        if (args.size > 1) {
                            if (args[1] !in map.keys) {
                                sender.sendMessage("${Miao.prefix}§6${args[1]} §7坐标点不存在")
                                return true
                            }
                            sender.teleport(map[args[1]]!!)
                            sender.sendMessage("${Miao.prefix}您已被传送至 §6${args[1]}")
                            return true
                        } else sender.sendMessage("${Miao.prefix}请补全命令")
                    }
                    "set" -> {
                        if (!sender.isOp) {
                            sender.sendMessage("${Miao.prefix}你没有使用此命令的权限")
                            return true
                        }
                        if (args.size > 1) {
                            map[args[1]] = sender.location
                            sender.sendMessage("${Miao.prefix}§6${args[1]} §7坐标点设置成功")
                            return true
                        } else sender.sendMessage("${Miao.prefix}请补全命令")
                    }
                    "remove" -> {
                        if (!sender.isOp) {
                            sender.sendMessage("${Miao.prefix}你没有使用此命令的权限")
                            return true
                        }
                        if (args.size > 1) {
                            if (args[1] !in map.keys) {
                                sender.sendMessage("${Miao.prefix}§6${args[1]} §7坐标点不存在")
                                return true
                            }
                            map.remove(args[1])
                            sender.sendMessage("${Miao.prefix}§6${args[1]} §7坐标点移除成功")
                            return true
                        } else sender.sendMessage("${Miao.prefix}请补全命令")
                    }
                }
                return true
            }
            sender.sendMessage("${Miao.prefix}指令错误,请使用 §6以下命令")
            sender.sendMessage("§6/warps tp name")
            sender.sendMessage("§6/warps set name")
            sender.sendMessage("§6/warps remove name")
            return true
        }
        if (args.size != 3){
            sender.sendMessage("${Miao.prefix}warps tp player name")
            return true
        }
        val player = Bukkit.getPlayer(args[1]) ?: return true
        if (args[2] !in map.keys) {
            sender.sendMessage("${Miao.prefix}§6${args[1]} §7坐标点不存在")
            return true
        }
        player.teleport(map[args[2]]!!)
        player.sendMessage("${Miao.prefix}您已被传送至 §6${args[2]}")
        return false
    }

    override fun onTabComplete(sender: CommandSender, args: Array<out String>): MutableList<String>? {
        if (args.size == 1) {
            return ArrayList<String>().also { it.add("tp");it.add("set");it.add("remove") }
        }
        if (args.isNotEmpty()) {
            if (args[0] == "tp" || args[0] == "remove") {
                return ArrayList(map.keys)
            }
        }
        return null
    }

}