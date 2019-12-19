import voogasalad.mock.Engine;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UseCase21 {
    public void main(String[] args){
        //Takes place in authoring environment
        GameEngine engine = new GameEngine();

        //User modifies speed of bullet
        String newspeed = "10";
        Map<String, String> props = new HashMap<>();
        props.put("speed", newspeed);

        /**
         * The Engine stores the Bullet object with the specified changed properties. Modifiable properties are found in
         * properties file
         */
        engine.create("Bullet", props);

        /**
         * In this example, the user changes many objects and adds them all to the authoring environment. The program
         * calls create from engine methods for each new object. Now ready to write to XML
         */

        /**
         * Writes the engine and all its data to XML
         */

        engine.save("/file/path/madeup");

    }

    private class GameEngine implements Engine {

        private GameEngine(){

        }
        @Override
        public int create(String objectType, Map<String, String> params) {
            return 0;
        }

        @Override
        public int create(String objectType, Map<String, String> params, int entityID) {
            return 0;
        }

        @Override
        public int delete(int entityID) {
            return 0;
        }

        @Override
        public Map<String, String> getEntity(int ID) {
            return null;
        }

        @Override
        public void update(double time) {

        }

        @Override
        public void initGameLoop() {

        }

        @Override
        public List<Map<String, String>> loadData(String path) {
            return null;
        }

        @Override
        public void save(String path) {

        }

    }
}
