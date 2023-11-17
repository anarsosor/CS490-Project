package HealthCare;

import HealthCare.entity.*;
import HealthCare.dto.UserDTO;
import HealthCare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication implements CommandLineRunner {
	@Autowired
	UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userService.addUser(new UserDTO("asosor@miu.edu", "password", "Anar", "Sosor", Gender.MALE, "206-710-5876", "7700 E Green Lake", "Seattle", "98103", true, 50.0, Speciality.PEDIATRICS, true, "14124525252", false, ""));
		userService.addUser(new UserDTO("jenny@outlook.com", "password", "Jenna", "Carter", Gender.FEMALE, "806-732-5646", "8585 Space wood Springs Rd", "Austin", "78759", true, 45.4, Speciality.FAMILY, false, "", true, "Monos Pharma"));
		userService.addUser(new UserDTO("ben@gmail.com", "password", "Ben", "John", Gender.MALE, "506-732-1245", "1234 Grand hill", "Dallas", "75444", true, 65.0, Speciality.INTERNAL, true, "987456123", true, "Agga Pharma"));
		userService.addUser(new UserDTO("nora@miu.edu", "password", "Nora", "John", Gender.FEMALE, "506-732-1245", "1234 Grand hill", "Dallas", "75444", true, 70.0, Speciality.PEDIATRICS, true, "987456123", false, ""));
		userService.addUser(new UserDTO("john@example.com", "johnpass", "John", "Smith", Gender.MALE, "111-222-3333", "789 Oak Street", "Los Angeles", "90001", false, 0.0, Speciality.NONE, true, "555555555", false, ""));
		userService.addUser(new UserDTO("lisa@example.com", "lisapass", "Lisa", "Johnson", Gender.FEMALE, "777-666-5555", "678 Birch Street", "Seattle", "98101", true, 48.0, Speciality.GYNECOLOGY, false, "", false, ""));
		userService.addUser(new UserDTO("daniel@example.com", "danielpass", "Daniel", "Davis", Gender.MALE, "555-444-3333", "123 Elm Avenue", "Miami", "33101", false, 0.0, Speciality.NONE, true, "999999999", false, ""));
		userService.addUser(new UserDTO("grace@example.com", "gracepass", "Grace", "Wilson", Gender.FEMALE, "333-222-1111", "234 Cedar Road", "Chicago", "60601", false, 0.0, Speciality.NONE, true, "111111111", false, ""));
		userService.addUser(new UserDTO("william@example.com", "williampass", "William", "Brown", Gender.MALE, "999-888-7777", "567 Pine Street", "Dallas", "75444", false, 0.0, Speciality.NONE, true, "777777777", false, ""));
		userService.addUser(new UserDTO("mary@example.com", "marypass", "Mary", "Miller", Gender.FEMALE, "111-222-3333", "890 Elm Road", "New York", "10001", false, 0.0, Speciality.NONE, true, "888888888", false, ""));
		userService.addUser(new UserDTO("michael@example.com", "michaelpass", "Michael", "Brown", Gender.MALE, "555-555-5555", "456 Oak Lane", "San Francisco", "94101", false, 0.0, Speciality.NONE, true, "11112222333", false, ""));
		userService.addUser(new UserDTO("linda@example.com", "lindapass", "Linda", "Johnson", Gender.FEMALE, "777-777-7777", "567 Cedar Avenue", "Los Angeles", "90001", false, 0.0, Speciality.NONE, true, "999999999", false, ""));
		userService.addUser(new UserDTO("steve@example.com", "stevepass", "Steve", "Wilson", Gender.MALE, "333-444-5555", "234 Pine Street", "Miami", "33101", false, 0.0, Speciality.NONE, true, "22225555333", false, ""));
		userService.addUser(new UserDTO("sophia@example.com", "sophiapass", "Sophia", "Davis", Gender.FEMALE, "123-123-1234", "123 Oak Lane", "Chicago", "60601", false, 0.0, Speciality.NONE, true, "555555555", false, ""));
		userService.addUser(new UserDTO("nancy@example.com", "nancypass", "Nancy", "Smith", Gender.FEMALE, "888-777-6666", "345 Maple Street", "Austin", "73301", true, 54.0, Speciality.FAMILY, true, "888888888", false, ""));
		userService.addUser(new UserDTO("peter@example.com", "peterpass", "Peter", "Wilson", Gender.MALE, "444-555-6666", "987 Pine Road", "Houston", "77001", false, 0.0, Speciality.NONE, true, "123456789", false, ""));
		userService.addUser(new UserDTO("jessica@example.com", "jessicapass", "Jessica", "Davis", Gender.FEMALE, "123-456-7890", "123 Elm Lane", "Los Angeles", "90001", true, 38.0, Speciality.PSYCHIATRY, false, "", true, "Mental Wellness Center"));
		userService.addUser(new UserDTO("george@example.com", "georgepass", "George", "Johnson", Gender.MALE, "777-888-9999", "456 Oak Avenue", "Miami", "33101", true, 46.0, Speciality.EMERGENCY, true, "666666666", false, ""));
		userService.addUser(new UserDTO("olivia@example.com", "oliviapass", "Olivia", "Miller", Gender.FEMALE, "555-444-3333", "123 Cedar Street", "Chicago", "60601", true, 38.0, Speciality.INTERNAL, true, "111111111", false, ""));
		userService.addUser(new UserDTO("williamsmith@example.com", "williampass", "William", "Smith", Gender.MALE, "999-999-9999", "890 Elm Avenue", "New York", "10001", true, 36.0, Speciality.NEUROLOGY, true, "888888888", false, ""));
		userService.addUser(new UserDTO("hannah@example.com", "hannahpass", "Hannah", "Brown", Gender.FEMALE, "111-222-3333", "345 Pine Road", "San Francisco", "94101", true, 55.0, Speciality.CARDIOLOGY, true, "11112222333", true, "Maternity Clinic"));
		userService.addUser(new UserDTO("david@example.com", "davidpass", "David", "Jones", Gender.MALE, "555-555-5555", "789 Oak Lane", "Seattle", "98101", true, 45.0, Speciality.RADIOLOGY, false, "", true, "Spice Wood Springs"));
		userService.addUser(new UserDTO("dnyamochir@miu.edu", "dulguunpass", "Dulguun", "Nyam-Ochir", Gender.MALE, "565-656-6565", "508 N 4th St", "Fairfield", "52556", true, 65.0, Speciality.PEDIATRICS, true, "87878787878", false, ""));
	}

}
