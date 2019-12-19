package doc.usecases;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import voogasalad.mock.FinalGame;
import voogasalad.mock.Minion;
import voogasalad.mock.Path;
import voogasalad.mock.PathSquare;


import java.util.ArrayList;
import java.util.List;

public class SerializeDataTest {

    public static void set(){

        XStream mySerializer = new XStream(new DomDriver());
        List<PathSquare> myarg  = new ArrayList<>(List.of(new PathSquare(), new PathSquare()));
        Path path = new Path(myarg);
        Minion minion = new Minion();
        FinalGame game = new FinalGame(minion,path);
        String mySavedEnemy = mySerializer.toXML(game);
        System.out.println(mySavedEnemy);

        var myEnemy = (FinalGame) mySerializer.fromXML(mySavedEnemy);
        myEnemy.himinion.fight();


    }

}
