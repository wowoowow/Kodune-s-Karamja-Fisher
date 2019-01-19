package Util;

import Main.Fisher;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.wrappers.interactive.NPC;

import static Main.Fisher.*;
import static Util.F_Constants.*;
import static org.dreambot.api.methods.MethodProvider.log;
import static org.dreambot.api.methods.MethodProvider.sleep;
import static org.dreambot.api.methods.MethodProvider.sleepUntil;

public class Fish extends Node {

    public Fish(Fisher c) {
        super(c);
    }

    @Override
    public boolean validate() {
        return c.getLocalPlayer().distance(KARAMJA_FISHING.getRandomTile()) <= 6 && !WALK_KARAMJA_PORT;
    }

    @Override
    public int execute() {
        switch (Calculations.random(0, 50)) {
            case 25:
                //log("longer wait....");
                status = "sleeping...";
                sleep(Calculations.random(120000, 240000));
                break;
        }
        checkInventory();
        NPC fishingSpot = c.getNpcs().closest(item -> item != null && item.getName().contains("Fishing spot") && item.hasAction("Cage"));
        if (!fishingSpot.isOnScreen()) {
            status = "Rotating camera...";
            //log("fishing spot is not on screen, rotating camera...");
            sleep(Calculations.random(0, 1500));
            c.getCamera().rotateToTile(fishingSpot.getTile().getRandomizedTile());
            sleep(Calculations.random(0, 1500));
        }
        if (c.getInventory().isItemSelected()) {
            //log("Some item is selected, deselecting...");
            c.getInventory().deselect();
            sleep(Calculations.random(0, 2500));
        }
        if (c.getLocalPlayer().getAnimation() == -1 && !c.getInventory().isFull()) {
            fishingSpot.interact("Cage");
        }
        walkSleep();
        sleep(Calculations.random(1500, 2500));
        status = "Fishing...";
        //log("animating check!");
        while (isAnimating()) {
            if (c.getDialogues().inDialogue()) {
                handleDialogue();
            }
            checkInventory();
            moveCursorOffScreen();
            sleep(Calculations.random(2500, 5000));
        }
        checkInventory();
        randomCamera();
        return 0;
    }

    private void handleDialogue() {
        sleep(Calculations.random(1500, 5000));
        c.getDialogues().clickContinue();
        sleep(Calculations.random(750, 2500));
        if (c.getDialogues().inDialogue()) {
            c.getDialogues().clickContinue();
        }

    }

    private void checkInventory() {
        if (c.getInventory().isFull()) {
            switch (Calculations.random(0, 50)) {
                case 25:
                    status = "Sleeping...";
                    sleep(Calculations.random(120000, 240000));
                    break;
            }
            if (c.getInventory().count(COINS) <= Calculations.random(0, 30)) {
                WALK_GENERAL = true;
            } else if (c.getInventory().count(COINS) >= Calculations.random(60, 90)) {
                WALK_KARAMJA_PORT = true;
            }
        }
    }

    private void randomCamera() {
        int r = Calculations.random(1, 100);
        switch (r) {
            case 1:
                c.getCamera().rotateToPitch(Calculations.random(333, 399));
                break;
            case 2:
                c.getCamera().rotateToYaw(Calculations.random(1420, 1700));
                break;
            case 3:
                c.getCamera().rotateToYaw(Calculations.random(455, 700));
                break;
            default:
        }
    }

    private void walkSleep() {
        sleepUntil(() -> c.getLocalPlayer().isMoving(), Calculations.random(2200, 7500));
        sleepUntil(() -> !c.getLocalPlayer().isMoving(), Calculations.random(2300, 8500));
    }

    private boolean isAnimating() {
        if (c.getPlayers().localPlayer().getInteractingIndex() != -1) {
            return true;
        } else if (c.getPlayers().localPlayer().getInteractingIndex() == -1) {
            return false;
        }
        return c.getPlayers().localPlayer().getInteractingIndex() != -1;
    }

    private void moveCursorOffScreen() {
        int r = Calculations.random(1, 40);
        switch (r) {
            case 20:
                if (c.getMouse().isMouseInScreen()) {
                    c.getMouse().moveMouseOutsideScreen();
                }
                break;
        }
    }
}
