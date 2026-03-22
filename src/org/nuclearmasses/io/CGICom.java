package org.nuclearmasses.io;

import java.awt.Frame;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;
import org.nuclearmasses.datastructure.*;
import org.nuclearmasses.gui.dialogs.*;
import org.nuclearmasses.enums.*;

/**
 * This class controls communication with our server side programming and database.
 */
public class CGICom {

	/** The data. */
	private String actionEnc, user, id, password
					, model_name, description, reference
					, overwrite, model_index, type
					, model_indices, field, author
					, first_name, last_name, email, institution
					, address, hear_of_us, info, country, research, data;
	
	/** The action string. */
	private final String actionString = "ACTION";
	
	/** The user string. */
	private final String userString = "USER";
	
	/** The id string. */
	private final String idString = "ID";
	
	/** The password string. */
	private final String passwordString = "PASSWORD";
	
	/** The model_name string. */
	private final String model_nameString = "MODEL_NAME";
	
	/** The description string. */
	private final String descriptionString = "DESCRIPTION";
	
	/** The reference string. */
	private final String referenceString = "REFERENCE";
	
	/** The overwrite string. */
	private final String overwriteString = "OVERWRITE";
	
	/** The model_index string. */
	private final String model_indexString = "MODEL_INDEX";
	
	/** The model_indices string. */
	private final String model_indicesString = "MODEL_INDICES";
	
	/** The type string. */
	private final String typeString = "TYPE";
	
	/** The field string. */
	private final String fieldString = "FIELD";
	
	/** The data string. */
	private final String dataString = "DATA";
	
	/** The author string. */
	private final String authorString = "AUTHOR";
	
	/** The first_name string. */
	private final String first_nameString = "FIRST_NAME";
	
	/** The last_name string. */
	private final String last_nameString = "LAST_NAME";
	
	/** The email string. */
	private final String emailString = "EMAIL";
	
	/** The institution string. */
	private final String institutionString = "INSTITUTION";
	
	/** The address string. */
	private final String addressString = "ADDRESS";
	
	/** The hear_of_us string. */
	private final String hear_of_usString = "HEAR_OF_US";
	
	/** The info string. */
	private final String infoString = "INFO";
	
	/** The country string. */
	private final String countryString = "COUNTRY";
	
	/** The research string. */
	private final String researchString = "RESEARCH";
	
	/** The parser. */
	private CGIComParser parser = new CGIComParser();
	
	/**
	 * Initialize.
	 */
	private void initialize(){
		actionEnc = "";
		user = "";
		id = "";
		password = "";
		model_name = "";
		description = "";
		reference = "";
		overwrite = "";
		model_index = "";
		model_indices = "";
		type = "";
		field = "";
		author = "";
		first_name = "";
		last_name = "";
		email = "";
		institution = "";
		address = "";
		hear_of_us = "";
		info = "";
		country = "";
		research = "";
		data = "";
	}

	/**
	 * Performs a CGI call to our server.
	 *
	 * @param mds 			a reference to the {@link MainDataStructure}
	 * @param ds 			a reference to a {@link CGICallDataStructure}
	 * @param action 		a {@link CGIAction}
	 * @param frame 		the {@link Frame} making this cgi call
	 * @param delayDialog 	a reference to a {@link DelayDialog}
	 * @return true, if successful
	 */
	public boolean doCGICall(MainDataStructure mds
								, CGICallDataStructure ds
								, CGIAction action
								, Frame frame
								, DelayDialog delayDialog){

		initialize();
		ArrayList<CGIComSubmitProperty> propList = getCGIComSubmitProperties(action, mds, ds);
		String outputString = getOutputString(propList);
		if(mds.getDebug()){
			try{
				System.out.println(URLDecoder.decode(outputString.trim(), "UTF-8"));
			}catch(Exception e){
				
			}
		}
		String inputString = transmitCGIString(outputString, mds, action);
		if(mds.getDebug()){
			if(action!=CGIAction.GET_MODEL_DATA){
				System.out.println(inputString.trim());
			}
			System.out.println("******************************************************************");
		}
		return !parser.parse(action, mds, ds, frame, inputString, delayDialog);
	}
	
	/**
	 * Performs a CGI call to our server.
	 *
	 * @param mds 			a reference to the {@link MainDataStructure}
	 * @param ds 			a reference to a {@link CGICallDataStructure}
	 * @param action 		a {@link CGIAction}
	 * @param frame 		the {@link Frame} making this cgi call
	 * @return true, if successful
	 */
	public boolean doCGICall(MainDataStructure mds
								, CGICallDataStructure ds
								, CGIAction action
								, Frame frame){
		return doCGICall(mds, ds, action, frame, null);
	}
	
	/**
	 * Transmit cgi string.
	 *
	 * @param string the string
	 * @param mds the mds
	 * @param action the action
	 * @return the string
	 */
	private String transmitCGIString(String string, MainDataStructure mds, CGIAction action){
		String totalInputString = "";
		try{
			URL url = null;
			if(mds.getURLType()==URLType.DEV){
				url = new URL("https://nucastrodata2.ornl.gov/phpd/mass_model_dev/mass_main.php");
			}else{
				url = new URL("https://nucastrodata2.ornl.gov/phpd/mass_model/mass_main.php");
			}
			HttpsURLConnection urlConnection = (HttpsURLConnection)url.openConnection();
			urlConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			urlConnection.setDoOutput(true);
			OutputStream os = urlConnection.getOutputStream();
			os.write(string.getBytes());
			os.close();
			InputStream inputStream = urlConnection.getInputStream();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			IOUtilities.readStream(inputStream, baos);
			totalInputString = new String(baos.toByteArray());
			baos.close();
		}catch(Exception e){
			return "ERROR=An error has occurred connecting to our web server. "
			+ "Please check your internet connection and restart this software.";
		}
		return totalInputString;
	}
	
	/**
	 * Gets the cGI com submit properties.
	 *
	 * @param action the action
	 * @param mds the mds
	 * @param ds the ds
	 * @return the cGI com submit properties
	 */
	private ArrayList<CGIComSubmitProperty> getCGIComSubmitProperties(CGIAction action
															, MainDataStructure mds
															, CGICallDataStructure ds){
		
		ArrayList<CGIComSubmitProperty> propList = new ArrayList<CGIComSubmitProperty>();
		
		try{
			
			actionEnc = URLEncoder.encode(action.toString(), "UTF-8");
			user = URLEncoder.encode(mds.getUser(), "UTF-8");
			id = URLEncoder.encode(mds.getID(), "UTF-8");
			password = URLEncoder.encode(getEncryptedString(mds.getPassword()), "UTF-8");
			if(ds!=null){
				type = URLEncoder.encode(ds.getType(), "UTF-8");
				model_index = URLEncoder.encode(ds.getModelIndex(), "UTF-8");
				model_indices = URLEncoder.encode(ds.getModelIndices(), "UTF-8");
				model_name = URLEncoder.encode(ds.getModelName(), "UTF-8");
				overwrite = URLEncoder.encode(ds.getOverwrite(), "UTF-8");
				model_name = URLEncoder.encode(ds.getModelName(), "UTF-8");
				description = URLEncoder.encode(ds.getDesc(), "UTF-8");
				reference = URLEncoder.encode(ds.getRef(), "UTF-8");
				field = URLEncoder.encode(ds.getField(), "UTF-8");
				author = URLEncoder.encode(ds.getAuthor(), "UTF-8");
				first_name = URLEncoder.encode(ds.getFirstName(), "UTF-8");
				last_name = URLEncoder.encode(ds.getLastName(), "UTF-8");
				email = URLEncoder.encode(ds.getEmail(), "UTF-8");
				institution = URLEncoder.encode(ds.getInstitution(), "UTF-8");
				address = URLEncoder.encode(ds.getAddress(), "UTF-8");
				hear_of_us = URLEncoder.encode(ds.getHearOfSuite(), "UTF-8");
				info = URLEncoder.encode(ds.getInfo(), "UTF-8");
				country = URLEncoder.encode(ds.getCountry(), "UTF-8");
				research = URLEncoder.encode(ds.getResearch(), "UTF-8");
				data = URLEncoder.encode(ds.getData(), "UTF-8");
			}
			
		}catch(UnsupportedEncodingException usee){
			usee.printStackTrace();
		}
		
		switch(action){
		
			case GET_ID:
				propList.add(new CGIComSubmitProperty(actionString, actionEnc));
				propList.add(new CGIComSubmitProperty(userString, user));
				propList.add(new CGIComSubmitProperty(passwordString, password));
				break;
				
			case LOGOUT:
				propList.add(new CGIComSubmitProperty(actionString, actionEnc));
				propList.add(new CGIComSubmitProperty(idString, id));
				break;
			
			case GET_MODELS: 
				propList.add(new CGIComSubmitProperty(actionString, actionEnc));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(typeString, type));
				break;
				
			case GET_MODEL_INFO: 
				propList.add(new CGIComSubmitProperty(actionString, actionEnc));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(model_indicesString, model_indices));
				break;
				
			case ERASE_MODEL: 
				propList.add(new CGIComSubmitProperty(actionString, actionEnc));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(model_indexString, model_index));
				break;
				
			case COPY_MODEL_TO_SHARED: 
				propList.add(new CGIComSubmitProperty(actionString, actionEnc));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(model_indexString, model_index));
				break;
				
			case MERGE_MODELS: 
				propList.add(new CGIComSubmitProperty(actionString, actionEnc));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(model_nameString, model_name));
				propList.add(new CGIComSubmitProperty(model_indicesString, model_indices));
				propList.add(new CGIComSubmitProperty(overwriteString, overwrite));
				break;
				
			case GET_MODEL_DATA: 
				propList.add(new CGIComSubmitProperty(actionString, actionEnc));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(model_indicesString, model_indices));
				break;
				
			case CREATE_MODEL:
				propList.add(new CGIComSubmitProperty(actionString, actionEnc));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(overwriteString, overwrite));
				propList.add(new CGIComSubmitProperty(model_nameString, model_name));
				propList.add(new CGIComSubmitProperty(descriptionString, description));
				propList.add(new CGIComSubmitProperty(referenceString, reference));
				propList.add(new CGIComSubmitProperty(fieldString, field));
				propList.add(new CGIComSubmitProperty(authorString, author));
				propList.add(new CGIComSubmitProperty(dataString, data));
				break;
				
			case REGISTER_USER: 
				propList.add(new CGIComSubmitProperty(actionString, actionEnc));
				propList.add(new CGIComSubmitProperty(idString, id));
				propList.add(new CGIComSubmitProperty(first_nameString, first_name));
				propList.add(new CGIComSubmitProperty(last_nameString, last_name));
				propList.add(new CGIComSubmitProperty(emailString, email));
				propList.add(new CGIComSubmitProperty(institutionString, institution));
				propList.add(new CGIComSubmitProperty(addressString, address));
				propList.add(new CGIComSubmitProperty(hear_of_usString, hear_of_us));
				propList.add(new CGIComSubmitProperty(infoString, info));
				propList.add(new CGIComSubmitProperty(countryString, country));
				propList.add(new CGIComSubmitProperty(researchString, research));
				break;
				
		}
		
		return propList;
		
	}
	
	/**
	 * Gets the encrypted string.
	 *
	 * @param string the string
	 * @return the encrypted string
	 */
	private String getEncryptedString(String string){
		
		String encryptedString = "";
		
		try{

			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			sha.update(string.getBytes());
			byte[] byteArray = sha.digest();
			
			for(int i=0; i<byteArray.length; i++){
				int temp = Integer.valueOf(Byte.toString(byteArray[i])).intValue();
				
				if(temp<0){
					if(String.valueOf(Integer.toHexString(temp + 256)).length()==1){
						encryptedString += "0" + String.valueOf(Integer.toHexString(temp + 256));
					}else{
						encryptedString += String.valueOf(Integer.toHexString(temp + 256));
					}
				
				}else{
				
					if(String.valueOf(Integer.toHexString(temp)).length()==1){
						encryptedString += "0" + String.valueOf(Integer.toHexString(temp));
					}else{
						encryptedString += String.valueOf(Integer.toHexString(temp));
					}
				
				}
			
			}
	
		}catch(NoSuchAlgorithmException nsae){
			nsae.printStackTrace();
		}
	
		return encryptedString;

	}
	
	/**
	 * Gets the output string.
	 *
	 * @param propList the prop list
	 * @return the output string
	 */
	private String getOutputString(ArrayList<CGIComSubmitProperty> propList){
		String string = "";
		Iterator<CGIComSubmitProperty> itr = propList.iterator();
		while(itr.hasNext()){
			CGIComSubmitProperty prop = itr.next();
			string += prop.getProperty() + "=" + prop.getValue();
			if(itr.hasNext()){
				string += "&";
			}
		}
		return string;
	}
	
}

class CGIComSubmitProperty{
	private String property;
	private String value;
	
	public CGIComSubmitProperty(String property, String value){
		this.property = property;
		this.value = value;
	}
	
	public String getProperty(){return property;}
	public String getValue(){return value;}
}

class CGIComParser{
	
	public boolean parse(CGIAction action
								, MainDataStructure mds
								, CGICallDataStructure ds
								, Frame frame
								, String string
								, DelayDialog delayDialog){
		
		String error = "";

		switch(action){

			case GET_ID:
				error = parseGET_IDString(mds, string);
				break;
				
			case LOGOUT:
				error = parseLOGOUTString(string);
				break;
				
			case GET_MODELS:
				error = parseGET_MODELSString(ds, string);
				break;
				
			case GET_MODEL_INFO:
				error = parseGET_MODEL_INFOString(ds, string);
				break;
				
			case ERASE_MODEL:
				error = parseERASE_MODELString(string);
				break;
				
			case COPY_MODEL_TO_SHARED:
				error = parseCOPY_MODEL_TO_SHAREDString(string);
				break;
				
			case MERGE_MODELS:
				error = parseMERGE_MODELSString(string);
				break;
				
			case GET_MODEL_DATA:
				error = parseGET_MODEL_DATAString(ds, string);
				break;
				
			case CREATE_MODEL:
				error = parseCREATE_MODELString(string);
				break;
				
			case REGISTER_USER:
				error = parseREGISTER_USERString(string);
				break;

		}

		if(!error.trim().equals("")){
			if(delayDialog!=null){
				delayDialog.closeDelayDialog();
			}
			GeneralDialog dialog = new GeneralDialog(frame, error, new String("Error!"));
			dialog.setVisible(true);
			return true;
		}

		return false;

	}
	
	private String parseGET_IDString(DataStructure ds, String string){
		String[] array = string.split("\n"); 
		for(int i=0; i<array.length; i++){
			String token = array[i];
			if(token.startsWith("ERROR=")){
				return token.substring(6);
			}else if(token.startsWith("ID=")){
				if(ds instanceof MainDataStructure){
					((MainDataStructure)ds).setID(token.substring(3));
				}
			}
		}
		return "";
	}

	private String parseLOGOUTString(String string){
		String[] array = string.split("\n"); 
		for(int i=0; i<array.length; i++){
			String token = array[i];
			if(token.startsWith("ERROR=")){
				return token.substring(6);
			}
		}
		return "";
	}
	
	private String parseGET_MODELSString(CGICallDataStructure ds, String string){
		HashMap<Integer, MassModelDataStructure> map = new HashMap<Integer, MassModelDataStructure>();
		int index = -1;
		String[] array = string.split("\n"); 
		for(int i=0; i<array.length; i++){
			String token = array[i];
			if(token.startsWith("ERROR=")){
				return token.substring(6); 
			}else if(token.startsWith("MODEL_INDEX=")){
				String[] subarray = token.split("=");
				MassModelDataStructure mmds = new MassModelDataStructure();
				mmds.setIndex(Integer.valueOf(subarray[1]));
				index = mmds.getIndex();
				map.put(mmds.getIndex(), mmds);
			}else if(token.startsWith("MODEL_NAME=")){
				String[] subarray = token.split("=");
				map.get(index).setName(subarray[1]);
				if(subarray[1].equals("AME2012")){
					ds.setAMEIndex(index);
				}
			}else if(token.startsWith("TYPE=")){
				String[] subarray = token.split("=");
				map.get(index).setType(Enum.valueOf(MassModelType.class, subarray[1]));
			}
		}
		ds.setModelMap(map);
		return "";
	}
	
	private String parseGET_MODEL_INFOString(CGICallDataStructure ds, String string){
		MassModelDataStructure mmds = null;
		String[] array = string.split("\n"); 
		for(int i=0; i<array.length; i++){
			String token = array[i];
			if(token.startsWith("ERROR=")){
				return token.substring(6); 
			}else if(token.startsWith("MODEL_INDEX=")){
				String[] subarray = token.split("=");
				mmds = ds.getModelMapSelected().get(Integer.valueOf(subarray[1]));
			}else if(token.startsWith("DESCRIPTION=")){
				String[] subarray = token.split("=");
				if(subarray.length>1){
					//mmds.setDesc(subarray[1]);
					mmds.setDesc(token.substring(subarray[0].length()+1));
				}
			}else if(token.startsWith("REFERENCE=")){
				String[] subarray = token.split("=");
				if(subarray.length>1){
					mmds.setRef(token.substring(subarray[0].length()+1));
				}
			}else if(token.startsWith("AUTHOR=")){
				String[] subarray = token.split("=");
				if(subarray.length>1){
					mmds.setAuthor(subarray[1]);
				}
			}else if(token.startsWith("FIELD=")){
				String[] subarray = token.split("=");
				if(subarray.length>1){
					mmds.setField(Enum.valueOf(MassModelField.class, subarray[1]));
				}
			}else if(token.startsWith("CREATION_DATE=")){
				String[] subarray = token.split("=");
				if(subarray.length>1){
					mmds.setCreationDate(getCalendar(subarray[1]));
				}
			}else if(token.startsWith("MODIFICATION_DATE=")){
				String[] subarray = token.split("=");
				if(subarray.length>1){
					mmds.setModificationDate(getCalendar(subarray[1]));
				}
			}
		}
		return "";
	}
	
	private String parseERASE_MODELString(String string){
		String[] array = string.split("\n"); 
		for(int i=0; i<array.length; i++){
			String token = array[i];
			if(token.startsWith("ERROR=")){
				return token.substring(6);
			}
		}
		return "";
	}
	
	private String parseCOPY_MODEL_TO_SHAREDString(String string){
		String[] array = string.split("\n"); 
		for(int i=0; i<array.length; i++){
			String token = array[i];
			if(token.startsWith("ERROR=")){
				return token.substring(6);
			}
		}
		return "";
	}
	
	private String parseMERGE_MODELSString(String string){
		String[] array = string.split("\n"); 
		for(int i=0; i<array.length; i++){
			String token = array[i];
			if(token.startsWith("ERROR=")){
				return token.substring(6);
			}
		}
		return "";
	}
	
	private String parseGET_MODEL_DATAString(CGICallDataStructure ds, String string){	
		MassModelDataStructure mmds = null;
		String[] array = string.split("\n"); 
		for(int i=0; i<array.length; i++){
			String token = array[i];
			if(token.startsWith("ERROR=")){
				return token.substring(6); 
			}else if(token.startsWith("MODEL_INDEX=")){
				String[] subarray = token.split("=");
				mmds = ds.getModelMapSelected().get(Integer.valueOf(subarray[1]));
			}else if(token.startsWith("DATA=")){
				String[] subarray = token.split("=");
				TreeMap<IsotopePoint, MassPoint> map = new TreeMap<IsotopePoint, MassPoint>();
				mmds.setMassMap(map);
				subarray = subarray[1].split(" ");
				for(String subtoken: subarray){
					String[] subsubarray = subtoken.split(",");
					int z = Integer.valueOf(subsubarray[0]);
					int n = Integer.valueOf(subsubarray[1]);
					double value = Double.valueOf(subsubarray[2]);
					double uncer = Double.valueOf(subsubarray[3]);
					IsotopePoint ip = new IsotopePoint(z, n);
					MassPoint mp = new MassPoint(value, uncer);
					map.put(ip, mp);
				}
			}
		}
		return "";
	}
	
	private String parseCREATE_MODELString(String string){
		String[] array = string.split("\n"); 
		for(int i=0; i<array.length; i++){
			String token = array[i];
			if(token.startsWith("ERROR=")){
				return token.substring(6);
			}
		}
		return "";
	}

	private String parseREGISTER_USERString(String string){
		String[] array = string.split("\n"); 
		for(int i=0; i<array.length; i++){
			String token = array[i];
			if(token.startsWith("ERROR=")){
				return token.substring(6);
			}
		}
		return "";
	}
	
	private Calendar getCalendar(String string){
		Calendar calendar = Calendar.getInstance();
		String day = string.split(" ")[0];
		String time = string.split(" ")[1];
		int year = Integer.valueOf(day.split("-")[0]);
		int month = Integer.valueOf(day.split("-")[1])-1;
		int date = Integer.valueOf(day.split("-")[2]);
		int hourOfDay = Integer.valueOf(time.split(":")[0]);
		int minute = Integer.valueOf(time.split(":")[1]);
		int second = Integer.valueOf(time.split(":")[2]);
		calendar.set(year, month, date, hourOfDay, minute, second);
		return calendar;
	}
	
}
