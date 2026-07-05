package com.zapatillas.catalogo;

import org.springframework.boot.CommandLineRunner;

import com.zapatillas.catalogo.model.Zapatilla;
import com.zapatillas.catalogo.repository.CatalogoRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {


    @Bean
    CommandLineRunner init(CatalogoRepository repository){
        return args -> {
            
            if (repository.count() == 0){
                
                repository.save(new Zapatilla(null,"Thunder Red", "Retro4","Jordan","9US",99999,5));
                repository.save(new Zapatilla(null,"Lightning", "Retro 4","Jordan","7.5US",120000,3));
                repository.save(new Zapatilla(null,"Virgil Abloh Archive Alaska", "Retro 4","Jordan","9US",210000,4));
                repository.save(new Zapatilla(null,"Zion Williamson Voodoo", "Dunk SB","Nike","9.5US",150000,7));
                repository.save(new Zapatilla(null,"Chicago Lost and Found", "Retro 1","Jordan","9US",100000,8));
                repository.save(new Zapatilla(null,"April SkateBoards", "DUNK SB","Nike","7.5US",110000,3));
                repository.save(new Zapatilla(null,"Air Max Phanton", "Air Max","Nike","8.5US",120000,4));
                repository.save(new Zapatilla(null,"Air Force 1 Lv8", "Air Force","Nike","7.5US",150000,3));
                repository.save(new Zapatilla(null,"Adidas Terrex Camp", "Terrex","Adidas","9.US",100000,6));


                    
            }
        
     };

    }
}
