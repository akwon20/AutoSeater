package autoseater;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;

public class Assigner {

    private Model model;

    public Assigner() {
        // System.out.println("Assigner() Constructor called!");
        model = new Model("Seating Assignment");
    }

    private void createRestrictions(Model model, List<Integer[]> pairsForbidden, int rows, int cols, IntVar[][] seat) {
        for (Integer[] pair_forbidden : pairsForbidden) {
            int val_forbidden1 = pair_forbidden[0];
            int val_forbidden2 = pair_forbidden[1];

            for (int r = 0; r < rows; r++) {
                for (int k = 0; k < cols; k++) {
                    // System.out.println("Current index: " + r + ", " + k);
                    if (r > 0) { // Up
                        // System.out.println("Up");
                        model.or(
                                model.arithm(seat[r][k], "!=", val_forbidden1),
                                model.arithm(seat[r-1][k], "!=", val_forbidden2)).post();

                        model.or(
                                model.arithm(seat[r][k], "!=", val_forbidden2),
                                model.arithm(seat[r-1][k], "!=", val_forbidden1)).post();
                    }

                    if (r < rows - 1) { // Down
                        // System.out.println("Down");
                        model.or(
                                model.arithm(seat[r][k], "!=", val_forbidden1),
                                model.arithm(seat[r+1][k], "!=", val_forbidden2)).post();
                        model.or(
                                model.arithm(seat[r][k], "!=", val_forbidden2),
                                model.arithm(seat[r+1][k], "!=", val_forbidden1)).post();
                    }

                    if (k > 0) { // Left
                        // System.out.println("Left");
                        model.or(
                                model.arithm(seat[r][k], "!=", val_forbidden1),
                                model.arithm(seat[r][k-1], "!=", val_forbidden2)).post();
                        model.or(
                                model.arithm(seat[r][k], "!=", val_forbidden2),
                                model.arithm(seat[1][k-1], "!=", val_forbidden1)).post();
                    }

                    if (k < cols - 1) { // Right
                        // System.out.println("Right");
                        model.or(
                                model.arithm(seat[r][k], "!=", val_forbidden1),
                                model.arithm(seat[r][k+1], "!=", val_forbidden2)).post();
                        model.or(
                                model.arithm(seat[r][k], "!=", val_forbidden2),
                                model.arithm(seat[1][k+1], "!=", val_forbidden1)).post();
                    }
                }
            }
        }
    }

    private void createConstraints(Model model, List<Integer[]> constraintPairs, String op, int rows, int cols, IntVar[][] seats) {
        for (Integer[] pair : constraintPairs) {
            int a = pair[0];
            int b = pair[1];

            System.out.println("Adding constraint for pair {" + a + ", " + b + "}...");

            for (int r = 0; r < rows; r++) {
                for (int k = 0; k < cols; k++) {
                    // System.out.println("Current index: " + r + ", " + k);
                    BoolVar isA = model.arithm(seats[r][k], op, a).reify();
                    BoolVar isB = model.arithm(seats[r][k], op, b).reify();

                    List<BoolVar> neighborsAreA = new ArrayList<BoolVar>();
                    List<BoolVar> neighborsAreB = new ArrayList<BoolVar>();

                    if (r > 0) {    // Up
                        // System.out.println("Up");
                        neighborsAreB.add(model.arithm(seats[r-1][k], op, b).reify());
                        neighborsAreA.add(model.arithm(seats[r-1][k], op, a).reify());
                    }
                    if (r < rows-1) {   // Down
                        // System.out.println("Down");
                        neighborsAreB.add(model.arithm(seats[r+1][k], op, b).reify());
                        neighborsAreA.add(model.arithm(seats[r+1][k], op, a).reify());
                    }
                    if (k > 0) {   // Left
                        // System.out.println("Left");
                        neighborsAreB.add(model.arithm(seats[r][k-1], op, b).reify());
                        neighborsAreA.add(model.arithm(seats[r][k-1], op, a).reify());
                    }
                    if (k < cols-1) {   // Right
                        // System.out.println("Right");
                        neighborsAreB.add(model.arithm(seats[r][k+1], op, b).reify());
                        neighborsAreA.add(model.arithm(seats[r][k+1], op, a).reify());
                    }

                    if (!neighborsAreB.isEmpty()) {
                        model.ifThen(
                            isA,
                            model.or(neighborsAreB.toArray(new BoolVar[0]))
                        );
                    }

                    if (!neighborsAreA.isEmpty()) {
                        model.ifThen(
                            isB,
                            model.or(neighborsAreA.toArray(new BoolVar[0]))
                        );
                    }
                }
            }
        }
    }

    public IntVar[][] assignSeats(int rows, int cols, List<Integer[]> pairsForbidden, List<Integer[]> pairsAllowed) {
        IntVar[][] seat = new IntVar[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                seat[i][j] = model.intVar(0, (rows * cols) - 1);
            }
        }

        IntVar[] flatMatrix = new IntVar[rows * cols];
        int flat_index = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                flatMatrix[flat_index++] = seat[i][j];
            }
        }
        model.allDifferent(flatMatrix).post();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.println(seat[i][j] + " ");
            }
            System.out.println();
        }

        // createConstraints(model, pairsForbidden, "!=", rows, cols, seat);   // Create constraints for restricted pairs
        createRestrictions(model, pairsForbidden, rows, cols, seat);            // Create constraints for restricted pairs
        createConstraints(model, pairsAllowed, "=", rows, cols, seat);      // Create constraints for allowed pairs

        Solver solver = model.getSolver();
        // Solution solution;
        SecureRandom random = new SecureRandom();

        int count = 0;
        IntVar[][] solution = null;

        while (solver.solve()) {
            count++;

            if (random.nextBoolean()) {
                solution = seat;
                break;
            }
        }

        if (count <= 0) {
            System.out.println("No solution found! :(");
        } else {
            System.out.println("Total solutions found so far: " + count);
        }

        return solution;
    }
}
