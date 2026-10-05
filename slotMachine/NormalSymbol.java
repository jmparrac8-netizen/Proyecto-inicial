// Arevalo-Parra
// Simbolo normal: un circulo de un color.
public class NormalSymbol extends Symbol
{
    private Circle circle;

    // Crea un simbolo normal del color dado en la posicion (x, y)
    public NormalSymbol(String color, int x, int y)
    {
        super(color, 20, 15);
        circle = new Circle();
        circle.changeColor(color);
        moveTo(x, y);
    }

    public String type()
    {
        return "normal";
    }

    public void moveTo(int x, int y)
    {
        circle.moveHorizontal(x - xPosition);
        circle.moveVertical(y - yPosition);
        xPosition = x;
        yPosition = y;
    }

    public void show()
    {
        circle.makeVisible();
    }

    public void hide()
    {
        circle.makeInvisible();
    }
}
