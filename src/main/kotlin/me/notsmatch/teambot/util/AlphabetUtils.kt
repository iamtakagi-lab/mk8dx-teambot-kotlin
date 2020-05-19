package me.notsmatch.teambot.util

import me.notsmatch.teambot.Bot
import java.lang.StringBuilder

object AlphabetUtils {

    var alphabet: CharArray = CharArray(26)

    init {
        var c = 'a'
        for (i in 0..25) {
            alphabet[i] = c++
        }
    }

    fun getAlphabet(i: Int) : String {
        return alphabet[i].toString()
    }

    fun randomAlphabets(length: Int): String{
        val tag = StringBuilder()

        for(i in 0 until length){
            tag.append(getAlphabet(Bot.random.nextInt(26)))
        }

        //小文字か大文字かランダムで決める
        //0なら小文字 1なら大文字

        if(Bot.random.nextInt(2) == 1){
            return tag.toString().toUpperCase()
        }

        return tag.toString()
    }
}