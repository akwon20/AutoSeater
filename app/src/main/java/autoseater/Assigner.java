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

    public IntVar[][] assignSeats(int rows, int cols, List<Integer[]> pairsForbidden, List<Integer[]> pairsAllowed) {

        IntVar[][] seat = new IntVar[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                seat[i][j] = model.intVar(0, (rows*cols)-1);
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

        for (Integer[] pair_forbidden : pairsForbidden) {
            int val_forbidden1 = pair_forbidden[0];
            int val_forbidden2 = pair_forbidden[1];

            System.out.println("Adding constraint for pair {" + val_forbidden1 + ", " + val_forbidden2 + "}...");

            for (int r = 0; r < rows; r++) {
                for (int k = 0; k < cols; k++) {
                    // System.out.println("Current index: " + r + ", " + k);

                    if (r > 0) {    // Up
                        // System.out.println("Up");
                        model.or(
                            model.arithm(seat[r][k], "!=", val_forbidden1),
                            model.arithm(seat[r-1][k], "!=", val_forbidden2)
                        ).post();
                        model.or(
                            model.arithm(seat[r][k], "!=", val_forbidden2),
                            model.arithm(seat[r-1][k], "!=", val_forbidden1)
                        ).post();
                    }
                    if (r < rows-1) {   // Down
                        // System.out.println("Down");
                        model.or(
                            model.arithm(seat[r][k], "!=", val_forbidden1),
                            model.arithm(seat[r+1][k], "!=", val_forbidden2)
                        ).post();
                        model.or(
                            model.arithm(seat[r][k], "!=", val_forbidden2),
                            model.arithm(seat[r+1][k], "!=", val_forbidden1)
                        ).post();
                    }
                    if (k > 0) {   // Left
                        // System.out.println("Left");
                        model.or(
                            model.arithm(seat[r][k], "!=", val_forbidden1),
                            model.arithm(seat[r][k-1], "!=", val_forbidden2)
                        ).post();
                        model.or(
                            model.arithm(seat[r][k], "!=", val_forbidden2),
                            model.arithm(seat[1][k-1], "!=", val_forbidden1)
                        ).post();
                    }
                    if (k < cols-1) {   // Right
                        // System.out.println("Right");
                        model.or(
                            model.arithm(seat[r][k], "!=", val_forbidden1),
                            model.arithm(seat[r][k+1], "!=", val_forbidden2)
                        ).post();
                        model.or(
                            model.arithm(seat[r][k], "!=", val_forbidden2),
                            model.arithm(seat[1][k+1], "!=", val_forbidden1)
                        ).post();
                    }
                }
            }
        }

        for (Integer[] pair_allowed : pairsAllowed) {
            int val_allowed1 = pair_allowed[0];
            int val_allowed2 = pair_allowed[1];

            System.out.println("Adding adjacency for pair {" + val_allowed1 + ", " + val_allowed2 + "}...");

            for (int r = 0; r < rows; r++) {
                for (int k = 0; k < cols; k++) {
                    // System.out.println("Current index: " + r + ", " + k);
                    BoolVar isA = model.arithm(seat[r][k], "=", val_allowed1).reify();
                    BoolVar isB = model.arithm(seat[r][k], "=", val_allowed2).reify();

                    List<BoolVar> neighborsAreA = new ArrayList<BoolVar>();
                    List<BoolVar> neighborsAreB = new ArrayList<BoolVar>();

                    if (r > 0) {    // Up
                        // System.out.println("Up");
                        neighborsAreB.add(model.arithm(seat[r-1][k], "=", val_allowed2).reify());
                        neighborsAreA.add(model.arithm(seat[r-1][k], "=", val_allowed1).reify());
                    }
                    if (r < rows-1) {   // Down
                        // System.out.println("Down");
                        neighborsAreB.add(model.arithm(seat[r+1][k], "=", val_allowed2).reify());
                        neighborsAreA.add(model.arithm(seat[r+1][k], "=", val_allowed1).reify());
                    }
                    if (k > 0) {   // Left
                        // System.out.println("Left");
                        neighborsAreB.add(model.arithm(seat[r][k-1], "=", val_allowed2).reify());
                        neighborsAreA.add(model.arithm(seat[r][k-1], "=", val_allowed1).reify());
                    }
                    if (k < cols-1) {   // Right
                        // System.out.println("Right");
                        neighborsAreB.add(model.arithm(seat[r][k+1], "=", val_allowed2).reify());
                        neighborsAreA.add(model.arithm(seat[r][k+1], "=", val_allowed1).reify());
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
        }
        else {
            System.out.println("Total solutions found so far: " + count);
        }

        return solution;
    }
}
