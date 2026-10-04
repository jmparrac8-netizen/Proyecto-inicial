// Autor: Tomas Arevalo - Jose Parra
// Un simbolo timido: cada vez que es seleccionado en la rueda alterna
// entre mostrarse y quedarse oculto.
public class ShySymbol extends Symbol
{
    private boolean revealed;

    // Crea un simbolo timido del color dado en la posicion (x, y)
    public ShySymbol(String color, int x, int y)
    {
        super(color, x, y);
        revealed = false;
    }

    // Cada seleccion alterna entre mostrarse y permanecer oculto
    @Override
    public void show()
    {
        revealed = !revealed;
        if (revealed) {
            super.show();
        } else {
            super.hide();
        }
    }
}
