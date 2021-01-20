package com.xbaimiao.server.etc

import com.xbaimiao.miao.command.CommandHandle
import com.xbaimiao.miao.command.MainCommand
import com.xbaimiao.resource.Pack
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * @author: 小白
 * @date: 2021/1/20 13:12
 */
@MainCommand("chongzhi")
object Etc : CommandHandle {

    enum class Type {
        A, Q, W;
    }

    @JvmStatic
    val map = HashMap<String, Type>()

    override fun onCommand(sender: CommandSender, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (args.isEmpty()) {
                return true
            }
            val type: Type = try {
                Type.valueOf(args[0].toUpperCase())
            } catch (e: Exception) {
                Type.W
            }
            when (type) {
                Type.A -> {
                    Pack.sendSignEditor(
                        sender, "",
                        "§7通过 §6支付宝 §7充值",
                        "§7请在上方输入充值金额",
                        "§7单位 §6元"
                    )
                    map[sender.name] = Type.A
                }
                Type.Q -> {
                    Pack.sendSignEditor(
                        sender, "",
                        "§7通过 §6QQ §7充值",
                        "§7请在上方输入充值金额",
                        "§7单位 §6元"
                    )
                    map[sender.name] = Type.Q
                }
                Type.W -> {
                    Pack.sendSignEditor(
                        sender, "",
                        "§7通过 §6微信 §7充值",
                        "§7请在上方输入充值金额",
                        "§7单位 §6元"
                    )
                    map[sender.name] = Type.W
                }
            }
            return true
        }
        return false
    }

    override fun onTabComplete(sender: CommandSender, args: Array<out String>): MutableList<String> {
        return ArrayList<String>().also { it.add("a"); it.add("q"); it.add("w") }
    }
}