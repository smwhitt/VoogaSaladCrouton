package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;

import java.util.Map;

/**
 * component to hold the image filepath for this entity
 */
public class Image extends Component{
    private static final String className = "Image";
    private String imagePath;

    public Image(String imagePath) {
        super(className);
        this.imagePath=imagePath;
    }

    public Image() {
        this("");
    }

    public Image(Map<String, String> params) {
        super(className);
        parseParameters(params);
    }

    public void parseParameters(Map<String, String> params) {
        try { this.imagePath=params.get("image");
        } catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }

    void updateImage(String newPath) { this.imagePath=newPath; }

    @Override
    public Map<String, String> asMap() {
        return Map.of(
                "image", imagePath
        );
    }

    @Override
    public Component copy() {
        return new Image(imagePath);
    }
}
