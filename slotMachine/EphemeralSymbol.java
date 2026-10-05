// Arevalo-Parra
// Simbolo efimero: un triangulo que se achica en cada giro hasta quedar como un punto.
public class EphemeralSymbol extends Symbol
{
    private static final int FULL_SIZE = 30;
    private static final int STEP = 5;
    private static final int MIN_SIZE = 2;

    private Triangle triangle;
    private int size;

    // Crea un simbolo efimero del color dado en la posicion (x, y)
    public EphemeralSymbol(String color, int x, int y)
    {
        super(color, 140, 15);
        size = FULL_SIZE;
        triangle = new Triangle();
        triangle.changeSize(size, size);
        triangle.changeColor(color);
        moveTo(x, y);
    }

    public String type()
    {
        return "ephemeral";
    }

    // Devuelve el tamano actual
    public int size()
    {
        return size;
    }

    // Se achica un poco, sin pasar del minimo
    public void spun()
    {
        int newSize = Math.max(MIN_SIZE, size - STEP);
        int shift = (size - newSize) / 2;
        triangle.changeSize(newSize, newSize);
        triangle.moveHorizontal(shift);
        triangle.moveVertical(shift);
        size = newSize;
    }

    public void moveTo(int x, int y)
    {
        triangle.moveHorizontal(x - xPosition);
        triangle.moveVertical(y - yPosition);
        xPosition = x;
        yPosition = y;
    }

    public void show()
    {
        triangle.makeVisible();
    }

    public void hide()
    {
        triangle.makeInvisible();
    }
}
