package br.com.fiap.epictaska.task;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{task.title.notblank}")
    private String title;

    @Size(min = 10, max = 255, message = "{task.description.size}")
    private String description;

    @Min(1) @Max(100)
    private int score;

    @Min(0) @Max(100)
    private int status;

}
