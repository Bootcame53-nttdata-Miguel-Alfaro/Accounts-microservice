# Account Microservice

Este microservicio se encarga de la administración de las cuentas bancarias pasivas de los usuarios, incluyendo cuentas de ahorro, cuentas corrientes y cuentas a plazo fijo. Está registrado en un API Gateway y puede ser consumido desde la siguiente dirección: [http://4.152.240.150:8080/](http://4.152.240.150:8080/).

## Descripción del Proyecto

El microservicio de cuentas proporciona diversas operaciones para la gestión de cuentas bancarias, incluyendo la creación, actualización, eliminación y consulta de cuentas, así como operaciones de depósito, retiro y validación de saldo. A continuación se detallan los endpoints disponibles y su funcionalidad.

## Endpoints

### Crear una Cuenta
- **Descripción**: Añadir una nueva cuenta a la base de datos.
- **Método**: `POST`
- **Ruta**: `http://4.152.240.150:8080/accounts`
- **Código de Respuesta**: 201 - Cuenta creada exitosamente.

### Obtener Cuentas de un Cliente
- **Descripción**: Obtener todas las cuentas asociadas a un cliente específico.
- **Método**: `GET`
- **Ruta**: `http://4.152.240.150:8080/accounts/customer/{customerId}`
- **Códigos de Respuesta**: 200 - Operación exitosa, 404 - Cliente no encontrado.

### Actualizar una Cuenta
- **Descripción**: Actualizar la información de una cuenta existente.
- **Método**: `PUT`
- **Ruta**: `http://4.152.240.150:8080/accounts/{id}`
- **Códigos de Respuesta**: 200 - Operación exitosa, 404 - Cuenta no encontrada.

### Obtener una Cuenta por ID
- **Descripción**: Obtener la información de una cuenta específica por su ID.
- **Método**: `GET`
- **Ruta**: `http://4.152.240.150:8080/accounts/{id}`
- **Códigos de Respuesta**: 200 - Operación exitosa, 404 - Cuenta no encontrada.

### Eliminar una Cuenta
- **Descripción**: Eliminar una cuenta específica por su ID.
- **Método**: `DELETE`
- **Ruta**: `http://4.152.240.150:8080/accounts/{id}`
- **Códigos de Respuesta**: 200 - Eliminación exitosa, 404 - Cuenta no encontrada.

### Depósito en una Cuenta
- **Descripción**: Realizar un depósito en una cuenta específica.
- **Método**: `POST`
- **Ruta**: `http://4.152.240.150:8080/accounts/{id}/deposit`
- **Código de Respuesta**: 200 - Depósito exitoso.

### Validar Suficiencia de Saldo
- **Descripción**: Validar si una cuenta tiene saldo suficiente para una transacción.
- **Método**: `POST`
- **Ruta**: `http://4.152.240.150:8080/accounts/{id}/balance/validate`
- **Código de Respuesta**: 200 - Validación exitosa.

### Retiro de una Cuenta
- **Descripción**: Realizar un retiro de una cuenta específica.
- **Método**: `POST`
- **Ruta**: `http://4.152.240.150:8080/accounts/{id}/withdraw`
- **Código de Respuesta**: 200 - Retiro exitoso.

### Obtener Balance de una Cuenta
- **Descripción**: Obtener el balance actual de una cuenta.
- **Método**: `GET`
- **Ruta**: `http://4.152.240.150:8080/accounts/{id}/balance`
- **Códigos de Respuesta**: 200 - Operación exitosa, 404 - Cuenta no encontrada.

### Obtener Balance Diario Promedio
- **Descripción**: Obtener el balance diario promedio de todas las cuentas de un cliente en el mes actual.
- **Método**: `GET`
- **Ruta**: `http://4.152.240.150:8080/accounts/{customerId}/average-daily-balance`
- **Códigos de Respuesta**: 200 - Operación exitosa, 404 - Cliente no encontrado.

### Obtener Transacciones de una Cuenta
- **Descripción**: Obtener todas las transacciones realizadas en una cuenta específica.
- **Método**: `GET`
- **Ruta**: `http://4.152.240.150:8080/accounts/{id}/transactions`
- **Códigos de Respuesta**: 200 - Operación exitosa, 404 - Cuenta no encontrada.

## Integración y Despliegue

Este microservicio está integrado dentro de un clúster de AKS con integración continua. Cada commit se almacena en un registro y el despliegue se realiza de manera automática, garantizando que siempre esté disponible la versión más reciente y funcional del servicio.

## Información Adicional

Para ver más información de las peticiones, tanto body, request y response, revisar el contrato API en el recurso del proyecto.