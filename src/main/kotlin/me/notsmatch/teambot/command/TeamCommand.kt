package me.notsmatch.teambot.command

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import me.notsmatch.teambot.util.AlphabetUtils
import me.notsmatch.teambot.util.NumberUtils
import me.notsmatch.teambot.util.TeamUtils
import net.dv8tion.jda.api.EmbedBuilder
import org.apache.commons.lang3.StringUtils
import java.awt.Color
import kotlin.math.ceil
import kotlin.math.round

class TeamCommand : Command() {

    init {
        this.name = "t"
        this.help = "チーム分けをします\nチーム形式\n2: 2v2\n3: 3v3\n4: 4v4\n5: 5v5\n6: 6v6"
        this.arguments = "<チーム形式> <name1> <name2> <name3>..."
    }

    override fun execute(event: CommandEvent?) {
        event?.apply {
            val args = StringUtils.split(args)

            var teamType: Int = 0
            if (args.isNotEmpty()) {

                if(!NumberUtils.isInteger(args[0])){
                    return reply(EmbedBuilder().apply {
                        setColor(Color.RED)
                        setAuthor(
                            "Error",
                            null,
                            null
                        )
                        setDescription("チーム形式は2~6で指定してください\n``_t <チーム形式> <name1> <name2> <name3>...``\n" +
                                "チーム形式\n" +
                                "2: 2v2\n" +
                                "3: 3v3\n" +
                                "4: 4v4\n" +
                                "5: 5v5\n" +
                                "6: 6v6")
                    }.build())
                }

                teamType = args[0].toInt()

                if(teamType > 6 || teamType < 2){
                    return reply(EmbedBuilder().apply {
                        setColor(Color.RED)
                        setAuthor(
                            "Error",
                            null,
                            null
                        )
                        setDescription("チーム形式は2~6で指定してください\n``_t <チーム形式> <name1> <name2> <name3>...``\n" +
                                "チーム形式\n" +
                                "2: 2v2\n" +
                                "3: 3v3\n" +
                                "4: 4v4\n" +
                                "5: 5v5\n" +
                                "6: 6v6")
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


            val makeTeamSize = (players.size + teamType - 1 ) / teamType

            val teams = TeamUtils.compose(players, makeTeamSize)

            //結果出力
            reply(EmbedBuilder().apply {
                setColor(Color.YELLOW)
                setAuthor(
                    "チーム分け結果" + "(" + teamType + "v" + teamType + ")",
                    null,
                    null
                )
                var i = 0
                teams.filter { team -> team.isNotEmpty() }.forEach{ team ->
                    addField(AlphabetUtils.getAlphabet(i).toUpperCase() + " (" + team.size + ")", team.toString().replace("[", "").replace("]", ""), true)
                    i++
                }
            }.build())
        }
    }
}