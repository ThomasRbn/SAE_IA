package rushhour.ia.problemes;

import rushhour.ia.framework.common.Action;
import rushhour.ia.framework.common.State;
import rushhour.ia.framework.jeux.Game;
import rushhour.ia.framework.jeux.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RushHour extends Game {

    RushHourCar redCar;
    List<RushHourCar> sillyCars;

    public RushHour() {
        this.sillyCars = new ArrayList<>();
        RushHourCar car1 = new RushHourCar(3, new int[][]{{2, 0}, {2, 1}, {2, 2}}, 'A', true);
        RushHourCar car2 = new RushHourCar(2, new int[][]{{4, 0}, {5, 0}}, 'B', false);
        RushHourCar car3 = new RushHourCar(3, new int[][]{{0, 3}, {1, 3}, {2, 3}}, 'C', false);
        RushHourCar car4 = new RushHourCar(3, new int[][]{{5, 3}, {5, 4}, {5, 5}}, 'D', true);
        this.redCar = new RushHourCar(2, new int[][]{{0, 2}, {1, 2}}, 'R', false);
        this.sillyCars.addAll(List.of(car1, car2, car3, car4, redCar));
    }


    @Override
    public ArrayList<Action> getActions(State s) {
        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new Action("UP"));
        actions.add(new Action("DOWN"));
        actions.add(new Action("LEFT"));
        actions.add(new Action("RIGHT"));
        return actions;
    }

    public int getCarIndex(char name){
        for (int i = 0; i < sillyCars.size(); i++) {
            if (sillyCars.get(i).getName() == name) {
                return i;
            }
        }
        return -1;
    }

    public RushHourCar getCar(char name){
        for (RushHourCar car : sillyCars) {
            if (car.getName() == name) {
                return car;
            }
        }
        return null;
    }

    @Override
    public State doAction(State s, Action a) {
        RushHourState state = (RushHourState) s;
        RushHourCar car = sillyCars.get(state.getPlayerToMove());
        car.doAction(a);
        return updateBoard();
    }

    public RushHourState updateBoard() {
        return new RushHourState(sillyCars);
    }

    @Override
    public GameState init() {
        RushHour jeu = new RushHour();
        return new RushHourState(jeu.sillyCars);
    }

    /**
     * Vérifie si la partie est fini
     *
     * @param s l'état du jeux
     * @return true si le plateau ne contient plus la voiture rouge
     */
    @Override
    public boolean endOfGame(GameState s) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 2; j++) {
                if (((RushHourState) s).getBoard()[i][j] == 'R') {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Action getHumanMove(GameState s) {

        System.out.println(s);

        Scanner in = new Scanner(System.in);
        Boolean played = false;

        while (!played) {
            System.out.println("Which car do you want to move ? (A, B, C, D, R)");
            String car = in.nextLine();
            while (!car.equals("A") && !car.equals("B") && !car.equals("C") && !car.equals("D") && !car.equals("R")) {
                System.out.println("Please enter a valid car name (A, B, C, D, R)");
                car = in.nextLine();
            }

            System.out.println("Which direction do you want to move ? (UP, DOWN, LEFT, RIGHT)");
            String direction = in.nextLine();
            while (!direction.equals("UP") && !direction.equals("DOWN") && !direction.equals("LEFT") && !direction.equals("RIGHT")) {
                System.out.println("Please enter a valid direction (UP, DOWN, LEFT, RIGHT)");
                direction = in.nextLine();
            }

            Action action = new Action(direction);
            RushHourState state = (RushHourState) s;
            state.setPlayerToMove(getCarIndex(car.charAt(0)));

            RushHourCar carToMove = sillyCars.get(state.getPlayerToMove());
            if (carToMove.isMoveLegal(action)) {
                played = true;
                return action;
            } else {
                System.out.println("This move is not legal");
            }
        }
        return null;
    }
}
