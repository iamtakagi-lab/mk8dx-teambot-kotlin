package me.notsmatch.teambot

import com.jagrosh.jdautilities.command.CommandClientBuilder
import com.jagrosh.jdautilities.commons.waiter.EventWaiter
import me.notsmatch.teambot.command.*
import me.notsmatch.tracktablebot.command.AboutCommand
import net.dv8tion.jda.api.*
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color
import java.util.*


class Bot (private val token: String) {

    companion object {
        @JvmStatic
        lateinit var instance: Bot

        @JvmStatic
        lateinit var random: Random
    }

    lateinit var jda: JDA
    val eventWaiter = EventWaiter()
    val WEBSITE = System.getenv("WEBSITE")

    fun start() {
        instance = this
        random = Random()
        jda = JDABuilder(AccountType.BOT).setToken(token).setStatus(OnlineStatus.ONLINE).build()
        val builder = CommandClientBuilder()
        builder.addCommands(
            TeamCommand(),
            TagCommand(),
            KanaTagCommand(),
            NumTagCommand(),
            AboutCommand(Color.GREEN, "https://github.com/riptakagi/mk8dx-teambot", Permission.VIEW_CHANNEL, Permission.MESSAGE_WRITE, Permission.MESSAGE_READ),
            GuildlistCommand(eventWaiter),
            ChooseCommand()
        )

        builder.setOwnerId("695218967173922866")
        builder.setPrefix("_")

        builder.setHelpWord("tbot")

        val client = builder.build()
        jda.addEventListener(Listener())
        jda.addEventListener(client)
    }
}

class Listener : ListenerAdapter() {

    override fun onReady(event: ReadyEvent) {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                event.jda.apply {
                    presence.setPresence(OnlineStatus.ONLINE, Activity.watching("${Bot.instance.WEBSITE} | ${guilds.size} servers"))
                }
            }
        }, 0, 1000*300)
    }

}
