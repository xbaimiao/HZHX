package com.xbaimiao.server.lobby

import com.xbaimiao.server.Main
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.scheduler.BukkitRunnable

/**
 * @Auther: 小白
 * @Date: 2020/12/24 11:05
 * @Description:
 */
object PlaySong : BukkitRunnable(), Listener {

    fun go() = PlaySong.runTaskTimer(Main.main, 20, 20)

    @EventHandler
    fun quit(event: PlayerQuitEvent) {
        stop(player = event.player)
    }

    private val map = HashMap<String, Boolean>()

    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            if (map[player.name] == null) {
                if (inLobby(player)) {
                    play(player)
                    continue
                }
                continue
            }
            if (inLobby(player)) {
                if (map[player.name] == false) {
                    play(player)
                    continue
                }
                continue
            }
            if (map[player.name] == true) {
                stop(player)
            }

        }
    }

    private fun inLobby(player: Player): Boolean {
        return player.world.name == "lobby"
    }

    fun play(player: Player) {
        map[player.name] = true
        player.playSound(player.location, Sound.MUSIC_DISC_STRAD, 100.0F, 1.0F)
    }

    fun stop(player: Player) {
        map[player.name] = false
        player.stopSound(Sound.MUSIC_DISC_STRAD)
    }

}