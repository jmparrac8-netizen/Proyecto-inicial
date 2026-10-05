// Arevalo-Parra
// Simbolo timido: un cuadrado que alterna entre visible e invisible
// cada vez que la rueda lo selecciona.
public class ShySymbol extends Symbol
{
    private Rectangle square;
    private boolean shown;

    // Crea un simbolo timido del color dado en la posicion (x, y)
    public ShySymbol(String color, int x, int y)
    {
        super(color, 70, 15);
        shown = true;
        square = new Rectangle();
        square.changeSize(30, 30);
        square.changeColor(color);
        moveTo(x, y);
    }

    public String type()
    {
        return "shy";
    }

    // Indica si el simbolo esta visible
    public boolean isShown()
    {
        return shown;
    }

    // Alterna entre visible e invisible
    public void selected()
    {
        shown = !shown;
    }

    public void moveTo(int x, int y)
    {
        square.moveHorizontal(x - xPosition);
        square.moveVertical(y - yPosition);
        xPosition = x;
        yPosition = y;
    }

    public void show()
    {
        if (shown) {
            square.makeVisible();
        } else {
            square.makeInvisible();
        }
    }

    public void hide()
    {
        square.makeInvisible();
    }
}
