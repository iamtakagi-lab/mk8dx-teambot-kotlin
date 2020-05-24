package me.notsmatch.teambot

import com.jagrosh.jdautilities.command.CommandClientBuilder
import me.notsmatch.teambot.command.ChooseCommand
import me.notsmatch.teambot.command.TagCommand
import me.notsmatch.teambot.command.TeamCommand
import net.dv8tion.jda.api.AccountType
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.events.ReadyEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.util.*


class Bot (private val token: String) {

    companion object {
        @JvmStatic
        lateinit var instance: Bot

        @JvmStatic
        lateinit var random: Random
    }

    lateinit var jda: JDA

    fun start() {
        instance = this
        random = Random()
        jda = JDABuilder(AccountType.BOT).setToken(token).setStatus(OnlineStatus.ONLINE).build()
        val builder = CommandClientBuilder()
        builder.addCommands(TeamCommand(), TagCommand(), ChooseCommand())

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
        event.jda.guilds.forEach{guild -> println(guild.name)}

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                event.jda.apply {
                    presence.setPresence(OnlineStatus.ONLINE, Activity.watching("github.com/notsmatch/mk8dx-teambot | ${guilds.size} servers"))
                }
            }
        }, 0, 1000*300)
    }
}
