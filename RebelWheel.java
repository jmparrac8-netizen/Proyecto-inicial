// Autor: Tomas Arevalo - Jose Parra
// Una rueda rebel: no se deja fijar, ni intercambiar, ni eliminar.
// Se marca con un triangulo rojo debajo de su marco.
public class RebelWheel extends MarkedWheel
{
    // Crea una rueda rebel en (x, y)
    public RebelWheel(int x, int y)
    {
        super(x, y, "red");
    }

    @Override
    public boolean canLock()
    {
        return false;
    }

    @Override
    public boolean canSwap()
    {
        return false;
    }

    @Override
    public boolean canRemove()
    {
        return false;
    }
}
