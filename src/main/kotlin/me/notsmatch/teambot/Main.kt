package me.notsmatch.teambot

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        Bot(System.getenv("MK8DXTEAMBOT_TOKEN")).start()
    }
}