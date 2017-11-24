package com.netgame.netgame.network;

/**
 * Created by arkanay on 8/11/17.
 */

public class NetGameApiService {
    // private static final String HOST = "http://192.168.0.32:3000";
    private static final String HOST = "https://api-rest-netgame.herokuapp.com";


    public static final String CREATE_USER_URL = String.format("%s/api/v1/user/create", HOST);
    public static final String AUTHENTICATE_URL = String.format("%s/api/v1/authorize", HOST);
    public static final String GAMES_URL = String.format("%s/api/v1/games", HOST);
    public static final String CREATE_PUBLICATION_URL = String.format("%s/api/v1/game/publication/create", HOST);
    public static final String PUBLICATIONS_URL = HOST + "/api/v1/game/publications/%s";

    public static final String LIKE_PUBLICATION = String.format("%s/api/v1/game/publication/like", HOST);
    public static final String FAVORITE_PUBLICATION = String.format("%s/api/v1/game/publication/favorite", HOST);

    public static final String CREATE_COMMENT_URL = String.format("%s/api/v1/comment/create", HOST);
    public static final String COMMENTS_URL = HOST + "/api/v1/game/publication/%s";

    public static final String EVENTS_URL = HOST + "/api/v1/game/events/%s";

    public static final String CREATE_EVENT_URL = String.format("%s/api/v1/game/event/create", HOST);
    public static final String ASSISTANCE_USER_URL = String.format("%s/api/v1/game/event/assistance", HOST);


    public static final String CABIN_URL = String.format("%s/api/v1/user/cabin", HOST);
    public static final String GAMER_URL = String.format("%s/api/v1/user/gamer", HOST);

    public static final String CABINS_URL = String.format("%s/api/v1/cabins", HOST);

    public static final String PUT_INFO_CABIN_URL = String.format("%s/api/v1/user/cabin", HOST);
    public static final String PUT_LOCATION_CABIN_URL = String.format("%s/api/v1/user/cabin/location", HOST);


    public static final String PUT_INFO_GAMER_URL = String.format("%s/api/v1/user/gamer", HOST);
}
