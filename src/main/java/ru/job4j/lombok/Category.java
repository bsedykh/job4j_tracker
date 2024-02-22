package ru.job4j.lombok;

import lombok.*;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {
    @NonNull
    private int id;
    @Setter
    @EqualsAndHashCode.Include
    private String name;
}
