Oompa Loompa - Napptilus
------------------------

En la siguiente prueba se ha desarrollado un servicio REST utilizando Spring Boot.
En cuanto a la persistencia de datos, ésta se ha llevado a cabo utilizando MySql como SGBD relacional productivo
y H2 como base de datos en memoria para los tests unitarios.

Se han realizado tests unitarios utilizando JUnit y Mockito.

Para la integración con base de datos MySql se ha utilizado JPA + Hibernate.

Como librerías adicionales se han utilizado:

	* lombok -> Para evitar la generación de getters/setters constructores etc.. minimizandop el código superfluo.
	* modelmapper -> Para facilitar las conversiones entre entidades de BD y DTOs.
	* aspectJ -> Para capturar los parámetros de entrada, y salidas de los métodos de la api y mostrarlos en el log de la aplicación.
	
Se han incluido los scripts DCL y DDL para la creación de la base de datos de la aplicación,
además del fichero de exportación de POSTMAN con una colección de peticiones de ejemplo sobre la api.


Temas pendientes
----------------
	
	Generar el fichero de orquestación necesario para instalar y conectar un par de contenedores Docker (uno para la aplicación y otro para MySql)
necesarios para disponer de la configuración necesaria para ejecutar la aplicación.