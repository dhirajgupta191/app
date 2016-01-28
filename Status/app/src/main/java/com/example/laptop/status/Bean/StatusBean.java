package com.example.laptop.status.Bean;

/**
 * Created by Laptop on 24-Dec-15.
 */
public class StatusBean {

    public String getStatus_text() {
        return status_text;
    }

    public void setStatus_text(String status_text) {
        this.status_text = status_text;
    }

    public String getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(String current_status) {
        this.current_status = current_status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String status_text;
    public String current_status;
    public String time;
}
