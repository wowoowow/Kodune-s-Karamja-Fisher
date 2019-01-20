package Main;

import Util.*;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.script.listener.MessageListener;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.widgets.message.Message;

import javax.swing.*;
import java.awt.*;

import static Util.F_Constants.*;

@ScriptManifest(
        category = Category.FISHING,
        name = "Kodune's Karamja Fisher",
        author = "Kodune",
        version = 0.1,
        description = "Fish ...."
)

public class Fisher extends AbstractScript implements MessageListener {

    private Timer t = new Timer();
    private Node[] nodes;
    private String bankOption[] = {"Deposit", "Drop"};

    public static String status = "Setting up bot...";


    @Override
    public void onStart() {
        getSkillTracker().resetAll();
        getSkillTracker().start();
        FISH_CATCH = 0;
        LEVELS_GAINED = 0;
        String bankChoice = "" + JOptionPane.showInputDialog(null,
                "Select bank option.",
                "Option", JOptionPane.PLAIN_MESSAGE,
                null, bankOption, bankOption[0]);
        switch (bankChoice) {
            case "Deposit":
                CHOICE = 0;
                break;
            case "Drop":
                CHOICE = 1;
                break;
        }
        nodes = new Node[]{
                new Fish(this),
                new Walk(this),
                new Deposit(this),
                new Drop(this)
        };
    }

    @Override
    public int onLoop() {
        for (Node node: nodes){
            if (node.validate()){
                return node.execute();
            }
        }
        return Calculations.random(300,400);
    }

    @Override
    public void onGameMessage(Message message) {
        if(message.getMessage() != null && (message.getMessage().toLowerCase().contains("you catch a lobster"))){
            FISH_CATCH++;
        }
        if(message.getMessage() != null && (message.getMessage().toLowerCase().contains("advanced your fishing level"))){
            LEVELS_GAINED++;
        }
    }


    @Override
    public void onPlayerMessage(Message message) {

    }

    @Override
    public void onTradeMessage(Message message) {

    }

    @Override
    public void onPrivateInMessage(Message message) {

    }

    @Override
    public void onPrivateOutMessage(Message message) {

    }

    public void onPaint(Graphics gg)
    {
        Color color1 = new Color(0, 255, 0);
        Color color2 = new Color(0, 0, 0);

        BasicStroke stroke1 = new BasicStroke(1);

        Font font1 = new Font("TimesRoman", 2, 13);
        Font font2 = new Font("TimesRoman", 0, 12);

        Graphics2D g = (Graphics2D)gg;
        g.setColor(color1);
        g.fillRoundRect(5, 5, 160, 145, 16, 16);
        g.setColor(color2);
        g.setStroke(stroke1);
        g.drawRoundRect(5, 5, 160, 145, 16, 16);
        g.setFont(font1);
        g.drawString("Kodune's Karamja Fisher", 15, 20);
        g.setFont(font2);
        g.drawString("Time Running: " + t.formatTime(), 9, 50);
        g.drawString("Fish catch: " + FISH_CATCH, 9, 65);
        g.drawString("Fishing level: " + getSkillTracker().getStartLevel(Skill.FISHING), 9, 80);
        g.drawString("Levels gained: " + LEVELS_GAINED, 9, 95);
        g.drawString("Fishing XP/H: " + getSkillTracker().getGainedExperiencePerHour(Skill.FISHING), 9, 110);
        g.drawString("Status: " + status, 9, 130);

    }

}
