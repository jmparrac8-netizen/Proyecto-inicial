import java.util.ArrayList;

// Autor: Tomas Arevalo - Jose Parra
// Una rueda de la maquina tragamonedas.
// Puede guardar varios simbolos, pero solo muestra uno a la vez.
// Se dibuja con un rectangulo y un triangulo que solo aparece cuando la maquina llego a un estado ganador.
public class Wheel
{
    private ArrayList<Symbol> symbols;
    private int currentIndex;
    private int xPosition;
    private int yPosition;
    private boolean visible;
    private boolean locked;
    private Rectangle frame;
    private Triangle mark;

    // Crea una rueda vacia en la posicion dada del canvas
    public Wheel(int x, int y)
    {
        symbols = new ArrayList<Symbol>();
        currentIndex = -1;
        visible = false;
        locked = false;
        frame = new Rectangle();
        frame.changeSize(40, 40);
        frame.changeColor("black");
        mark = new Triangle();
        mark.changeSize(20, 20);
        mark.changeColor("magenta");
        mark.moveHorizontal(-50);
        mark.moveVertical(-25);
        xPosition = 75;
        yPosition = 20;
        moveTo(x, y);
    }

    // Lleva la rueda completa a la posicion (x, y)
    public void moveTo(int x, int y)
    {
        if (x == xPosition && y == yPosition) {
            return;
        }
        frame.moveHorizontal(x - xPosition);
        frame.moveVertical(y - yPosition);
        mark.moveHorizontal(x - xPosition);
        mark.moveVertical(y - yPosition);
        xPosition = x;
        yPosition = y;
        for (Symbol symbol : symbols) {
            symbol.moveTo(x, y);
        }
        refresh();
    }

    // Adiciona un simbolo.
    public boolean addSymbol(String color)
    {
        if (!Symbol.isValidColor(color) || indexOf(color) != -1) {
            return false;
        }
        symbols.add(new Symbol(color, xPosition, yPosition));
        if (currentIndex == -1) {
            placeSymbol(color);
        }
        return true;
    }

    // Elimina el simbolo del color dado.
    public boolean delSymbol(String color)
    {
        int index = indexOf(color);
        if (index == -1) {
            return false;
        }
        symbols.get(index).hide();
        symbols.remove(index);
        if (index == currentIndex) {
            currentIndex = -1;
        } else if (index < currentIndex) {
            currentIndex--;
        }
        refresh();
        return true;
    }

    // Ubica en la rueda el simbolo del color dado.
    public boolean placeSymbol(String color)
    {
        int index = indexOf(color);
        if (index == -1) {
            return false;
        }
        place(index);
        return true;
    }

    // Gira la rueda al azar. Falla si esta fijada o vacia.
    public boolean spin()
    {
        if (locked || symbols.isEmpty()) {
            return false;
        }
        place((int) (Math.random() * symbols.size()));
        return true;
    }

    // Rota la rueda un numero de pasos, hacia adelante o hacia atras.
    
    public boolean spin(int steps)
    {
        if (locked || symbols.isEmpty()) {
            return false;
        }
        int size = symbols.size();
        int start = (currentIndex == -1) ? 0 : currentIndex;
        int direction = (steps < 0) ? -1 : 1;
        for (int step = 1; step <= Math.abs(steps); step++) {
            place(((start + direction * step) % size + size) % size);
            if (visible) {
                Canvas.getCanvas().wait(200);
            }
        }
        return true;
    }

    // Indica si la rueda tiene un simbolo de ese color
    public boolean hasSymbol(String color)
    {
        return indexOf(color) != -1;
    }

    // Fija la rueda
    public void lock()
    {
        locked = true;
    }

    // Desbloquea la rueda
    public void unlock()
    {
        locked = false;
    }

    // Indica si la rueda esta fija
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

    // Devuelve el color del simbolo ubicado, o "none" si no hay ninguno
    public String getVisibleSymbol()
    {
        if (currentIndex == -1) {
            return "none";
        }
        return symbols.get(currentIndex).getColor();
    }

    // Muestra u oculta la marca de premio de esta rueda
    public void highlight(boolean win)
    {
        if (win && visible) {
            mark.makeVisible();
        } else {
            mark.makeInvisible();
        }
    }

    // Hace visible la rueda
    public void makeVisible()
    {
        visible = true;
        refresh();
    }

    // Hace invisible la rueda y todo lo que la compone
    public void makeInvisible()
    {
        visible = false;
        mark.makeInvisible();
        for (Symbol symbol : symbols) {
            symbol.hide();
        }
        frame.makeInvisible();
    }

    // Redibuja la rueda
    private void refresh()
    {
        if (!visible) {
            return;
        }
        frame.makeVisible();
        if (currentIndex != -1) {
            symbols.get(currentIndex).show();
        }
    }

    // Deja visible el simbolo que esta en esa posicion de la rueda
    private void place(int index)
    {
        hideCurrent();
        currentIndex = index;
        refresh();
    }

    // Oculta el simbolo ubicado
    private void hideCurrent()
    {
        if (currentIndex != -1) {
            symbols.get(currentIndex).hide();
        }
    }

    // Busca la posicion del simbolo de ese color
    private int indexOf(String color)
    {
        for (int i = 0; i < symbols.size(); i++) {
            if (symbols.get(i).getColor().equalsIgnoreCase(color)) {
                return i;
            }
        }
        return -1;
    }
}
