package br.com.fiap.epictaska.task.dto;

import br.com.fiap.epictaska.task.Task;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskForm(
        @NotBlank(message = "{task.title.notblank}")
        String title,

        @Size(min = 10, max = 255, message = "{task.description.size}")
        String description,

        @Min(1) @Max(100)
        int score
) {
    public Task toModel() {
        return Task.builder()
                .title(title)
                .description(description)
                .score(score)
                .build();
    }
}
