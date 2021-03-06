package de.uni.leipzig.asv.zitationsgraph.tests;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import javax.swing.Action;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import prefuse.Display;
import prefuse.util.display.ExportDisplayAction;

import de.uni.leipzig.asv.zitationsgraph.tests.vis.PubVis;
import de.uni.leipzig.asv.zitationsgraph.db.DBLoader;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.*;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.<br>
* <br>
* control the interaction with the graph and holds the option to save the publication
* in the DB
*/
public class GraphToolbar extends javax.swing.JPanel {
	
	private JToggleButton jToggleButton1;
	private static final Logger log = Logger.getLogger(GraphToolbar.class.getName());
	
	/**
	 * export the graph as pic
	 */
	private JButton picSaveBt;
	
	/**
	 * prepare the db
	 */
	private JButton prepareDBbt;
	/**
	 * store the publication in the db
	 */
	private JButton exportDBbt;
	private JLabel jLabel2;
	
	/**
	 * component which hold the graph
	 */
	private ReferencePan refPan;
	
	/**
	 * option for graph visualizing
	 */
	private JCheckBox jGraphCheckBox;
	
	/**
	 * option for db saving 
	 */
	private JCheckBox jDBCheckBox;
	
	/**
	 * filter toolbar
	 */
	private JToolBar jToolBar1;
	private JLabel jLabel1;
	/**
	 * slider to set the maximal count of incoming edges
	 */
	private JSlider refSlider;
	
	private int minYear =1900;
	private int maxYear =2012;

	/**
	 * slider, which limit the time
	 */
	private JSlider yearSlider;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new GraphToolbar());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public GraphToolbar() {
		super();
		initGUI();
	}
	
	public GraphToolbar (ReferencePan refPan){
		super();
		this.refPan = refPan;
		initGUI();
		
	}
	
	private void initGUI() {
		try {
			BoxLayout thisLayout = new BoxLayout(this, javax.swing.BoxLayout.X_AXIS);
			this.setLayout(thisLayout);
			{
				jGraphCheckBox = new JCheckBox();
				jGraphCheckBox.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						refPan.setIsGraphVis(jGraphCheckBox.isSelected());
						
					}
					
				});
				this.jGraphCheckBox.setSelected(true);
				this.add(jGraphCheckBox);
				jGraphCheckBox.setText("graph vis");
			}
			{
				jDBCheckBox = new JCheckBox();
				jDBCheckBox.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent arg0) {
						refPan.storeInDb(jDBCheckBox.isSelected());
						prepareDBbt.setEnabled(jDBCheckBox.isSelected());
						exportDBbt.setEnabled(jDBCheckBox.isSelected());
					}
					
				});
				
				this.add(jDBCheckBox);
				jDBCheckBox.setText("store in DB");
			}
			//Button to refresh the DB (drop/create all tables)
			{
				prepareDBbt = new JButton();
				this.add(prepareDBbt);
				prepareDBbt.setEnabled(false);
				prepareDBbt.setText("prepare DB");
				
				prepareDBbt.addActionListener(new ActionListener(){

					@Override
					/**
					 * Method to clear all DB-content on click
					 */
					public void actionPerformed(ActionEvent e) {
						DBLoader dbLoader =new DBLoader();
						dbLoader.drop();
						dbLoader.create();
						dbLoader.closeConnection();
					}
					
				});
			}
			{
				exportDBbt = new JButton();
				this.add(exportDBbt);
				exportDBbt.setEnabled(false);
				exportDBbt.setText("export Graph(DB)");
				
				exportDBbt.addActionListener(new ActionListener(){

					@Override
					/**
					 * Method to export the db-Graph
					 */
					public void actionPerformed(ActionEvent e) {
						DBLoader dbLoader =new DBLoader();
						DirectedGraph<String, DefaultEdge> graph = dbLoader.createGraph();
						dbLoader.writeGraphToFile(graph);
						
						dbLoader.closeConnection();
					}
					
				});
			}
			{
				jToggleButton1 = new JToggleButton();
				this.add(jToggleButton1);
				jToggleButton1.setText("stop animation");
				jToggleButton1.addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						refPan.animateLayout(jToggleButton1.isSelected());
						
						String text = (jToggleButton1.isSelected())?"stop animation":"start Animation";
						log.info(text);
						jToggleButton1.setText(text);
					}
					
				});
			}
			{
				picSaveBt = new JButton();
				this.add(picSaveBt);
				Action a = refPan.getImageAction();
				a.putValue(Action.NAME, "save pic");
				picSaveBt.setAction(a);
			}
			{
				jToolBar1 = new JToolBar();
				this.add(jToolBar1);
				{
					jLabel1 = new JLabel();
					jToolBar1.add(jLabel1);
					jLabel1.setText("reference filter");
				}
				{
					refSlider = new JSlider();
					jToolBar1.add(refSlider);
					refSlider.setMinimum(1);
					
					refSlider.setMaximum(5);
					refSlider.setBounds(0, 0, 150, 20);
					refSlider.setPaintLabels(true);
					refSlider.setMajorTickSpacing(1);
					refSlider.setPaintTicks(true);
					refSlider.setValue(1);
					refSlider.addChangeListener(new ChangeListener(){
						
						@Override
						public void stateChanged(ChangeEvent e) {
							int numRef = refSlider.getValue();
							refPan.setNumOfRef(numRef);
							
						}
						
					});
					
					yearSlider = new JSlider (){
						
					};
					
					jToolBar1.add(yearSlider);
					yearSlider.setMinimum(minYear);
					yearSlider.setMaximum(maxYear);
				
					yearSlider.setPaintLabels(true);
					yearSlider.setMinorTickSpacing(10);
					yearSlider.setMajorTickSpacing(20);
					yearSlider.setPaintTicks(true);
					yearSlider.addChangeListener(new ChangeListener(){

						@Override
						public void stateChanged(ChangeEvent e) {
							int year = yearSlider.getValue();
							refPan.setYearBegin(year);
							
						}
						
					});

				}
				{
					jLabel2 = new JLabel();
					jToolBar1.add(jLabel2);
					jLabel2.setText("time filter");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setExtremes(int minYear2, int maxYear2) {
		yearSlider.setMinimum(minYear2);
		yearSlider.setMaximum(maxYear2);
		int dif = maxYear2 -minYear2;
		yearSlider.setPaintLabels(false);
		yearSlider.setLabelTable(null);
		if (dif >200){
			yearSlider.setMajorTickSpacing(50);
			yearSlider.setMinorTickSpacing(25);
		}else {
			yearSlider.setMajorTickSpacing(20);
			yearSlider.setMinorTickSpacing(10);
		}
		yearSlider.setPaintLabels(true);
		
		
	}

}
