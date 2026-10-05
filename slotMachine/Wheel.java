import java.util.ArrayList;

// Arevalo-Parra
// Una rueda de la maquina tragamonedas.
// Puede guardar varios simbolos, pero solo muestra uno a la vez.
// Se dibuja con un rectangulo, una barra de color que indica su tipo
// y un triangulo que solo aparece cuando la maquina llego a un estado ganador.
// Tipos: normal, lefty, rebel y righty.
public abstract class Wheel
{
    private static final String[] TYPES = {"normal", "lefty", "rebel", "righty"};

    private ArrayList<Symbol> symbols;
    private int currentIndex;
    private int xPosition;
    private int yPosition;
    private boolean visible;
    private boolean locked;
    private Rectangle frame;
    private Rectangle badge;
    private Triangle mark;

    // Crea una rueda vacia en la posicion dada del canvas.
    // La barra de color es lo que distingue a cada tipo de rueda.
    protected Wheel(int x, int y, String badgeColor)
    {
        symbols = new ArrayList<Symbol>();
        currentIndex = -1;
        visible = false;
        locked = false;
        frame = new Rectangle();
        frame.changeSize(40, 40);
        frame.changeColor("black");
        badge = new Rectangle();
        badge.changeSize(6, 40);
        badge.changeColor(badgeColor);
        badge.moveVertical(42);
        mark = new Triangle();
        mark.changeSize(20, 20);
        mark.changeColor("magenta");
        mark.moveHorizontal(-50);
        mark.moveVertical(-25);
        xPosition = 75;
        yPosition = 20;
        moveTo(x, y);
    }

    // Crea una rueda del tipo dado, o null si el tipo no existe
    public static Wheel create(String type, int x, int y)
    {
        if (type.equalsIgnoreCase("normal")) {
            return new NormalWheel(x, y);
        }
        if (type.equalsIgnoreCase("lefty")) {
            return new LeftyWheel(x, y);
        }
        if (type.equalsIgnoreCase("rebel")) {
            return new RebelWheel(x, y);
        }
        if (type.equalsIgnoreCase("righty")) {
            return new RightyWheel(x, y);
        }
        return null;
    }

    // Indica si el tipo de rueda existe
    public static boolean isValidType(String type)
    {
        for (String valid : TYPES) {
            if (valid.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    // Devuelve el tipo de la rueda
    public abstract String type();

    // Indica si la rueda se deja fijar
    public boolean canLock()
    {
        return true;
    }

    // Indica si la rueda se deja intercambiar
    public boolean canSwap()
    {
        return true;
    }

    // Indica si la rueda se deja eliminar
    public boolean canDelete()
    {
        return true;
    }

    // Lleva la rueda completa a la posicion (x, y)
    public void moveTo(int x, int y)
    {
        if (x == xPosition && y == yPosition) {
            return;
        }
        frame.moveHorizontal(x - xPosition);
        frame.moveVertical(y - yPosition);
        badge.moveHorizontal(x - xPosition);
        badge.moveVertical(y - yPosition);
        mark.moveHorizontal(x - xPosition);
        mark.moveVertical(y - yPosition);
        xPosition = x;
        yPosition = y;
        for (Symbol symbol : symbols) {
            symbol.moveTo(x, y);
        }
        refresh();
    }

    // Adiciona un simbolo del tipo y color dados.
    public boolean addSymbol(String type, String color)
    {
        if (!Symbol.isValidType(type) || !Symbol.isValidColor(color) || indexOf(color) != -1) {
            return false;
        }
        symbols.add(Symbol.create(type, color, xPosition, yPosition));
        if (currentIndex == -1) {
            currentIndex = symbols.size() - 1;
            refresh();
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
    // Recibe las ruedas vecinas por si el tipo de rueda las necesita.
    public boolean spin(Wheel left, Wheel right)
    {
        if (locked || symbols.isEmpty()) {
            return false;
        }
        place((int) (Math.random() * symbols.size()));
        turned(left, right);
        return true;
    }

    // Rota la rueda un numero de pasos, hacia adelante o hacia atras.
    public boolean spin(int steps, Wheel left, Wheel right)
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
        turned(left, right);
        return true;
    }

    // Se llama al terminar un giro. Cada tipo de rueda puede completarlo.
    protected void turned(Wheel left, Wheel right)
    {
        for (Symbol symbol : symbols) {
            symbol.spun();
        }
        refresh();
    }

    // Copia el simbolo que muestra otra rueda, si esta tiene ese color
    protected void copy(Wheel other)
    {
        if (other != null) {
            placeSymbol(other.getVisibleSymbol());
        }
    }

    // Indica si la rueda tiene un simbolo de ese color
    public boolean hasSymbol(String color)
    {
        return indexOf(color) != -1;
    }

    // Devuelve el simbolo de ese color, o null si no existe.
    // Sin modificador para poder revisarlo desde las pruebas.
    Symbol symbolOf(String color)
    {
        int index = indexOf(color);
        if (index == -1) {
            return null;
        }
        return symbols.get(index);
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
        badge.makeInvisible();
        frame.makeInvisible();
    }

    // Redibuja la rueda
    private void refresh()
    {
        if (!visible) {
            return;
        }
        frame.makeVisible();
        badge.makeVisible();
        if (currentIndex != -1) {
            symbols.get(currentIndex).show();
        }
    }

    // Deja ubicado el simbolo que esta en esa posicion de la rueda
    private void place(int index)
    {
        hideCurrent();
        currentIndex = index;
        symbols.get(index).selected();
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
