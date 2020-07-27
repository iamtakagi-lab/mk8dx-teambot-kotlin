package me.notsmatch.teambot.command

import com.jagrosh.jdautilities.command.Command
import com.jagrosh.jdautilities.command.CommandEvent
import me.notsmatch.teambot.util.TagUtils
import me.notsmatch.teambot.util.NumberUtils
import net.dv8tion.jda.api.EmbedBuilder
import org.apache.commons.lang3.StringUtils
import java.awt.Color

class TagCommand : Command() {

    init {
        this.name = "tag"
        this.help = "ランダムなアルファベットでタグを決めます"
        this.arguments = "<文字数>"
    }

    override fun execute(event: CommandEvent?) {
        event?.apply {
            val args = StringUtils.split(args)

            var tagLength: Int = 0
            if (args.isNotEmpty()) {

                if (!NumberUtils.isInteger(args[0])) {
                    return reply(EmbedBuilder().apply {
                        setColor(Color.RED)
                        setTitle("Error")
                        setDescription("文字数は1~10で指定してください\n``_tag <文字数>``")
                    }.build())
                }

                tagLength = args[0].toInt()

                if (tagLength > 10 || tagLength < 1) {
                    return reply(EmbedBuilder().apply {
                        setColor(Color.RED)
                        setTitle("Error")
                        setDescription("文字数は1~10で指定してください\n``_tag <文字数>``")
                    }.build())
                }

                //結果出力
                reply("タグ: " + TagUtils.randomAlphabets(tagLength))
            }
        }
    }
}