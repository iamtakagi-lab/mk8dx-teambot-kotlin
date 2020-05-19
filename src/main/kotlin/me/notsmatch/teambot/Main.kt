package me.notsmatch.teambot

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        Bot(System.getenv("TOKEN")).start()
    }
}