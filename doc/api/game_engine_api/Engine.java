package voogasalad.mock;

import java.util.List;
import java.util.Map;

public interface Engine {
    public int create(String objectType, Map<String, String> params);

    public int create(String objectType, Map<String, String> params, int entityID);

    public int delete(int entityID);

    public Map<String, String> getEntity(int ID);

    public void update(double time);

    public void initGameLoop();

    public List<Map<String, String>> loadData(String path);

    public void save(String path);
}
