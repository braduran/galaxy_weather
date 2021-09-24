package co.com.galaxy.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Periodo {
    private String clima;
    private long dias;
}
