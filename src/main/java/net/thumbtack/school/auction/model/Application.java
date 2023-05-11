package net.thumbtack.school.auction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Application {
    private int id;
    private String type;
    private String status;
    private Customer customer;
    private List<String> requiredFields;

    public Application(int id) {
        setId(id);
    }
}
