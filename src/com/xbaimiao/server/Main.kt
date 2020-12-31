package com.xbaimiao.server

import com.xbaimiao.miao.utils.Utils.registerCommand
import com.xbaimiao.miao.utils.Utils.registerListener
import com.xbaimiao.server.guide.NewPlayerLead
import com.xbaimiao.server.islands.AddPlayer
import com.xbaimiao.server.islands.IslandsListeners
import com.xbaimiao.server.lobby.Lobby
import com.xbaimiao.server.lobby.LobbyGuard
import com.xbaimiao.server.lobby.PlaySong
import com.xbaimiao.server.lobby.PrimeraJoin
import com.xbaimiao.server.shop.FindShop
import com.xbaimiao.server.shop.Listeners
import com.xbaimiao.server.world.EndGuard
import com.xbaimiao.server.world.Warp
import org.bukkit.plugin.java.JavaPlugin

/**
 * @Auther: 小白
 * @Date: 2020/12/24 10:24
 * @Description:
 */
class Main : JavaPlugin() {

    companion object {
        lateinit var main: Main
    }

    override fun onEnable() {
        main = this
        saveResource("npc.yml", false)
        EndGuard.registerListener(this)
        PlaySong.registerListener(this)
        Listeners().registerListener(this)
        IslandsListeners().registerListener(this)
        PrimeraJoin.registerListener(this)
        LobbyGuard.registerListener(this)
        Lobby().registerCommand(this)
        AddPlayer().registerCommand(this)
        FindShop().registerCommand(this)
        Warp.registerCommand(this)
        LobbyGuard.registerCommand(this)
        NewPlayerLead().registerCommand(this)
        PlaySong.go()
    }

    override fun onDisable() {
        Warp.save()
        PrimeraJoin.save()
        saveConfig()
    }

}