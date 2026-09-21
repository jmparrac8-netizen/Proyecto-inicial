PROYECTO: slotMachine
AUTORES: Tomas Arevalo - Jose Parra
CURSO: Desarrollo Orientado por Objetos (DOPO-POOB) - Proyecto inicial 2026-2

Simulador de una maquina tragamonedas inspirado en el Problem I
de la maraton 2025 (Slot Machine). Ciclos 1 a 3.

CLASES PROPIAS
  SlotMachine        : clase principal. Maneja las ruedas y responde ok().
  Wheel              : una rueda. Guarda varios simbolos y muestra uno a la vez.
  Symbol             : un simbolo, dibujado como un circulo de color.
  SlotMachineContest : resuelve el problema de la maraton (solve y simulate).

CLASES REUTILIZADAS DEL PROYECTO shapes (sin modificar)
  Canvas, Circle, Rectangle, Triangle

COMO SE VE
  - Cada rueda es un cuadro negro (Rectangle) con un circulo de color
    adentro (Circle): el simbolo que esta ubicado en ese momento.
  - Cuando las ruedas coinciden, aparece un triangulo magenta (Triangle)
    encima de cada rueda: asi la maquina luce distinta al ganar.
  - Si una accion no se puede realizar se muestra un JOptionPane,
    solo cuando el simulador esta visible.

COLORES VALIDOS
  red, blue, green, yellow, magenta, black
  (son los que el Canvas de shapes sabe pintar)
  Por eso SlotMachine(n) acepta n entre 2 y 6.

CICLO 3
  SlotMachine(n)                crea n ruedas con los mismos n simbolos en el
                                mismo orden, cada una girada al azar. Nace
                                invisible y nunca en premio.
  SlotMachineContest.solve(n)   devuelve las acciones {rueda, pasos} que ganan.
                                Trabaja siempre con la maquina invisible.
  SlotMachineContest.simulate(n) resuelve y despues muestra la solucion.
  Lo unico que consulta el solver es distinctSymbols(); nunca lee un color.

PRUEBAS
  SlotMachineC2Test       : pruebas de unidad del ciclo 2 (modo invisible).
  SlotMachineCC2Test      : pruebas compartidas del ciclo 2.
  SlotMachineContestTest  : pruebas de unidad del ciclo 3.
  SlotMachineContestCTest : pruebas compartidas del ciclo 3.
