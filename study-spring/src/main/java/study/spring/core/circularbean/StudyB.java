package study.spring.core.circularbean;

public class StudyB {
    private StudyC studyC;

    public StudyB() {
    }

    public StudyB(StudyC studyC) {
        this.studyC = studyC;
    }

    public StudyC getStudyC() {
        return studyC;
    }

    public void setStudyC(StudyC studyC) {
        this.studyC = studyC;
    }
}
