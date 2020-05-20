package me.notsmatch.teambot.command

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import me.notsmatch.teambot.util.AlphabetUtils
import me.notsmatch.teambot.util.NumberUtils
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color

class TagCommand : Command() {

    init {
        this.name = "tag"
        this.help = "ランダムにタグを決めます"
        this.arguments = "<文字数>"
    }

    override fun execute(event: CommandEvent?) {
        event?.apply {
            val args = args.replace("  ", " ").split(" ")

            var tagLength: Int = 0
            if (args.isNotEmpty()) {

                if(!NumberUtils.isInteger(args[0])){
                    return reply(EmbedBuilder().apply {
                        setColor(Color.RED)
                        setAuthor(
                            "Error",
                            null,
                            null
                        )
                        setDescription("文字数は1~10で指定してください\n``_tag <文字数>``")
                    }.build())
                }

                tagLength = args[0].toInt()

                if(tagLength > 10 || tagLength < 1){
                    return reply(EmbedBuilder().apply {
                        setColor(Color.RED)
                        setAuthor(
                            "Error",
                            null,
                            null
                        )
                        setDescription("文字数は1~10で指定してください\n``_tag <文字数>``")
                    }.build())
                }

                //結果出力
                reply("タグ: " + AlphabetUtils.randomAlphabets(tagLength))
            }
        }
    }
}