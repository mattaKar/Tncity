package model.event;

import java.util.ArrayList;
import java.util.List;

import localization.LocalizedTexts;
import model.CityResources;
import model.GameBoard;
import model.tiles.Destroyable;




/**
 * The Meteor event gets a rain of meteor on the city
 */
public class MeteorEvent extends Event {

    private String message;

	/**
     * specific with gameboard constructor.
     */
	public MeteorEvent(GameBoard world) {
        super(world);
        
    }

    /**
     * chooses a random tile and destroys it if it is a buildable tile, does nothing if it is not .
     */
	@Override
    public List<Event> applyEffects(CityResources resources) {
        int row = this.startingTile.getRow();
    	int column = this.startingTile.getColumn();
    	if (this.world.getTile(row,column) instanceof Destroyable){
        ((Destroyable) this.world.getTile(row, column)).disassemble(resources);
        this.message=" Ho no! One of them has hit one of your buildings";
    	} 
    	else{
    		this.message="Thankfully nothing was broken";
    	}

        return new ArrayList<>(0);
    }

    /**
     * Return an appropriate message
     */
	@Override
    public String getMessage(LocalizedTexts texts) {
        return "Meteors are pouring from the sky! "+ this.message;
    }

}