// Autor: Tomas Arevalo - Jose Parra
// Una rueda lefty: si hay una rueda a su izquierda, al girar al azar
// copia el simbolo que esa rueda tiene visible, en vez de girar al azar.
// Se marca con un triangulo azul debajo de su marco.
public class LeftyWheel extends MarkedWheel
{
    private Wheel leftNeighbor;

    // Crea una rueda lefty en (x, y)
    public LeftyWheel(int x, int y)
    {
        super(x, y, "blue");
    }

    // La maquina le avisa cual es la rueda inmediatamente a su izquierda
    @Override
    public void setLeftNeighbor(Wheel left)
    {
        leftNeighbor = left;
    }

    // Copia el estado de su vecina izquierda si puede; si no, gira al azar
    @Override
    public boolean spin()
    {
        if (!isLocked() && leftNeighbor != null) {
            String color = leftNeighbor.getVisibleSymbol();
            if (!color.equals("none") && placeSymbol(color)) {
                return true;
            }
        }
        return super.spin();
    }
}
