import org.junit.Test;
import static org.junit.Assert.*;

// Pruebas de unidad del ciclo 3.
// Todo corre invisible: SlotMachine(n) nace invisible y solve nunca la muestra.
public class SlotMachineContestTest
{
    // la maquina de n ruedas deberia nacer completa y sin premio
    @Test
    public void newMachineShouldHaveEqualWheelsAndSymbols()
    {
        for (int n = 2; n <= 6; n++) {
            SlotMachine machine = new SlotMachine(n);
            assertTrue(machine.ok());
            assertEquals(n, machine.configuration().length);
            assertEquals(n, machine.symbols().length);
            assertFalse(machine.isJackpot());
        }
    }

    // un tamaño imposible no deberia crear maquina
    @Test
    public void newMachineWithInvalidSizeShouldSetOkFalse()
    {
        assertFalse(new SlotMachine(1).ok());
        assertFalse(new SlotMachine(9).ok());
    }

    // la busqueda deberia dejar la maquina en premio
    @Test
    public void searchShouldReachJackpot()
    {
        for (int n = 2; n <= 6; n++) {
            SlotMachine machine = new SlotMachine(n);
            SlotMachineContest.search(machine, n);
            assertTrue(machine.isJackpot());
            assertEquals(1, machine.distinctSymbols());
        }
    }

    // las acciones deberian llevar del estado inicial al premio
    @Test
    public void searchActionsShouldReplayFromTheStart()
    {
        for (int n = 2; n <= 6; n++) {
            SlotMachine machine = new SlotMachine(n);
            int[][] moves = SlotMachineContest.search(machine, n);
            for (int[] move : moves) {
                machine.spin(move[0], -move[1]);
            }
            assertFalse(machine.isJackpot());
            for (int[] move : moves) {
                machine.spin(move[0], move[1]);
            }
            assertTrue(machine.isJackpot());
        }
    }

    // solve deberia devolver un solo giro por rueda y dentro del rango
    @Test
    public void solveShouldReturnOneValidMovePerWheel()
    {
        int n = 5;
        int[][] moves = SlotMachineContest.solve(n);
        boolean[] seen = new boolean[n + 1];
        for (int[] move : moves) {
            assertTrue(move[0] >= 1 && move[0] <= n);
            assertTrue(move[1] >= 1 && move[1] < n);
            assertFalse(seen[move[0]]);
            seen[move[0]] = true;
        }
    }

    // solve con un tamano imposible no deberia devolver acciones
    @Test
    public void solveWithInvalidSizeShouldReturnNoActions()
    {
        assertEquals(0, SlotMachineContest.solve(1).length);
        assertEquals(0, SlotMachineContest.solve(9).length);
    }

    // solve nunca deberia ganar sin mover nada
    @Test
    public void solveShouldAlwaysNeedAtLeastOneMove()
    {
        for (int t = 0; t < 20; t++) {
            assertTrue(SlotMachineContest.solve(4).length > 0);
        }
    }

    // distinctSymbols deberia contar lo que se ve, no lo que hay guardado
    @Test
    public void distinctSymbolsShouldCountVisibleSymbols()
    {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(2, "red");
        machine.placeSymbol(1, "red");
        machine.placeSymbol(2, "red");
        assertEquals(1, machine.distinctSymbols());
        machine.placeSymbol(1, "blue");
        assertEquals(2, machine.distinctSymbols());
    }

    // spin con pasos negativos deberia rotar al reves
    @Test
    public void spinWithNegativeStepsShouldRotateBackwards()
    {
        SlotMachine machine = new SlotMachine();
        machine.makeInvisible();
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(1, "green");
        machine.spin(1, -1);
        assertEquals("green", machine.configuration()[0]);
        machine.spin(1, 1);
        assertEquals("red", machine.configuration()[0]);
    }
}
