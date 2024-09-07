# tup2024final

#notas
-metodos service en español
-metodos dao en ingles
-Transacciones solo se pueden usar en pesos

COMPLETE:
-ClienteDto y PersonaDto
-Cliente y Persona model
-CuentaBancaria y CBDto
-Tranferencias y TDto
-Transacciones y TDto
-Estructurar los CRUD
-Hacer bien los enum Tipo
-ClienteValidator y @Valid en controllers
-Handler en controllers de Valid globales
-aplication.properties puerto y ruta
-Corregir los enums de CuentaB Transacciones y Transferencias
-Cliente controller, service y dao
-Test unitarios Cliente service y cliente dao
-CuentaBancaria controller, service y dao
-HACER ID CORRECTA A CUENTAB
-Verificar en service si el cliente ya tiene una cuenta de cc o ca en ars o usd
-ARREGLAR ENUMS TIPO
-DELETE NO BORRA EN CLIENTEJSON
-ARREGLAR DEPOSITO Y RETIRO
-hacer que las monedas coincidan en depositos y retiros
-Hacer que en requestBody pueda ser USD o dolares o minusculas y mayus
-HACER QUE CUANDO DELETEO UN CLIENTE TAMBIEN BORRE CUENTAS
-Transferencias controller, service y dao
-Validar que no te mandes plata a tu propia cuenta (SERVICE)
-Test transferencias
-TRANSFER EN BANCO NO MODIFICA AL QUE PAGA
-ACTUALIZAR CLIENTE BORRA CUENTAS
-Transacciones controller, service y dao
-Transacciones solo en pesos
-Testear Transacciones

In progress:


To do List:
-Poner private a los validate no usables de validators
-Hacer que los exception de service sean validator
-test controllers
-COMENTAR Y DOCUMENTAR EL PROYECTO


GET/POST/PUT/DELETE

Clientes
GET /api/cliente   Obtener todos los clientes        !
GET /api/cliente/{id}  Obtener cliente individual    !
POST /api/cliente   Crear cliente                    !
PUT /api/cliente/{id}  Modificar cliente             !
DELETE /api/cliente/{id}   Borrar cliente            !

Cuentas 
GET /api/cuenta    Obtener todas las cuentas                     !
GET /api/cuenta/{cuentaId}  Obtener cuentas por id               !
GET /api/cuenta/{cuentaId}/transfers   Obtener transfers         !
GET /api/cuenta/{cuentaId}/transacciones   Obtener transacciones !
POST /api/cuenta/{cuentaId}/deposito  crea un deposito           !
POST /api/cuenta/{cuentaId}/retiro   crea un retiro              !
DELETE /api/cuenta/{cuentaId}     Borra cuenta                   !

Transfer
GET /api/transfer   Obtener todos las transfers                  !
GET /api/cuenta/{cuentaId}/transfers   Obtener transfers         !
POST /api/transfer  Hacer transfers                              !
DELETE /api/transfer borra todas las transfers                   !

Transacciones
GET /api/transaccion  Obtener todos las transacciones
GET /api/cuenta/{cuentaId}/transacciones   Obtener transacciones
POST /api/transaccion  Hacer transfers
DELETE /api/transaccion borra todas las transacciones    
