package com.xbaimiao.server.lobby

import com.xbaimiao.miao.Miao
import com.xbaimiao.miao.command.CommandHandle
import com.xbaimiao.miao.command.MainCommand
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerInteractEvent

/**
 * @Auther: 小白
 * @Date: 2020/12/30 09:17
 * @Description:
 */
@MainCommand("LobbyGuard")
object LobbyGuard : Listener, CommandHandle {

    private val map = HashMap<String, Boolean>()

    private fun isBreak(name: String): Boolean = map[name] ?: false

    private fun success(player: Player): Boolean {
        if (player.isOp && isBreak(player.name)) {
            return true
        }
        return false
    }

    override fun onCommand(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender !is Player) {
            return true
        }
        if (!sender.isOp){
            sender.sendMessage("${Miao.prefix}你没有使用此命令的权限")
            return true
        }
        val value = map[sender.name]
        if (value == null) {
            map[sender.name] = true
        } else map[sender.name] = !value
        if (map[sender.name] == true) {
            sender.sendMessage("${Miao.prefix}你现在可以破坏主城了")
        } else sender.sendMessage("${Miao.prefix}你当前不可以破坏主城了")
        return true
    }

    override fun onTabComplete(sender: CommandSender, args: Array<out String>): MutableList<String>? {
        TODO("Not yet implemented")
    }

    private val lobbyWorld = Bukkit.getWorld("lobby")

    init {
        if (lobbyWorld != null) {
            lobbyWorld.keepSpawnInMemory = true
            lobbyWorld.pvp = false
        }
    }

    @EventHandler
    fun interact(event: PlayerInteractEvent) {
        if (event.player.world != lobbyWorld){
            return
        }
        if (success(event.player)) {
            return
        }
        when (event.action) {
            Action.RIGHT_CLICK_AIR -> return
            Action.LEFT_CLICK_AIR -> return
            Action.RIGHT_CLICK_BLOCK -> return
            else -> {
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun place(event: BlockPlaceEvent) {
        if (event.player.world != lobbyWorld){
            return
        }
        if (success(event.player)) {
            return
        }
        event.isCancelled = true
    }

    @EventHandler
    fun d(event: BlockBreakEvent) {
        if (event.player.world != lobbyWorld){
            return
        }
        if (success(event.player)) {
            return
        }
        event.isCancelled = true
    }

}