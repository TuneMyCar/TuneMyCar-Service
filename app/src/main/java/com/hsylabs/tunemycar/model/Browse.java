package com.hsylabs.tunemycar.model;

/**
 * Created by Hassan Yousuf on 2/3/2017.
 */

public class Browse {
    private int id;
    private String s1, s2, s3, s4;

    public Browse(String s1, String s2, String s3, String s4) {
//        this.id = id;
        this.s1 = s1;
        this.s2= s2;
        this.s3 = s3;
        this.s4 = s4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public String getS3() {
        return s3;
    }

    public void setS3(String s3) {
        this.s3 = s3;
    }

    public String getS4() {
        return s4;
    }

    public void setS4(String s4) {
        this.s4 = s4;
    }
}
