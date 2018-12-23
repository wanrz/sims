package com.sims.common.util.date;

public class ExcelCellDate implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6266091288147144245L;

	/*
	 * 行
	 */
	private int row;
	
	/*
	 * 列
	 */
	private int col;
	
	/*
	 * 内容
	 */
	private String cellContents;
	
	
	public void setRow(int row){
		this.row = row;
	}

	public int getRow(){
		return this.row;
	}
	
	public void setCol(int col){
		this.col = col;
	}
	
	public int getCol(){
		return this.col;
	}
	
	public void setCellContents(String cellContents){
		this.cellContents = cellContents;
	}
	
	public String getCellContents(){
		return this.cellContents;
	}
}
