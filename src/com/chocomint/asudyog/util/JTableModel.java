package com.chocomint.asudyog.util;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class JTableModel extends DefaultTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4859099927209379724L;
	public JTableModel(Vector<String> v, int c) {
		super(v,c);
	}
	@Override
    public boolean isCellEditable(int i, int i1) {
        return false; //To change body of generated methods, choose Tools | Templates.
    }
}
