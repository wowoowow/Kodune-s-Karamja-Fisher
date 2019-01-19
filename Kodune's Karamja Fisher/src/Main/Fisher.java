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

    public void onPaint(Graphics g)
    {
        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", 0, 17));
        g.drawString("Time Running: " + t.formatTime(), 10, 120);
        g.drawString("Status: " + status, 10, 140);
        g.drawString("Fish catch: " + FISH_CATCH, 10, 160);
        g.drawString("Fishing level: " + getSkillTracker().getStartLevel(Skill.FISHING), 10, 180);
        g.drawString("Levels gained: " + LEVELS_GAINED, 10, 200);
        //g.drawString("Fishing begin level: " + fishBeginLevel + " ( " + fishGainedLevel + " )",10,160);
        g.drawString("Fishing XP/H: " + getSkillTracker().getGainedExperiencePerHour(Skill.FISHING),10,220);

        //int fishBeginLevel =  getSkillTracker().getStartLevel(Skill.FISHING);
        //int fishGainedLevel = getSkillTracker().getGainedLevels(Skill.FISHING);
        int fishXpHour = getSkillTracker().getGainedExperiencePerHour(Skill.FISHING);


    }

}
