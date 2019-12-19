package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.Map;

/**
 * AI class: Extends components and manages the type of AI for the entity from the following: findstrong, findweak
 * findfirst, findlast
 * @author Alex Qiao, Micheal Head, Lakshya Bakshi
 */
public class AI extends Component {
    private static final String className = "AI";
    private String myType;

    /**
     * AI Constructor: sets the type of AI component the entity has
     */
    public AI(String type) {
        super(className);
        this.myType = type;
    }

    /**
     * AI Constructor: default to no AI
     */
    public AI() {
        this("findlast");
    }

    public AI(Map<String, String> params) {
        super(className);
        parseParameters(params);
    }

    /**
     * parses the parameter map for the type of AI
     */
    public void parseParameters(Map<String, String> params) {
        try {
            this.myType =params.get("ai");
        } catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }

    /**
     * getter for the AI type
     */
    public String getAIType(){
        return myType;
    }

    @Override
    public Map<String, String> asMap() {
        return Map.of(
                "ai", myType +""
        );
    }

    @Override
    public Component copy() {
        return new AI(myType +"");
    }
}
