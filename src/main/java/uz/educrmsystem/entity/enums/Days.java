package uz.educrmsystem.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum Days {
    ODD_DAYS(List.of("MONDAY", "WEDNESDAY", "FRIDAY")),
    EVEN_DAYS(List.of("TUESDAY", "THURSDAY", "SATURDAY")),
    BOOTCAMP_DAYS(List.of("EVERYDAY"));

    private final List<String> days;
}
