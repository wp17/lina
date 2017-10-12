package com.github.wp17.lina.db.mybatis.model;

public class RoleInfo {
    private String roleUuid;

    private String birthday;

    private String city;

    private String feeling;

    private String icon;

    private Integer popular;

    private String soundFeeling;

    private Integer mood;

    private Integer totalZan;

    private String icon2;

    private Long lastChargeTime;

    private String propSet;

    public String getRoleUuid() {
        return roleUuid;
    }

    public void setRoleUuid(String roleUuid) {
        this.roleUuid = roleUuid == null ? null : roleUuid.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getFeeling() {
        return feeling;
    }

    public void setFeeling(String feeling) {
        this.feeling = feeling == null ? null : feeling.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getPopular() {
        return popular;
    }

    public void setPopular(Integer popular) {
        this.popular = popular;
    }

    public String getSoundFeeling() {
        return soundFeeling;
    }

    public void setSoundFeeling(String soundFeeling) {
        this.soundFeeling = soundFeeling == null ? null : soundFeeling.trim();
    }

    public Integer getMood() {
        return mood;
    }

    public void setMood(Integer mood) {
        this.mood = mood;
    }

    public Integer getTotalZan() {
        return totalZan;
    }

    public void setTotalZan(Integer totalZan) {
        this.totalZan = totalZan;
    }

    public String getIcon2() {
        return icon2;
    }

    public void setIcon2(String icon2) {
        this.icon2 = icon2 == null ? null : icon2.trim();
    }

    public Long getLastChargeTime() {
        return lastChargeTime;
    }

    public void setLastChargeTime(Long lastChargeTime) {
        this.lastChargeTime = lastChargeTime;
    }

    public String getPropSet() {
        return propSet;
    }

    public void setPropSet(String propSet) {
        this.propSet = propSet == null ? null : propSet.trim();
    }

	@Override
	public String toString() {
		return "RoleInfo [roleUuid=" + roleUuid + ", birthday=" + birthday
				+ ", city=" + city + ", feeling=" + feeling + ", icon=" + icon
				+ ", popular=" + popular + ", soundFeeling=" + soundFeeling
				+ ", mood=" + mood + ", totalZan=" + totalZan + ", icon2="
				+ icon2 + ", lastChargeTime=" + lastChargeTime + ", propSet="
				+ propSet + "]";
	}
}