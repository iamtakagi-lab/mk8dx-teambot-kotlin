package me.notsmatch.teambot.command

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import me.notsmatch.teambot.util.AlphabetUtils
import me.notsmatch.teambot.util.NumberUtils
import me.notsmatch.teambot.util.TeamUtils
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color

class TeamCommand : Command() {

    init {
        this.name = "t"
        this.help = "チーム分けをします"
        this.arguments = "<チーム数> <name1> <name2> <name3>..."
    }

    override fun execute(event: CommandEvent?) {
        event?.apply {
            val args = args.split(" ")

            var makeTeamSize: Int = 0
            if (args.isNotEmpty()) {

                if(args.contains("　")){
                    return reply(EmbedBuilder().apply {
                        setColor(Color.RED)
                        setAuthor(
                            "Error",
                            null,
                            null
                        )
                        setDescription("半角スペースにしてください")
                    }.build())
                }

                if(!NumberUtils.isInteger(args[0])){
                    return reply(EmbedBuilder().apply {
                        setColor(Color.RED)
                        setAuthor(
                            "Error",
                            null,
                            null
                        )
                        setDescription("チーム数は1~6で指定してください\n``_t <チーム数> <name1> <name2> <name3>...``")
                    }.build())
                }

                makeTeamSize = args[0].toInt()

                if(makeTeamSize > 6 || makeTeamSize < 1){
                    return reply(EmbedBuilder().apply {
                        setColor(Color.RED)
                        setAuthor(
                            "Error",
                            null,
                            null
                        )
                        setDescription("チーム数は1~6で指定してください\n``_t <チーム数> <name1> <name2> <name3>...``")
                    }.build())
                }
            }

            //名前格納用
            val players = arrayListOf<String>()

            //格納処理
            val copy = args.toMutableList()
            copy.removeAt(0)
            for (element in copy) {
                players.add(element)
            }

            println(players.toString())

            if (players.size > 12) {
                return reply(EmbedBuilder().apply {
                    setColor(Color.RED)
                    setAuthor(
                        "Error",
                        null,
                        null
                    )
                    setDescription("12人を超えています")
                }.build())
            }

            // 格納用
            val teams = TeamUtils.compose(players, makeTeamSize)

            //結果出力
            reply(EmbedBuilder().apply {
                setColor(Color.YELLOW)
                setAuthor(
                    "チーム分け結果",
                    null,
                    null
                )
                for(i in teams.indices){
                    val team = teams[i]
                    addField(AlphabetUtils.getAlphabet(i).toUpperCase(), team.toString().replace("[", "").replace("]", ""), true);
                }
            }.build())
        }
    }
}