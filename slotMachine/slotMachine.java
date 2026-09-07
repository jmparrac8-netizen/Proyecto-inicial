import java.util.ArrayList;

// Autor: Tomas Arevalo - Jose Parra
// Simula una maquina tragamonedas con varias ruedas.
// Cada rueda muestra un simbolo (un circulo de un color) a la vez.
// Las posiciones empiezan en 1. Si una posicion es invalida, se ajusta
// al valor mas cercano permitido.
public class SlotMachine
{
    private ArrayList<Wheel> wheels;
    private boolean visible;
    private boolean ok;

    // Crea una maquina sin ruedas
    public SlotMachine()
    {
        wheels = new ArrayList<>();
        visible = true;
        ok = true;
    }

    // Adiciona una rueda vacia en la posicion indicada
    public void addWheel(int pos)
    {
        int index = fixPos(pos, wheels.size() + 1) - 1;
        int xPos = 60 + index * 100;
        int yPos = 100;
        Wheel wheel = new Wheel(xPos, yPos);
        if (!visible) {
            wheel.makeInvisible();
        }
        wheels.add(index, wheel);
        ok = true;
    }

    // Intercambia la posicion de dos ruedas
    public void swap(int wheel1, int wheel2)
    {
        if (wheels.isEmpty()) {
            ok = false;
            return;
        }
        int i1 = fixPos(wheel1, wheels.size()) - 1;
        int i2 = fixPos(wheel2, wheels.size()) - 1;
        Wheel temp = wheels.get(i1);
        wheels.set(i1, wheels.get(i2));
        wheels.set(i2, temp);
        ok = true;
    }

    // Fija una rueda: deja de responder a los distintos spin
    public void lock(int wheel)
    {
        if (wheels.isEmpty()) {
            ok = false;
            return;
        }
        int index = fixPos(wheel, wheels.size()) - 1;
        wheels.get(index).lock();
        ok = true;
    }

    // Suelta una rueda previamente fijada
    public void unlock(int wheel)
    {
        if (wheels.isEmpty()) {
            ok = false;
            return;
        }
        int index = fixPos(wheel, wheels.size()) - 1;
        wheels.get(index).unlock();
        ok = true;
    }

    // Elimina la rueda en la posicion indicada
    public void delWheel(int pos)
    {
        if (wheels.isEmpty()) {
            ok = false;
            return;
        }
        int index = fixPos(pos, wheels.size()) - 1;
        wheels.get(index).makeInvisible();
        wheels.remove(index);
        ok = true;
    }

    // Adiciona un simbolo del color dado a una rueda
    public void addSymbol(int pos, String color)
    {
        if (wheels.isEmpty()) {
            ok = false;
            return;
        }
        int index = fixPos(pos, wheels.size()) - 1;
        wheels.get(index).addSymbol(color);
        ok = true;
    }

    // Elimina un simbolo del color dado de todas las ruedas
    public void delSymbol(String color)
    {
        for (Wheel wheel : wheels) {
            wheel.delSymbol(color);
        }
        ok = true;
    }

    // Ubica en una rueda el simbolo del color dado
    public void placeSymbol(int wheel, String color)
    {
        if (wheels.isEmpty()) {
            ok = false;
            return;
        }
        int index = fixPos(wheel, wheels.size()) - 1;
        wheels.get(index).placeSymbol(color);
        ok = true;
    }

    // Hace girar al azar una rueda
    public void spin(int wheel)
    {
        if (wheels.isEmpty()) {
            ok = false;
            return;
        }
        int index = fixPos(wheel, wheels.size()) - 1;
        wheels.get(index).spin();
        ok = true;
    }

    // Hace girar al azar todas las ruedas
    public void spin()
    {
        for (Wheel wheel : wheels) {
            wheel.spin();
        }
        ok = true;
    }

    // Rota una rueda un numero de pasos. No afecta una rueda fijada.
    public void spin(int wheel, int steps)
    {
        if (wheels.isEmpty()) {
            ok = false;
            return;
        }
        int index = fixPos(wheel, wheels.size()) - 1;
        wheels.get(index).spin(steps);
        ok = true;
    }

    // Deja la maquina en la configuracion dada. No afecta ruedas fijadas.
    public void spin(String[] configuration)
    {
        if (configuration.length != wheels.size()) {
            ok = false;
            return;
        }
        for (int i = 0; i < wheels.size(); i++) {
            if (!wheels.get(i).isLocked()) {
                wheels.get(i).placeSymbol(configuration[i]);
            }
        }
        ok = true;
    }

    // Devuelve los colores de los simbolos de la primera rueda
    public String[] symbols()
    {
        if (wheels.isEmpty()) {
            return new String[0];
        }
        return wheels.get(0).getSymbols();
    }

    // Cuenta cuantos colores distintos hay en la primera rueda
    public int distinctSymbols()
    {
        String[] colors = symbols();
        int count = 0;
        for (int i = 0; i < colors.length; i++) {
            boolean repeated = false;
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

    // Devuelve el color visible de cada rueda, de izquierda a derecha
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
    }

    // Hace invisible la maquina y todas sus ruedas
    public void makeInvisible()
    {
        visible = false;
        for (Wheel wheel : wheels) {
            wheel.makeInvisible();
        }
    }

    // Termina el simulador
    public void exit()
    {
        makeInvisible();
    }

    // Indica si la ultima operacion fue exitosa
    public boolean ok()
    {
        return ok;
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
}
