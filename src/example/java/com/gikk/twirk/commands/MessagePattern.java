package com.gikk.twirk.commands;

import com.crazy.FileWriter;
import com.crazy.GenericParser;
import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;
import kotlin.Triple;


public class MessagePattern extends CommandExampleBase {

    private static String PATTERN1 = "PRIVMSG";

    private final Twirk twirk;

    private final FileWriter fileWriter;
    private final GenericParser genericParser;

    public MessagePattern(Twirk twirk, FileWriter fileWriter, GenericParser genericParser) {
        super(CommandType.CONTENT_COMMAND);
        this.twirk = twirk;
        this.fileWriter = fileWriter;
        this.genericParser = genericParser;
    }

    @Override
    protected String getCommandWords() {
        return PATTERN1;
    }

    @Override
    protected USER_TYPE getMinUserPrevilidge() {
        return USER_TYPE.DEFAULT;
    }

    @Override
    protected void performCommand(String command, TwitchUser sender, TwitchMessage message) {
        Triple<String, String, String> results = genericParser.getStuff(message.toString());
        fileWriter.writeToMessageFile(results.getFirst(), results.getThird());
    }
}

//@badge-info=subscriber/11;badges=vip/1,subscriber/3009,bits-leader/2;bits=15;color=#FF0000;display-name=zarathustra500;emotes=;first-msg=0;flags=;id=83077698-bf86-47eb-af19-383e154b439c;mod=0;returning-chatter=0;room-id=550565368;subscriber=1;tmi-sent-ts=1697847549430;turbo=0;user-id=48940828;user-type=;vip=1 :zarathustra500!zarathustra500@zarathustra500.tmi.twitch.tv PRIVMSG #angel_steps :Cheer15 so Angel where is my Palkia?