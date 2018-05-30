package model.event;

import java.util.ArrayList;
import java.util.List;

import localization.LocalizedTexts;
import model.CityResources;

import model.GameBoard;

import dialog.LuckyYouMessage;



/**
 * The LuckyYouEvent Gives 100 $ to the player.
 */
public class LuckyYouEvent extends Event {

    /**
     * Default Constructor.
     */
	public LuckyYouEvent() {
        super();
    }

    /**
     * Gives 100 $.
     */
	@Override
    public List<Event> applyEffects(CityResources resources) {
		resources.credit(100);

        System.out.println("you're so lucky, the bank found out she owes you 100$");
        LuckyYouMessage message=new LuckyYouMessage();

        return new ArrayList<>(0);
    }

    /**
     * Return an explanation message.
     */
	@Override
    public String getMessage(LocalizedTexts texts) {
        return "you're so lucky, the bank found out she owes you 100$";

    }

}