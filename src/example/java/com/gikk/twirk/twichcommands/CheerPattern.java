package com.gikk.twirk.twichcommands;

import com.gikk.twirk.Twirk;
import com.gikk.twirk.enums.USER_TYPE;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.users.TwitchUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheerPattern extends CommandExampleBase {

    private static String PATTERN1 = "Cheer";
    private static String PATTERN2 = "BibleThump";
    private static String PATTERN3 = "cheerwhal";
    private static String PATTERN4 = "Corgo";
    private static String PATTERN5 = "uni";
    private static String PATTERN6 = "Showlove";
    private static String PATTERN7 = "Party";
    private static String PATTERN8 = "SeemsGood";
    private static String PATTERN9 = "Pride";
    private static String PATTERN10 = "Kappa";
    private static String PATTERN11= "FrankerZ";
    private static String PATTERN12 = "HeyGuys";
    private static String PATTERN13 = "DansGame";
    private static String PATTERN14 = "EleGiggle";
    private static String PATTERN15 = "TriHard";
    private static String PATTERN16 = "Kreygasm";
    private static String PATTERN17 = "4Head";
    private static String PATTERN18= "SwiftRage";
    private static String PATTERN19 = "NotLikeThis";
    private static String PATTERN20 = "FailFish";
    private static String PATTERN21 = "VoHiYo";
    private static String PATTERN22 = "PJSalt";
    private static String PATTERN23 = "MrDestructoid";
    private static String PATTERN24 = "bday";
    private static String PATTERN25 = "RIPCheer";
    private static String PATTERN26 = "Shamrock";

    public int CheerCount = 0;
    public double CheerValue = 0;

    private final Twirk twirk;

    public  CheerPattern(Twirk twirk) {
        super(CommandType.CONTENT_COMMAND);
        this.twirk = twirk;
    }

    @Override
    protected String getCommandWords() {
        return PATTERN1 + "|" + PATTERN2  + "|" + PATTERN3  + "|" + PATTERN4  + "|" + PATTERN5  + "|" + PATTERN6  + "|" + PATTERN7  + "|" + PATTERN8
                + "|" + PATTERN9  + "|" + PATTERN10  + "|" + PATTERN11  + "|" + PATTERN12  + "|" + PATTERN13  + "|" + PATTERN14  + "|" + PATTERN15
                + "|" + PATTERN16  + "|" + PATTERN17  + "|" + PATTERN18  + "|" + PATTERN19  + "|" + PATTERN20  + "|" + PATTERN21  + "|" + PATTERN22
                + "|" + PATTERN23  + "|" + PATTERN24  + "|" + PATTERN25 + "|" + PATTERN26;
    }

    @Override
    protected USER_TYPE getMinUserPrevilidge() {
        return USER_TYPE.DEFAULT;
    }

    @Override
    protected void performCommand(String command, TwitchUser sender, TwitchMessage message) {
        int ChValue = 0;
        String TMessage = message.toString();
        Pattern p = Pattern.compile("bits="+"(\\d*)");
        Matcher m = p.matcher(TMessage);
        if (m.find())
        {
            ChValue  =  Integer.parseInt(m.group(1));
        }
        CheerCount += ChValue;
        CheerValue += CheerCount;
        CheerValue = CheerValue/100;

       // twirk.channelMessage("Count: " + count);
        //System.out.println(TMessage);
        System.out.println("Count: " + CheerCount);
        System.out.println("CheerValue: " + CheerValue);
        twirk.setCheerCount(CheerCount);
        twirk.setCheervalue(CheerValue);
    }
}

//normal message
//@badge-info=;badges=broadcaster/1,premium/1;client-nonce=cd024a4840b17637ce39c64f8ea49242;color=#FF0000;display-name=Pkrock;emotes=;first-msg=0;flags=;id=d09f441d-2676-486b-af3d-3ab2ff98ee55;mod=0;returning-chatter=0;
// room-id=41911856;subscriber=0;tmi-sent-ts=1690442388991;turbo=0;user-id=41911856;user-type= :pkrock!pkrock@pkrock.tmi.twitch.tv PRIVMSG #pkrock :test

//cheer example
// @badge-info=;badges=glhf-pledge/1;bits=1;color=#0000FF;display-name=WhosMontu;emotes=;first-msg=1;flags=;id=998b364d-b7ed-4b82-9d9e-a7046122b9f0;mod=0;returning-chatter=0;room-id=641972806;
// subscriber=0;tmi-sent-ts=1690439036917;turbo=0;user-id=464801243;user-type= :whosmontu!whosmontu@whosmontu.tmi.twitch.tv PRIVMSG #kaicenat :Cheer1

//@badge-info=subscriber/11;badges=subscriber/9,no_video/1;color=#CC00BE;display-name=Necrothunder;emotes=emotesv2_fac9579a586f4b36a7c21e383be42e81:0-11/emotesv2_ab9b81ab9e604163b479752364d0bf84:13-24;flags=;
// id=444f92fd-d271-471d-8fbc-8007da894e8e;login=necrothunder;mod=0;msg-id=resub;msg-param-cumulative-months=11;msg-param-months=0;msg-param-multimonth-duration=0;msg-param-multimonth-tenure=0;msg-param-should-share-streak=1;
// msg-param-streak-months=11;msg-param-sub-plan-name=Channel\sSubscription\s(fefe_asmr);msg-param-sub-plan=1000;msg-param-was-gifted=false;room-id=806193178;subscriber=1;
// system-msg=Necrothunder\ssubscribed\sat\sTier\s1.\sThey've\ssubscribed\sfor\s11\smonths,\scurrently\son\sa\s11\smonth\sstreak!;tmi-sent-ts=1690439510004;user-id=23749073;user-type= :tmi.twitch.tv USERNOTICE #fefe_asmr :fefeasmrCozy fefeasmrMelt
