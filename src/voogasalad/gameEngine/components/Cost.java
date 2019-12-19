package voogasalad.gameEngine.components;

import voogasalad.gameEngine.exceptions.IncorrectVariableTypeException;
import voogasalad.gameEngine.exceptions.NotEnoughMoneyException;

import java.util.List;
import java.util.Map;

/**
 * Cost class: concrete price component used for upgrades and purchases by the user, extends the component abstract class.
 *
 * @author Alex Qiao, Lakshya Bakshi
 */
public class Cost extends Component {
    private static final String className = "Cost";
    private int cost;



    public Cost(String cost) {
        super(className);
        this.cost = Integer.parseInt(cost);
    }

    public Cost() {
        this("0");
    }

    public Cost(Map<String,String> params) {
        super(className);
        parseParameters(params);
    }

    public void parseParameters(Map<String,String> params) {
        try { this.cost =Integer.parseInt(params.get("cost"));
        } catch (Exception e) {
            throw new IncorrectVariableTypeException(className);
        }
    }
    // function to be called by EntityManager, if costs are NOT non-incremental

//    public int updateStateAndCost(int usermoney) throws NotEnoughMoneyException {
//        if (usermoney > price.get(state) && state < price.size()){
//            state += 1;
//            usermoney = usermoney - price.get(state);
//        }
//        else{
//
//            //throw (new NotEnoughMoneyException(" "));
//        }
//
//        return usermoney;
//
//    }

    public int updateStateAndCost(int usermoney) throws NotEnoughMoneyException {
        if (usermoney > cost){
            usermoney = usermoney - cost;
        }
        else{
            throw (new NotEnoughMoneyException(" "));
        }
        return usermoney;
    }

    public int getCost() {
        return this.cost;
    }

    @Override
    public Map<String, String> asMap() {
        return Map.of(
                "cost", cost +""
        );
    }

    @Override
    public Component copy() {
        return new Cost(cost +"");
    }
}
