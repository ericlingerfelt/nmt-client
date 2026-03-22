package org.nuclearmasses.gui.reg;

import info.clearthought.layout.TableLayout;
import info.clearthought.layout.TableLayoutConstants;
import java.awt.Container;
import java.awt.event.*;
import javax.swing.*;
import org.nuclearmasses.enums.*;
import org.nuclearmasses.gui.StateAccessor;
import org.nuclearmasses.gui.format.*;
import org.nuclearmasses.datastructure.MainDataStructure;
import org.nuclearmasses.datastructure.RegDataStructure;
import org.nuclearmasses.gui.dialogs.*;
import org.nuclearmasses.io.CGICom;

/**
 * This class is the Registration form.
 */
public class RegFrame extends JFrame implements ActionListener, StateAccessor{

	/** The ds. */
	private RegDataStructure ds = new RegDataStructure();
	
	/** The mds. */
	private MainDataStructure mds;
	
	/** The cgi com. */
	private CGICom cgiCom;
	
	/** The reg button. */
	private JButton regButton;
	
	/** The research text area. */
	private JTextArea addressTextArea, hearTextArea, infoTextArea, researchTextArea;
	
	/** The institution field. */
	private JTextField firstNameField, lastNameField, emailField, institutionField;
	
	/** The country combo box. */
	private SizedComboBox countryComboBox;
	
	/**
	 * Class constructor.
	 *
	 * @param mds 	a reference to the {@link MainDataStructure} class
	 * @param cgiCom a reference to the {@link CGICom} class
	 */
	public RegFrame(MainDataStructure mds, CGICom cgiCom){
		
		this.mds = mds;
		this.cgiCom = cgiCom;
		
		setTitle("Registration");
		setSize(630, 770);
		
		Container c = getContentPane();
		
		double gap = 20;
		double[] col = {gap, TableLayoutConstants.PREFERRED
							, gap, TableLayoutConstants.FILL
							, gap};
		double[] row = {gap
						, TableLayoutConstants.PREFERRED, gap
						, TableLayoutConstants.PREFERRED, gap
						, TableLayoutConstants.PREFERRED, gap
						, TableLayoutConstants.PREFERRED, gap
						, TableLayoutConstants.PREFERRED, gap
						, TableLayoutConstants.PREFERRED, gap
						, TableLayoutConstants.FILL, gap
						, TableLayoutConstants.FILL, gap
						, TableLayoutConstants.FILL, gap
						, TableLayoutConstants.FILL, gap
						, TableLayoutConstants.PREFERRED, gap
						, TableLayoutConstants.PREFERRED, gap};
		
		c.setLayout(new TableLayout(col, row));
		
		firstNameField = new JTextField();
		lastNameField = new JTextField();
		emailField = new JTextField();
		institutionField = new JTextField();
		
		countryComboBox = new SizedComboBox();
		countryComboBox.setFont(Fonts.textFont);
		for(String string: countryArray){
			countryComboBox.addItem(string);
		}
		countryComboBox.setSelectedIndex(0);
		countryComboBox.setPopupWidthToLongest();
		
		addressTextArea = new JTextArea();
		addressTextArea.setLineWrap(true);
		addressTextArea.setWrapStyleWord(true);
		addressTextArea.setFont(Fonts.textFont);
		JScrollPane sp = new JScrollPane(addressTextArea);

		hearTextArea = new JTextArea();
		hearTextArea.setLineWrap(true);
		hearTextArea.setWrapStyleWord(true);
		hearTextArea.setFont(Fonts.textFont);
		JScrollPane sp2 = new JScrollPane(hearTextArea);
		
		researchTextArea = new JTextArea();
		researchTextArea.setLineWrap(true);
		researchTextArea.setWrapStyleWord(true);
		researchTextArea.setFont(Fonts.textFont);
		JScrollPane sp3 = new JScrollPane(researchTextArea);
		
		infoTextArea = new JTextArea();
		infoTextArea.setLineWrap(true);
		infoTextArea.setWrapStyleWord(true);
		infoTextArea.setFont(Fonts.textFont);
		JScrollPane sp4 = new JScrollPane(infoTextArea);
		
		WordWrapLabel topLabel = new WordWrapLabel(true);
		topLabel.setText("<html>Please fill out the information below and click <i>REGISTER ME</i> to register. Required fields are labeled with an asterisk (*).</html>");
		
		WordWrapLabel noticeLabel = new WordWrapLabel(false);
		noticeLabel.setText("<html>Notice to Users: "
								+ " Use of this system constitutes consent to "
								+ "security monitoring and testing. "
								+ "All activity is logged with your host name "
								+ "and IP address. The responses to these "
								+ "questions will be archived on our system. "
								+ "Send all questions to "
								+ "coordinator@nuclearmasses.org</html>");
		
		JLabel firstNameLabel = new JLabel("First Name* : ");
		firstNameLabel.setFont(Fonts.textFont);
		
		JLabel lastNameLabel = new JLabel("Last Name* : ");
		lastNameLabel.setFont(Fonts.textFont);
		
		JLabel emailLabel = new JLabel("Email Address* : ");
		emailLabel.setFont(Fonts.textFont);
		
		JLabel institutionLabel = new JLabel("Institution* : ");
		institutionLabel.setFont(Fonts.textFont);
		
		JLabel addressLabel = new JLabel("Mailing Address* : ");
		addressLabel.setFont(Fonts.textFont);
		
		JLabel countryLabel = new JLabel("Country* : ");
		countryLabel.setFont(Fonts.textFont);
		
		JLabel descriptionLabel = new JLabel("<html>Description of research<p>requiring full access* : </html>");
		descriptionLabel.setFont(Fonts.textFont);
		
		JLabel hearOfSuiteLabel = new JLabel("<html>Where did you<p>hear of this suite? : </html>");
		hearOfSuiteLabel.setFont(Fonts.textFont);
		
		JLabel notesLabel = new JLabel("<html>Additional Information<p>(supervisor/research mentor) : </html>");
		notesLabel.setFont(Fonts.textFont);
		
		regButton = new JButton("REGISTER ME");
		regButton.setFont(Fonts.buttonFont);
		regButton.addActionListener(this);
		
		add(topLabel, "1, 1, 3, 1, c, c");
		add(firstNameLabel, "1, 3, r, c");
		add(firstNameField, "3, 3, f, c");
		add(lastNameLabel, "1, 5, r, c");
		add(lastNameField, "3, 5, f, c");
		add(emailLabel, "1, 7, r, c");
		add(emailField, "3, 7, f, c");
		add(institutionLabel, "1, 9, r, c");
		add(institutionField, "3, 9, f, c");
		add(countryLabel, "1, 11, r, c");
		add(countryComboBox, "3, 11, f, c");
		add(addressLabel, "1, 13, r, c");
		add(sp, "3, 13, f, f");
		add(descriptionLabel, "1, 15, r, c");
		add(sp3, "3, 15, f, f");
		add(hearOfSuiteLabel, "1, 17, r, c");
		add(sp2, "3, 17, f, f");
		add(notesLabel, "1, 19, r, c");
		add(sp4, "3, 19, f, f");
		add(noticeLabel, "1, 21, 3, 21, c, c");
		add(regButton, "1, 23, 3, 23, c, c");
	}
	
	/**
	 * Gets the reference to the {@link RegDataStructure}.
	 * 
	 * @return	the reference to the RegDataStructure
	 */
	public RegDataStructure getDataStructure(){return ds;}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==regButton){
			if(goodData()){
				getCurrentState();
				if(cgiCom.doCGICall(mds, ds, CGIAction.REGISTER_USER, this)){
					String string = "Your information has been sent to "
										+ "bigbangonline.org. You will be emailed a "
		   								+ "username and password usually within 24 "
		   								+ "hours. Thank you.";
					GeneralDialog dialog = new GeneralDialog(this, string, "Registration Successful!");
					dialog.setVisible(true);
				}
			}else{
				String string = "One or more required registration fields are empty. Please fill in all required fields.";
				GeneralDialog dialog = new GeneralDialog(this, string, "Attention!");
				dialog.setVisible(true);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#setCurrentState()
	 */
	public void setCurrentState(){}
	
	/* (non-Javadoc)
	 * @see org.nuclearmasses.gui.StateAccessor#getCurrentState()
	 */
	public void getCurrentState(){
		ds.setFirstName(firstNameField.getText());
    	ds.setLastName(lastNameField.getText());
    	ds.setEmail(emailField.getText());
    	ds.setInstitution(institutionField.getText());
    	ds.setAddress(addressTextArea.getText());
    	ds.setHearOfSuite(hearTextArea.getText());
    	ds.setInfo(infoTextArea.getText());
    	ds.setCountry(countryComboBox.getSelectedItem().toString());
    	ds.setResearch(researchTextArea.getText());
	}
	
	/**
	 * Good data.
	 *
	 * @return true, if successful
	 */
	private boolean goodData(){
		if(firstNameField.getText().trim().equals("")
		    	|| lastNameField.getText().trim().equals("")
		    	|| emailField.getText().trim().equals("")
		    	|| institutionField.getText().trim().equals("")
		    	|| addressTextArea.getText().trim().equals("")
		    	|| researchTextArea.getText().trim().equals("")){
			return false;
		}
		return true;
	}
	
	/** The country array. */
	private String[] countryArray = {"United States of America",
			"Afghanistan",
			"Åland Islands",
			"Albania",
			"Algeria",
			"American Samoa",
			"Andorra",
			"Angola",
			"Anguilla",
			"Antigua and Barbuda",
			"Argentina",
			"Armenia",
			"Aruba",
			"Australia",
			"Austria",
			"Azerbaijan",
			"Bahamas",
			"Bahrain",
			"Bangladesh",
			"Barbados",
			"Belarus",
			"Belgium",
			"Belize",
			"Benin",
			"Bermuda",
			"Bhutan",
			"Bolivia",
			"Bosnia and Herzegovina",
			"Botswana",
			"Brazil",
			"British Virgin Islands",
			"Brunei Darussalam",
			"Bulgaria",
			"Burkina Faso",
			"Burundi",
			"Cambodia",
			"Cameroon",
			"Canada",
			"Cape Verde",
			"Cayman Islands",
			"Central African Republic",
			"Chad",
			"Channel Islands",
			"Chile",
			"China",
			"Hong Kong Special Administrative Region of China",
			"Macao Special Administrative Region of China",
			"Colombia",
			"Comoros",
			"Congo",
			"Cook Islands",
			"Costa Rica",
			"Côte d'Ivoire",
			"Croatia",
			"Cuba",
			"Cyprus",
			"Czech Republic",
			"Democratic People's Republic of Korea",
			"Democratic Republic of the Congo",
			"Denmark",
			"Djibouti",
			"Dominica",
			"Dominican Republic",
			"Ecuador",
			"Egypt",
			"El Salvador",
			"Equatorial Guinea",
			"Eritrea",
			"Estonia",
			"Ethiopia",
			"Faeroe Islands",
			"Falkland Islands (Malvinas)",
			"Fiji",
			"Finland",
			"France",
			"French Guiana",
			"French Polynesia",
			"Gabon",
			"Gambia",
			"Georgia",
			"Germany",
			"Ghana",
			"Gibraltar",
			"Greece",
			"Greenland",
			"Grenada",
			"Guadeloupe",
			"Guam",
			"Guatemala",
			"Guernsey",
			"Guinea",
			"Guinea-Bissau",
			"Guyana",
			"Haiti",
			"Holy See",
			"Honduras",
			"Hungary",
			"Iceland",
			"India",
			"Indonesia",
			"Iran, Islamic Republic of",
			"Iraq",
			"Ireland",
			"Isle of Man",
			"Israel",
			"Italy",
			"Jamaica",
			"Japan",
			"Jersey",
			"Jordan",
			"Kazakhstan",
			"Kenya",
			"Kiribati",
			"Kuwait",
			"Kyrgyzstan",
			"Lao People's Democratic Republic",
			"Latvia",
			"Lebanon",
			"Lesotho",
			"Liberia",
			"Libyan Arab Jamahiriya",
			"Liechtenstein",
			"Lithuania",
			"Luxembourg",
			"Madagascar",
			"Malawi",
			"Malaysia",
			"Maldives",
			"Mali",
			"Malta",
			"Marshall Islands",
			"Martinique",
			"Mauritania",
			"Mauritius",
			"Mayotte",
			"Mexico",
			"Micronesia, Federated States of",
			"Monaco",
			"Mongolia",
			"Montenegro",
			"Montserrat",
			"Morocco",
			"Mozambique",
			"Myanmar",
			"Namibia",
			"Nauru",
			"Nepal",
			"Netherlands",
			"Netherlands Antilles",
			"New Caledonia",
			"New Zealand",
			"Nicaragua",
			"Niger",
			"Nigeria",
			"Niue",
			"Norfolk Island",
			"Northern Mariana Islands",
			"Norway",
			"Occupied Palestinian Territory",
			"Oman",
			"Pakistan",
			"Palau",
			"Panama",
			"Papua New Guinea",
			"Paraguay",
			"Peru",
			"Philippines",
			"Pitcairn",
			"Poland",
			"Portugal",
			"Puerto Rico",
			"Qatar",
			"Republic of Korea",
			"Republic of Moldova",
			"Réunion",
			"Romania",
			"Russian Federation",
			"Rwanda",
			"Saint Helena",
			"Saint Kitts and Nevis",
			"Saint Lucia",
			"Saint Pierre and Miquelon",
			"Saint Vincent and the Grenadines",
			"Samoa",
			"San Marino",
			"Sao Tome and Principe",
			"Saudi Arabia",
			"Senegal",
			"Serbia",
			"Seychelles",
			"Sierra Leone",
			"Singapore",
			"Slovakia",
			"Slovenia",
			"Solomon Islands",
			"Somalia",
			"South Africa",
			"Spain",
			"Sri Lanka",
			"Sudan",
			"Suriname",
			"Svalbard and Jan Mayen Islands",
			"Swaziland",
			"Sweden",
			"Switzerland",
			"Syrian Arab Republic",
			"Tajikistan",
			"Thailand",
			"The former Yugoslav Republic of Macedonia",
			"Timor-Leste",
			"Togo",
			"Tokelau",
			"Tonga",
			"Trinidad and Tobago",
			"Tunisia",
			"Turkey",
			"Turkmenistan",
			"Turks and Caicos Islands",
			"Tuvalu",
			"Uganda",
			"Ukraine",
			"United Arab Emirates",
			"United Kingdom of Great Britain and Northern Ireland",
			"United Republic of Tanzania",
			"United States Virgin Islands",
			"Uruguay",
			"Uzbekistan",
			"Vanuatu",
			"Venezuela (Bolivarian Republic of)",
			"Viet Nam",
			"Wallis and Futuna Islands",
			"Western Sahara",
			"Willie Nelson",
			"Yemen",
			"Zambia",
			"Zimbabwe"};
	
}
