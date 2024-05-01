package enrollment.server.model.course;

import java.util.Collections;
import java.util.List;

public class Prerequisite {
    private final List<Integer> prerequisite; // 선행과목 - Course의 id 값들이 저장됨

    public Prerequisite(List<Integer> prerequisite) {
        this.prerequisite = prerequisite;
    }

    public List<Integer> getPrerequisite() {
        return Collections.unmodifiableList(prerequisite);
    }
}
