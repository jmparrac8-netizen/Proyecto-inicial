import java.util.ArrayList;

// Autor: Tomas Arevalo - Jose Parra
// Resuelve el problema de la maraton usando la maquina como testing tool.
// Lo unico que puede consultar es cuantos simbolos distintos se ven;
// nunca lee un color directamente.
public class SlotMachineContest
{
    // Devuelve las acciones {rueda, pasos} que dejan la maquina en premio
    public static int[][] solve(int n)
    {
        SlotMachine machine = new SlotMachine(n);
        if (!machine.ok()) {
            return new int[0][];
        }
        return search(machine, n);
    }

    // Resuelve una maquina y muestra la solucion paso a paso
    public static void simulate(int n)
    {
        SlotMachine machine = new SlotMachine(n);
        if (!machine.ok()) {
            return;
        }
        int[][] moves = search(machine, n);
        for (int[] move : moves) {
            machine.spin(move[0], -move[1]);
        }
        machine.makeVisible();
        for (int[] move : moves) {
            machine.spin(move[0], move[1]);
        }
    }

    // Las tres fases: separar, ordenar y alinear.
    // Sin modificador para poder revisarla desde las pruebas.
    static int[][] search(SlotMachine machine, int n)
    {
        int[] applied = new int[n + 1];
        separate(machine, n, applied);
        int[] offset = order(machine, n, applied);
        for (int wheel = 2; wheel <= n; wheel++) {
            turn(machine, wheel, (n - offset[wheel]) % n, applied);
        }
        return moves(applied, n);
    }

    // Deja cada rueda mostrando un simbolo distinto al de las demas
    private static void separate(SlotMachine machine, int n, int[] applied)
    {
        for (int wheel = 2; wheel <= n; wheel++) {
            int best = 0;
            int bestCount = machine.distinctSymbols();
            for (int step = 1; step < n; step++) {
                turn(machine, wheel, 1, applied);
                int count = machine.distinctSymbols();
                if (count > bestCount) {
                    bestCount = count;
                    best = step;
                }
            }
            turn(machine, wheel, (best + 1) % n, applied);
        }
    }

    // Averigua que tan adelantada esta cada rueda respecto a la primera
    private static int[] order(SlotMachine machine, int n, int[] applied)
    {
        int[] offset = new int[n + 1];
        boolean[] known = new boolean[n + 1];
        known[1] = true;
        int current = 1;
        for (int position = 1; position < n; position++) {
            for (int candidate = 2; candidate <= n; candidate++) {
                if (!known[candidate] && follows(machine, current, candidate, n, applied)) {
                    offset[candidate] = position;
                    known[candidate] = true;
                    current = candidate;
                    break;
                }
            }
        }
        return offset;
    }

    // Indica si la rueda b va justo una posicion adelante de la rueda a
    private static boolean follows(SlotMachine machine, int a, int b, int n, int[] applied)
    {
        turn(machine, a, 1, applied);
        turn(machine, b, -1, applied);
        boolean result = machine.distinctSymbols() == n;
        turn(machine, a, -1, applied);
        turn(machine, b, 1, applied);
        return result;
    }

    // Gira una rueda y lleva la cuenta de lo que se le ha hecho
    private static void turn(SlotMachine machine, int wheel, int steps, int[] applied)
    {
        machine.spin(wheel, steps);
        applied[wheel] += steps;
    }

    // Resume todo lo hecho en un solo giro por rueda
    private static int[][] moves(int[] applied, int n)
    {
        ArrayList<int[]> list = new ArrayList<int[]>();
        for (int wheel = 1; wheel <= n; wheel++) {
            int steps = ((applied[wheel] % n) + n) % n;
            if (steps != 0) {
                list.add(new int[] {wheel, steps});
            }
        }
        int[][] result = new int[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }
}
