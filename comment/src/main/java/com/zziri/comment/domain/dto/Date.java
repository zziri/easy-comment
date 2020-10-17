package com.zziri.comment.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@NoArgsConstructor
@Data
public class Date {
    private Integer year;
    private Integer month;
    private Integer day;
    private Integer hour;
    private Integer min;
    private Integer sec;

    private Date(LocalDateTime time) {
        this.year = time.getYear();
        this.month = time.getMonthValue();
        this.day  = time.getDayOfMonth();
        this.hour = time.getHour();
        this.min = time.getMinute();
        this.sec = time.getSecond();
    }

    public static Date of(LocalDateTime time) {
        return new Date(time);
    }

    @Override
    public String toString() {
        return LocalDateTime.of(this.year, this.month, this.day, this.hour, this.min, this.sec).toString();
    }
}
