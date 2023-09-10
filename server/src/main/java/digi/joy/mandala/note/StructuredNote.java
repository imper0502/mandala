package digi.joy.mandala.note;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class StructuredNote {
    private UUID parentId;
    private UUID childId;
}
