package Util;

import Main.Fisher;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.widgets.WidgetChild;

import static Main.Fisher.*;
import static Util.F_Constants.*;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleep;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;

public class Walk extends Node {

    public Walk(Fisher c) {
        super(c);
    }

    @Override
    public boolean validate() {
        return WALK_PORT_SARIM_PORT || WALK_GENERAL || WALK_KARAMJA_PORT || WALK_DEPOSIT || WALK_FISHING_SHOP || WALK_FISHING_SPOT;
    }

    @Override
    public int execute() {
        switch (Calculations.random(0, 50)) {
            case 25:
                status = "sleeping....";
                sleep(Calculations.random(120000, 240000));
                break;
        }
        if (c.getLocalPlayer().distance(KARAMJA_FISHING.getRandomTile()) <= Calculations.random(3, 6)) {
            if (c.getInventory().isFull()) {
                WALK_KARAMJA_PORT = true;
            }
        }
        if (c.getLocalPlayer().distance(PORT_SARIM.getRandomTile()) <= Calculations.random(3, 6)) {
            if (c.getInventory().isFull()) {
                WALK_DEPOSIT = true;
            }
        }
        if (WALK_FISHING_SPOT) {
            status = "Walking to fishing spots...";
            c.getWalking().walk(KARAMJA_FISHING.getRandomTile());
            walkSleep();
            if (c.getLocalPlayer().distance(KARAMJA_FISHING.getRandomTile()) <= Calculations.random(3, 6)) {
                WALK_FISHING_SPOT = false;
            }
        }
        if (WALK_PORT_SARIM_PORT) {
            status = "Walking to Port Sarim port...";
            c.getWalking().walk(PORT_SARIM.getRandomTile());
            walkSleep();
            if (c.getLocalPlayer().distance(PORT_SARIM.getRandomTile()) <= Calculations.random(3, 6)) {
                WALK_PORT_SARIM_PORT = false;
                WALK_FISHING_SPOT = true;
            }
            sleep(Calculations.random(2000, 5000));
            if (WALK_FISHING_SPOT) {
                NPC seaman = c.getNpcs().closest(i -> i != null && i.hasAction("Pay-fare"));
                c.getCamera().rotateToTile(seaman.getTile().getRandomizedTile());
                status = "Found Customs officer, Pay-fare";
                seaman.interact("Pay-fare");
                sleep(Calculations.random(1000, 4000));
                GameObject plank = c.getGameObjects().closest("Gangplank");
                while (plank == null) {
                    sleep(Calculations.random(1000, 4000));
                    plank = c.getGameObjects().closest("Gangplank");
                }
                status = "found plank, crossing...";
                plank.interact("Cross");
                sleep(Calculations.random(1000, 4000));
            }
        }
        if (WALK_GENERAL) {
            status = "Walking to Karamja general store...";
            c.getWalking().walk(KARAMJA_GENERAL.getRandomTile());
            walkSleep();
            if (c.getLocalPlayer().distance(KARAMJA_GENERAL.getRandomTile()) <= Calculations.random(3, 6)) {
                sleep(Calculations.random(1500, 2500));
                WALK_GENERAL = false;
                sellFish();
                if (c.getInventory().onlyContains(COINS, LOBSTER_POT)) {
                    WALK_FISHING_SPOT = true;
                }
            }
        }
        if (WALK_KARAMJA_PORT) {
            status = "Walking to Karamja port...";
            c.getWalking().walk(KARAMJA_PORT.getRandomTile());
            walkSleep();
            if (c.getLocalPlayer().distance(KARAMJA_PORT.getRandomTile()) <= Calculations.random(3, 6)) {
                WALK_KARAMJA_PORT = false;
                WALK_DEPOSIT = true;
            }
            sleep(Calculations.random(2000, 5000));
            if (WALK_DEPOSIT) {
                NPC seaman = c.getNpcs().closest(i -> i != null && i.hasAction("Pay-Fare"));
                c.getCamera().rotateToTile(seaman.getTile().getRandomizedTile());
                status = "Found Customs officer, Pay-Fare";
                seaman.interact("Pay-Fare");
                sleep(Calculations.random(1000, 4000));
                GameObject plank = c.getGameObjects().closest("Gangplank");
                while (plank == null) {
                    sleep(Calculations.random(1000, 4000));
                    plank = c.getGameObjects().closest("Gangplank");
                }
                status = "Found plank, crossing...";
                plank.interact("Cross");
                sleep(Calculations.random(1000, 4000));
            }

        }
        if (WALK_DEPOSIT) {
            status = "Walking to depositBox...";
            c.getWalking().walk(PORT_SARIM_DEPOSIT.getRandomTile());
            walkSleep();
            if (c.getLocalPlayer().distance(PORT_SARIM_DEPOSIT.getRandomTile()) <= Calculations.random(3, 6)) {
                WALK_DEPOSIT = false;
            }
        }
        if (WALK_FISHING_SHOP) {
            status = "Walking to fishing shop...";
            //c.getWalking().walk(PORT_SARIM.getRandomTile());
            // missing area!!!
            walkSleep();
            if (c.getLocalPlayer().distance(PORT_SARIM.getRandomTile()) <= Calculations.random(3, 6)) {
                WALK_FISHING_SHOP = false;
            }
        }
        return 0;
    }

    private void walkSleep() {
        sleepUntil(() -> c.getLocalPlayer().isMoving(), Calculations.random(1100, 1500));
        sleepUntil(() -> !c.getLocalPlayer().isMoving(), Calculations.random(1500, 3500));
    }

    private void sellFish() {
        NPC npc = c.getNpcs().closest(item -> item.getName().toLowerCase().contains("assistant") || item.getName().toLowerCase().contains("keeper"));
        if (npc.hasAction("Trade")) {
            sleep(Calculations.random(0, 15000));
            npc.interact("Trade");
            walkSleep();
        }
        WidgetChild shop = c.getWidgets().getWidgetChild(300, 1);
        if (shop.isVisible()) {
            sleep(Calculations.random(0, 1500));
            c.getInventory().get(item -> item != null && item.getName().toLowerCase().contains("raw")).interact("Sell 50");
            sleep(Calculations.random(500, 2500));
        }
    }
}
