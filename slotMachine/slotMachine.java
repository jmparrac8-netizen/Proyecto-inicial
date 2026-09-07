import java.util.ArrayList;

/**
 * SlotMachine simula una maquina tragamonedas.
 * La maquina tiene varias ruedas y cada rueda muestra un simbolo
 * (un circulo de un color) a la vez.
 *
 * Las posiciones se enumeran a partir de 1. Si una posicion dada es
 * menor a 1 se usa la posicion 1, y si es mayor al maximo posible se
 * usa el maximo.
 *
 * @author Tomas Arevalo - Jose Parra
 * @version 1.0
 */
public class SlotMachine
{
    private ArrayList<Wheel> wheels;
    private boolean ok;

    /**
     * Crea una maquina tragamonedas sin ruedas.
     */
    public SlotMachine()
    {
        wheels = new ArrayList<>();
        ok = true;
    }

    /**
     * Adiciona una rueda vacia en la posicion indicada.
     *
     * @param pos posicion donde queda la nueva rueda (empieza en 1)
     */
    public void addWheel(int pos)
    {
        int index = fixPos(pos, wheels.size() + 1) - 1;
        int xPos = 60 + index * 100;
        int yPos = 100;
        wheels.add(index, new Wheel(xPos, yPos));
        ok = true;
    }

    /**
     * Elimina la rueda que se encuentra en la posicion indicada.
     *
     * @param pos posicion de la rueda a eliminar (empieza en 1)
     */
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

    /**
     * Adiciona un simbolo del color indicado a la rueda dada.
     *
     * @param pos posicion de la rueda (empieza en 1)
     * @param color color del nuevo simbolo (nombre de color CSS)
     */
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

    /**
     * Elimina el simbolo del color indicado de todas las ruedas.
     *
     * @param color color del simbolo a eliminar
     */
    public void delSymbol(String color)
    {
        for (Wheel wheel : wheels) {
            wheel.delSymbol(color);
        }
        ok = true;
    }

    /**
     * Ubica en la rueda indicada el simbolo del color dado.
     *
     * @param wheel posicion de la rueda (empieza en 1)
     * @param color color del simbolo a ubicar
     */
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

    /**
     * Hace girar al azar la rueda indicada.
     *
     * @param wheel posicion de la rueda (empieza en 1)
     */
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

    /**
     * Hace girar al azar todas las ruedas de la maquina.
     */
    public void spin()
    {
        for (Wheel wheel : wheels) {
            wheel.spin();
        }
        ok = true;
    }

    /**
     * Consulta los colores de los simbolos de la primera rueda, en el
     * orden en que fueron adicionados (empezando por el 1).
     *
     * @return arreglo con los colores de los simbolos
     */
    public String[] symbols()
    {
        if (wheels.isEmpty()) {
            return new String[0];
        }
        return wheels.get(0).getSymbols();
    }

    /**
     * Consulta cuantos simbolos de color diferente tiene la primera rueda.
     *
     * @return cantidad de colores distintos
     */
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

    /**
     * Consulta la configuracion actual de la maquina: el color visible
     * de cada rueda, de izquierda a derecha.
     *
     * @return arreglo con el color visible de cada rueda
     */
    public String[] configuration()
    {
        String[] colors = new String[wheels.size()];
        for (int i = 0; i < wheels.size(); i++) {
            colors[i] = wheels.get(i).getVisibleSymbol();
        }
        return colors;
    }

    /**
     * Consulta si la configuracion actual es ganadora, es decir, si
     * todas las ruedas muestran el mismo color.
     *
     * @return true si la maquina esta en una configuracion ganadora
     */
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

    /**
     * Hace visible la maquina y todas sus ruedas.
     */
    public void makeVisible()
    {
        for (Wheel wheel : wheels) {
            wheel.makeVisible();
        }
    }

    /**
     * Hace invisible la maquina y todas sus ruedas.
     */
    public void makeInvisible()
    {
        for (Wheel wheel : wheels) {
            wheel.makeInvisible();
        }
    }

    /**
     * Termina el simulador, ocultando la maquina.
     */
    public void exit()
    {
        makeInvisible();
    }

    /**
     * Consulta si la ultima operacion realizada fue exitosa.
     *
     * @return true si la ultima operacion fue correcta
     */
    public boolean ok()
    {
        return ok;
    }

    /**
     * Ajusta una posicion al rango valido [1, max].
     *
     * @param pos posicion solicitada
     * @param max posicion maxima valida
     * @return la posicion ajustada
     */
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
