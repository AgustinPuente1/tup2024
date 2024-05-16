package tup2024.src.main.java.ar.edu.utn.frbb.tup;

import java.time.LocalDate;

public class Banco {
    public static void main(String[] args) {
        Cliente cliente1 = new Cliente("Juan", "Perez", 12345678, LocalDate.of(2000, 5, 10),
        new Direccion("CallePrincipal", 123), "+123456789", "juan@example.com");

        // Crear un objeto CuentaBancaria (corriente)
        CuentaBancaria cuentaCorriente = new CuentaBancaria(cliente1, 1001, 1000, "corriente", LocalDate.of(2020, 4, 15));

        // Crear un objeto MovimientosCuenta y realizar un depósito
        MovimientosCuenta.registrarDepositoCorriente(cuentaCorriente, 500);

        // Crear un objeto CuentaBancaria (ahorros)
        CuentaBancaria cuentaAhorros = new CuentaBancaria(cliente1, 1002, 2000, "ahorros", LocalDate.of(2022, 4, 15));

        // Crear un objeto MovimientosCuenta y realizar un retiro
        MovimientosCuenta.registrarRetiroAhorros(cuentaAhorros, 300);

        // Imprimir los datos de las cuentas
        System.out.println("Datos de la cuenta corriente:");
        System.out.println(cuentaCorriente.toString());
        System.out.println("\nDatos de la cuenta de ahorros:");
        System.out.println(cuentaAhorros.toString());
    }
}
