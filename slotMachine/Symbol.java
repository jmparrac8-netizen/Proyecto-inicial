// Autor: Tomas Arevalo - Jose Parra
// Un simbolo de la maquina: un circulo de un color.
// Reutiliza la clase Circle del proyecto shapes para dibujarse.
public class Symbol
{
    
    private static final String[] COLORS = {"red", "blue", "green", "yellow", "magenta", "black"};

    private Circle circle;
    private String color;
    private int xPosition;
    private int yPosition;

    // Crea un simbolo del color dado en la posicion (x, y)
    public Symbol(String color, int x, int y)
    {
        this.color = color;
        circle = new Circle();
        circle.changeColor(color);
        xPosition = 20;
        yPosition = 15;
        moveTo(x, y);
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

    // Devuelve el color del simbolo
    public String getColor()
    {
        return color;
    }

    // Lleva el simbolo a la posicion (x, y)
    public void moveTo(int x, int y)
    {
        circle.moveHorizontal(x - xPosition);
        circle.moveVertical(y - yPosition);
        xPosition = x;
        yPosition = y;
    }

    // Muestra el simbolo en el canvas
    public void show()
    {
        circle.makeVisible();
    }

    // Oculta el simbolo del canvas
    public void hide()
    {
        circle.makeInvisible();
    }
}
