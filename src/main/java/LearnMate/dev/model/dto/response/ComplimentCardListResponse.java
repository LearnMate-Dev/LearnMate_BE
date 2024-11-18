package LearnMate.dev.model.dto.response;

import LearnMate.dev.model.entity.ComplimentCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComplimentCardListResponse {

    private Long complimentId;
    private String complimentTitle;
    private String complimentContent;

}
