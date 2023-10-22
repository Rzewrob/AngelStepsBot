package com.gikk.twirk

import com.crazy.CheerParser
import com.crazy.FileWriterV2
import com.crazy.models.FileDefinition
import com.crazy.models.FilePatterns
import com.crazy.models.FileWriterConfigV2
import org.junit.Test

class TestFileWritter {

    private val cheerMessage = "@badge-info=;badges=glhf-pledge/1;bits=1;color=#0000FF;display-name=WhosMontu;emotes=;first-msg=1;flags=;id=998b364d-b7ed-4b82-9d9e-a7046122b9f0;mod=0;returning-chatter=0;room-id=641972806;" +
            "subscriber=0;tmi-sent-ts=1690439036917;turbo=0;user-id=464801243;user-type= :whosmontu!whosmontu@whosmontu.tmi.twitch.tv PRIVMSG #kaicenat :Cheer1"

    private val patternDefinition1 = FilePatterns("bits=", "")
    private val patternDefinition2 = FilePatterns("test=", "")
    private val patternDefinition3 = FilePatterns("", "")

    private val fileDefinition1 = FileDefinition("dono", "Dono-Log", "txt", true, mutableListOf(patternDefinition1), true, true)
    private val fileDefinition2 = FileDefinition("donoCsv", "Dono-Log", "csv", false, mutableListOf(patternDefinition1), false, false)
    private val fileDefinition3 = FileDefinition("test", "test-Log", "txt", true, mutableListOf(patternDefinition2, patternDefinition3), false, false)

    private val fileDefinitions = mutableListOf(fileDefinition1, fileDefinition2, fileDefinition3)

    private val config = FileWriterConfigV2("Dump_", "", fileDefinitions, true)

    @Test
    fun createsFiles() {
        val fileWriter = FileWriterV2()
        fileWriter.initFiles(config)

        fileWriter.listen(cheerMessage)
    }

    @Test
    fun doStuff() {
        val message2 = "@badge-info=;badges=glhf-pledge/1;bits=1;color=#0000FF;display-name=WhosMontu;emotes=;first-msg=1;flags=;id=998b364d-b7ed-4b82-9d9e-a7046122b9f0;mod=0;returning-chatter=0;room-id=641972806;" +
                        "subscriber=0;tmi-sent-ts=1690439036917;turbo=0;user-id=464801243;user-type= :whosmontu!whosmontu@whosmontu.tmi.twitch.tv PRIVMSG #kaicenat :Cheer1"

        val message = "@badge-info=subscriber/22;badges=vip/1,subscriber/18,sub-gift-leader/3;bits=15;color=#9ACD32;display-name=LilFr0stey;emotes=;first-msg=0;flags=;id=9ee79c53-8566-49cf-ad6c-8f71080e2a89;mod=0;returning-chatter=0;room-id=550565368;subscriber=1;tmi-sent-ts=1696626659073;turbo=0;user-id=188254807;user-type=;vip=1 :lilfr0stey!lilfr0stey@lilfr0stey.tmi.twitch.tv PRIVMSG #angel_steps :Cheer15 Angle, you'll get to see me and Nogard in this game, we're both eldritch horrors"
        val parser = CheerParser()

        val parsed = parser.parseMessage(message)
        val usernameAndBits = parser.getStuff(message)
//        Assert.assertTrue("Check the bits parser", (usernameAndBits == Pair("WhosMontu", "1")))
    }


    /*
            val message = "@badge-info=subscriber/11;badges=subscriber/9,no_video/1;color=#CC00BE;display-name=Necrothunder;emotes=emotesv2_fac9579a586f4b36a7c21e383be42e81:0-11/emotesv2_ab9b81ab9e604163b479752364d0bf84:13-24;flags=;" +
"id=444f92fd-d271-471d-8fbc-8007da894e8e;login=necrothunder;mod=0;msg-id=resub;msg-param-cumulative-months=11;msg-param-months=0;msg-param-multimonth-duration=0;msg-param-multimonth-tenure=0;msg-param-should-share-streak=1;" +
"msg-param-streak-months=11;msg-param-sub-plan-name=Channel\\sSubscription\\s(fefe_asmr);msg-param-sub-plan=1000;msg-param-was-gifted=false;room-id=806193178;subscriber=1;" +
"system-msg=Necrothunder\\ssubscribed\\sat\\sTier\\s1.\\sThey've\\ssubscribed\\sfor\\s11\\smonths,\\scurrently\\son\\sa\\s11\\smonth\\sstreak!;tmi-sent-ts=1690439510004;user-id=23749073;user-type= :tmi.twitch.tv USERNOTICE #fefe_asmr :fefeasmrCozy fefeasmrMelt"

     */

    private fun setFileWriterConfig() {

    }
}