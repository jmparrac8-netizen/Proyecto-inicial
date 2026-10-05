import java.util.ArrayList;

// Arevalo-Parra
// Simula una maquina tragamonedas con varias ruedas.
// Cada rueda muestra un simbolo (un circulo de color) a la vez.
// Las posiciones empiezan en 1. Si una posicion es invalida, se ajusta
// al valor mas cercano permitido.
public class SlotMachine
{
    private static final int START_X = 18;
    private static final int STEP_X = 46;
    private static final int POS_Y = 130;

    private ArrayList<Wheel> wheels;
    private boolean visible;
    private boolean ok;

    // Crea una maquina sin ruedas
    public SlotMachine()
    {
        wheels = new ArrayList<Wheel>();
        visible = true;
        ok = true;
    }

    // Crea una maquina de n ruedas con los mismos n simbolos
    public SlotMachine(int n)
    {
        wheels = new ArrayList<Wheel>();
        visible = false;
        ok = true;
        String[] palette = Symbol.colors();
        if (n < 2 || n > palette.length) {
            ok = false;
            return;
        }
        for (int i = 1; i <= n; i++) {
            addWheel(i);
            for (int j = 0; j < n; j++) {
                addSymbol(i, palette[j]);
            }
            spin(i, (int) (Math.random() * n));
        }
        if (isJackpot()) {
            spin(1, 1);
        }
        ok = true;
    }

    // Adiciona una rueda normal vacia en la posicion indicada
    public void addWheel(int pos)
    {
        addWheel("normal", pos);
    }

    // Adiciona una rueda vacia del tipo dado en la posicion indicada
    public void addWheel(String type, int pos)
    {
        if (!Wheel.isValidType(type)) {
            fail("Ese tipo de rueda no existe.");
            return;
        }
        int index = fixPos(pos, wheels.size() + 1) - 1;
        Wheel wheel = Wheel.create(type, START_X, POS_Y);
        wheels.add(index, wheel);
        if (visible) {
            wheel.makeVisible();
        }
        ok = true;
        refresh();
    }

    // Elimina la rueda en la posicion indicada
    public void delWheel(int pos)
    {
        if (wheels.isEmpty()) {
            fail("No hay ruedas para eliminar.");
            return;
        }
        int index = fixPos(pos, wheels.size()) - 1;
        if (!wheels.get(index).canDelete()) {
            fail("Esa rueda es rebelde y no se deja eliminar.");
            return;
        }
        wheels.get(index).makeInvisible();
        wheels.remove(index);
        ok = true;
        refresh();
    }

    // Intercambia la posicion de dos ruedas
    public void swap(int wheel1, int wheel2)
    {
        if (wheels.isEmpty()) {
            fail("No hay ruedas para intercambiar.");
            return;
        }
        int i1 = fixPos(wheel1, wheels.size()) - 1;
        int i2 = fixPos(wheel2, wheels.size()) - 1;
        if (i1 != i2 && (!wheels.get(i1).canSwap() || !wheels.get(i2).canSwap())) {
            fail("Una rueda rebelde no se deja intercambiar.");
            return;
        }
        Wheel temp = wheels.get(i1);
        wheels.set(i1, wheels.get(i2));
        wheels.set(i2, temp);
        ok = true;
        refresh();
    }

    // Fija una rueda y devuelve su posicion, o 0 si no se pudo
    public int lock(int wheel)
    {
        if (wheels.isEmpty()) {
            fail("No hay ruedas para fijar.");
            return 0;
        }
        int index = fixPos(wheel, wheels.size()) - 1;
        if (!wheels.get(index).canLock()) {
            fail("Esa rueda es rebelde y no se deja fijar.");
            return 0;
        }
        wheels.get(index).lock();
        ok = true;
        return index + 1;
    }

    // Suelta una rueda previamente fijada
    public void unlock(int wheel)
    {
        if (wheels.isEmpty()) {
            fail("No hay ruedas para soltar.");
            return;
        }
        wheels.get(fixPos(wheel, wheels.size()) - 1).unlock();
        ok = true;
    }

    // Adiciona un simbolo normal del color dado a una rueda
    public void addSymbol(int pos, String color)
    {
        addSymbol("normal", pos, color);
    }

    // Adiciona un simbolo del tipo y color dados a una rueda
    public void addSymbol(String type, int pos, String color)
    {
        if (wheels.isEmpty()) {
            fail("No hay ruedas para agregar el simbolo.");
            return;
        }
        int index = fixPos(pos, wheels.size()) - 1;
        if (!wheels.get(index).addSymbol(type, color)) {
            fail("Ese tipo o color no se puede usar o esa rueda ya lo tiene.");
            return;
        }
        ok = true;
        refresh();
    }

    // Elimina el simbolo del color dado de todas las ruedas
    public void delSymbol(String color)
    {
        boolean removed = false;
        for (Wheel wheel : wheels) {
            if (wheel.delSymbol(color)) {
                removed = true;
            }
        }
        if (!removed) {
            fail("Ese color no existe en ninguna rueda.");
            return;
        }
        ok = true;
        refresh();
    }

    // Ubica en una rueda el simbolo del color dado
    public void placeSymbol(int wheel, String color)
    {
        if (wheels.isEmpty()) {
            fail("No hay ruedas en la maquina.");
            return;
        }
        int index = fixPos(wheel, wheels.size()) - 1;
        if (!wheels.get(index).placeSymbol(color)) {
            fail("Esa rueda no tiene un simbolo de ese color.");
            return;
        }
        ok = true;
        refresh();
    }

    // Hace girar al azar una rueda
    public void spin(int wheel)
    {
        if (wheels.isEmpty()) {
            fail("No hay ruedas en la maquina.");
            return;
        }
        int index = fixPos(wheel, wheels.size()) - 1;
        if (!wheels.get(index).spin(leftOf(index), rightOf(index))) {
            fail("Esa rueda esta fija o no tiene simbolos.");
            return;
        }
        ok = true;
        refresh();
    }

    // Rota una rueda un numero de pasos, paso a paso si esta visible
    public void spin(int wheel, int steps)
    {
        if (wheels.isEmpty()) {
            fail("No hay ruedas en la maquina.");
            return;
        }
        int index = fixPos(wheel, wheels.size()) - 1;
        if (!wheels.get(index).spin(steps, leftOf(index), rightOf(index))) {
            fail("Esa rueda esta fija o no tiene simbolos.");
            return;
        }
        ok = true;
        refresh();
    }

    // Hace girar al azar todas las ruedas que no esten fijadas
    public void spin()
    {
        if (wheels.isEmpty()) {
            fail("No hay ruedas en la maquina.");
            return;
        }
        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).spin(leftOf(i), rightOf(i));
        }
        ok = true;
        refresh();
    }

    // Deja la maquina en la configuracion dada.
    public void spin(String[] configuration)
    {
        if (configuration.length != wheels.size()) {
            fail("La configuracion no coincide con el numero de ruedas.");
            return;
        }
        if (!canApply(configuration)) {
            fail("Alguna rueda no tiene el simbolo pedido.");
            return;
        }
        for (int i = 0; i < wheels.size(); i++) {
            if (!wheels.get(i).isLocked()) {
                wheels.get(i).placeSymbol(configuration[i]);
            }
        }
        ok = true;
        refresh();
    }

    // Devuelve los colores de los simbolos de la primera rueda
    public String[] symbols()
    {
        if (wheels.isEmpty()) {
            return new String[0];
        }
        return wheels.get(0).getSymbols();
    }

    // Cuenta cuantos colores distintos se ven en la maquina
    public int distinctSymbols()
    {
        String[] colors = configuration();
        int count = 0;
        for (int i = 0; i < colors.length; i++) {
            boolean repeated = colors[i].equals("none");
            for (int j = 0; j < i; j++) {
                if (colors[i].equalsIgnoreCase(colors[j])) {
                    repeated = true;
                }
            }
            if (!repeated) {
                count++;
            }
        }
        return count;
    }

    // Devuelve el color visible de cada rueda
    public String[] configuration()
    {
        String[] colors = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++) {
            colors[i] = wheels.get(i).getVisibleSymbol();
        }
        return colors;
    }

    // Indica si todas las ruedas muestran el mismo color
    public boolean isJackpot()
    {
        if (wheels.isEmpty()) {
            return false;
        }
        String first = wheels.get(0).getVisibleSymbol();
        if (first.equals("none")) {
            return false;
        }
        for (Wheel wheel : wheels) {
            if (!wheel.getVisibleSymbol().equalsIgnoreCase(first)) {
                return false;
            }
        }
        return true;
    }

    // Hace visible la maquina y todas sus ruedas
    public void makeVisible()
    {
        visible = true;
        for (Wheel wheel : wheels) {
            wheel.makeVisible();
        }
        ok = true;
        refresh();
    }

    // Hace invisible la maquina y todas sus ruedas
    public void makeInvisible()
    {
        visible = false;
        for (Wheel wheel : wheels) {
            wheel.makeInvisible();
        }
        ok = true;
    }

    // Termina el simulador
    public void exit()
    {
        makeInvisible();
        ok = true;
    }

    // Indica si la ultima operacion se pudo realizar
    public boolean ok()
    {
        return ok;
    }

    // Devuelve la rueda de esa posicion. Sin modificador para poder revisarla desde las pruebas.
    Wheel wheelAt(int pos)
    {
        return wheels.get(fixPos(pos, wheels.size()) - 1);
    }

    // Devuelve la rueda a la izquierda del indice, o null si no hay
    private Wheel leftOf(int index)
    {
        return (index > 0) ? wheels.get(index - 1) : null;
    }

    // Devuelve la rueda a la derecha del indice, o null si no hay
    private Wheel rightOf(int index)
    {
        return (index < wheels.size() - 1) ? wheels.get(index + 1) : null;
    }

    // Revisa si la maquina quedo en estado ganador
    private void refresh()
    {
        boolean win = isJackpot();
        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).moveTo(START_X + i * STEP_X, POS_Y);
            wheels.get(i).highlight(win);
        }
    }

    // Revisa que cada rueda libre tenga el simbolo que pide la configuracion
    private boolean canApply(String[] configuration)
    {
        for (int i = 0; i < wheels.size(); i++) {
            if (!wheels.get(i).isLocked() && !wheels.get(i).hasSymbol(configuration[i])) {
                return false;
            }
        }
        return true;
    }

    // Ajusta una posicion al rango valido [1, max]
    private int fixPos(int pos, int max)
    {
        if (pos < 1) {
            return 1;
        }
        if (pos > max) {
            return max;
        }
        return pos;
    }

    // Marca la operacion como fallida y avisa al usuario
    private void fail(String message)
    {
        ok = false;
        if (visible) {
            javax.swing.JOptionPane.showMessageDialog(null, message);
        }
    }
}
