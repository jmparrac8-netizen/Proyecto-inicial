// Arevalo-Parra
// Un simbolo de la maquina. Tiene un color y una forma segun su tipo.
// Tipos: normal, ephemeral y shy.
public abstract class Symbol
{
    private static final String[] COLORS = {"red", "blue", "green", "yellow", "magenta", "black"};
    private static final String[] TYPES = {"normal", "ephemeral", "shy"};

    protected String color;
    protected int xPosition;
    protected int yPosition;

    // Crea un simbolo del color dado en la posicion (x, y)
    protected Symbol(String color, int x, int y)
    {
        this.color = color;
        xPosition = x;
        yPosition = y;
    }

    // Crea un simbolo del tipo dado, o null si el tipo no existe
    public static Symbol create(String type, String color, int x, int y)
    {
        if (type.equalsIgnoreCase("normal")) {
            return new NormalSymbol(color, x, y);
        }
        if (type.equalsIgnoreCase("ephemeral")) {
            return new EphemeralSymbol(color, x, y);
        }
        if (type.equalsIgnoreCase("shy")) {
            return new ShySymbol(color, x, y);
        }
        return null;
    }

    // Devuelve los colores disponibles
    public static String[] colors()
    {
        String[] copy = new String[COLORS.length];
        for (int i = 0; i < COLORS.length; i++) {
            copy[i] = COLORS[i];
        }
        return copy;
    }

    // Indica si el color es uno de los que se pueden pintar
    public static boolean isValidColor(String color)
    {
        for (String valid : COLORS) {
            if (valid.equalsIgnoreCase(color)) {
                return true;
            }
        }
        return false;
    }

    // Indica si el tipo de simbolo existe
    public static boolean isValidType(String type)
    {
        for (String valid : TYPES) {
            if (valid.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    // Devuelve el color del simbolo
    public String getColor()
    {
        return color;
    }

    // Devuelve el tipo del simbolo
    public abstract String type();

    // Lleva el simbolo a la posicion (x, y)
    public abstract void moveTo(int x, int y);

    // Muestra el simbolo en el canvas
    public abstract void show();

    // Oculta el simbolo del canvas
    public abstract void hide();

    // Se llama cada vez que la rueda gira. Por defecto no hace nada
    public void spun()
    {
    }

    // Se llama cada vez que la rueda selecciona el simbolo. Por defecto no hace nada
    public void selected()
    {
    }
}
