package Util;

import org.dreambot.api.methods.map.Area;
import org.dreambot.api.methods.map.Tile;

public class F_Constants {
    public static int CHOICE = -1;
    public static int FISH_CATCH = 0;
    public static int LEVELS_GAINED = 0;

    public static int LOBSTER_POT = 301;
    public static int COINS = 995;

    public static boolean WALK_GENERAL = false;
    public static boolean WALK_FISHING_SPOT = false;
    public static boolean WALK_KARAMJA_PORT = false;
    public static boolean WALK_DEPOSIT = false;
    public static boolean WALK_FISHING_SHOP = false;
    public static boolean WALK_PORT_SARIM_PORT = false;


    public static Area KARAMJA_FISHING = new Area(new Tile(2924, 3180), new Tile(2925, 3180), new Tile(2926, 3175), new Tile(2920, 3175), new Tile(2918, 3178));
    public static Area KARAMJA_GENERAL = new Area(new Tile(2901, 3152), new Tile(2905, 3152), new Tile(2905, 3148), new Tile(2910, 3148), new Tile(2910, 3144), new Tile(2901, 3144));
    public static Area KARAMJA_PORT = new Area(new Tile(2957, 3146), new Tile(2955, 3149), new Tile(2953, 3149), new Tile(2953, 3147), new Tile(2953, 3146));

    public static Area PORT_SARIM_DEPOSIT = new Area(new Tile(3048, 3234), new Tile(3048, 3237), new Tile(3042, 3237), new Tile(3042, 3235));
    public static Area PORT_SARIM = new Area(new Tile(3026, 3224), new Tile(3029, 3224), new Tile(3029, 3212), new Tile(3026, 3211));


}
