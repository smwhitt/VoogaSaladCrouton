import voogasalad.mock.Engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UseCase20 {
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
        int ID = engine.create("Bullet", props);

        /**
         * User now wants to remove bullet from the authoring environment. They deselect the Bullet. Front end executes the following code.
         */

        engine.delete(ID);

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
