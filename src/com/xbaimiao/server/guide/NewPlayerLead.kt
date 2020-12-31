package com.xbaimiao.server.guide

import com.xbaimiao.miao.command.CommandHandle
import com.xbaimiao.miao.command.MainCommand
import com.xbaimiao.miao.utils.Config
import com.xbaimiao.server.Main
import com.xbaimiao.server.lobby.Lobby
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

/**
 * @Auther: 小白
 * @Date: 2020/12/30 13:38
 * @Description:
 */
@MainCommand("leadplayer")
class NewPlayerLead : CommandHandle {

    val config = Config(Main.main, "npc.yml").config

    override fun onCommand(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender is Player) {
            run(sender)
            return true
        }
        return false
    }

    override fun onTabComplete(sender: CommandSender, args: Array<out String>): MutableList<String>? {
        TODO("Not yet implemented")
    }

    private fun run(player: Player) {
        player.gameMode = GameMode.SPECTATOR
        val keys = config.getKeys(false)
        Thread {
            keys.forEach {
                if (!player.isOnline){
                    return@Thread
                }
                object : BukkitRunnable() {
                    override fun run() {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "npc tpto ${player.name} $it")
                        player.sendTitle(config.getString("$it.title"), config.getString("$it.subtitle"), 20, 50, 20)
                    }
                }.runTask(Main.main)
                Thread.sleep(4000)
            }
            Bukkit.getScheduler().runTask(Main.main, Runnable {
                player.gameMode = GameMode.SURVIVAL
                player.teleport(Lobby.lobby)
            })
        }.start()
    }

}