import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;



public class SlotMachineCC2Test
{
    private SlotMachine machine;

    @Before
    public void setUp()
    {
        machine = new SlotMachine();
        machine.makeInvisible();
    }

    

    // deberia dejar sin cambios una rueda fijada al pedir una configuracion
    @Test
    public void accordingArPaShouldKeepLockedWheelUnchanged()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "red");
        machine.addSymbol(2, "blue");
        machine.lock(1);

        machine.spin(new String[] {"blue", "blue"});

        assertEquals("red", machine.configuration()[0]);
        assertEquals("blue", machine.configuration()[1]);
    }

    // no deberia romperse con un swap con posicion invalida
    @Test
    public void accordingArPaShouldNotBreakOnInvalidSwap()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");

        machine.swap(1, 5);

        assertTrue(machine.ok());
        assertEquals(1, machine.configuration().length);
        assertEquals("red", machine.configuration()[0]);
    }

    
}
