/* This is a running demo of our Senior Project program.
 * It is a handheld ticketing system designed for Metra Rail.
 */
import static java.lang.Math.abs; 
import java.util.*;
import java.util.Date;
import java.awt.Container;
import java.util.Calendar;
import java.text.*; 
import java.awt.event.*;
import java.awt.*;
import java.applet.*;
import javax.swing.*;
import javax.swing.JCheckBox.*;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.swing.event.*;
import java.applet.Applet;
import java.util.Timer;
import java.awt.print.*;
import java.awt.Graphics.*;
import java.awt.Graphics2D.*;
import javax.swing.JComponent;
import java.sql.*;       
    
public class jop 
    extends JApplet
    implements Printable,
    		   ActionListener,
    		   ItemListener
{
/************************Varibles****************************/
/************************************************************/ 
 	static Connection Database;
static String query = "SELECT * FROM Popeye";
	static Statement stmt;
	static ResultSet rs;
	static Statement DataRequest;
	
	static int alpha;
	static Timer timer= new Timer();
	static int pop;
	static int pop2;
	static int poppy;
	static int realDecision=0;
	static JLabel mainLabel,comboLabel,	discountLabel,
		 	      paymentLabel,	totalLabel;
	static JPanel datetimePanel,	mainPanel,comboPanel,	discountPanel,
				  paymentPanel,totalPanel;
	static JComboBox 		starting =null;
	static JComboBox 		ending =null;
	static JRadioButton adult,senior,student,child,other;
	static ButtonGroup discount;
	static JRadioButton cash,check,other2;
	static ButtonGroup payment;
	static JFormattedTextField		total=null;
	static JButton			payButton;
	static JFormattedTextField 			paymentField1;
	static JCheckBox child2;
	static Label dateText;
	static JSpinner kids;
    static String DATE_TIME;
	static String childStg = "Child";
	static String adultStg = "Adult";
	static String seniorStg = "Senior";
    static String studentStg = "Student";
    static String otherstg = "Other"; 
	static String checkStg = "Check";
    static String cashStg = "Cash";
    static String child2stg = "w/ child";
    static String kidsstg = "How many?";
    static String BUTTONPANEL = "Main";
    static String TEXTPANEL = "Notes";
    static String TEXTPANEL1 = "Running Balance";
   	static String blah=null;
   	static String displayTotal;
    protected JCheckBox b1,	b2,b3,b4,label3;
    protected JTextField textField;
    protected JTextArea textArea;									
    static String discountS = null;
    static String payT = null;
    static JSlider combo = new JSlider(JSlider.VERTICAL,0, 30, 0);
    static JSlider combo2 = new JSlider(JSlider.VERTICAL,0, 30, 0);
 // stops can become databased
 static final String[] stops ={"Union Station Chicago","Western Avenue","Healy",
							   "Grayland","Mayfair","Forest Glen",
							   "Edgebrook","Morton Grove","Golf","Glenview",
							   "North Glenview","Northbrook","Lake Cook Rd.",
							   "Deerfield","Lake Forest","Libertyville",
							   "Prairie Crossing","Grayslake","Round Lake",
							   "Long Lake","Ingleside","Fox Lake"
							  };

    /**********************Start of panels************************/
    /*************************************************************/
public void init(){
	//init is a method JApplet looks for to run. our init runs 
	//the GUI method.
	//jdbc_test();
	GUI();
	//pprint();
}

public static void printSet() {
	//This pprint() method sets up the paper width and height to 
	//2x3 inches (our Cameo2 printer's paper size). The setImageableArea 
	//as will as most other methods shows in 1/72nds of inches where
	//applicable. The method also calls for a new printer job
	
            PrinterJob pjob = PrinterJob.getPrinterJob();
            PageFormat pf = pjob.defaultPage();
             Paper paper = new Paper();
        paper.setSize(144,216);
        paper.setImageableArea(1, 1, 144, 216);
       	pf.setPaper(paper);
        
        
            pjob.setPrintable(new jop(), pf);
            try {
                pjob.print();
            } catch (PrinterException e) {
            }
        }   
        private Object makeObj(final String item)  {
     return new Object() { public String toString() { return item; } };
   }     
public int print(Graphics g, PageFormat pf, int pageIndex) {
      //this print method calls to the printer and printsa receipt
      // for the patron displaying (in future) date, time 
      //customer number, bording and deparing stops, customer type
      //price and etc.
            if (pageIndex > 0) {
                return Printable.NO_SUCH_PAGE;
            }
            Graphics2D g2d = (Graphics2D)g;
            g2d.translate(pf.getImageableX(), pf.getImageableY());
            //g2d.drawString("hi hi TOTI",3,9);
            //g2d.drawLine(0,0,20,20);
            //g2d.fillOval(10,10,20,20);
            g2d.drawString(stops[pop],10,12);
            g2d.drawString(stops[pop2],10,24);
            g2d.drawString("________________",10,34);
            g2d.drawString(discountS,10,49);
            g2d.drawString(payT,90,60);
            g2d.drawString(displayTotal, 90,60+10);
            
            Font font = new Font("Arial", Font.PLAIN, 9);
    		
    		g2d.setFont(font);
    		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//			g2d.drawString("Hello World", 25, 100);
            
            g2d.drawString("This ticket is good for the above ",10,80+70);
            g2d.drawString ("bording and departing stops on",10,92+70);
            g2d.drawString("the date given",10,104+70);
            System.out.print("POP IS "+pop+"\nPOP2 IS "+pop2);
            //System.out.print("Stop is "+stops[pop]);
            return Printable.PAGE_EXISTS;
        }
     
public void paint(Graphics g){
      
    // refreshing the clock
         try{Thread.sleep(1000);} 
            catch (InterruptedException e){            
            }
      
         DATE_TIME = (new Date().toString());
         dateText.setText(DATE_TIME);
         repaint();
      }
      
public void GUI() {
	//This section creates four different sections for the GUI
	//and adds them to container c
             Container c = getContentPane();
             c.setLayout(new GridLayout(0,1) );
//        //Create Subpanels
        datetimePanel = new JPanel(new GridLayout(0,1));
        comboPanel = new JPanel(new GridLayout(0,1));
		discountPanel = new JPanel(new GridLayout(0,1));
		paymentPanel = new JPanel(new GridLayout(0,1));
		totalPanel = new JPanel(new GridLayout(0,1));
		
	c.add(datetimePanel);	
	c.add(comboPanel);					
	c.add(discountPanel);
	c.add(paymentPanel);
	c.add(totalPanel);

    /******************First panel datetime********************/
    /*************************************************************/
	//This section adds a date time function as the new first panel
	//this will add the day of the week, month, year and time refreshed
	//along with the time zone displayed
		
	DATE_TIME = (new Date().toString());
    dateText = new Label(DATE_TIME);
         
	datetimePanel.add(dateText);
	//dateText.setPreferredSize(new Dimension(1,1));
	//datetimePanel.setPreferredSize(new Dimension(1,1));

	
	
	/******************Second panel destination********************/
    /*************************************************************/
	//This section adds two drop down boxes or JcomboBoxes to the
    //first panel of the user interface and puts the String[] of 
    //stops into both.
    
    starting = new JComboBox(stops);// for the starting place
	starting.setSelectedIndex(0);
	//starting.insertItemAt(makeObj("ORIGIN"),0);
	starting.addActionListener(this);
	comboPanel.add(starting);	
	
	ending = new JComboBox(stops);  // for the destination
	ending.setSelectedIndex(0);
	//ending.insertItemAt(makeObj("DESTINATION"),0);
	ending.addActionListener(this);
	comboPanel.add(ending);	
	//comboPanel.add(combo);	
	//comboPanel.add(combo2);	
	/*******************Third panel discounts********************/
    /*************************************************************/	
     //This section adds components to the second panel and
     //defaults to adult. It also registers Itemlisteners
     
     adult = new JRadioButton(adultStg);
    adult.setMnemonic(KeyEvent.VK_C);
    adult.setActionCommand(adultStg);
    adult.setSelected(true);
    adult.doClick(100);
    discountPanel.add(adult);
     senior = new JRadioButton(seniorStg);
    senior.setMnemonic(KeyEvent.VK_B);
    senior.setActionCommand(seniorStg);
    discountPanel.add(senior);
	 student = new JRadioButton(studentStg);
    student.setMnemonic(KeyEvent.VK_C);
    student.setActionCommand(studentStg);
    discountPanel.add(student);
     child = new JRadioButton(childStg);
    child.setMnemonic(KeyEvent.VK_C);
    child.setActionCommand(childStg);
    discountPanel.add(child);
     other = new JRadioButton(otherstg);
    other.setMnemonic(KeyEvent.VK_D);
    other.setActionCommand(otherstg);
    discountPanel.add(other);
     child2 = new JCheckBox(child2stg);
    child2.setMnemonic(KeyEvent.VK_E);
    child2.setActionCommand(child2stg);
    child2.setSelected(false);
    discountPanel.add(child2); 
     kids = new JSpinner(kidsstg);
    kids.setMnemonic(KeyEvent.VK_F);
    kids.setActionCommand(kidsstg);
    kids.setVisible(false);
    discountPanel.add(kids);
    kids.setValue(new Integer(100)); 
	
    //Group the radio buttons.
     discount = new ButtonGroup();
    discount.add(senior);
    discount.add(student);
    discount.add(adult);
    discount.add(other);
    discount.add(child);
    
  
    //Registers a listener for radio buttons
    senior.addItemListener(this);
    student.addItemListener(this);
    adult.addItemListener(this);
    other.addItemListener(this);
    child.addItemListener(this);
    child2.addItemListener(this);
    
	    
	/*******************third panel payment-types*****************/
    /*************************************************************/	
    //This section adds components to the third panel and
    //defaults to cash. It also registers Itemlisteners
      
      cash = new JRadioButton(cashStg);
    cash.setMnemonic(KeyEvent.VK_C);
    cash.setActionCommand(cashStg);
	cash.setSelected(true);
	cash.doClick(100);
	discountPanel.add(cash);
	 check = new JRadioButton(checkStg);
    check.setMnemonic(KeyEvent.VK_B);
    check.setActionCommand(checkStg);
   	discountPanel.add(check);
     other2 = new JRadioButton(otherstg);
    other2.setMnemonic(KeyEvent.VK_D);
    other2.setActionCommand(otherstg);
    discountPanel.add(other2);
    //Group the radio buttons.
     payment = new ButtonGroup();
    payment.add(cash);
	payment.add(check);
	payment.add(other2);
	
	check.addItemListener(this);
	cash.addItemListener(this);
	other2.addItemListener(this);
	
	/*******************fourth panel total owed*******************/
    /*************************************************************/
    //this section Adds the calculation/print button  and 
    //display (JFormattedTextField)to the fourth and final panel. 
    
	 total =new JFormattedTextField(null,"Please Set Fields");
    total.setColumns(10);
    total.setEditable(false);
    total.setBackground(Color.BLACK);
    total.setForeground(Color.RED);
	totalPanel.add(total);
	
	 payButton =new JButton("Get Payment");
	totalPanel.add(payButton);
	
	//listens for actions of button payButton
	payButton.addActionListener(this);	

           
    /*********adding colored boarders to the main page************/
    /*************************************************************/	   
        datetimePanel.setBorder
        (BorderFactory.createBevelBorder(BevelBorder.LOWERED,
	    Color.BLUE, Color.GREEN));
        comboPanel.setBorder
        (BorderFactory.createBevelBorder(BevelBorder.LOWERED,
	    Color.BLUE, Color.GREEN));
	    discountPanel.setBorder
	    (BorderFactory.createBevelBorder(BevelBorder.LOWERED,
	    Color.BLUE, Color.GREEN));
	    paymentPanel.setBorder
	    (BorderFactory.createBevelBorder(BevelBorder.LOWERED,
	    Color.BLUE, Color.GREEN));
	    totalPanel.setBorder
	    (BorderFactory.createBevelBorder(BevelBorder.LOWERED,
	    Color.BLUE, Color.GREEN));
        
	/*********setting the panels to the main page*****************/
    /*************************************************************/	   
      comboPanel.setLayout(new GridLayout(4,1));
	            comboLabel=new JLabel("Stops") ;
	        	comboPanel.add(comboLabel);
	            comboPanel.add(starting);
	            comboPanel.add(ending);
	  discountPanel.setLayout(new GridLayout(0,2));
	            discountLabel=new JLabel("Reductions") ;//add an adult button
	            discountPanel.add(discountLabel);
	         	discountPanel.add(adult); 
	         	discountPanel.add(senior);
	         	discountPanel.add(student);
	         	discountPanel.add(child);
	         	discountPanel.add(other);
	         	discountPanel.add(child2);
	         	discountPanel.add(kids);
	  paymentPanel.setLayout(new GridLayout(0,1));
	    	    paymentLabel=new JLabel("Payment") ;
	            paymentPanel.add(paymentLabel);
	            paymentPanel.add(cash);
	        	paymentPanel.add(check);
	        	paymentPanel.add(other2);
      totalPanel.setLayout(new GridLayout(0,1));
        		totalLabel=new JLabel("Total") ;
        		totalPanel.add(totalLabel);
        	  	totalPanel.add(total);
        	  	totalPanel.add(payButton);
        	  	
    }
       /** Listens to the combo box and button ***/
public void actionPerformed(ActionEvent e) {
  	
  		 if (e.getSource() == payButton)
    	{
    	pop=starting.getSelectedIndex();
    	pop2=ending.getSelectedIndex();
    	 if(pop != pop2){ //stop can't equal itself
    	poppy = decision();
    	displayTotal=determine_value(poppy);
    	displayDOS();
      	total.setValue(displayTotal);
    	jdbc_test();
    	printSet();
    	reset();
    	}
    	else 
    	total.setValue("Stations can't be the same");
    	 
    	}
     }
public void itemStateChanged(ItemEvent e) {
       
        if (senior.isSelected() || student.isSelected() || child.isSelected()||
        other.isSelected())
        
             {
             	
       	           	realDecision =decision()+11;
       	           	if(senior.isSelected() ){
       	           		discountS = "Senior";
       	           	}
       	           	else if(student.isSelected() ){
       	           		discountS = "Student";
       	           		}
       	           	else if(child.isSelected() ){
       	           		discountS = "Child";
       	           		}
       	           	else {discountS = "Other";
       	           	}
          	}
          	
        else if (adult.isSelected()){
        	realDecision =decision();
        	discountS = "Adult";
			}

		if(cash.isSelected()|| check.isSelected() || other2.isSelected())
		{
       		if(cash.isSelected() ){
       	           	payT = "Cash";
       	           	}
       	           		
       	 	else if(check.isSelected() ){
       	           		payT = "Check";
       	           		}
       	    else {payT = "Other";}
       	}
    }  
    
public void reset(){
	starting.setSelectedIndex(0);
	ending.setSelectedIndex(0);
	adult.setSelected(true);
	cash.setSelected(true );
}   
public void displayDOS(){
	  	System.out.println("----------------------------------"); 
    	System.out.println("selectedindex is "+pop);
    	System.out.println("Stop is "+stops[pop]);
    	System.out.println();
    	System.out.println("selectedindex is "+pop2);
    	System.out.println("Stop is "+stops[pop2]);
    	System.out.println();
    	System.out.println("Number of stops are "+ abs(pop-pop2));
    	System.out.println("Alpha1= "+conditional(pop));
    	System.out.println("Alpha2= "+conditional(pop2));
    	System.out.println();
    	System.out.println(displayTotal);
    	System.out.println("seniorStg= "+seniorStg);
//    	System.out.println("blah= "+blah);
    	System.out.println("realDecision= "+realDecision);
}

   public enum Alpha {A,B,C,D,E,F,G,H,I}
//   public int conditional2(int q){
//   	Alpha alpha =null;
//		switch (q) {
//            case 0:
//            case 1:
//               System.out.print( alpha.A);
//                break;
//            case 2:
//            case 3:
//            case 4:
//                System.out.print( alpha.B);
//                break;
//            case 5:
//            case 6:
//            case 7:    
//            	System.out.print( alpha.C);
//                break;
//            case 8:
//            case 9:
//            case 10:
//            	System.out.print( alpha.D);
//            	break;
//            case 11:
//            case 12:
//            case 13:
//            	System.out.print( alpha.E);
//            	break;
//            case 14:
//            	System.out.print( alpha.F);	
//            	break;
//            case 15:
//            case 16:
//            	System.out.print( alpha.G);	
//            	break;
//            case 17:
//            case 18:
//            	System.out.print( alpha.H);
//            	break;
//            case 19:
//            case 20:
//            case 21:
//            	System.out.print( alpha.I);	
//            	break;
//            default:
//                alpha = null;
//                break;
//                
//          return q;
//        }
//        }
public int conditional(int q){

				
		if (q>=0 && q<=1)
		    { alpha = 1; }	
		else if (q>=2 && q<=4)
			{ alpha = 2; }	
		else if (q>=5 && q<=7)
			{ alpha = 3; }	
		else if (q>=8 && q<=10)
			{ alpha = 4; }	
		else if (q>=11 && q<=13)
			{ alpha = 5; }	
		else if (q ==14)
			{ alpha = 6; }	
		else if (q>=15 && q<=16)
			{ alpha = 7; }	
		else if (q>=17 && q<=18)
			{ alpha = 8; }	
		else if (q>=19 && q<=21)
			{ alpha = 9; }	
					
		return alpha;
	}
public int decision(){        
    return abs((conditional(pop2))-(conditional(pop)));
    }
    
    //double x= Double.parseDouble(str);
public String determine_value(int j){
		 String price[]={"$1.95","$2.15","$3.45",
						"$3.60","$4.30","$4.70",
						"$5.15","$5.55","$6.00","$6.40",null,
						/*reduced fairs*/
						"$.95","$1.05","$1.50",
						"$1.70","$1.95","$2.15",
						"$2.35","$2.55","$2.75","$3.00"};
		
		return price[j];	 
		} 
public void groupSelect(){
}
 



public void jdbc_test()
       {
	try {
              Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	     Database = DriverManager.getConnection("jdbc:odbc:Ice","senior_project","devryun");
	     //Database = DriverManager.getConnection(url,"","devryun");
	    } 
              catch(java.lang.ClassNotFoundException e) 
             {
	     System.err.print("ClassNotFoundException: "); 
	     System.err.println(e.getMessage());
	    }

              catch(SQLException ex) 
             {
	    System.err.print("SQLException: ");
	    System.err.println(ex.getMessage());
	    }

int yup=70;
try{
	String query = "INSERT INTO Popeye VALUES('"+stops[pop]+"', '"+stops[pop2]+"','"+
	discountS+"', '"+payT+"', '"+displayTotal+"')";
	DataRequest = Database.createStatement();
	DataRequest.executeUpdate(query);
	DataRequest.close();

    }
catch (SQLException error){
	System.err.println("SQL error." +error);
}
	
	
	 } //end of public jdbc_test() 
//public void getValues()
//{
//	
//	
//	}

public String [] getValues()
      {
       String [] myresponse = null;
      
      try 
         {
         myresponse = new String[10];  // for now lets just keep 10 slots
	stmt = Database.createStatement();							
	rs = stmt.executeQuery(query);


         int i = 0;
         while(rs.next()) 
         {
            myresponse[i] = rs.getString(2);
            i++;           
          }
           } 
         catch( Exception e ) 
          {
            myresponse = null;
          }
         return myresponse;   
       }//end of getValues



       public void close() 
       {
	try 
            {
	    Database.close();
	   }
	catch ( Exception e ) {};
       }




  public void sql() 
        {
         		
        System.out.println("Here are the values:");
        jop connection = new jop();
       
        String [] values = connection.getValues();
	  for (int i = 0; i < values.length; i++) 
            {
	    System.out.println(values[i]);
	   }
        connection.close();
        }
        
public static void main(String[] args){

}

}