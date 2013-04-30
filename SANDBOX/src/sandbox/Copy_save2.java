// TODO this only displays ONE dataset so far...
//      how do we display all of them?
//      table? tabbed panes + search field?
// TODO frame setting is bad
// TODO change in display is messed up (refresh?)
// TODO change the classname
// TODO remove hard-coding of file locations
// TODO private static methods are bad
// TODO maybe refactor to classes
// TODO replace indexed element with currentX var
package sandbox;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class Copy_save2 {

	// public int[] blocks =
	// {10,10,10,20,20,20,30,20,30,100,100,50,100,100,60,80,20,80,30,30,30,30,30,30,80,10,3,20,30,30,4,7,10,10,10,20,10,1,1,1,10,1024,1,1,1,1,1,50,10,1024,1,20,10,40,8,8,11,7,10,3,10,1,10,1,11,1,100,20,1,1,10,1,1,1,1,1,30,30,1,10,1,10,1,10,1,10,1,10,1,10,1,10,10,10,1,10,1,1,1,1,1,30,30,1,10,1,10,5,5,1,1,10,2,1,1,1,1,1,1,1,10,27,1,1,80,25,25,25,25};

	// TODO generalize Textfield ?
	private static void inputToGUI(String input, char[] dataset, int[] blocks,
			JTextField[] textfields) throws IOException {
		BufferedReader in = null;
		// Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		try {
			in = new BufferedReader(new FileReader(input));
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
				// textfields[i].setSize(dim);
			}
		} finally {
			if (in != null)
				in.close();
		}
	}


	protected static void outputFromGUI(String output, JTextField[] textfields)
			throws IOException {

		BufferedWriter out = null;

		try {
			out = new BufferedWriter(new FileWriter(output));
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
		final char[] dataset = new char[4195];
		// TODO create file dialog
		final String inputFile = "C:\\in.txt";
		final String outputFile = "C:\\out.txt";
		final int rows = blocks.length;

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel main = new JPanel(new BorderLayout());

		/* populating the info panel */

		JPanel dataPane = new JPanel(new SpringLayout());

		// should be an array, because we might want to write the labels to file
		// at some point
		JLabel[] labels = new JLabel[rows];
		// FIXME
		// must be an array, because we definitely need to write them to file
		final JTextField[] textfields = new JTextField[rows];
		for (int i = 0; i < rows; i++) {
			// with Spring Form
			labels[i] = new JLabel("(" + (i + 1) + ")");
			dataPane.add(labels[i]);
			textfields[i] = new JTextField();
			labels[i].setLabelFor(textfields[i]);
			dataPane.add(textfields[i]);
		}

		/* lay out the panel with SpringLayout */
		// TODO only use this method, not the whole utilities class
		// (dependencies?)
		SpringUtilities.makeCompactGrid(dataPane, rows, 2, // rows, cols
				0, 0, // initX, initY
				1, 1); // xPad, yPad

		/* adding the panel to the scroll pane */
		JScrollPane scrollPane = new JScrollPane(dataPane);

		/* creating and setting the buttons */

		JButton readButton = new JButton("Read");
		readButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Copy_save2.inputToGUI(inputFile, dataset, blocks, textfields);
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
					Copy_save2.outputFromGUI(outputFile, textfields);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.add(readButton);
		buttonPane.add(writeButton);

		main.add(scrollPane, BorderLayout.CENTER);
		main.add(buttonPane, BorderLayout.SOUTH);

		frame.getContentPane().add(main);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
