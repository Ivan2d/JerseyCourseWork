package net.thumbtack.school.auction.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddApplicationToOperatorRequest {
    private String type;
    private String status;
}
