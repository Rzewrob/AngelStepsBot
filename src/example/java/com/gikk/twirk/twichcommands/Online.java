package com.gikk.twirk.twichcommands;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.twichcommands.CommandExampleBase;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;


public class Online extends CommandExampleBase {


    private static String PATTERN = "!BotOnline";


    private final Twirk twirk;

    public Online(Twirk twirk) {
        super(CommandType.CONTENT_COMMAND);
        this.twirk = twirk;
    }

    @Override
    protected String getCommandWords() {
        return PATTERN;
    }

    @Override
    protected USER_TYPE getMinUserPrevilidge() {
        return USER_TYPE.DEFAULT;
    }

    @Override
    protected void performCommand(String command, TwitchUser sender, TwitchMessage message) {

        twirk.channelMessage("Bot is online and active!");

    }
}
