package Util;

import Main.Fisher;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.depositbox.DepositBox;

import static Main.Fisher.*;
import static Util.F_Constants.*;
import static org.dreambot.api.methods.MethodProvider.sleep;

public class Deposit extends Node {

    public Deposit(Fisher c) {
        super(c);
    }

    @Override
    public boolean validate() {
        return CHOICE == 0 && c.getLocalPlayer().distance(PORT_SARIM_DEPOSIT.getRandomTile()) <= 6;
    }

    @Override
    public int execute() {
        if (c.getInventory().isFull()) {
            switch (Calculations.random(1,10)){
                case 2:
                    status = "sleeping....";
                    sleep(Calculations.random(0,180000));
                    break;
            }
            if (!c.getDepositBox().isOpen()) {
                status = "Open depositBox";
                c.getDepositBox().open();

            }
            status = "Deposit fish...";
            sleep(Calculations.random(500,2500));
            c.getDepositBox().depositAll(item -> item != null && item.getName().toLowerCase().contains("raw"));
            sleep(Calculations.random(500,2500));
            switch (Calculations.random(0,50)){
                case 25:
                    status = "sleeping....";
                    sleep(Calculations.random(120000,240000));
                    break;
            }
        }
        if (c.getInventory().onlyContains(COINS,LOBSTER_POT)) {
            WALK_PORT_SARIM_PORT = true;
        }
        return 0;
    }
}
