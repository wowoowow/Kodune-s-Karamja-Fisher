package Util;

import static Main.Fisher.*;
import static Util.F_Constants.*;
import static org.dreambot.api.methods.MethodProvider.sleep;

import Main.Fisher;
import org.dreambot.api.methods.Calculations;

public class Drop extends Node {

    public Drop(Fisher c) {
        super(c);
    }

    @Override
    public boolean validate() {
        return CHOICE == 1 && c.getInventory().isFull();
    }

    @Override
    public int execute() {
        switch (Calculations.random(1,4)){
            case 2:
                sleep(Calculations.random(0,180000));
                break;
        }
        if (c.getInventory().isItemSelected()) {
            c.getInventory().deselect();
            sleep(Calculations.random(0,1500));
        }
        while (c.getInventory().contains(item -> item != null && item.getName().toLowerCase().contains("raw"))) {
            status = "Dropping fish...";
            c.getInventory().dropAllExcept(COINS,LOBSTER_POT);
        }
        return 0;
    }
}
