import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

// Clase de creacion colectiva.
public class SlotMachineCC4Test
{
    private SlotMachine machine;

    @Before
    public void setUp()
    {
        machine = new SlotMachine();
        machine.makeInvisible();
    }

    // Arevalo-Parra

    // deberia dejar la rueda rebel en su lugar al intentar fijarla, intercambiarla o eliminarla
    @Test
    public void accordingArPaShouldKeepRebelWheelUntouched()
    {
        machine.addWheel("rebel", 1);
        machine.addWheel("normal", 2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "red");

        machine.lock(1);
        machine.swap(1, 2);
        machine.delWheel(1);

        assertEquals(2, machine.configuration().length);
        assertEquals("rebel", machine.wheelAt(1).type());
        assertFalse(machine.wheelAt(1).isLocked());
    }

    // deberia dejar premio al girar todo con una lefty a la derecha de una normal
    @Test
    public void accordingArPaShouldKeepLeftyEqualToItsLeftWheel()
    {
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        for (int i = 1; i <= 2; i++) {
            machine.addSymbol(i, "red");
            machine.addSymbol(i, "blue");
            machine.addSymbol(i, "green");
        }
        for (int i = 0; i < 10; i++) {
            machine.spin();
            assertTrue(machine.isJackpot());
        }
    }

    // deberia volver a ser visible el simbolo shy al seleccionarlo dos veces
    @Test
    public void accordingArPaShouldShowShyAgainAfterTwoSelections()
    {
        machine.addWheel(1);
        machine.addSymbol("shy", 1, "red");
        machine.addSymbol(1, "blue");
        ShySymbol symbol = (ShySymbol) machine.wheelAt(1).symbolOf("red");

        machine.spin(1, 2);
        assertFalse(symbol.isShown());
        machine.spin(1, 2);
        assertTrue(symbol.isShown());
    }
}
