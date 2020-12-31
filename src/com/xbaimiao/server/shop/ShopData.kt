package com.xbaimiao.server.shop

import org.bukkit.Location
import org.maxgamer.quickshop.shop.Shop

/**
 * @Auther: 小白
 * @Date: 2020/12/28 13:41
 * @Description:
 */
class ShopData(list: List<Shop>) {

    private val map = HashMap<Int, Location>()

    init {
        var a = 0
        list.forEach {
            map[a++] = it.location
        }
    }

    fun getLocation(int: Int): Location? {
        return map[int]
    }

}