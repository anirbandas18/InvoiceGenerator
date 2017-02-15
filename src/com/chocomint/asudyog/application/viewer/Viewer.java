package com.chocomint.asudyog.application.viewer;

import javax.swing.JPanel;


public class Viewer extends JPanel {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7445466003024924156L;
	protected static Search s;
	protected static Results r;
	
	public Viewer() {
		createComponents();
		addComponents();
		setUpComponents();
	}
	
	private void createComponents() {
		s = new Search();
		r = new Results();
	}
	
	private void addComponents() {
		add(s);
		add(r);
	}
	
	private void setUpComponents() {
		setLayout(null);
	}

}
