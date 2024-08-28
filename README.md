# tup2024final
-CuentaBancaria validaciones: Saldo > 0.
-CuentaBancaria inversiones que puedas hacer una transaccion de dinero para comprar inversiones y generar plata.

-USAR INTERFACES PARA IMPLEMENTAR METODOS. 

-Reestructurar CuentaBancaria.
-Reestructurar MovimientosCuenta.
-Unir MovimientosCuenta con CuentaBancaria.
-Unir Cliente con CuentaBancaria.
-Hacer la base de datos de todo el banco.
-Hacer los input para que devuelva datos.
-Agregar funcionalidades a CuentaBancaria y MovimientosCuenta.




COMPLETE:
-ClienteDto y PersonaDto
-Cliente y Persona model
-CuentaBancaria y CBDto
-Tranferencias y TDto
-Transacciones y TDto
-Estructurar los CRUD

In progress:
-ClienteValidator
-Validator en controllers

To do List:
-Handler en controllers
-HACER ID CORRECTA A CUENTAB, TRANSACCIONES Y TRANSFERENCIAS
-Hacer que transferencias y transacciones se agreguen a banco
-Hacer bien los enum Tipo
-Rehacer service
-Rehacer persistence
-Borrar presentation
-Hacer controllers, ClienteController, CuentaController
-Hacer test unitarios


GET/POST/PUT/DELETE

Clientes
GET /api/cliente   Obtener todos los clientes
GET /api/cliente/{id}  Obtener cliente individual
POST /api/cliente   Crear cliente
PUT /api/cliente/{id}  Modificar cliente
DELETE /api/cliente/{id}   Borrar cliente

Cuentas 
GET /api/cuenta    Obtener todas las cuentas
GET /api/cuentas/{titular}  Obtener cuentas de cliente
GET /api/cuenta/{cuentaId}/transfer   Obtener transfers
GET /api/cuenta/{cuentaId}/transaccion   Obtener transacciones
POST /api/cuentas/{titular}  Crea cuenta de cliente
PUT /api/cuentas/{titular}/{cuentaId}    Actualiza cuenta
DELETE /api/cuentas/{titular}/{cuentaId}     Borra cuenta

Transfer
GET /api/transfer   Obtener todos las transfers
GET /api/cuenta/{cuentaId}/transfer   Obtener transfers
POST /api/transfer  Hacer transfers

Transacciones
GET /api/transaccion  Obtener todos las transacciones
GET /api/cuenta/{cuentaId}/transaccion   Obtener transacciones
POST /api/transaccion  Hacer transfers
