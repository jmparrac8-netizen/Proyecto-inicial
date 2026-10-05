// Arevalo-Parra
// Rueda lefty: barra azul. Si hay una rueda a su izquierda, al girar copia su estado.
public class LeftyWheel extends Wheel
{
    // Crea una rueda lefty vacia en la posicion (x, y)
    public LeftyWheel(int x, int y)
    {
        super(x, y, "blue");
    }

    public String type()
    {
        return "lefty";
    }

    protected void turned(Wheel left, Wheel right)
    {
        super.turned(left, right);
        copy(left);
    }
}
