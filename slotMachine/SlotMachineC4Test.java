import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

// Pruebas de unidad del ciclo 4: tipos de ruedas y de simbolos.
// Todas corren en modo invisible para que no aparezca ninguna ventana.
public class SlotMachineC4Test
{
    private SlotMachine machine;

    // Antes de cada prueba: maquina nueva e invisible
    @Before
    public void setUp()
    {
        machine = new SlotMachine();
        machine.makeInvisible();
    }

    // addWheel sin tipo deberia crear una rueda normal
    @Test
    public void addWheelWithoutTypeShouldCreateNormalWheel()
    {
        machine.addWheel(1);
        assertEquals("normal", machine.wheelAt(1).type());
    }

    // addWheel deberia crear cada tipo de rueda
    @Test
    public void addWheelShouldCreateEachType()
    {
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.addWheel("rebel", 3);
        machine.addWheel("righty", 4);
        assertEquals("normal", machine.wheelAt(1).type());
        assertEquals("lefty", machine.wheelAt(2).type());
        assertEquals("rebel", machine.wheelAt(3).type());
        assertEquals("righty", machine.wheelAt(4).type());
    }

    // addWheel con un tipo que no existe deberia fallar
    @Test
    public void addWheelWithUnknownTypeShouldSetOkFalse()
    {
        machine.addWheel("fast", 1);
        assertFalse(machine.ok());
        assertEquals(0, machine.configuration().length);
    }

    // addWheel no deberia distinguir mayusculas
    @Test
    public void addWheelShouldIgnoreCaseOfType()
    {
        machine.addWheel("LEFTY", 1);
        assertTrue(machine.ok());
        assertEquals("lefty", machine.wheelAt(1).type());
    }

    // addSymbol sin tipo deberia crear un simbolo normal
    @Test
    public void addSymbolWithoutTypeShouldCreateNormalSymbol()
    {
        machine.addWheel(1);
        machine.addSymbol(1, "red");
        assertEquals("normal", machine.wheelAt(1).symbolOf("red").type());
    }

    // addSymbol deberia crear cada tipo de simbolo
    @Test
    public void addSymbolShouldCreateEachType()
    {
        machine.addWheel(1);
        machine.addSymbol("normal", 1, "red");
        machine.addSymbol("ephemeral", 1, "blue");
        machine.addSymbol("shy", 1, "green");
        assertEquals("normal", machine.wheelAt(1).symbolOf("red").type());
        assertEquals("ephemeral", machine.wheelAt(1).symbolOf("blue").type());
        assertEquals("shy", machine.wheelAt(1).symbolOf("green").type());
    }

    // addSymbol con un tipo que no existe deberia fallar
    @Test
    public void addSymbolWithUnknownTypeShouldSetOkFalse()
    {
        machine.addWheel(1);
        machine.addSymbol("fast", 1, "red");
        assertFalse(machine.ok());
        assertEquals(0, machine.symbols().length);
    }

    // delSymbol deberia quitar un simbolo de cualquier tipo
    @Test
    public void delSymbolShouldRemoveSpecialSymbols()
    {
        machine.addWheel(1);
        machine.addSymbol("ephemeral", 1, "red");
        machine.addSymbol("shy", 1, "blue");
        machine.delSymbol("red");
        machine.delSymbol("blue");
        assertEquals(0, machine.symbols().length);
    }

    // lefty deberia copiar a la rueda de su izquierda al girar
    @Test
    public void leftyShouldCopyLeftWheelWhenSpun()
    {
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(2, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "blue");
        machine.spin(2, 1);
        assertEquals("blue", machine.configuration()[1]);
        machine.spin(2);
        assertEquals("blue", machine.configuration()[1]);
    }

    // lefty sin rueda a su izquierda deberia girar como una normal
    @Test
    public void leftyWithoutLeftWheelShouldSpinNormally()
    {
        machine.addWheel("lefty", 1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.spin(1, 1);
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
    }

    // lefty no deberia copiar un color que no tiene
    @Test
    public void leftyShouldNotCopyMissingColor()
    {
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.addSymbol(1, "green");
        machine.addSymbol(2, "red");
        machine.addSymbol(2, "blue");
        machine.spin(2, 1);
        assertEquals("blue", machine.configuration()[1]);
    }

    // lefty fijada no deberia copiar
    @Test
    public void lockedLeftyShouldNotCopy()
    {
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(2, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(1, "blue");
        machine.lock(2);
        machine.spin(2, 1);
        assertFalse(machine.ok());
        assertEquals("red", machine.configuration()[1]);
    }

    // spin de todas las ruedas con una lefty deberia dejar premio
    @Test
    public void spinAllWithLeftyShouldLeaveJackpot()
    {
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        for (int i = 1; i <= 2; i++) {
            machine.addSymbol(i, "red");
            machine.addSymbol(i, "blue");
            machine.addSymbol(i, "green");
        }
        machine.spin();
        assertTrue(machine.isJackpot());
    }

    // righty deberia copiar a la rueda de su derecha al girar
    @Test
    public void rightyShouldCopyRightWheelWhenSpun()
    {
        machine.addWheel("righty", 1);
        machine.addWheel("normal", 2);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.addSymbol(2, "red");
        machine.addSymbol(2, "blue");
        machine.placeSymbol(2, "blue");
        machine.spin(1, 1);
        assertEquals("blue", machine.configuration()[0]);
    }

    // rebel no deberia dejarse fijar
    @Test
    public void rebelShouldNotBeLocked()
    {
        machine.addWheel("rebel", 1);
        machine.addSymbol(1, "red");
        machine.addSymbol(1, "blue");
        machine.lock(1);
        assertFalse(machine.ok());
        assertFalse(machine.wheelAt(1).isLocked());
        machine.spin(1, 1);
        assertTrue(machine.ok());
        assertEquals("blue", machine.configuration()[0]);
    }

    // rebel no deberia dejarse eliminar
    @Test
    public void rebelShouldNotBeDeleted()
    {
        machine.addWheel("rebel", 1);
        machine.delWheel(1);
        assertFalse(machine.ok());
        assertEquals(1, machine.configuration().length);
    }

    // rebel no deberia dejarse intercambiar
    @Test
    public void rebelShouldNotBeSwapped()
    {
        machine.addWheel("rebel", 1);
        machine.addWheel("normal", 2);
        machine.swap(1, 2);
        assertFalse(machine.ok());
        assertEquals("rebel", machine.wheelAt(1).type());
        assertEquals("normal", machine.wheelAt(2).type());
    }

    // las demas ruedas deberian seguir intercambiandose
    @Test
    public void nonRebelWheelsShouldStillSwap()
    {
        machine.addWheel("normal", 1);
        machine.addWheel("lefty", 2);
        machine.swap(1, 2);
        assertTrue(machine.ok());
        assertEquals("lefty", machine.wheelAt(1).type());
        assertEquals("normal", machine.wheelAt(2).type());
    }

    // ephemeral deberia empezar con el tamano completo
    @Test
    public void ephemeralShouldStartWithFullSize()
    {
        machine.addWheel(1);
        machine.addSymbol("ephemeral", 1, "red");
        EphemeralSymbol symbol = (EphemeralSymbol) machine.wheelAt(1).symbolOf("red");
        assertEquals(30, symbol.size());
    }

    // ephemeral deberia achicarse en cada giro
    @Test
    public void ephemeralShouldShrinkOnEachSpin()
    {
        machine.addWheel(1);
        machine.addSymbol("ephemeral", 1, "red");
        machine.addSymbol(1, "blue");
        EphemeralSymbol symbol = (EphemeralSymbol) machine.wheelAt(1).symbolOf("red");
        machine.spin(1, 1);
        assertEquals(25, symbol.size());
        machine.spin(1);
        assertEquals(20, symbol.size());
    }

    // ephemeral no deberia pasar de un punto
    @Test
    public void ephemeralShouldStopAtAPoint()
    {
        machine.addWheel(1);
        machine.addSymbol("ephemeral", 1, "red");
        EphemeralSymbol symbol = (EphemeralSymbol) machine.wheelAt(1).symbolOf("red");
        for (int i = 0; i < 20; i++) {
            machine.spin(1, 1);
        }
        assertEquals(2, symbol.size());
    }

    // ephemeral no deberia achicarse si la rueda esta fija
    @Test
    public void ephemeralShouldNotShrinkOnLockedWheel()
    {
        machine.addWheel(1);
        machine.addSymbol("ephemeral", 1, "red");
        EphemeralSymbol symbol = (EphemeralSymbol) machine.wheelAt(1).symbolOf("red");
        machine.lock(1);
        machine.spin(1, 1);
        assertEquals(30, symbol.size());
    }

    // shy deberia empezar visible
    @Test
    public void shyShouldStartVisible()
    {
        machine.addWheel(1);
        machine.addSymbol("shy", 1, "red");
        ShySymbol symbol = (ShySymbol) machine.wheelAt(1).symbolOf("red");
        assertTrue(symbol.isShown());
    }

    // shy deberia alternar su estado cada vez que es seleccionado
    @Test
    public void shyShouldToggleWhenSelected()
    {
        machine.addWheel(1);
        machine.addSymbol("shy", 1, "red");
        machine.addSymbol(1, "blue");
        ShySymbol symbol = (ShySymbol) machine.wheelAt(1).symbolOf("red");
        machine.placeSymbol(1, "red");
        assertFalse(symbol.isShown());
        machine.placeSymbol(1, "red");
        assertTrue(symbol.isShown());
    }

    // shy no deberia cambiar si no es seleccionado
    @Test
    public void shyShouldNotChangeWhenNotSelected()
    {
        machine.addWheel(1);
        machine.addSymbol("shy", 1, "red");
        machine.addSymbol(1, "blue");
        ShySymbol symbol = (ShySymbol) machine.wheelAt(1).symbolOf("red");
        machine.placeSymbol(1, "blue");
        assertTrue(symbol.isShown());
    }

    // shy invisible deberia seguir contando en la configuracion
    @Test
    public void hiddenShyShouldStillCountInConfiguration()
    {
        machine.addWheel(1);
        machine.addSymbol("shy", 1, "red");
        machine.addSymbol(1, "blue");
        machine.placeSymbol(1, "red");
        assertEquals("red", machine.configuration()[0]);
    }
}
