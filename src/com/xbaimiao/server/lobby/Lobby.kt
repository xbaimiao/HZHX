package com.xbaimiao.server.lobby

import com.xbaimiao.miao.Miao
import com.xbaimiao.miao.command.CommandHandle
import com.xbaimiao.miao.command.MainCommand
import com.xbaimiao.server.Main
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @Auther: 小白
 * @Date: 2020/12/24 10:26
 * @Description:
 */
@MainCommand("lobby")
class Lobby : CommandHandle {

    companion object {
        val lobby = LobbyKey.lobby
    }

    enum class LobbyKey(val key: String) {
        W("lobby.world"), X("lobby.x"), Y("lobby.y"), Z("lobby.z"), P("lobby.p"), PY("lobby.py");

        companion object {
            private val config = Main.main.config
            private fun getWorld(): World = Bukkit.getWorld(config.getString(W.key)!!)!!
            private fun getX(): Double = config.getDouble(X.key)
            private fun getY(): Double = config.getDouble(Y.key)
            private fun getZ(): Double = config.getDouble(Z.key)
            private fun getYaw(): Float = config.getDouble(PY.key).toFloat()
            private fun getPitch(): Float = config.getDouble(P.key).toFloat()
            val lobby = Location(getWorld(), getX(), getY(), getZ(), getYaw(), getPitch())

            fun setLobby(location: Location) {
                config.set(W.key, location.world.name)
                config.set(X.key, location.x)
                config.set(Y.key, location.y)
                config.set(Z.key, location.z)
                config.set(P.key, location.pitch.toDouble())
                config.set(PY.key, location.yaw.toDouble())
            }
        }
    }

    override fun onCommand(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (args.isEmpty()) {
                sender.teleport(LobbyKey.lobby)
                sender.sendMessage("${Miao.prefix}已将您传送回主城")
                return true
            }
            if (args[0] == "set") {
                if (!sender.hasPermission("op")) {
                    sender.sendMessage("${Miao.prefix}你没有使用此命令的权限")
                    return true
                }
                LobbyKey.setLobby(sender.location)
                sender.sendMessage("${Miao.prefix}已将你脚下的位置设置为Lobby传送点")
                return true
            }
            sender.sendMessage("${Miao.prefix}未找到该命令")
            return true
        }
        return false
    }

    override fun onTabComplete(sender: CommandSender, args: Array<out String>): MutableList<String>? {
        return null
    }

}