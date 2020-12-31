package com.xbaimiao.server.lobby

import com.xbaimiao.miao.utils.Config
import com.xbaimiao.server.Main
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerRespawnEvent

/**
 * @Auther: 小白
 * @Date: 2020/12/30 09:08
 * @Description:
 */
object PrimeraJoin : Listener {

    private val config = Config(Main.main, "PrimeraJoin.yml")

    fun save() = config.save()

    private val data = config.config

    @EventHandler
    fun join(event: PlayerJoinEvent) {
        val player = event.player
        val name = player.name
        if (isPrimeraJoin(name)) {
            player.teleport(Lobby.lobby)
            setHasJoin(name)
        }
    }

    private fun isPrimeraJoin(name: String): Boolean {
        return !data.getBoolean(name)
    }

    private fun setHasJoin(name: String) {
        data.set(name, true)
    }

    @EventHandler
    fun reSpawn(event: PlayerRespawnEvent) {
        event.respawnLocation = Lobby.lobby
    }

}