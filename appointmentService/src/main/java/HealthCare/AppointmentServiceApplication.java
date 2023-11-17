package HealthCare;

import HealthCare.dto.AppointmentDTO;
import HealthCare.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.time.LocalDateTime;


@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class AppointmentServiceApplication implements CommandLineRunner {
	@Autowired
	AppointmentService appointmentService;
	public static void main(String[] args) {
		SpringApplication.run(AppointmentServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		appointmentService.makeAppointment(new AppointmentDTO(1, 6, LocalDateTime.of(2023, 10, 1, 12, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(4, 15, LocalDateTime.of(2023, 10, 2, 8, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(3, 1, LocalDateTime.of(2023, 10, 2, 9, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(3, 6, LocalDateTime.of(2023, 10, 1, 9, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(5, 15, LocalDateTime.of(2023, 10, 2, 13, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(5, 20, LocalDateTime.of(2023, 10, 3, 14, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(5, 22, LocalDateTime.of(2023, 10, 1, 15, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(7, 15, LocalDateTime.of(2023, 10, 3, 10, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(7, 2, LocalDateTime.of(2023, 10, 3, 12, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(8, 18, LocalDateTime.of(2023, 10, 2, 13, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(9, 1, LocalDateTime.of(2023, 10, 2, 16, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(9, 18, LocalDateTime.of(2023, 10, 3, 15, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(10, 1, LocalDateTime.of(2023, 10, 4, 10, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(4, 1, LocalDateTime.of(2023, 10, 4, 14, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(11, 19, LocalDateTime.of(2023, 10, 3, 11, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(12, 19, LocalDateTime.of(2023, 10, 2, 9, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(12, 3, LocalDateTime.of(2023, 10, 4, 16, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(13, 15, LocalDateTime.of(2023, 10, 5, 14, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(15, 20, LocalDateTime.of(2023, 10, 5, 11, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(16, 1, LocalDateTime.of(2023, 10, 2, 13, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(16, 3, LocalDateTime.of(2023, 10, 5, 14, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(18, 21, LocalDateTime.of(2023, 10, 4, 10, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(18, 22, LocalDateTime.of(2023, 10, 6, 10, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(19, 17, LocalDateTime.of(2023, 10, 3, 15, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(19, 15, LocalDateTime.of(2023, 10, 3, 11, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(20, 2, LocalDateTime.of(2023, 10, 2, 16, 0)));
		appointmentService.makeAppointment(new AppointmentDTO(21, 18, LocalDateTime.of(2023, 10, 2, 14, 0)));


	}
}
