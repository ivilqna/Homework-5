package fmi.informatics.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SortOrder;

import fmi.informatics.comparators.AgeComparator;
import fmi.informatics.comparators.EgnComparator;
import fmi.informatics.comparators.HeightComparator;
import fmi.informatics.comparators.NameComparator;
import fmi.informatics.comparators.PersonComparator;
import fmi.informatics.comparators.WeightComparator;
import fmi.informatics.extending.Person;
import fmi.informatics.extending.Professor;
import fmi.informatics.extending.Student;

// ��������� ���� PersonDataGUI
public class PersonDataGUI {
	
	public static Person[] people;
	JTable table;
	PersonDataModel personDataModel;

	public static void main(String[] args) {
		getPeople();
		PersonDataGUI gui = new PersonDataGUI();
		gui.createAndShowGUI();
	}
	
	public static void getPeople() {
		people = new Person[8];
		
		for (int i = 0; i < 4; i++) {
			Person student = Student.StudentGenerator.make();
			people[i] = student;
		}
		
		for (int i = 4; i < 8; i++) {
			Person professor = Professor.ProfessorGenerator.make();
			people[i] = professor;
		}
	}
	
	public void createAndShowGUI() {
		JFrame frame = new JFrame("������� � ����� �� ����");
		frame.setSize(500, 500);
		
		JLabel label = new JLabel("������ � �����������", JLabel.CENTER);
		
		personDataModel = new PersonDataModel(people);
		table = new JTable(personDataModel);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		// �������� ����� �� ��������� �� ������ � Comparable interface
		JButton buttonSortAge = new JButton("�������� �� ������");

		// �������� ����� �� ���������
		JButton buttonSort = new JButton("��������");
		
		Container container = frame.getContentPane();
		container.setLayout(new BorderLayout());
		container.add(label, BorderLayout.NORTH);
		container.add(scrollPane, BorderLayout.CENTER);
		
		container.add(buttonSortAge, BorderLayout.BEFORE_FIRST_LINE);
		container.add(buttonSort, BorderLayout.SOUTH);
		
		// �������� listener ��� ������ �� ��������� �� ������
		buttonSortAge.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Arrays.sort(people);
				table.repaint();
			}
		});
		
		// �������� ������
		final JDialog sortDialog = new CustomDialog(getSortText(), this);
		
		// �������� listener ��� ������ �� ���������
		buttonSort.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sortDialog.pack();
				sortDialog.setVisible(true);
			}
		});
		
		frame.setVisible(true);
	}
	
	public void sortTable(int intValue, JTable table, Person[] people) {
		PersonComparator comparator = null;
		
		switch (intValue) {
			case 1: 
				comparator = new NameComparator(); 
				comparator.setSortOrder(SortOrder.DESCENDING);
				break;
			case 2: 
				comparator = new EgnComparator();
				comparator.setSortOrder(SortOrder.ASCENDING);
				break;
			case 3:
				comparator = new HeightComparator();
				comparator.setSortOrder(SortOrder.DESCENDING);
				break;
			case 4: 
				comparator = new WeightComparator();
				comparator.setSortOrder(SortOrder.ASCENDING);
				break;
			case 5:
				comparator = new AgeComparator();
				comparator.setSortOrder(SortOrder.DESCENDING);
				break;
		
		}

		if (comparator == null) { // ��� ���������� � null - �������� �� ������������
			Arrays.sort(people); // ���������� �� ������������ �� ������
		} else {
			Arrays.sort(people, comparator);
		}
		
		table.repaint();	
	}
	
	private static String getSortText() {
		return "����, �������� ������� �� ��������, �� ����� �� �� �������� �������: \n" +
			
				" 1 - ���(�������� ���) \n" +
				" 2 - ��� (�������� ���)\n" +
				" 3 - ��������(�������� ���) \n" +
				" 4 - ����� (�������� ���) \n" +
				" 5 - ������(�������� ���) \n";
				
	}
}
