package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.Map;

/**
 * Component for holding the info of bounding boxes (With width and height) for relevant entities
 * @authors Milan Shah, Lakshya Bakshi
 */
public class Collision extends Component {
    private static final String className = "Collision";
    private double width;
    private double height;

    /**
     * The constructor for a Collision Component. The Collision Component takes in the
     * width and height of an Entity's display. This can be used to make a bounding box for Entities
     * @param w : Width of the Entity's display
     * @param h : Height of the Entity's display
     */
    public Collision(String w, String h){
        super(className);
        width = Double.parseDouble(w);
        height = Double.parseDouble(h);
    }

    public Collision(){
        this("0","0");
    }

    public Collision(Map<String, String> params) {
        super(className);
        parseParameters(params);
    }

    public void parseParameters(Map<String, String> params) {
        try {
            this.width=Double.parseDouble(params.get("width"));
            this.height=Double.parseDouble(params.get("height"));
        } catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }

    /**
     * getter for width
     * @return width
     */
    public double getWidth(){return width;}

    /**
     * getter for height
     * @return height
     */
    public double getHeight(){return height;}

    @Override
    public Map<String, String> asMap() {
        return Map.of(
                "width", width+"",
                "height", height+""
        );
    }

    @Override
    public Component copy() {
        return new Collision(width+"", height+"");
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
