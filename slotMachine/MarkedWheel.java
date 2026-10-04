// Autor: Tomas Arevalo - Jose Parra
// Una rueda que ademas de su comportamiento normal se distingue
// visualmente con un triangulo de un color fijo debajo de su marco.
// Las ruedas especiales (lefty, rebel) heredan de esta.
public abstract class MarkedWheel extends Wheel
{
    private Triangle typeMark;
    private int xPosition;
    private int yPosition;

    // Crea la rueda en (x, y) con un triangulo distintivo del color dado
    public MarkedWheel(int x, int y, String markColor)
    {
        super(x, y);
        typeMark = new Triangle();
        typeMark.changeSize(15, 20);
        typeMark.changeColor(markColor);
        int targetX = x + 15;
        int targetY = y + 40;
        typeMark.moveHorizontal(targetX - 140);
        typeMark.moveVertical(targetY - 15);
        xPosition = x;
        yPosition = y;
    }

    // Lleva la rueda y su triangulo distintivo a (x, y)
    @Override
    public void moveTo(int x, int y)
    {
        super.moveTo(x, y);
        if (typeMark == null) {
            return;
        }
        typeMark.moveHorizontal(x - xPosition);
        typeMark.moveVertical(y - yPosition);
        xPosition = x;
        yPosition = y;
    }

    // Hace visible la rueda junto con su triangulo distintivo
    @Override
    public void makeVisible()
    {
        super.makeVisible();
        typeMark.makeVisible();
    }

    // Oculta la rueda junto con su triangulo distintivo
    @Override
    public void makeInvisible()
    {
        super.makeInvisible();
        typeMark.makeInvisible();
    }
}
