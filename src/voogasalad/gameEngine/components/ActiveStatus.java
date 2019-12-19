package voogasalad.gameEngine.components;

import java.util.*;

/**
 * Component to hold the "live" status of an entity, a boolean describing whether an entity is live and able to interact with things on the board
 * @authors: Milan Shah, Lakshya Bakshi, Micheal Head
 */
public class ActiveStatus extends Component {
    private static final String className = "ActiveStatus";
    private boolean activestatus;


    public ActiveStatus(String status) {
        super(className);
        activestatus = Boolean.parseBoolean(status);
    }

    public ActiveStatus() {
        this("false");
    }

    public ActiveStatus(Map<String, String> params) throws InvalidPropertiesFormatException {
        super(className);
        parseParameters(params);
    }

    /**
     * parses the relevant parameters for this component
     * @param params map of all parameters for the parent entity
     * @throws InvalidPropertiesFormatException
     */
    @Override
    public void parseParameters(Map<String, String> params) throws InvalidPropertiesFormatException {
        try {
            activestatus = Boolean.parseBoolean(params.get("activestatus"));
        }
        catch (Exception e) {
            throw new InvalidPropertiesFormatException(className);
        }
    }

    /**
     * getter of status value
     * @return activestatus boolean
     */
    public boolean getStatus(){
        return activestatus;
    }

    /**
     * setter of status value
     * @param newstatus
     */
    public void setStatus(boolean newstatus){activestatus = newstatus;}


    @Override
    public Map<String, String> asMap() {
        return Map.of("activestatus", Boolean.toString(activestatus));
    }

    @Override
    public Component copy() {
        return new ActiveStatus(Boolean.toString(activestatus));
    }
}
