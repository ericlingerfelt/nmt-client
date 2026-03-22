package org.nuclearmasses.datastructure;

import java.util.*;
import org.nuclearmasses.datastructure.DataStructure;

/**
 * This class is the superclass data structure for any data structure class 
 * used in conjunction with {@link org.nuclearmasses.io.CGICom}. This class contains 
 * fields corresponding to all required variables for each CGI call to the server.
 */
public class CGICallDataStructure implements DataStructure{

	/** The model map selected. */
	private HashMap<Integer, MassModelDataStructure> modelMap, modelMapSelected;
	
	/** The mass model data structure. */
	private MassModelDataStructure massModelDataStructure;
	
	/** The data. */
	private String modelIndices, overwrite, modelName, desc
					, ref, refNew, descNew, authorNew, author
					, field, modelIndex, type, firstName
					, lastName, email, institution, address
					, hearOfSuite, info, country, research, data;
	
	/** The ame index. */
	private int refIndex, ameIndex;
	
	/**
	 * Initializes all fields.
	 */
	public void initialize(){
		setModelMap(null);
		setType("");
		setModelIndex("");
		setModelIndices("");
		setModelMapSelected(null);
		setOverwrite("N");
		setModelName("");
		setMassModelDataStructure(null);
		setDesc("");
		setRef("");
		setAuthor("");
		setDescNew("");
		setRefNew("");
		setAuthorNew("");
		setData("");
		setField("");
		setRefIndex(-1);
		setAMEIndex(-1);
		setFirstName("");
		setLastName("");
		setEmail("");
		setInstitution("");
		setAddress("");
		setHearOfSuite("");
		setInfo("");
		setCountry("");
		setResearch("");
	}
	
	/**
	 * Gets the value for <i>Description of research requiring full access</i>
	 * entered in the registration form.
	 *
	 * @return 			the value for 
	 * entered in the registration form
	 */
	public String getResearch(){return research;}
	
	/**
	 * Sets the value for <i>Description of research requiring full access</i> as
	 * entered in the registration form.
	 * 
	 * @param	research 	the value for <i>Description of research requiring full access</i> 
	 * entered in the registration form
	 */
	public void setResearch(String research){this.research = research;}
	
	/**
	 * Gets the value for <i>Country</i> as entered in the registration form.
	 *
	 * @return 			the value for  as entered in the registration form
	 */
	public String getCountry(){return country;}
	
	/**
	 * Sets the value for <i>Country</i> as entered in the registration form.
	 * 
	 * @param	country		the value for <i>Country</i> as entered in the registration form
	 */
	public void setCountry(String country){this.country = country;}
	
	/**
	 * Gets the value for <i>Additional Information</i> as entered in the registration form.
	 *
	 * @return 			the value for  as entered in the registration form
	 */
	public String getInfo(){return info;}
	
	/**
	 * Sets the value for <i>Additional Information</i> as entered in the registration form.
	 * 
	 * @param	info		the value for <i>Additional Information</i> as entered in the registration form
	 */
	public void setInfo(String info){this.info = info;}
	
	/**
	 * Gets the value for <i>Where did you hear of this suite?</i> as entered in the registration form.
	 *
	 * @return 			the value for  as entered in the registration form
	 */
	public String getHearOfSuite(){return hearOfSuite;}
	
	/**
	 * Sets the value for <i>Where did you hear of this suite?</i> as entered in the registration form.
	 * 
	 * @param	hearOfSuite	the value for <i>Where did you hear of this suite?</i> as entered in the registration form
	 */
	public void setHearOfSuite(String hearOfSuite){this.hearOfSuite = hearOfSuite;}
	
	/**
	 * Gets the value for <i>Mailing Address</i> as entered in the registration form.
	 *
	 * @return 			the value for  as entered in the registration form
	 */
	public String getAddress(){return address;}
	
	/**
	 * Sets the value for <i>Mailing Address</i> as entered in the registration form.
	 * 
	 * @param	address		the value for <i>Mailing Address</i> as entered in the registration form
	 */
	public void setAddress(String address){this.address = address;}
	
	/**
	 * Gets the value for <i>Institution</i> as entered in the registration form.
	 *
	 * @return 			the value for  as entered in the registration form
	 */
	public String getInstitution(){return institution;}
	
	/**
	 * Sets the value for <i>Institution</i> as entered in the registration form.
	 * 
	 * @param	institution	the value for <i>Institution</i> as entered in the registration form
	 */
	public void setInstitution(String institution){this.institution = institution;}
	
	/**
	 * Gets the value for <i>Email Address</i> as entered in the registration form.
	 *
	 * @return 			the value for  as entered in the registration form
	 */
	public String getEmail(){return email;}
	
	/**
	 * Sets the value for <i>Email Address</i> as entered in the registration form.
	 * 
	 * @param	email		the value for <i>Email Address</i> as entered in the registration form
	 */
	public void setEmail(String email){this.email = email;}
	
	/**
	 * Gets the value for <i>First Name</i> as entered in the registration form.
	 *
	 * @return 			the value for  as entered in the registration form
	 */
	public String getFirstName(){return firstName;}
	
	/**
	 * Sets the value for <i>First Name</i> as entered in the registration form.
	 * 
	 * @param	firstName	the value for <i>First Name</i> as entered in the registration form
	 */
	public void setFirstName(String firstName){this.firstName = firstName;}
	
	/**
	 * Gets the value for <i>Last Name</i> as entered in the registration form.
	 *
	 * @return 			the value for  as entered in the registration form
	 */
	public String getLastName(){return lastName;}
	
	/**
	 * Sets the value for <i>Last Name</i> as entered in the registration form.
	 * 
	 * @param	lastName	the value for <i>Last Name</i> as entered in the registration form
	 */
	public void setLastName(String lastName){this.lastName = lastName;}
	
	/**
	 * Gets the String which contains the properly formatted data for upload to the server. 
	 * The String is of the form Z,N,MASS EXCESS [MeV],UNCERTAINTY <SPACE CHAR> repeat...
	 * 
	 * @return				the String which contains the properly formatted data for upload to the server
	 */
	public String getData(){return data;}
	
	/**
	 * Sets the String which contains the properly formatted data for upload to the server. 
	 * The String is of the form Z,N,MASS EXCESS [MeV],UNCERTAINTY <SPACE CHAR> repeat...
	 * 
	 * @param	data		the String which contains the properly formatted data for upload to the server
	 */
	public void setData(String data){this.data = data;}
	
	/**
	 * Gets the String which contains the description of a mass dataset as retrieved from the server.
	 * 
	 * @return 				the String which contains the description of a mass dataset as retrieved from the server
	 */
	public String getDesc(){return desc;}
	
	/**
	 * Sets the String which contains the description of a mass dataset as retrieved from the server.
	 * 
	 * @param	desc		the String which contains the description of a mass dataset as retrieved from the server
	 */
	public void setDesc(String desc){this.desc = desc;}
	
	/**
	 * Gets the String which contains the reference of a mass dataset as retrieved from the server.
	 * 
	 * @return 				the String which contains the reference of a mass dataset as retrieved from the server
	 */
	public String getRef(){return ref;}
	
	/**
	 * Sets the String which contains the reference of a mass dataset as retrieved from the server.
	 * 
	 * @param	ref		the String which contains the reference of a mass dataset as retrieved from the server
	 */
	public void setRef(String ref){this.ref = ref;}
	
	/**
	 * Gets the String which contains the author of a mass dataset as retrieved from the server.
	 * 
	 * @return 				the String which contains the author of a mass dataset as retrieved from the server
	 */
	public String getAuthor(){return author;}
	
	/**
	 * Sets the String which contains the author of a mass dataset as retrieved from the server.
	 * 
	 * @param	author		the String which contains the author of a mass dataset as retrieved from the server
	 */
	public void setAuthor(String author){this.author = author;}
	
	/**
	 * Gets the String which contains the description of a mass dataset as retrieved from the server
	 * concatenated with the description entered by the User. 
	 * 
	 * @return 				the String which contains the description of a mass dataset as retrieved from the server
	 * concatenated with the description entered by the User
	 */
	public String getDescNew(){return descNew;}
	
	/**
	 * Sets the String which contains the description of a mass dataset as retrieved from the server
	 * concatenated with the description entered by the User. 
	 * 
	 * @param	descNew		the String which contains the description of a mass dataset as retrieved from the server
	 * concatenated with the description entered by the User
	 */
	public void setDescNew(String descNew){this.descNew = descNew;}
	
	/**
	 * Gets the String which contains the reference of a mass dataset as retrieved from the server
	 * concatenated with the reference entered by the User. 
	 * 
	 * @return 				the String which contains the reference of a mass dataset as retrieved from the server
	 * concatenated with the reference entered by the User
	 */
	public String getRefNew(){return refNew;}
	
	/**
	 * Sets the String which contains the reference of a mass dataset as retrieved from the server
	 * concatenated with the reference entered by the User. 
	 * 
	 * @param	refNew		the String which contains the reference of a mass dataset as retrieved from the server
	 * concatenated with the reference entered by the User
	 */
	public void setRefNew(String refNew){this.refNew = refNew;}
	
	/**
	 * Gets the String which contains the author of a mass dataset as retrieved from the server
	 * concatenated with the author entered by the User. 
	 * 
	 * @return 				the String which contains the author of a mass dataset as retrieved from the server
	 * concatenated with the author entered by the User
	 */
	public String getAuthorNew(){return authorNew;}
	
	/**
	 * Sets the String which contains the author of a mass dataset as retrieved from the server
	 * concatenated with the author entered by the User. 
	 * 
	 * @param	authorNew		the String which contains the author of a mass dataset as retrieved from the server
	 * concatenated with the author entered by the User
	 */
	public void setAuthorNew(String authorNew){this.authorNew = authorNew;}
	
	/**
	 * Gets the String representation of the Enum {@link org.nuclearmasses.enums.MassModelField}.
	 * <p>
	 * The value can be either EXPERIMENTAL or THEORY.
	 * 
	 * @return					the String representation of the Enum MassModelField.
	 */
	public String getField(){return field;}
	
	/**
	 * Sets the String representation of the Enum {@link org.nuclearmasses.enums.MassModelField}.
	 * <p>
	 * The value can be either EXPERIMENTAL or THEORY.
	 * 
	 * @param	field			the String representation of the Enum MassModelField.
	 */
	public void setField(String field){this.field = field;}
	
	/**
	 * Gets the {@link MassModelDataStructure} being currently used.
	 * 
	 * @return					the MassModelDataStructure being currently used
	 */
	public MassModelDataStructure getMassModelDataStructure(){return massModelDataStructure;}
	
	/**
	 * Sets the {@link MassModelDataStructure} being currently used.
	 * 
	 * @param	massModelDataStructure	the MassModelDataStructure being currently used
	 */
	public void setMassModelDataStructure(MassModelDataStructure massModelDataStructure){this.massModelDataStructure = massModelDataStructure;}
	
	/**
	 * Gets the String allowing a mass dataset to be overwritten on the server. 
	 * The value can be either Y or N.
	 * 
	 * @return					the String allowing a mass dataset to be overwritten on the server
	 */
	public String getOverwrite(){return overwrite;}
	
	/**
	 * Sets the String allowing a mass dataset to be overwritten on the server. 
	 * The value can be either Y or N.
	 * 
	 * @param	overwrite 		the String allowing a mass dataset to be overwritten on the server
	 */
	public void setOverwrite(String overwrite){this.overwrite = overwrite;}
	
	/**
	 * Gets a comma separated list of mass dataset indices. 
	 * Each index uniquely matches a mass dataset on the server.
	 * 
	 * @return					a comma separated list of mass dataset indices	
	 */
	public String getModelIndices(){return modelIndices;}
	
	/**
	 * Sets a comma separated list of mass dataset indices. 
	 * Each index uniquely matches a mass dataset on the server.
	 * 
	 * @param	modelIndices	a comma separated list of mass dataset indices	
	 */
	public void setModelIndices(String modelIndices){this.modelIndices = modelIndices;}
	
	/**
	 * Gets the value for the new mass dataset's name.
	 * 
	 * @return					the value for the new mass dataset's name
	 */
	public String getModelName(){return modelName;}
	
	/**
	 * Sets the value for the new mass dataset's name.
	 * 
	 * @param	modelName		the value for the new mass dataset's name
	 */
	public void setModelName(String modelName){this.modelName = modelName;}
	
	/**
	 * Gets the value for the current mass dataset's index.
	 * 
	 * @return					the value for the current mass dataset's index
	 */
	public String getModelIndex(){return modelIndex;}
	
	/**
	 * Sets the value for the current mass dataset's index.
	 * 
	 * @param	modelIndex		the value for the current mass dataset's index
	 */
	public void setModelIndex(String modelIndex){this.modelIndex = modelIndex;}
	
	/**
	 * Gets the String representation of the Enum {@link org.nuclearmasses.enums.MassModelType}.
	 * <p>
	 * The value can be either PUBLIC, SHARED, USER, or ALL.
	 * 
	 * @return					the String representation of the Enum MassModelType.
	 */
	public String getType(){return type;}
	
	/**
	 * Sets the String representation of the Enum {@link org.nuclearmasses.enums.MassModelType}.
	 * <p>
	 * The value can be either PUBLIC, SHARED, USER, or ALL.
	 * 
	 * @param	type			the String representation of the Enum MassModelType.
	 */
	public void setType(String type){this.type = type;}
	
	/**
	 * Gets the value for the reference mass dataset's index.
	 * 
	 * @return					the value for the reference mass dataset's index
	 */
	public int getRefIndex(){return refIndex;}
	
	/**
	 * Sets the value for the reference mass dataset's index.
	 * 
	 * @param	refIndex		the value for the reference mass dataset's index
	 */
	public void setRefIndex(int refIndex){this.refIndex = refIndex;}
	
	/**
	 * Gets the value for the AME mass dataset's index.
	 * 
	 * @return					the value for the AME mass dataset's index
	 */
	public int getAMEIndex(){return ameIndex;}
	
	/**
	 * Sets the value for the AME mass dataset's index.
	 * 
	 * @param	ameIndex		the value for the AME mass dataset's index
	 */
	public void setAMEIndex(int ameIndex){this.ameIndex = ameIndex;}
	
	/**
	 * Gets a {@link HashMap} of {@link MassModelDataStructure}s keyed on mass dataset index 
	 * containing all available MassModelDataStructures.
	 * 
	 * @return					a HashMap of MassModelDataStructures keyed on mass dataset index 
	 * containing all available MassModelDataStructures
	 */
	public HashMap<Integer, MassModelDataStructure> getModelMap(){return modelMap;}
	
	/**
	 * Sets a {@link HashMap} of {@link MassModelDataStructure}s keyed on mass dataset index 
	 * containing all available MassModelDataStructures.
	 * 
	 * @param	modelMap		a HashMap of MassModelDataStructures keyed on mass dataset index 
	 * containing all available MassModelDataStructures
	 */
	public void setModelMap(HashMap<Integer, MassModelDataStructure> modelMap){this.modelMap = modelMap;}
	
	/**
	 * Gets a {@link HashMap} of {@link MassModelDataStructure}s keyed on mass dataset index 
	 * containing only User selected MassModelDataStructures.
	 * 
	 * @return					a HashMap of MassModelDataStructures keyed on mass dataset index 
	 * containing only User selected MassModelDataStructures
	 */
	public HashMap<Integer, MassModelDataStructure> getModelMapSelected(){return modelMapSelected;}
	
	/**
	 * Sets a {@link HashMap} of {@link MassModelDataStructure}s keyed on mass dataset index 
	 * containing only User selected MassModelDataStructures.
	 * 
	 * @param	modelMapSelected		a HashMap of MassModelDataStructures keyed on mass dataset index 
	 * containing only User selected MassModelDataStructures
	 */
	public void setModelMapSelected(HashMap<Integer, MassModelDataStructure> modelMapSelected){this.modelMapSelected = modelMapSelected;}
	
}
