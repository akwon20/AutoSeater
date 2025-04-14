package autoseater;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;


public class Assigner {

    public static void assignSeats(int rows, int cols, List<Integer[]> pairsForbidden, List<Integer[]> pairsAllowed) {
        Model model = new Model("Seating Assignment");

        IntVar[][] seat = new IntVar[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                seat[i][j] = model.intVar(0, (rows*cols)-1);
            }
        }

        IntVar[][] flatMatrix = new IntVar[rows * cols];
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

        for (Integer[] pair : pairsForbidden) {
            int a = pair[0];
            int b = pair[1];

            System.out.println("Adding constraint for pair {" + a + ", " + b + "}...");

            for (int r = 0; r < rows; r++) {
                for (int k = 0; k < cols; k++) {
                    // System.out.println("Current index: " + r + ", " + k);

                    if (r > 0) {    // Up
                        // System.out.println("Up");
                        model.or(
                            model.arithm(seat[r][k], "!=", a),
                            model.arithm(seat[r-1][k], "!=", b)
                        ).post();
                        model.or(
                            model.arithm(seat[r][k], "!=", b),
                            model.arithm(seat[r-1][k], "!=", a)
                        ).post();
                    }
                    if (r < rows-1) {   // Down
                        // System.out.println("Down");
                        model.or(
                            model.arithm(seat[r][k], "!=", a),
                            model.arithm(seat[r+1][k], "!=", b)
                        ).post();
                        model.or(
                            model.arithm(seat[r][k], "!=", b),
                            model.arithm(seat[r+1][k], "!=", a)
                        ).post();
                    }
                    if (k > 0) {   // Left
                        // System.out.println("Left");
                        model.or(
                            model.arithm(seat[r][k], "!=", a),
                            model.arithm(seat[r][k-1], "!=", b)
                        ).post();
                        model.or(
                            model.arithm(seat[r][k], "!=", b),
                            model.arithm(seat[1][k-1], "!=", a)
                        ).post();
                    }
                    if (k < cols-1) {   // Right
                        // System.out.println("Right");
                        model.or(
                            model.arithm(seat[r][k], "!=", a),
                            model.arithm(seat[r][k+1], "!=", b)
                        ).post();
                        model.or(
                            model.arithm(seat[r][k], "!=", b),
                            model.arithm(seat[1][k+1], "!=", a)
                        ).post();
                    }
                }
            }
        }

        Solver solver = model.getSolver();
        SecureRandom random = new SecureRandom();

        int count = 0;

        while (solver.solve()) {
            count++;

            if (random.nextBoolean()) {
                System.out.println("Example solution: ");
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        System.out.print(seat[i][j].getValue() + " ");
                    }
                    System.out.println();
                }
                break;
            }
        }

        if (count <= 0) {
            System.out.println("No solution found! :(");
        }
        else {
            System.out.println("Total solutions found so far: " + count);
        }
    }
}
