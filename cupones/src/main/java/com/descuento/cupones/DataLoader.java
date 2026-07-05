package com.descuento.cupones;
import com.descuento.cupones.model.Cupones;
import com.descuento.cupones.repository.CuponesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {
    
    @Bean
    CommandLineRunner init(CuponesRepository repository){

        return args -> {

            if(repository.count() == 0){
                repository.save(new Cupones(null,"CuponBlack","ASGFBEKRW1",10));
                repository.save(new Cupones(null,"CuponDiaTrabajador","TRABAJO2026",20));
                repository.save(new Cupones(null,"CuponVerano","SUMMER20",30));

            }
        };
        
    }

}
