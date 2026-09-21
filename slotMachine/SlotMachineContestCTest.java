import org.junit.Test;
import static org.junit.Assert.*;

// Clase de creacion colectiva.

public class SlotMachineContestCTest
{
    // ---- Arevalo - Parra ----

    // deberia resolver cualquier tamano de maquina permitido
    @Test
    public void accordingArPaShouldSolveEverySupportedSize()
    {
        for (int n = 2; n <= 6; n++) {
            SlotMachine machine = new SlotMachine(n);
            SlotMachineContest.search(machine, n);
            assertTrue("fallo con n = " + n, machine.isJackpot());
        }
    }

    // no deberia necesitar mover la misma rueda dos veces
    @Test
    public void accordingArPaShouldNotMoveTheSameWheelTwice()
    {
        int n = 6;
        int[][] moves = SlotMachineContest.solve(n);
        boolean[] seen = new boolean[n + 1];
        for (int[] move : moves) {
            assertFalse("rueda repetida: " + move[0], seen[move[0]]);
            seen[move[0]] = true;
        }
    }

 
}
