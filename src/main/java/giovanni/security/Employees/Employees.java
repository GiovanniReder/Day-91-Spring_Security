package giovanni.security.Employees;

import giovanni.security.Devices.Devices;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "employees")

public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String name;

    private String surname;

    private String email;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Devices> devices;

    private String password;

    public Employees(String username, String name, String surname, String email, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    /*
    public Employees(String username, String name, String surname, String email, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

 */
}
