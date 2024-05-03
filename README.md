# Distributed-sorting-algorithm-with-ICE

## Integrantes
#### - Isabella Huila Cerón - A00394751
#### - Leidy Daniela Londoño Candelo - A00392917
#### - Sebastian Libreros - A00379813
#### - Pablo Fernando Pineda Patiño -A00395831


Este proyecto implementa un algoritmo de ordenamiento distribuido utilizando llamadas asíncronas con ICE (Internet Communications Engine). Le permite al usuario organizar una lista de valores ingresándolos en el archivo input.txt que se encuentra en la ruta: client/src/main/resources/input/
Dentro de este archivo, usted podrá ingresar una serie de valores separados por espacios, aunque ya viene con unos valores predeterminados que puede modificar. Después de haber editado este archivo, puede ejecutar el cliente.

Pasos para ejecutar el proyecto
1.	Primero si hace un cambio en el archivo input.txt, usar el comando
`./gradlew build `

2.	Después se ejecuta el servidor con el siguiente comando:

`java -jar server/build/libs/server.jar `

3.	Despues se ejecutan los workers tantas veces en diferentes terminales como usted quiera con el siguiente comando:

`java -jar worker/build/libs/worker.jar `

4.	Por último, se ejecuta el cliente:
`java -jar client/build/libs/client.jar `


nota: Cada vez que desees utilizar un número diferente de workers, deberás repetir los pasos, comenzando desde el segundo
