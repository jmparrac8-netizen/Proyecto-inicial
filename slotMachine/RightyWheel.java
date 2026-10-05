// Arevalo-Parra
// Rueda righty (tipo nuevo): barra verde.
// Es como la lefty, pero copia a la rueda de su derecha.
public class RightyWheel extends Wheel
{
    // Crea una rueda righty vacia en la posicion (x, y)
    public RightyWheel(int x, int y)
    {
        super(x, y, "green");
    }

    public String type()
    {
        return "righty";
    }

    protected void turned(Wheel left, Wheel right)
    {
        super.turned(left, right);
        copy(right);
    }
}
