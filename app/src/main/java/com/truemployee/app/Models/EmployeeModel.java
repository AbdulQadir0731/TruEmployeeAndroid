
package com.truemployee.app.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeModel {

    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("skills")
    @Expose
    private List<Object> skills = null;
    @SerializedName("experience")
    @Expose
    private List<String> experience = null;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("education")
    @Expose
    private List<Object> education = null;
    @SerializedName("cnic")
    @Expose
    private String cnic;
    @SerializedName("avatar")
    @Expose
    private String avatar;

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<Object> getSkills() {
        return skills;
    }

    public void setSkills(List<Object> skills) {
        this.skills = skills;
    }

    public List<String> getExperience() {
        return experience;
    }

    public void setExperience(List<String> experience) {
        this.experience = experience;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Object> getEducation() {
        return education;
    }

    public void setEducation(List<Object> education) {
        this.education = education;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
