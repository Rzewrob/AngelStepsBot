package com.gikk.twirk.twichcommands.rafflecommands;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.twichcommands.CommandExampleBase;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;

public class RaffleFile extends CommandExampleBase {
    private static String PATTERNA = "!LoadFile";

    private final Twirk twirk;
    public RaffleFile(Twirk twirk) {
        super(CommandType.CONTENT_COMMAND);
        this.twirk = twirk;
    }
    @Override
    protected String getCommandWords() {
        return null;
    }

    @Override
    protected USER_TYPE getMinUserPrevilidge() {
        return null;
    }

    @Override
    protected void performCommand(String command, TwitchUser sender, TwitchMessage message) {

    }
}
