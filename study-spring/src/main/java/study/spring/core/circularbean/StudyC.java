package study.spring.core.circularbean;

public class StudyC {
    private StudyA studyA;

    public StudyC() {
    }

    public StudyC(StudyA studyA) {
        this.studyA = studyA;
    }

    public StudyA getStudyA() {
        return studyA;
    }

    public void setStudyA(StudyA studyA) {
        this.studyA = studyA;
    }
}
