import java.util.ArrayList;

// Autor: Tomas Arevalo - Jose Parra
// Una rueda de la maquina tragamonedas.
// Puede tener varios simbolos, pero solo muestra uno a la vez.
public class Wheel
{
    private int xPosition;
    private int yPosition;
    private ArrayList<Symbol> symbols;
    private int currentIndex;
    private boolean visible;
    private boolean locked;

    // Crea una rueda vacia en la posicion dada del canvas
    public Wheel(int xPos, int yPos)
    {
        xPosition = xPos;
        yPosition = yPos;
        symbols = new ArrayList<>();
        currentIndex = -1;
        visible = true;
        locked = false;
    }

    // Adiciona un simbolo. Si es el primero, queda visible de una vez.
    public void addSymbol(String color)
    {
        symbols.add(new Symbol(color, xPosition, yPosition));
        if (currentIndex == -1) {
            placeSymbol(color);
        }
    }

    // Elimina el primer simbolo con el color dado
    public void delSymbol(String color)
    {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equalsIgnoreCase(color)) {
                symbols.get(i).hide();
                symbols.remove(i);
                if (i == currentIndex) {
                    currentIndex = -1;
                }
                return;
            }
        }
    }

    // Ubica en la rueda el simbolo del color dado
    public void placeSymbol(String color)
    {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equalsIgnoreCase(color)) {
                hideCurrent();
                currentIndex = i;
                if (visible) {
                    symbols.get(i).show();
                }
                return;
            }
        }
    }

    // Gira la rueda al azar. No hace nada si esta fijada.
    public void spin()
    {
        if (locked || symbols.isEmpty()) {
            return;
        }
        int randomIndex = (int) (Math.random() * symbols.size());
        placeSymbol(symbols.get(randomIndex).getColor());
    }

    // Rota la rueda un numero de pasos. No hace nada si esta fijada.
    public void spin(int steps)
    {
        if (locked || symbols.isEmpty()) {
            return;
        }
        int start = (currentIndex == -1) ? 0 : currentIndex;
        for (int step = 1; step <= steps; step++) {
            int nextIndex = (start + step) % symbols.size();
            placeSymbol(symbols.get(nextIndex).getColor());
            if (visible) {
                Canvas.getCanvas().wait(300);
            }
        }
    }

    // Fija la rueda
    public void lock()
    {
        locked = true;
    }

    // Suelta la rueda
    public void unlock()
    {
        locked = false;
    }

    // Indica si la rueda esta fijada
    public boolean isLocked()
    {
        return locked;
    }

    // Devuelve los colores de los simbolos de la rueda
    public String[] getSymbols()
    {
        String[] colors = new String[symbols.size()];
        for (int i = 0; i < symbols.size(); i++) {
            colors[i] = symbols.get(i).getColor();
        }
        return colors;
    }

    // Devuelve el color del simbolo actualmente ubicado
    public String getVisibleSymbol()
    {
        if (currentIndex == -1) {
            return "none";
        }
        return symbols.get(currentIndex).getColor();
    }

    // Hace visible el simbolo actual
    public void makeVisible()
    {
        visible = true;
        if (currentIndex != -1) {
            symbols.get(currentIndex).show();
        }
    }

    // Oculta el simbolo actual
    public void makeInvisible()
    {
        visible = false;
        hideCurrent();
    }

    // Oculta el simbolo visible, si hay alguno
    private void hideCurrent()
    {
        if (currentIndex != -1) {
            symbols.get(currentIndex).hide();
        }
    }
}
