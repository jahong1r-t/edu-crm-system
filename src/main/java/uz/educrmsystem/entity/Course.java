package uz.educrmsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int duration;
    private Double price;
    private boolean isActive;

    @OneToOne
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;
}
