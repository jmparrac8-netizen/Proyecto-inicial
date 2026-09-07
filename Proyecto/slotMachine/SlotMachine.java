/**
 * Its a slot machine with wheels and symbols
 *
 * @author Tomas Arevalo-Jose Parra
 * @version 1.0
 */

import java.util.ArrayList;

public class SlotMachine{
    private ArrayList<Wheel> wheels;
    private boolean visible;
    private boolean ok;

    /**
     * Crea una maquina tragamonedas
     */
    public SlotMachine()
    {
        wheels = new ArrayList<>();
        visible = true;
        ok = true;
    }

    /**
     * Añade una rueda en la posicion indicada
     */
    public void addWheel(int pos)
    {
        if (pos < 0 || pos > wheels.size()) {
            ok = false;
            return;
        }

        int xPos = 50 + pos * 100;
        int yPos = 100;
        Wheel wheel=new Wheel(xPos, yPos);
        wheels.add(pos,wheel);
        ok = true;
    }

    /**
     * Elimina la rueda en la posicion indicada
     */
    public void delWheel(int pos)
    {
        if (pos < 0 || pos >= wheels.size()) {
            ok = false;
            return;
        }

        wheels.get(pos).makeInvisible();
        wheels.remove(pos);
        ok = true;
    }

    /**
     * Intercambia dos ruedas
     */
    public void swap(int wheel1, int wheel2)
    {
        if (wheel1 < 0 || wheel1 >= wheels.size() ||
            wheel2 < 0 || wheel2 >= wheels.size()) {
            ok = false;
            return;
        }

        Wheel temp = wheels.get(wheel1);
        wheels.set(wheel1, wheels.get(wheel2));
        wheels.set(wheel2, temp);
        ok = true;
    }

    /**
     * Fija una rueda: deja de responder a los spin
     */
    public void lock(int wheel)
    {
        if (wheel < 0 || wheel >= wheels.size()) {
            ok = false;
            return;
        }

        wheels.get(wheel).lock();
        ok = true;
    }

    /**
     * Suelta una rueda previamente fijada
     */
    public void unlock(int wheel)
    {
        if (wheel < 0 || wheel >= wheels.size()) {
            ok = false;
            return;
        }

        wheels.get(wheel).unlock();
        ok = true;
    }

    /**
     * Añade un simbolo a la rueda en la posicion indicada
     */
    public void addSymbol(int pos, String color)
    {
        if (pos < 0 || pos >= wheels.size()) {
            ok = false;
            return;
        }

        wheels.get(pos).addSymbols(color);
        ok = true;
    }

    /**
     * Elimina un simbolo de todas las ruedas
     */
    public void delSymbol(String symbol)
    {
        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).delSymbols(symbol);
        }
    }

    /**
     * Ubica un simbolo en una rueda
     */
    public void placeSymbol(int wheel, String symbol)
    {
        if (wheel >= 0 && wheel < wheels.size()) {
            wheels.get(wheel).placeSymbols(symbol);
        }
        else {
            ok = false;
        }
    }

    /**
     * Hace girar una rueda al azar
     */
    public void spin(int wheel)
    {
        if (wheel >= 0 && wheel < wheels.size()) {
            wheels.get(wheel).spin();
        }
        else {
            ok = false;
        }
    }

    /**
     * Rota una rueda un numero de pasos
     */
    public void spin(int wheel, int steps)
    {
        if (wheel >= 0 && wheel < wheels.size()) {
            wheels.get(wheel).spin(steps);
        }
        else {
            ok = false;
        }
    }

    /**
     * Deja la maquina en la configuracion dada. Las ruedas bloqueadas
     * no se ven afectadas.
     */
    public void spin(String[] setSymbols)
    {
        if (setSymbols.length != wheels.size()) {
            ok = false;
            return;
        }

        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).placeSymbols(setSymbols[i]);
        }
        ok = true;
    }

    /**
     * Hace girar todas las ruedas al azar
     */
    public void spin()
    {
        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).spin();
        }
    }

    /**
     * Consulta los simbolos de la maquina
     */
    public String[] symbols()
    {
        if (wheels.size() == 0) {
            return new String[0];
        }

        return wheels.get(0).getSymbols();
    }

    /**
     * Consulta la cantidad de simbolos diferentes
     */
    public int distinctSymbols()
    {
        String[] colors = symbols();
        int cantidad = 0;

        for (int i = 0; i < colors.length; i++) {
            int repetido = 0;

            for (int j = 0; j < i; j++) {
                if (colors[i].equalsIgnoreCase(colors[j])) {
                    repetido = 1;
                }
            }

            if (repetido == 0) {
                cantidad++;
            }
        }

        return cantidad;
    }

    /**
     * Consulta la configuracion actual de la maquina
     */
    public String[] configuration()
    {
        String[] colors = new String[wheels.size()];

        for (int i = 0; i < wheels.size(); i++) {
            colors[i] = wheels.get(i).getVisibleSymbols();
        }

        return colors;
    }

    /**
     * Consulta si la configuracion actual es ganadora
     */
    public boolean isJackpot()
    {
        if (wheels.size() == 0) {
            return false;
        }

        String color = wheels.get(0).getVisibleSymbols();

        for (int i = 1; i < wheels.size(); i++) {
            if (!wheels.get(i).getVisibleSymbols().equalsIgnoreCase(color)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Hace visible la maquina
     */
    public void makeVisible()
    {
        visible = true;
        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).makeVisible();
        }
    }

    /**
     * Hace invisible la maquina
     */
    public void makeInvisible()
    {
        visible = false;
        for (int i = 0; i < wheels.size(); i++) {
            wheels.get(i).makeInvisible();
        }
    }

    /**
     * Termina la maquina
     */
    public void exit()
    {
        makeInvisible();
    }

    /**
     * Consulta si la ultima operacion fue correcta
     */
    public boolean ok()
    {
        return ok;
    }

    /**
     * Indica que ocurrio un error
     */
    public void Itserror(String message)
    {
        ok = false;
    }
}
