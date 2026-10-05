// Arevalo-Parra
// Rueda normal: barra negra.
public class NormalWheel extends Wheel
{
    // Crea una rueda normal vacia en la posicion (x, y)
    public NormalWheel(int x, int y)
    {
        super(x, y, "black");
    }

    public String type()
    {
        return "normal";
    }
}
