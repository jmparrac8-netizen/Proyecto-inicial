/**
 * Its a slot machine with wheels and symbols
 *
 * @author Tomas Arevalo-Jose Parra
 * @version 1.0
 */

import java.util.ArrayList;

public class SlotMachine
{
    private ArrayList<Wheel> wheels;
    private boolean visible;
    private boolean ok;

    /**
     * Crea una maquina tragamonedas
     */
    public SlotMachine()
    {
        wheels = new ArrayList<>();
        visible = false;
        ok = true;
    }

    /**
     * Añade una rueda a la maquina
     */
    public void addWheel(int xPos, int yPos)
    {
        Wheel wheel = new Wheel(xPos, yPos);
        wheels.add(wheel);
    }

    /**
     * Elimina una rueda de la maquina
     */
    public void delWheel(int xPos, int yPos)
    {
        for (int i = 0; i < wheels.size(); i++) {
            if (wheels.get(i).getXPosition() == xPos &&
                wheels.get(i).getYPosition() == yPos) {
                wheels.remove(i);
                return;
            }
        }

        ok = false;
    }

    /**
     * Añade un simbolo a una rueda
     */
    public void addSymbol(int xPos, int yPos, String color)
    {
        for (int i = 0; i < wheels.size(); i++) {
            if (wheels.get(i).getXPosition() == xPos &&
                wheels.get(i).getYPosition() == yPos) {
                wheels.get(i).addSymbols(color);
                return;
            }
        }

        ok = false;
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
     * Hace girar una rueda
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
     * Consulta si la configuracion es ganadora
     */
    public void isjackpot()
    {
        if (wheels.size() == 0) {
            ok = false;
            return;
        }

        String color = wheels.get(0).getVisibleSymbols();

        for (int i = 1; i < wheels.size(); i++) {
            if (!wheels.get(i).getVisibleSymbols().equalsIgnoreCase(color)) {
                ok = false;
                return;
            }
        }

        ok = true;
    }

    /**
     * Hace visible la maquina
     */
    public void makeVisible()
    {
        visible = true;
    }

    /**
     * Hace invisible la maquina
     */
    public void makeInvisible()
    {
        visible = false;
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
    public void ok()
    {
        if (ok == false) {
            return;
        }
    }

    /**
     * Indica que ocurrio un error
     */
    public void Itserror(String message)
    {
        ok = false;
    }
}