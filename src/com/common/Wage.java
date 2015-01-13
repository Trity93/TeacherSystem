package com.common;

public class Wage{
	private int ID;
	private String empId;
	private int workNote;
	private int leaveNote;
	private int overNote;
	private float empWage;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public int getWorkNote() {
		return workNote;
	}
	public void setWorkNote(int workNote) {
		this.workNote = workNote;
	}
	public int getLeaveNote() {
		return leaveNote;
	}
	public void setLeaveNote(int leaveNote) {
		this.leaveNote = leaveNote;
	}
	public int getOverNote() {
		return overNote;
	}
	public void setOverNote(int overNote) {
		this.overNote = overNote;
	}
	public float getEmpWage() {
		return empWage;
	}
	public void setEmpWage(float empWage) {
		this.empWage = empWage;
	}
}
