Fill Zone Solver
================

Implementaci�n de un motor de inferencias para resolver el juego Fill Zone, desarrollado junto a Francisco Pagliaricci y Jorge Mozzino para la c�tedra de Sistemas de Inteligencia Artificial del ITBA.

Para m�s informaci�n sobre la implementaci�n de este proyecto, lea el informe en la carpeta /doc.


Ejecuci�n
-----------

Para correr el programa, ejecutar, por l�nea de comandos
		
			java - jar FillZone.jar

Los argumentos v�lidos son:

-M (dfs|bfs|id|astar|greedy): Indica el tipo de b�squeda a realizar. Par�metro obligatorio.

-m cantMoves: Indica la cantidad m�xima de movimientos en los que se tiene que resolver
 el tablero. Opcional, el default es 30.

-d dimension: Indica la dimensi�n del tablero cuadrado. Opcional, el default e 14.

-h (1|2|3|4): Indica la heur�stica a ser utilizada.
		1 es la heur�stica de celdas dominadas.
		2 es la heur�stica de las islas.
		3 es la heur�stica de acercarse a las esquinas.
		4 es la heur�stica de los k-vecinos.

NOTA: El archivo FillZoneHValue.jar imprime adem�s el valor heur�stico de cada tablero para cada heur�sticaNOTA: El archivo FillZoneHValue.jar imprime adem�s el valor heur�stico de cada tablero para cada heur�stica.
