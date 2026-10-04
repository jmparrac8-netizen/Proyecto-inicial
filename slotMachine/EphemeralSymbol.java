// Autor: Tomas Arevalo - Jose Parra
// Un simbolo efimero: cada vez que es seleccionado en la rueda,
// se muestra un poco mas pequeno que la vez anterior, hasta quedar
// reducido a un punto.
public class EphemeralSymbol extends Symbol
{
    private static final int INITIAL_SIZE = 30;
    private static final int STEP = 5;
    private static final int MIN_SIZE = 4;

    private int size;
    private int displayedSize;

    // Crea un simbolo efimero del color dado en la posicion (x, y)
    public EphemeralSymbol(String color, int x, int y)
    {
        super(color, x, y);
        size = INITIAL_SIZE;
        displayedSize = INITIAL_SIZE;
    }

    // Se muestra con el tamano actual 
    // y luego lo reduce para la proxima vez
    @Override
    public void show()
    {
        int shrink = displayedSize - size;
        if (shrink != 0) {
            circle.moveHorizontal(shrink / 2);
            circle.moveVertical(shrink / 2);
        }
        circle.changeSize(size);
        displayedSize = size;
        super.show();
        if (size > MIN_SIZE) {
            size = Math.max(MIN_SIZE, size - STEP);
        }
    }
}
