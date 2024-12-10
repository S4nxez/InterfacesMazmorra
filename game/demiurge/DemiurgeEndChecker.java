package game.demiurge;

import game.conditions.Condition;
import game.conditions.SimpleCondition;

import java.util.ArrayList;

public class DemiurgeEndChecker {

    ArrayList<Condition> conditions;

    public DemiurgeEndChecker(){
        conditions = new ArrayList<>();
        conditions.add(new SimpleCondition());
    }

    public void addCondition(Condition condition){ conditions.add(condition); }

    public boolean check(){
        for(Condition condition: conditions) {
            if (!condition.check())
                return false;
        }
        return true;
    }

}
