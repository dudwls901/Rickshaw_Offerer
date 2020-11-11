package kr.co.ilg.activity.findwork;

public class ListViewItem {
    public String title, date, pay, job, place, office, current_people, total_people, urgency, jp_job_start_time, jp_job_finish_time, jp_contents, business_reg_num, jp_num, field_name;
    public String[] title_array, jp_num_array;

    public ListViewItem(String title, String date, String pay, String job, String place, String office, String current_people, String total_people) {
        this.title = title;
        this.date = date;
        this.pay = pay;
        this.job = job;
        this.place = place;
        this.office = office;
        this.current_people = current_people;
        this.total_people = total_people;
    }

    public ListViewItem(String title, String date, String pay, String job, String place, String office, String current_people, String total_people, String urgency, String jp_job_start_time, String jp_job_finish_time, String jp_contents, String business_reg_num, String jp_num, String field_name) {
        this.title = title;
        this.date = date;
        this.pay = pay;
        this.job = job;
        this.place = place;
        this.office = office;
        this.current_people = current_people;
        this.total_people = total_people;
        this.urgency = urgency;
        this.jp_job_start_time = jp_job_start_time;
        this.jp_job_finish_time = jp_job_finish_time;
        this.jp_contents = jp_contents;
        this.business_reg_num = business_reg_num;
        this.jp_num = jp_num;
        this.field_name = field_name;
    }

    public ListViewItem(String title, String place, String date, String[] title_array, String[] jp_num_array) {
        this.title = title;
        this.place = place;
        this.date = date;
        this.title_array = title_array;
        this.jp_num_array = jp_num_array;


    }
}
