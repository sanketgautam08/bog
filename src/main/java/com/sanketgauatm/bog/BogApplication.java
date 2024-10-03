package com.sanketgauatm.bog;

import com.sanketgauatm.bog.model.Conference;
import com.sanketgauatm.bog.model.Gender;
import com.sanketgauatm.bog.model.Room;
import com.sanketgauatm.bog.model.User;
import com.sanketgauatm.bog.repo.room.RoomRepo;
import com.sanketgauatm.bog.repo.user.UserRepo;
import com.sanketgauatm.bog.repo.conference.ConferenceRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootApplication
public class BogApplication {
	public static void main(String[] args) {
		SpringApplication.run(BogApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepo userRepo, RoomRepo roomRepo, ConferenceRepo conferenceRepo) {
		List<Room> rooms = new ArrayList<>();
		User user1 = new User(1,"Sanket","Gautam",Gender.MALE,LocalDate.parse("1990-02-01"));
		User user2 = new User(2,"Salona","Rupakheti",Gender.FEMALE, LocalDate.parse("1991-08-02"));
		rooms.addAll(Arrays.asList(
				new Room("Annapurna", "2nd Floor", 10, user1),
				new Room("Everest", "3nd Floor", 4, user2)
		));
		return args -> {
			userRepo.saveAll(Arrays.asList(user1, user2));
			roomRepo.saveAll(rooms);
			Optional<Room> room1 = roomRepo.findById(2);
			conferenceRepo.save(new Conference("Party", "Party all night", room1.get(), LocalDate.now()));
			conferenceRepo.save(new Conference("Con 2", "Testing 2", room1.get(), LocalDate.now()));
			System.out.println(LocalDateTime.now());
		};

	}
}
