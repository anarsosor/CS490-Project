package HealthCare.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    private String phone;
    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToMany(mappedBy = "user")
    private Set<Role> roles = new HashSet<>();

    public User(String firstName, String lastName, String username, String phone, Address address, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
    }
}
