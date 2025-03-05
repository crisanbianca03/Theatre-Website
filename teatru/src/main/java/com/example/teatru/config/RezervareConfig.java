package com.example.teatru.config;

import com.example.teatru.model.*;
import com.example.teatru.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class RezervareConfig {

    @Bean(name = "rezervareRun")
    CommandLineRunner commandLineRunner(
            RezervareRepository rezervareRepository,
            UtilizatorRepository utilizatorRepository,
            SpectacolRepository spectacolRepository,
            SalaRepository salaRepository,
            ActorRepository actorRepository,
            SpectacolActorRepository spectacolActorRepository) {
        return args -> {
            Utilizator utilizator = utilizatorRepository.findById(3)
                    .orElseGet(() -> utilizatorRepository.save(new Utilizator(
                            "Bianca",
                            "Crisan",
                            "bianca@gmail.com",
                            "parola",
                            "client"
                    )));
            Utilizator utilizator1 = utilizatorRepository.findById(4)
                    .orElseGet(() -> utilizatorRepository.save(new Utilizator(
                            "Diana",
                            "Zahan",
                            "diana@gmail.com",
                            "parola1",
                            "client"
                    )));
            Utilizator utilizator2 = utilizatorRepository.findById(5)
                    .orElseGet(() -> utilizatorRepository.save(new Utilizator(
                            "Laura",
                            "Botezan",
                            "laura@gmail.com",
                            "parola2",
                            "client"
                    )));


            Sala sala1 = salaRepository.save(new Sala());
            Sala sala2 = salaRepository.save(new Sala());
            Sala sala3 = salaRepository.save(new Sala());


            Actor actor1 = actorRepository.findByNumeAndPrenume("Ion", "Popescu")
                    .orElseGet(() -> actorRepository.save(new Actor("Ion", "Popescu")));
            Actor actor2 = actorRepository.findByNumeAndPrenume("Maria", "Ionescu")
                    .orElseGet(() -> actorRepository.save(new Actor("Maria", "Ionescu")));
            Actor actor3 = actorRepository.findByNumeAndPrenume("Andrei", "Vasilescu")
                    .orElseGet(() -> actorRepository.save(new Actor("Andrei", "Vasilescu")));
            Actor actor4 = actorRepository.findByNumeAndPrenume("Elena", "Petrescu")
                    .orElseGet(() -> actorRepository.save(new Actor("Elena", "Petrescu")));
            Actor actor5 = actorRepository.save(new Actor("Ana", "Ionescu"));
            Actor actor6 = actorRepository.save(new Actor("Mihai", "Georgescu"));
            Actor actor7 = actorRepository.save(new Actor("Andrei", "Radu"));
            Actor actor8 = actorRepository.save(new Actor("Elena", "Mihailescu"));

            Spectacol spectacol1 = spectacolRepository.save(new Spectacol(
                            "Romeo și Julieta",
                            LocalDateTime.of(2025, 1, 11, 19, 0),
                            "19:00",
                            "Drama clasică despre iubire și tragedie",
                            "Dramă",
                            100,
                            "2h 30min",
                            sala1,
                            "/images/romeo_si_julieta.jpg"
                    ));
            Spectacol spectacol5 = spectacolRepository.save(new Spectacol(
                            "Romeo și Julieta",
                            LocalDateTime.of(2025, 12, 16, 19, 0),
                            "20:00",
                            "Drama clasică despre iubire și tragedie",
                            "Dramă",
                            100,
                            "2h 30min",
                            sala1,
                            "/images/romeo_si_julieta.jpg"
                    ));
            Spectacol spectacol6 = spectacolRepository.save(new Spectacol(
                            "Romeo și Julieta",
                            LocalDateTime.of(2025, 1, 15, 19, 0),
                            "19:00",
                            "Drama clasică despre iubire și tragedie",
                            "Dramă",
                            100,
                            "2h 30min",
                            sala3,
                            "/images/romeo_si_julieta.jpg"
                    ));

            Spectacol spectacol2 = spectacolRepository.save(new Spectacol(
                            "Concert de Crăciun",
                            LocalDateTime.of(2025, 1, 10, 18, 0),
                            "18:00",
                            "Concertul de sărbători pentru toată familia",
                            "Concert",
                            120,
                            "2h",
                            sala1,
                            "/images/concert_craciun.jpg"
                    ));

            Spectacol spectacol7 = spectacolRepository.save(new Spectacol(
                            "Concert de Crăciun",
                            LocalDateTime.of(2025, 1, 17, 18, 0),
                            "18:00",
                            "Concertul de sărbători pentru toată familia",
                            "Concert",
                            90,
                            "2h",
                            sala1,
                            "/images/concert_craciun.jpg"
                    ));

            Spectacol spectacol3 = spectacolRepository.save(new Spectacol(
                            "Opera Carmen",
                            LocalDateTime.of(2025, 1, 13, 20, 0),
                            "20:00",
                            "Opera clasică într-o interpretare modernă",
                            "Operă",
                            80,
                            "3h",
                            sala2,
                            "/images/opera_carmen.png"
                    ));
            Spectacol spectacol8 =spectacolRepository.save(new Spectacol(
                            "Opera Carmen",
                            LocalDateTime.of(2025, 1, 17, 20, 0),
                            "19:00",
                            "Opera clasică într-o interpretare modernă",
                            "Operă",
                            100,
                            "3h",
                            sala2,
                            "/images/opera_carmen.png"
                    ));
            Spectacol spectacol9 =spectacolRepository.save(new Spectacol(
                    "Opera Carmen",
                    LocalDateTime.of(2025, 1, 19, 20, 0),
                    "14:00",
                    "Opera clasică într-o interpretare modernă",
                    "Operă",
                    100,
                    "3h",
                    sala3,
                    "/images/opera_carmen.png"
            ));
            Spectacol spectacol4 = spectacolRepository.save(new Spectacol(
                            "Stand-up Comedy Night",
                            LocalDateTime.of(2025, 1, 14, 21, 0),
                            "21:00",
                            "O seară plină de râsete cu cei mai buni comedieni",
                            "Comedie",
                            150,
                            "2h",
                            sala2,
                            "/images/comedy.jpg"
                    ));
            Spectacol spectacol10 = spectacolRepository.save(new Spectacol(
                            "Stand-up Comedy Night",
                            LocalDateTime.of(2025, 1, 17, 21, 0),
                            "17:00",
                            "O seară plină de râsete cu cei mai buni comedieni",
                            "Comedie",
                            125,
                            "2h",
                            sala2,
                            "/images/comedy.jpg"
                    ));
            Spectacol spectacol11 = spectacolRepository.save(new Spectacol(
                            "Stand-up Comedy Night",
                            LocalDateTime.of(2025, 1, 15, 21, 0),
                            "21:30",
                            "O seară plină de râsete cu cei mai buni comedieni",
                            "Comedie",
                            125,
                            "2h",
                            sala3,
                            "/images/comedy.jpg"
                    ));
            Spectacol spectacol12 = spectacolRepository.save(new Spectacol(
                            "O noaptea furtunoasă",
                            LocalDateTime.of(2025, 1, 17, 19, 0),
                            "15:00",
                            "Un spectacol captivant bazat pe o comedie clasică românească.",
                            "Comedie",
                            150,
                            "1h 45min",
                            sala2,
                            "/images/noaptea_furtunoasa1.jpg"
                    ));
            Spectacol spectacol13 = spectacolRepository.save(new Spectacol(
                            "Concert Simfonic",
                            LocalDateTime.of(2025, 1, 23, 20, 0),
                            "20:00",
                            "Un concert extraordinar cu cele mai frumoase compoziții clasice.",
                            "Concert",
                            200,
                            "2h",
                            sala3,
                            "/images/concert_simfonic.jpg"
                    ));


            spectacolActorRepository.save(new SpectacolActor(spectacol1, actor1));
            spectacolActorRepository.save(new SpectacolActor(spectacol1, actor2));
            spectacolActorRepository.save(new SpectacolActor(spectacol5, actor1));
            spectacolActorRepository.save(new SpectacolActor(spectacol5, actor2));
            spectacolActorRepository.save(new SpectacolActor(spectacol6, actor1));
            spectacolActorRepository.save(new SpectacolActor(spectacol6, actor2));
            spectacolActorRepository.save(new SpectacolActor(spectacol2, actor3));
            spectacolActorRepository.save(new SpectacolActor(spectacol7, actor3));
            spectacolActorRepository.save(new SpectacolActor(spectacol3, actor4));
            spectacolActorRepository.save(new SpectacolActor(spectacol8, actor4));
            spectacolActorRepository.save(new SpectacolActor(spectacol9, actor4));
            spectacolActorRepository.save(new SpectacolActor(spectacol4, actor1));
            spectacolActorRepository.save(new SpectacolActor(spectacol4, actor3));
            spectacolActorRepository.save(new SpectacolActor(spectacol10, actor1));
            spectacolActorRepository.save(new SpectacolActor(spectacol10, actor3));
            spectacolActorRepository.save(new SpectacolActor(spectacol11, actor1));
            spectacolActorRepository.save(new SpectacolActor(spectacol11, actor3));
            spectacolActorRepository.save(new SpectacolActor(spectacol4, actor1));
            spectacolActorRepository.save(new SpectacolActor(spectacol12, actor5));
            spectacolActorRepository.save(new SpectacolActor(spectacol12, actor6));
            spectacolActorRepository.save(new SpectacolActor(spectacol12, actor1));
            spectacolActorRepository.save(new SpectacolActor(spectacol13, actor7));
            spectacolActorRepository.save(new SpectacolActor(spectacol13, actor8));


            Rezervare rezervare1 = new Rezervare(utilizator, spectacol1, 2, LocalDateTime.of(2025, 1, 10, 19, 0));
            Rezervare rezervare2 = new Rezervare(utilizator, spectacol2, 4, LocalDateTime.of(2025, 1, 9, 19, 0));
            Rezervare rezervare3 = new Rezervare(utilizator, spectacol8, 1, LocalDateTime.now());
            Rezervare rezervare4 = new Rezervare(utilizator, spectacol5, 3, LocalDateTime.now());
            Rezervare rezervare5 = new Rezervare(utilizator1, spectacol3, 2, LocalDateTime.of(2025, 1, 8, 19, 0));
            Rezervare rezervare6 = new Rezervare(utilizator1, spectacol4, 4, LocalDateTime.of(2025, 1, 2, 19, 0));
            Rezervare rezervare7 = new Rezervare(utilizator1, spectacol8, 5, LocalDateTime.now());
            Rezervare rezervare8 = new Rezervare(utilizator1, spectacol9, 6, LocalDateTime.now());
            Rezervare rezervare9 = new Rezervare(utilizator2, spectacol10, 2, LocalDateTime.of(2025, 1, 11, 19, 0));
            Rezervare rezervare10 = new Rezervare(utilizator2, spectacol1, 4, LocalDateTime.of(2025, 1, 5, 19, 0));
            Rezervare rezervare11 = new Rezervare(utilizator2, spectacol6, 5, LocalDateTime.now());
            Rezervare rezervare12 = new Rezervare(utilizator2, spectacol12, 6, LocalDateTime.now());


            rezervareRepository.saveAll(List.of(rezervare1, rezervare2, rezervare3, rezervare4,rezervare5,rezervare6,rezervare7,rezervare8,rezervare9,rezervare10,rezervare11,rezervare12));

            System.out.println("Rezervări și spectacole create cu succes!");
        };
    }
}
