package sandbox;

import java.awt.*;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Copy_save1 {

	// public int[] blocks =
	// {10,10,10,20,20,20,30,20,30,100,100,50,100,100,60,80,20,80,30,30,30,30,30,30,80,10,3,20,30,30,4,7,10,10,10,20,10,1,1,1,10,1024,1,1,1,1,1,50,10,1024,1,20,10,40,8,8,11,7,10,3,10,1,10,1,11,1,100,20,1,1,10,1,1,1,1,1,30,30,1,10,1,10,1,10,1,10,1,10,1,10,1,10,10,10,1,10,1,1,1,1,1,30,30,1,10,1,10,5,5,1,1,10,2,1,1,1,1,1,1,1,10,27,1,1,80,25,25,25,25};

	private static void copySimple(String inLocation, String outLocation)
			throws IOException {
		BufferedReader in = null;
		BufferedWriter out = null;

		try {
			in = new BufferedReader(new FileReader(inLocation));
			out = new BufferedWriter(new FileWriter(outLocation));

			int data;
			while ((data = in.read()) != -1) {
				out.write(data);
			}
		} finally {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
		}
	}

	private static void copyBlocks(String inLocation, String outLocation,
			char[] dataset, int[] blocks) throws IOException {
		BufferedReader in = null;
		BufferedWriter out = null;
		String delimiter = " ";

		try {
			in = new BufferedReader(new FileReader(inLocation));
			out = new BufferedWriter(new FileWriter(outLocation));

			in.read(dataset);
			int numberOfBlocks = blocks.length;
			int charIndex = 0;
			for (int i = 0; i < numberOfBlocks; i++) {
				for (int j = 0; j < blocks[i]; j++, charIndex++) {
					out.write(dataset[charIndex]);
				}
				out.write(delimiter);
				// JAVA 1.7
				// System.lineSeparator();

			}

			// hard-coded same-size blocks
			// for (int i = 0; i < dataset.length; i++) {
			// if (i != 0 && i % 32 == 0) out.write(" ");
			// out.write(dataset[i]);
			// }

		} finally {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
		}
	}

	// TODO generalize Textfield ?
	private static void inputToGUI(String inLocation, char[] dataset,
			int[] blocks, JTextField[] textfields) throws IOException {
		BufferedReader in = null;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		try {
			in = new BufferedReader(new FileReader(inLocation));

			in.read(dataset);
			int numberOfBlocks = blocks.length;
			int charIndex = 0;
			String text;
			for (int i = 0; i < numberOfBlocks; i++) {
				text = "";
				for (int j = 0; j < blocks[i]; j++, charIndex++) {
					text += (dataset[charIndex]);
				}
				textfields[i].setText(text);
				textfields[i].setSize(dim);
				// JAVA 1.7
				// System.lineSeparator();

			}

			// hard-coded same-size blocks
			// for (int i = 0; i < dataset.length; i++) {
			// if (i != 0 && i % 32 == 0) out.write(" ");
			// out.write(dataset[i]);
			// }

		} finally {
			if (in != null)
				in.close();
		}
	}
	
	protected static void outputFromGUI(String output, JTextField[] textfields)
			throws IOException {
		
		BufferedWriter out = new BufferedWriter(new FileWriter(output));
		
		try {
			for (int i = 0; i < textfields.length; i++) {
				out.write(textfields[i].getText());
			}
		} finally {
			if (out != null)
				out.close();
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		final int[] blocks = { 10, 10, 10, 20, 20, 20, 30, 20, 30, 100, 100,
				50, 100, 100, 60, 80, 20, 80, 30, 30, 30, 30, 30, 30, 80, 10,
				3, 20, 30, 30, 4, 7, 10, 10, 10, 20, 10, 1, 1, 1, 10, 1024, 1,
				1, 1, 1, 1, 50, 10, 1024, 1, 20, 10, 40, 8, 8, 1, 1, 7, 10, 3,
				10, 1, 10, 1, 11, 1, 100, 20, 1, 1, 10, 1, 1, 1, 1, 1, 30, 30,
				1, 10, 1, 10, 1, 10, 1, 10, 1, 10, 1, 10, 1, 10, 10, 10, 1, 10,
				1, 1, 1, 1, 1, 30, 30, 1, 10, 1, 10, 5, 5, 1, 1, 10, 2, 1, 1,
				1, 1, 1, 1, 1, 10, 27, 1, 1, 80, 25, 25, 25, 25 };
		// int[] blocks = { 10, 10, 10 };
		String delimiter = " ";
		final char[] dataset = new char[4195];

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel main = new JPanel(new BorderLayout());

		/* populating the info panel */

//		 JPanel panel = new JPanel(new FlowLayout());
//		JPanel panel = new JPanel(new GridLayout(130, 2));
//		 JPanel panel = new JPanel(new BorderLayout());
		
		JPanel panel = new JPanel(new GridBagLayout());
//		GridBagConstraints labelConstraint = new GridBagConstraints();
//		GridBagConstraints textfieldConstraint = new GridBagConstraints();
		

		JTextArea[] textAreas = new JTextArea[blocks.length];
		JButton[] buttons = new JButton[blocks.length];
		JLabel[] labels = new JLabel[blocks.length];
		// FIXME
		final JTextField[] textfields = new JTextField[blocks.length];

		for (int i = 0; i < blocks.length; i++) {
			// textAreas[i] = new JTextArea();
			// panel.add(textAreas[i]);
			// buttons[i] = new JButton("PUSH");
			// panel.add(buttons[i]);
			
			// without GridBagLayout
			labels[i] = new JLabel("(" + (i+1) + ")");
			panel.add(labels[i]);
			textfields[i] = new JTextField();
			textfields[i].setEditable(false);
			panel.add(textfields[i]);
			
//			// with GridBagLayout
//			labelConstraint.gridx = 0;
//			labelConstraint.gridy = i;
//			labelConstraint.gridwidth = 1;
////			labelConstraints[i].fill = GridBagConstraints.HORIZONTAL;;
//			labels[i] = new JLabel("(" + (i+1) + ")");
//			panel.add(labels[i], labelConstraint);
//			textfieldConstraint.gridx = 1;
//			textfieldConstraint.gridy = i;
//			textfieldConstraint.gridwidth = 9;
//			textfields[i] = new JTextField();
//			textfields[i].setEditable(false);
//			panel.add(textfields[i], textfieldConstraint);
			
		}

		/* creating the rest */

		JScrollPane panelScroll = new JScrollPane(panel);

		JButton readButton = new JButton("Read");

		readButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Copy_save1.inputToGUI("C:\\in.txt", dataset, blocks, textfields);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		JButton writeButton = new JButton("Write");

		writeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Copy_save1.outputFromGUI("C:\\out.txt", textfields);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(readButton);
		buttonPanel.add(writeButton);

		main.add(panelScroll, BorderLayout.CENTER);
		main.add(buttonPanel, BorderLayout.SOUTH);

		frame.getContentPane().add(main);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// Copy.copySimple("C:\\in.txt", "C:\\out.txt");
		// Copy.copyBlocks("C:\\in.txt", "C:\\out.txt", dataset, blocks);
		// Copy.copyBlocksToGUI("C:\\in.txt", dataset, blocks, textfields);

	}
}
