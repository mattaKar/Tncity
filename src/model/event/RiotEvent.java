package model.event;

import java.util.ArrayList;
import java.util.List;

import dialog.RiotMessage;
import localization.LocalizedTexts;
import model.CityResources;




/**
 * The riot event get buildings to riot 
 */
public class RiotEvent extends Event {

    private String message;

	/**
     * Default Constructor.
     */
	public RiotEvent() {
        super();
    }

    /**
     * Gets more or less building rioting depending on the current happiness and saves a message according to it
     */
	@Override
    public List<Event> applyEffects(CityResources resources) {
		if (resources.getOldHappiness()<20.0){
        resources.eventsetriotbuildingchange(1);
        RiotMessage message=new RiotMessage();
        this.message= "a riot occured";
		} else {
			if (resources.geteventsetriotbuilding()!=0){
			resources.eventsetriotbuildingchange(-1);
			this.message = "tensions are going down, some buildings will stop rioting";
			}else {
				this.message = "your people are happy";
			}
			
		}
        

        return new ArrayList<>(0);
    }

    /**
     * Return a message depending on what really happened during the event
     */
	@Override
    public String getMessage(LocalizedTexts texts) {
        return this.message;
    }

}