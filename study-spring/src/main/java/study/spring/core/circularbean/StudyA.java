package study.spring.core.circularbean;

public class StudyA {
    private StudyB studyB;

    public void setStudyB(StudyB studyB) {
        this.studyB = studyB;
    }

    public StudyB getStudyB() {
        return studyB;
    }

    public StudyA(){}

    public StudyA(StudyB studyB) {
        this.studyB = studyB;
    }
}
