package net.thumbtack.school.auction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DocumentOfApplication {
    private Customer customer;
    private Application application;
    private Executor executor;
    private String whatsDone;
}
