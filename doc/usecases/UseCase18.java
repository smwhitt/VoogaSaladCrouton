import voogasalad.mock.Engine;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UseCase18 {
    public void main(String[] args){
        GameEngine engine = new GameEngine();

        //User modifies a defense's upgrade status by checking box
        String upgrade = "True";
        Map<String, String> props = new HashMap<>();
        props.put("Upgradable", upgrade);

        /**
         * The Engine stores the Projectile object with the specified changed properties. Modifiable properties are found in
         * properties file
         */
        engine.create("Projectile", props);

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