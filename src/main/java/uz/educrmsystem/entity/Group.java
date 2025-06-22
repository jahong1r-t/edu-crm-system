package uz.educrmsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.educrmsystem.entity.enums.GroupStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
    public class Group {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private LocalDate startDate;
        private LocalDate endDate;
        private String startTime;
        private String endTime;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "teacher_id")
        private Teacher teacher;

        @ManyToMany(mappedBy = "groups")
        private List<Student> students = new ArrayList<>();

        @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Homework> homeworks = new ArrayList<>();

        @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
        private List<Attendance> attendances = new ArrayList<>();

        @Enumerated(EnumType.STRING)
        private GroupStatus groupStatus;
    }
