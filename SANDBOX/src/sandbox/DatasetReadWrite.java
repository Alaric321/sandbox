// TODO this only displays ONE dataset so far...
//      how do we display all of them?
//      table? tabbed panes + search field?
// TODO remove hard-coding of file locations
// TODO private static methods are bad
// TODO maybe refactor to classes
// TODO replace indexed element with currentX var

/* varianten zur aufnahme aller datasets 
 * 
 * A) combobox
 *    + einfachste loesung
 *    - unpraktisch
 * 1. array von datasets (pro session persistent), zugriff auf index des arrays anhand 
 *    auswahl in dropdown
 * 2. datasets fortlaufend in textfields (pro session persistent) einlesen, counter i laufen 
 *    lassen zur nummerierung in dropdown, (i-1)*rowPerDataset ist startindex zur anzeige in GUI 
 * 
 * B) table
 * 1. datasets fortlaufend einlesen und in tabelle(n) schreiben
 *    + pro session nichts persistent
 *    - tabellen muessen aufgeteilt werden oder horizontal muss sehr weit gescrollt werden
 *    ? prioritaet der datensaetze koennte anzeige reduzieren
 *    
 * C) suchfeld
 *    +- suche gezielt nach bestimmten infos
 *    - aktuell ohne Persistence ist das nur moeglich, wenn ein primary key pro dataset festegelegt
 *      wird, wie z.B. die kunden-id
 *    
 * natuerlich koennen A und C auch fuer B als auswahlkomponente benutzt werden      
 *    
 * in kombination mit obigen ansaetzen:
 * i) card pane (keine aufteilung der infos moeglich)
 * ii)tabbed pane (aufteilung der infos)
 *    
 *  */

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

public class DatasetReadWrite {


	private static final String INPUT_FILE = "C:\\in.txt";
	private static final String OUTPUT_FILE = "C:\\out.txt";
	private static final char[] DATASETS = new char[4195];
	private static final int[] BLOCKS = { 10, 10, 10, 20, 20, 20, 30, 20, 30, 100, 100,
			50, 100, 100, 60, 80, 20, 80, 30, 30, 30, 30, 30, 30, 80, 10,
			3, 20, 30, 30, 4, 7, 10, 10, 10, 20, 10, 1, 1, 1, 10, 1024, 1,
			1, 1, 1, 1, 50, 10, 1024, 1, 20, 10, 40, 8, 8, 1, 1, 7, 10, 3,
			10, 1, 10, 1, 11, 1, 100, 20, 1, 1, 10, 1, 1, 1, 1, 1, 30, 30,
			1, 10, 1, 10, 1, 10, 1, 10, 1, 10, 1, 10, 1, 10, 10, 10, 1, 10,
			1, 1, 1, 1, 1, 30, 30, 1, 10, 1, 10, 5, 5, 1, 1, 10, 2, 1, 1,
			1, 1, 1, 1, 1, 10, 27, 1, 1, 80, 25, 25, 25, 25 };
	private static int rows = BLOCKS.length;
	private static JTextField[] textfields = new JTextField[rows];

	// TODO generalize Textfield ?
	private static void inputToGUI() throws IOException {
		BufferedReader in = null;
		// Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		try {
			in = new BufferedReader(new FileReader(INPUT_FILE));
			in.read(DATASETS);
			int numberOfBlocks = BLOCKS.length;
			int charIndex = 0;
			String text;
			for (int i = 0; i < numberOfBlocks; i++) {
				text = "";
				for (int j = 0; j < BLOCKS[i]; j++, charIndex++) {
					text += (DATASETS[charIndex]);
				}
				textfields[i].setText(text);
				// textfields[i].setSize(dim);
			}
		} finally {
			if (in != null)
				in.close();
		}
	}

	protected static void outputFromGUI()
			throws IOException {

		BufferedWriter out = null;

		try {
			out = new BufferedWriter(new FileWriter(OUTPUT_FILE));
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

		// FIXME final
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel main = new JPanel(new BorderLayout());

		/* populating the info panel */

		// FIXME final
		final JPanel dataPane = new JPanel(new SpringLayout());

		// should be an array, because we might want to write the labels to file
		// at some point
		JLabel[] labels = new JLabel[rows];
		for (int i = 0; i < rows; i++) {
			// with Spring Form
			labels[i] = new JLabel("(" + (i + 1) + ")");
			dataPane.add(labels[i]);
			textfields[i] = new JTextField();
			labels[i].setLabelFor(textfields[i]);
			dataPane.add(textfields[i]);
		}

		/* lay out the panel with SpringLayout */
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
					DatasetReadWrite.inputToGUI();
//					frame.repaint();
					dataPane.repaint();
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
					DatasetReadWrite.outputFromGUI();
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
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}

}
