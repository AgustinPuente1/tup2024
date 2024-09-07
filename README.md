# tup2024final

#notas
-Tipos de Clientes: Fisica (F) o Juridica (J), se pueden ingresar mediante cualquiera de esas formas
-Tipos de cuenta: Cuenta corriente (CC) o Caja de ahorro (CA)
-Tipos de Moneda: Pesos (ARS) o Dolares(USD)
-Tipos de Transaccion: Debito (D) o Credito (C)
-Hay cuatro tipos de movimientos posibles en las cuentas
-Depositos: cargas plata a la cuenta
-Retiro: sacas plata de la cuenta
-Transferencias: movimiento de plata entre dos cuentas, si la cuenta destino no se encuentra en el banco se simula mandar plata a otro banco
-Transacciones: serían cobros o pagos que tu cuenta hace, por ejemplo a un local o un kiosko
-Use json para almacenar los datos, los archivos se ubican en resources
-Son 4 json: cliente, cuentaBancarias, transacciones y tranferencias
-Los json vienen con datos dentro para demostración
-Cada vez que haces algo en cuentaBancaria, modifica cliente
-Cada movimiento, transaccion o transferencia, modifica cuentaBancaria y clientes, además de sus propios json
-Todas las exceptions son checked
-Use Valid en los controllers para detectar que esten todos los datos
-Use banco para almacenar constantes que considere importantes
-Los dao de cada clase tiene las funciones save y find, save es para guardar en json y find los trae a una list
-Interaces en service y persistence para mejor legibilidad
-metodos service en español
-metodos dao en ingles
-Cuando haces una transferencia te sale como un recibo
-Cuando haces una transaccion te sale la transaccion
-En transacciones, transferencias o retiros hay un limite de sobregiro y comision a partir de un limite
-Hay una chance de entre el 1% y el 5% de que una transferencia falle, como simulando un fallo del sistema 
-En la trasacciones CREDITO es para pagar y DEBITO es para cobrar
-Transacciones solo se pueden usar en pesos
-Se puede hacer transferencias entre cuenta de mismo moneda mismo cliente, sería una CC y una CA
-Abajo los endpoints que hice
-Abajo del todo esta los pasos que seguí para hacer el proyecto (Probablemente falten muchos pero intente ir anotando todo lo que iba haciendo)



GET/POST/PUT/DELETE

Clientes
GET /api/cliente   Obtener todos los clientes                    !
GET /api/cliente/{id}  Obtener cliente individual                !
POST /api/cliente   Crear cliente                                !
PUT /api/cliente/{id}  Modificar cliente                         !
DELETE /api/cliente/{id}   Borrar cliente                        !

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
GET /api/transaccion  Obtener todos las transacciones            !
GET /api/cuenta/{cuentaId}/transacciones   Obtener transacciones !
POST /api/transaccion  Hacer transfers                           !
DELETE /api/transaccion borra todas las transacciones            ! 





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
-test controllers
-Poner private a los validate no usables de validators
-COMENTAR Y DOCUMENTAR EL PROYECTO

In progress:
To do List: