import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

// Pruebas de unidad de SlotMachine.
// Todas corren en modo invisible para que no aparezca ninguna ventana.
public class SlotMachineC2Test
{
    private SlotMachine machine;

    // Antes de cada prueba: maquina nueva e invisible
    @Before
    public void setUp()
    {
        machine = new SlotMachine();
        machine.makeInvisible();
    }

    // addWheel deberia aumentar el numero de ruedas
    @Test
    public void addWheelShouldIncreaseConfigurationSize()
    {
        machine.addWheel(1);
        assertEquals(1, machine.configuration().length);
        machine.addWheel(2);
        assertEquals(2, machine.configuration().length);
    }

    // addWheel con posicion invalida no deberia fallar
    @Test
    public void addWheelWithInvalidPositionShouldNotFail()
    {
        machine.addWheel(-5);
        assertTrue(machine.ok());
        assertEquals(1, machine.configuration().length);
    }

    // delWheel deberia disminuir el numero de ruedas
    @Test
    public void delWheelShouldDecreaseConfigurationSize()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.delWheel(1);
        assertEquals(1, machine.configuration().length);
    }

    // delWheel sin ruedas no deberia funcionar
    @Test
    public void delWheelOnEmptyMachineShouldSetOkFalse()
    {
        machine.delWheel(1);
        assertFalse(machine.ok());
    }

    // addSymbol deberia aparecer en symbols()
    @Test
    public void addSymbolShouldAppearInSymbols()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        String[] colors = machine.symbols();
        assertEquals(2, colors.length);
        assertEquals("red", colors[0]);
        assertEquals("blue", colors[1]);
    }

    // delSymbol deberia quitar el color de symbols()
    @Test
    public void delSymbolShouldRemoveColorFromSymbols()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.delSymbol("red");
        String[] colors = machine.symbols();
        assertEquals(1, colors.length);
        assertEquals("blue", colors[0]);
    }

    // placeSymbol deberia dejar ese color visible
    @Test
    public void placeSymbolShouldChangeVisibleConfiguration()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.placeSymbol(1, "blue");
        assertEquals("blue", machine.configuration()[0]);
    }

    // swap deberia intercambiar el color visible de dos ruedas
    @Test
    public void swapShouldExchangeVisibleSymbols()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.swap(1, 2);
        String[] config = machine.configuration();
        assertEquals("blue", config[0]);
        assertEquals("red", config[1]);
    }

    // una rueda fijada no deberia cambiar al girarla
    @Test
    public void lockedWheelShouldNotChangeWhenSpun()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.lock(1);
        machine.spin(1);
        assertEquals("red", machine.configuration()[0]);
    }

    // unlock deberia permitir girar de nuevo
    @Test
    public void unlockShouldAllowSpinAgain()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.lock(1);
        machine.unlock(1);
        machine.spin(1, 1);
        assertEquals("blue", machine.configuration()[0]);
    }

    // spin con pasos deberia mover esa cantidad exacta
    @Test
    public void spinWithStepsShouldMoveExactNumberOfPositions()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(1, "green");
        // empieza en "red" (indice 0), avanza 2 pasos -> "green"
        machine.spin(1, 2);
        assertEquals("green", machine.configuration()[0]);
    }

    // spin con configuracion deberia dejar exactamente esos colores
    @Test
    public void spinWithConfigurationShouldSetExactColors()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(2, "red");
        machine.addSymbol(2, "green");
        machine.spin(new String[] {"blue", "green"});
        String[] config = machine.configuration();
        assertEquals("blue", config[0]);
        assertEquals("green", config[1]);
    }

    // spin con configuracion de tamano distinto no deberia aplicarse
    @Test
    public void spinWithWrongSizeConfigurationShouldSetOkFalse()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.spin(new String[] {"red", "blue"});
        assertFalse(machine.ok());
    }

    // spin con configuracion no deberia afectar una rueda fijada
    @Test
    public void spinWithConfigurationShouldNotAffectLockedWheel()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "red");
        machine.addSymbol(2, "green");
        machine.lock(1);
        machine.spin(new String[] {"blue", "green"});
        assertEquals("red", machine.configuration()[0]);
        assertEquals("green", machine.configuration()[1]);
    }

    // isJackpot deberia ser true si todas las ruedas coinciden
    @Test
    public void isJackpotShouldBeTrueWhenAllWheelsMatch()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "red");
        assertTrue(machine.isJackpot());
    }

    // isJackpot no deberia ser true si las ruedas son distintas
    @Test
    public void isJackpotShouldBeFalseWhenWheelsDiffer()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        assertFalse(machine.isJackpot());
    }

    // isJackpot no deberia ser true si una rueda esta vacia
    @Test
    public void isJackpotShouldBeFalseWhenAWheelIsEmpty()
    {
        machine.addWheel(1);
        assertFalse(machine.isJackpot());
    }

    // placeSymbol con un color que la rueda no tiene no deberia cambiar nada
    @Test
    public void placeSymbolWithMissingColorShouldSetOkFalse()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.placeSymbol(1, "purple");
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
    }

    // spin sobre una rueda fija no deberia ser exitoso
    @Test
    public void spinOnLockedWheelShouldSetOkFalse()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.lock(1);
        machine.spin(1);
        assertFalse(machine.ok());
    }

    // spin sobre una rueda sin simbolos no deberia ser exitoso
    @Test
    public void spinOnEmptyWheelShouldSetOkFalse()
    {
        machine.addWheel(1);
        machine.spin(1);
        assertFalse(machine.ok());
    }

    // delSymbol de un color que no existe en ninguna rueda no deberia ser exitoso
    @Test
    public void delSymbolWithMissingColorShouldSetOkFalse()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.delSymbol("purple");
        assertFalse(machine.ok());
    }

    // addSymbol con un color que el canvas no pinta no deberia agregarse
    @Test
    public void addSymbolWithUnsupportedColorShouldSetOkFalse()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "purple");
        assertFalse(machine.ok());
        assertEquals(0, machine.symbols().length);
    }

    // addSymbol con un color repetido en la misma rueda no deberia agregarse
    @Test
    public void addSymbolWithRepeatedColorShouldSetOkFalse()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "red");
        assertFalse(machine.ok());
        assertEquals(1, machine.symbols().length);
    }

    // spin con una configuracion que pide un color inexistente no deberia aplicarse
    @Test
    public void spinWithUnavailableColorShouldNotChangeAnything()
    {
        machine.addWheel(1);
        machine.addWheel(2);
        machine.addSymbol(1, "red");
        machine.addSymbol(2, "blue");
        machine.spin(new String[] {"green", "blue"});
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[0]);
        assertEquals("blue", machine.configuration()[1]);
    }

    // delSymbol de un color anterior al actual no deberia cambiar el visible
    @Test
    public void delSymbolBeforeCurrentShouldKeepVisibleSymbol()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.placeSymbol(1, "blue");
        machine.delSymbol("red");
        assertEquals("blue", machine.configuration()[0]);
    }

    // delSymbol del simbolo actual deberia dejar la rueda sin nada visible
    @Test
    public void delSymbolOfCurrentShouldLeaveWheelEmpty()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        machine.delSymbol("red");
        assertEquals("none", machine.configuration()[0]);
        assertFalse(machine.isJackpot());
    }
}
