package com.mongodb.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class StudentAndSeat 
{
	private String studentID;
	private String seatNo;
	private String rowNo;
	
	public void setStudentID(String id)
	{
		this.studentID = id;
	}
	public String getStudentID()
	{
		return this.studentID;
	}
	public void setSeatNo(String seatNo)
	{
		this.seatNo = seatNo;
	}
	public String getSeatNo()
	{
		return seatNo;
	}
	public void setRowNo(String rowNo)
	{
		this.rowNo = rowNo;
	}
	public String getRowNo()
	{
		return rowNo;
	}
}
