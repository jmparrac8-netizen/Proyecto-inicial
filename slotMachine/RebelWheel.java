// Arevalo-Parra
// Rueda rebel: barra roja. No se deja bloquear, ni intercambiar, ni eliminar.
public class RebelWheel extends Wheel
{
    // Crea una rueda rebel vacia en la posicion (x, y)
    public RebelWheel(int x, int y)
    {
        super(x, y, "red");
    }

    public String type()
    {
        return "rebel";
    }

    public boolean canLock()
    {
        return false;
    }

    public boolean canSwap()
    {
        return false;
    }

    public boolean canDelete()
    {
        return false;
    }
}
