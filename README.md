# WildFly-MDB-SQL-Server

Este proyecto contiene un ejemplo sencillo de un **Message-Driven Bean (MDB)** que escucha una cola JMS en WildFly y almacena los mensajes recibidos en una tabla de SQL Server.

## Compilación

```bash
mvn clean package
```

El resultado es un archivo `wildfly-mdb-sqlserver-1.0-SNAPSHOT.jar` que puede desplegarse en WildFly.

## Configuración en WildFly

1. **Datasource a SQL Server**. Puede agregarse mediante el script `config/sqlserver-datasource.cli`:

```bash
# Dentro del directorio bin de WildFly
./jboss-cli.sh --file=../config/sqlserver-datasource.cli
```

2. **Cola JMS**. Se declara con el script `config/jms-queue.cli`:

```bash
./jboss-cli.sh --file=../config/jms-queue.cli
```

3. Cree la tabla de ejemplo ejecutando `sql/create-table.sql` en la base de datos.

## Descripción del MDB

El bean `MessageProcessorBean` se encuentra en `src/main/java`. Está anotado con `@MessageDriven` para escuchar la cola `java:/jms/queue/ExampleQueue` y utiliza el datasource `java:jboss/datasources/SQLServerDS` para persistir cada mensaje en la tabla `messages`.

## Despliegue

Copie el JAR generado al directorio `standalone/deployments` de WildFly.

