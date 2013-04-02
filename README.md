Fill Zone Solver

Implementación de un motor de inferencias para resolver el juego Fill Zone, desarrollado junto a Francisco Pagliaricci y Jorge Mozzino para la cátedra de Sistemas de Inteligencia Artificial del ITBA.

Para más información sobre la implementación de este proyecto, lea el informe en la carpeta /doc.

------------------------------------------------------------------------------

Ejecución

Para correr el programa, ejecutar, por línea de comandos
		
			java - jar FillZone.jar

Los argumentos válidos son:

-M (dfs|bfs|id|astar|greedy): Indica el tipo de búsqueda a realizar. Parámetro obligatorio.

-m cantMoves: Indica la cantidad máxima de movimientos en los que se tiene que resolver
 el tablero. Opcional, el default es 30.

-d dimension: Indica la dimensión del tablero cuadrado. Opcional, el default e 14.

-h (1|2|3|4): Indica la heurística a ser utilizada.
		1 es la heurística de celdas dominadas.
		2 es la heurística de las islas.
		3 es la heurística de acercarse a las esquinas.
		4 es la heurística de los k-vecinos.

NOTA: El archivo FillZoneHValue.jar imprime además el valor heurístico de cada tablero para cada heurísticaNOTA: El archivo FillZoneHValue.jar imprime además el valor heurístico de cada tablero para cada heurística.
