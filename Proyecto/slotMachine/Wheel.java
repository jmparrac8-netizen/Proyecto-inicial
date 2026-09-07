
/**
 * Its a wheel with symbols, part of the slotMachine
 * 
 * @author Tomas Arevalo-Jose Parra 
 * @version 1.0
 */
import java.util.ArrayList;
public class Wheel
{
    private int xPosition;
    private int yPosition;
    private ArrayList<Symbol> symbols;
    private int currentIndex;
    private boolean isVisible;
    private boolean locked;
    

    /**
     * Crea una rueda
     */
    public Wheel(int xpos,int ypos)
    {
        symbols=new ArrayList<>();
        currentIndex = -1;
        isVisible = true;
        locked = false;
        this.xPosition=xpos;
        this.yPosition=ypos;
    }

    /**
     * Añade un symbolo a la rueda
     */
    public void addSymbols(String color)
    {
        Symbol symbol=new Symbol(color,xPosition,yPosition);
        symbols.add(symbol);
    }
    
    /**
     * Elimina el primer symbolo que encuentre con un determinado color
     */
    public void delSymbols(String color)
    {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equalsIgnoreCase(color)) {
                symbols.get(i).makeInVisibleS();
                symbols.remove(i);
                if (i == currentIndex) {
                    currentIndex = -1;
                }
                else if (i < currentIndex) {
                    currentIndex--;
                }
                return;
            }
        }
    }
    
    /**
     * Ubica en la rueda el simbolo del color indicado
     */
    public void placeSymbols(String color)
    {
        for (int i = 0; i < symbols.size(); i++) {
            symbols.get(i).makeInVisibleS();
        }

        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equalsIgnoreCase(color)) {
                currentIndex = i;
                if (isVisible) {
                    symbols.get(i).makeVisibleS();
                }
                return;
            }
        }
    }
    
    /**
     * Hace girar a la rueda al azar. No hace nada si la rueda esta bloqueada.
     */
    public void spin()
    {
        if (locked || symbols.isEmpty()){
            return;
        }
        int newIndex = (int) (Math.random() * symbols.size());
        String findcolor=symbols.get(newIndex).getColor();
        placeSymbols(findcolor);
    }

    /**
     * Rota la rueda un numero de pasos a partir del simbolo actualmente
     * ubicado. Si la rueda esta visible, el recorrido se muestra paso a paso.
     * No hace nada si la rueda esta bloqueada.
     */
    public void spin(int steps)
    {
        if (locked || symbols.isEmpty()){
            return;
        }
        int start;
        if (currentIndex == -1) {
            start = 0;
        } else {
            start = currentIndex;
        }
        for (int step = 1; step <= steps; step++) {
            int nextIndex = (start + step) % symbols.size();
            placeSymbols(symbols.get(nextIndex).getColor());
            if (isVisible && step < steps) {
                Canvas.getCanvas().wait(300);
            }
        }
    }
    
    /**
     * Devuelve los simbolos de la rueda
     */
    public String[] getSymbols()
    {
        String[] colors = new String[symbols.size()];

        for (int i = 0; i < symbols.size(); i++) {
            colors[i] = symbols.get(i).getColor();
        }
        return colors;
    }

    /**
     * Devuelve el color del simbolo actualmente ubicado en la rueda
     */
    public String getVisibleSymbols()
    {
        if (currentIndex == -1) {
            return "None";
        }
        return symbols.get(currentIndex).getColor();
    }

    /**
      *Devuelve la posicion X de la rueda
     */
    public int getXPosition()
    {
        return xPosition;
    }

    /**
    * Devuelve la posicion Y de la rueda
     */
    public int getYPosition()
        {
        return yPosition;
     }

    /**
     * Bloquea la rueda: deja de responder a los distintos spin
     */
    public void lock()
    {
        locked = true;
    }

    /**
     * Libera la rueda: vuelve a responder a los distintos spin
     */
    public void unlock()
    {
        locked = false;
    }

    /**
     * Hace visible en el canvas el simbolo actualmente ubicado
     */
    public void makeVisible()
    {
        isVisible = true;
        if (currentIndex != -1) {
            symbols.get(currentIndex).makeVisibleS();
        }
    }

    /**
     * Oculta del canvas el simbolo actualmente ubicado
     */
    public void makeInvisible()
    {
        isVisible = false;
        if (currentIndex != -1) {
            symbols.get(currentIndex).makeInVisibleS();
        }
    }
}
