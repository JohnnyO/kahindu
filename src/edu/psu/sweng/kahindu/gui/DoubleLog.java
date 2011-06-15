package edu.psu.sweng.kahindu.gui;

import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DoubleLog extends JPanel {

	private final List<JLabel> labels;
	private final List<JTextField> fields;

	public DoubleLog(Map<String, Double> map) {
		super();
		int rowNum = map.size();
		labels = new ArrayList<JLabel>(map.size());
		fields = new ArrayList<JTextField>(map.size());
		this.initialize(map);
	}

	private void initialize(Map<String, Double> map) {
		int rowNum = map.size();

		this.setLayout(new GridLayout(rowNum, 2));
		for (Entry<String, Double> e : map.entrySet()) {
			JLabel label = new JLabel(e.getKey());
			JTextField field = new JTextField(e.getValue().toString());
			labels.add(label);
			fields.add(field);
			this.add(label);
			this.add(field);
		}
	}

	public Map<String, Double> getParameters() throws NumberFormatException {
		Map<String, Double> values = new HashMap<String, Double>();
		for (int i = 0; i < labels.size(); i++) {
			String key = labels.get(i).getText();
			Double value = Double.parseDouble(fields.get(i).getText());
			values.put(key, value);
		}

		return values;
	}

}
