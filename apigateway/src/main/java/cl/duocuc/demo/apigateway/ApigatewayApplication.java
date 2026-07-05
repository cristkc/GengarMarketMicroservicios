package cl.duocuc.demo.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApigatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApigatewayApplication.class, args);

        System.out.println("==================================================");
        System.out.println(" API Gateway iniciado correctamente");
        System.out.println(" URL: http://localhost:8099");
        System.out.println("--------------------------------------------------");
        System.out.println(" /api/autentificacion/**     -> AUTO");
        System.out.println(" /api/catalogo/**   			-> CATALOGO");
        System.out.println(" /api/carrito/**   			-> CARRITO");
        System.out.println(" /api/pago/**   				-> PAGO");
        System.out.println(" /api/GestionPedido/**   	-> GESTIONPEDIDO");
        System.out.println(" /api/cupones/**   			-> CUPONES");
        System.out.println(" /api/referencias/**   		-> REFERENCIAS");
        System.out.println(" /api/reporte/**   			-> REPORTE");
		System.out.println(" /api/despacho/**   			-> DESPACHO");
		System.out.println(" /api/certificacion/**   	-> CERTIFICACION");
        System.out.println("--------------------------------------------------");
        System.out.println(" Eureka: http://localhost:8761");
        System.out.println("==================================================");
    }
}