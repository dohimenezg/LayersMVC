# Práctica LayerMVC con Publisher-Subscriber
## Pasos previos
El proyecto fue creado usando [maven](https://maven.apache.org/), asegúrese de tenerlo previamente instalado. \
También el proyecto fue creado usando [sqlite](https://www.sqlite.org/index.html), asegúrese de tenerlo previamente instalado.
## Limpiar, instalar dependencias, construir y ejecutar los .jar corriendo los main correspondientes
1. Para PublisherMVC: \
Estando en el directorio `.\PublisherMVC`
~~~sh
mvn clean install package
~~~
Y para ejecutarlo
~~~sh
java -cp target/LayersMVC-1.jar co.edu.unicauca.layersmvc.presentation.ClientMain
~~~
2. Para ProductConsumer:
Estando en el directorio `.\ProductConsumer` \
IMPORTANTE: Como se construyó una única app para ambos consumer (memoria y base de datos), revise el `pom.xml` y asegurarse de que las siguientes líneas de código se encuentren agregadas o estén eliminadas según el usuario lo requiera.
~~~
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.23</version>
</dependency>
~~~
    1. En memoria \
Si es en memoria, elimine (si están) las lineas anteriores y escriba los siguientes comandos
~~~sh
mvn clean install package
~~~
Y para ejecutar
~~~sh
java '-cp' '.\target\ProductConsumer-1.jar' 'co.edu.unicauca.productconsumer.presentation.ClientMain'
~~~
    2. En base de datos \
Si es en base de datos, agrege (si no están) las lineas anteriores y escriba los siguientes comandos
~~~sh
mvn clean install package
~~~
Y para ejecutar
~~~sh
java '-cp' '.\target\ProductConsumer-1.jar' 'co.edu.unicauca.productconsumer.presentation.ClientMain' 'database'
~~~
## Usabilidad
Si desea o necesita cambiar el servidor donde está la instancia de rabbitmq modifique en `PublisherMVC` el archivo `Publisher.java` con los datos que coresponda, en `ProductConsumer` modifique el archivo `RabbitListener.java` según los datos que necesite. \
Para la instancia de base de datos de MySQL modifique en `ProductConsumer` el archivo `DataBaseProductRepository` con los datos que corresponda.

