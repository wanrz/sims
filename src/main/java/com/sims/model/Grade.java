package com.sims.model;

public class Grade implements Comparable<Grade> {
    private Integer id;

    private String gradename;

    private String gradedesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGradename() {
        return gradename;
    }

    public void setGradename(String gradename) {
        this.gradename = gradename == null ? null : gradename.trim();
    }

    public String getGradedesc() {
        return gradedesc;
    }

    public void setGradedesc(String gradedesc) {
        this.gradedesc = gradedesc == null ? null : gradedesc.trim();
    }

	@Override
	public int compareTo(Grade o) {
		return this.id - o.getId();
	}
}