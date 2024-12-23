package com.gikk.twirk.twichcommands;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MassShoutout extends CommandExampleBase {



    private static String PATTERN = "!MassShoutout";
    public String[] ShoutoutUsers;
    private final Twirk twirk;

    private String StreamerChannel;

    public MassShoutout(Twirk twirk, String channel) {
        super(CommandType.CONTENT_COMMAND);
        this.twirk = twirk;
        this.StreamerChannel = channel;
    }

    @Override
    protected String getCommandWords() {
        return PATTERN;
    }

    @Override
    protected USER_TYPE getMinUserPrevilidge() {
        return USER_TYPE.MOD;
    }

    @Override
    protected void performCommand(String command, TwitchUser sender, TwitchMessage message) {
        boolean Runner = true;
        long Time = 0;
        String CutoutTags = message.toString().substring(message.toString().lastIndexOf(PATTERN)+PATTERN.length());
        System.out.println("RemovedTags = " + CutoutTags);
        ShoutoutUsers = CutoutTags.split(",");
        Set<String> names =  new HashSet<String>(Arrays.asList(ShoutoutUsers));
        Iterator<String> iterator = names.iterator();

        //Below opens a thread to be excecuted
        ExecutorService execService = Executors.newFixedThreadPool(1);
        //Below contains code to be executed in Run()
            execService.execute(new Runnable() {
                @Override
                public void run() {
                    //Loop through the list of users and shouts them out.
                    while(iterator.hasNext()) {
                         twirk.channelMessage("/shoutout @" + iterator.next().replaceAll("\\s+", ""));
                           //If last user does not exist we just break loop before we hold a thread for 2 minutes
                            if(!iterator.hasNext()) {
                                break;
                            }
                            //Delays next shoutout by 2 minutes
                            try {
                                Thread.sleep(120005);
                            }
                            catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                    }
                }
            });
        }
    }

