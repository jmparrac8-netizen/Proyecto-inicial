// Autor: Tomas Arevalo - Jose Parra
// Un simbolo: un circulo de un color, ubicado en una posicion fija.
// Reutiliza la clase Circle para dibujarse.
public class Symbol
{
    private Circle circle;
    private String color;

    // Crea un simbolo del color dado en la posicion (x, y)
    public Symbol(String color, int x, int y)
    {
        this.color = color;
        circle = new Circle();
        circle.changeColor(color);
        // Circle nace en (20, 15): lo movemos a (x, y)
        circle.moveHorizontal(x - 20);
        circle.moveVertical(y - 15);
    }

    // Devuelve el color del simbolo
    public String getColor()
    {
        return color;
    }

    // Muestra el simbolo en el canvas
    public void show()
    {
        circle.makeVisible();
    }

    // Oculta el simbolo del canvas
    public void hide()
    {
        circle.makeInvisible();
    }
}
