package kr.co.ilg.activity.workermanage;

public class JobCareerLVItem {
    private String job;
    private String career;
    private String date;
    private String field_name;

    // 경력
    public JobCareerLVItem(String job, String career) {
        this.job = job;
        this.career = career;
    }

    // 근무 이력
    public JobCareerLVItem(String job, String date, String field_name) {
        this.job = job;
        this.date = date;
        this.field_name = field_name;
    }

    public void setMyJob(String job) {
        this.job = job;
    }
    public void setMyCareer(String career) {
        this.career = career;
    }
    public void setMyDate(String date) {
        this.date = date;
    }
    public void setMyFieldName(String field_name) {
        this.field_name = field_name;
    }

    public String getMyJob() {
        return this.job;
    }
    public String getMyCareer() {
        return this.career;
    }
    public String getMyDate() {
        return this.date;
    }
    public String getMyFieldName() {
        return this.field_name;
    }
}
