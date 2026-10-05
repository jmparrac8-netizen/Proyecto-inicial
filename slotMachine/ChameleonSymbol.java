// Autor: Tomas Arevalo - Jose Parra
// Tipo propio (requisito 19): un simbolo camaleonico.
// Cada vez que es seleccionado en la rueda cambia su color VISIBLE al azar,
// pero su color LOGICO (el que usan addSymbol/placeSymbol/hasSymbol para
// identificarlo) nunca cambia, por eso sigue siendo manejable igual que
// cualquier otro simbolo.
public class ChameleonSymbol extends Symbol
{
    // Crea un simbolo camaleonico del color logico dado en la posicion (x, y)
    public ChameleonSymbol(String color, int x, int y)
    {
        super(color, x, y);
    }

    // Antes de mostrarse, se repinta de un color al azar de la paleta
    @Override
    public void show()
    {
        String[] palette = Symbol.colors();
        String randomColor = palette[(int) (Math.random() * palette.length)];
        circle.changeColor(randomColor);
        super.show();
    }
}
