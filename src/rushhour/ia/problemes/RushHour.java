package rushhour.ia.problemes;

import rushhour.ia.framework.common.Action;
import rushhour.ia.framework.common.State;
import rushhour.ia.framework.recherche.SearchProblem;

import java.util.ArrayList;
import java.util.List;

public class RushHour extends SearchProblem {

    public RushHour() {
        ACTIONS = new Action[]{new Action("UP"), new Action("LEFT"), new Action("RIGHT"), new Action("DOWN")};
    }

    @Override
    public ArrayList<Action> getActions(State s) {
        ArrayList<Action> actions = new ArrayList<>();
        List<RushHourCar> cars = ((RushHourState) s).getCars();

        for (RushHourCar car : cars) {
            for (Action a : ACTIONS) {
                if (car.isLegal(a, cars)) {
                    actions.add(new Action(car.getName() + "-" + a.getName()));
                }
            }
        }
        return actions;
    }

    @Override
    public State doAction(State s, Action a) {
        RushHourState newState = (RushHourState) s.clone();
        switch (a.getName().substring(2)) {
            case "UP" -> newState.moveCarUp(a.getName().charAt(0));
            case "DOWN" -> newState.moveCarDown(a.getName().charAt(0));
            case "LEFT" -> newState.moveCarLeft(a.getName().charAt(0));
            case "RIGHT" -> newState.moveCarRight(a.getName().charAt(0));
        }
        newState.updateBoard();
        return newState;
    }

    @Override
    public boolean isGoalState(State s) {
        return ((RushHourState) s).getBoard()[6][2] == 'R' && ((RushHourState) s).getBoard()[6][3] == 'R';
    }

    @Override
    public double getActionCost(State s, Action a) {
        return 1.0;
    }
}
