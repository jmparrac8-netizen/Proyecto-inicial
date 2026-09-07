import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

// Clase de creacion colectiva (via wiki del curso).
// Cada pareja agrega minimo dos pruebas, con nombres que las identifiquen.
//
// Nombre pedido: accordingXxYyShould...
// XxYy son las iniciales de los primeros apellidos de cada autor,
// en orden alfabetico. Ejemplo con "Arevalo" y "Parra": accordingArPaShould...
// (cambien ArPa por sus propias iniciales)
//
// No borrar el trabajo de otras parejas, solo agregar el propio al final.
public class SlotMachineCC2Test
{
    private SlotMachine machine;

    @Before
    public void setUp()
    {
        machine = new SlotMachine();
        machine.makeInvisible();
    }

    // ---- Ejemplo: Arevalo - Parra (cambiar ArPa por sus iniciales) ----

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

    // ---- Agreguen aqui abajo las pruebas de las demas parejas ----
}
