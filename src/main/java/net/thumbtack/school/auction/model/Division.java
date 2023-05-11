package net.thumbtack.school.auction.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Division {
    private int id;
    private String name;
    private List<Executor> operatorList = new ArrayList<>();

    public Division(int idDivision) {
        setId(idDivision);
    }
}
