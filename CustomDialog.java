package fmi.informatics.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/* ��� ��������� �� ����� �� �� ���� ���������� �� ����������� �� ������ �� ��� ������ �� �������
*	��������� �� ������
*	�������� �� ����� �� �����
*	�������� �� �������� ����, ������ ����������� �� ������ ���� �����
*	�������� �� �����, ����� ������ ������ �� ������� ����������� ������
*	�������� �� Custom Comparators
*/
public class CustomDialog extends JDialog implements ActionListener, PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	
	private String typedText;
	private JTextField textField;
	private JOptionPane optionPane;
	
	private String okLabel = "Ok";
	private String cancelLabel = "Cancel";

	private PersonDataGUI parentGui;

	// ��������� �� ������
	public CustomDialog(String text, PersonDataGUI parent) {
		setTitle("����� �� ���������");

		this.parentGui = parent;
		this.textField = new JTextField(2);

		// ��������� �� ����� � ����� � � ����������, ����� �� �� ������������
		Object[] array = {text, textField};
		Object[] options = {okLabel, cancelLabel};

		optionPane = new JOptionPane(array, JOptionPane.QUESTION_MESSAGE, 
				JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);

		// ������������� �� �������
		setContentPane(optionPane);
		
		// ���������� �� ����������� �� �������
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				// ������ �� ��������� �������� ���������, �� �� ������� ���������� �� JOptionPane
				optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
			}
		});

		// ��������� ��, �� ���������� ���� ������ �������� ������ �����
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				textField.requestFocusInWindow();
			}
		});

		// ������������ �� event handler �� ���������� ����
		textField.addActionListener(this);
		
		// ������������ �� event handler, ����� ������� ��� ������� �� ����������� �� �ptionPane
		optionPane.addPropertyChangeListener(this);
	} // end CustomDialog constructor

	//���� �����
	@SuppressWarnings("unused")
	private int Integer() {
	    int i = 0;
	    try {
	        String text = null;
			i = Integer.parseInt(text);
	    } catch (NumberFormatException e) {
	        i = 0;
	    }
	    return i;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String propertyName = evt.getPropertyName();

		// ��������, ���� ��� ������� � �ptionPane ����������
		if (isVisible() && (evt.getSource() == optionPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(propertyName) || 
						JOptionPane.INPUT_VALUE_PROPERTY.equals(propertyName))) {
			
			Object value = optionPane.getValue();

			if (value == JOptionPane.UNINITIALIZED_VALUE) {
				return;
			}

			/*
			 * �������� �� ���������� �� JOptionPane. 
			 * ��� �� ��������� ����, ������, ��� ������������ ������� ����� ����� ��������� ���,
			 * ���� �� �� �������� ������� �� �������.
			 */
			optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

			if (value.equals(okLabel)) {
				if (textField.getText() != null && !textField.getText().isEmpty()) {
					typedText = textField.getText();
					int intValue = Integer.parseInt(typedText);
					
					if (intValue >= 1 && intValue <= 5) {
						parentGui.sortTable(intValue, parentGui.table, PersonDataGUI.people);
						clearAndHide();
					} else {
						// ������� � ���������
						textField.selectAll();

						JOptionPane.showMessageDialog(CustomDialog.this,
								"���������, ����������: " + typedText + " �� � �������!", 
								"������", JOptionPane.ERROR_MESSAGE);

						typedText = null;
						textField.requestFocusInWindow();
					}
				} else {
					// ������, � ����� ������������ �� � ����� ��������
					JOptionPane.showMessageDialog(CustomDialog.this,
							"����������, ������ �� �������� ��������!", 
							"������", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				typedText = null;
				clearAndHide();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		optionPane.setValue(okLabel);

	}

	// ���� ����� �������� ������� � �� ������
	private void clearAndHide() {
		textField.setText(null);
		setVisible(false);
	}
}
