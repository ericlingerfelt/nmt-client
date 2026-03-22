package org.nuclearmasses.datastructure;

import org.nuclearmasses.datastructure.DataStructure;
import org.nuclearmasses.enums.URLType;

/**
 * This class is a data structure containing system and session specific fields and methods.
 */
public class MainDataStructure implements DataStructure{

	/** The symbol string. */
	private static String[] symbolString = {"n","H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne", "Na"
			, "Mg", "Al", "Si", "P", "S", "Cl", "Ar", "K", "Ca", "Sc", "Ti", "V", "Cr", "Mn", "Fe" 
			, "Co", "Ni", "Cu", "Zn", "Ga", "Ge", "As", "Se", "Br", "Kr", "Rb", "Sr", "Y", "Zr", "Nb"
			, "Mo", "Tc", "Ru", "Rh", "Pd", "Ag", "Cd", "In", "Sn", "Sb", "Te", "I", "Xe", "Cs", "Ba"
			, "La", "Ce", "Pr", "Nd", "Pm", "Sm", "Eu", "Gd", "Tb", "Dy", "Ho", "Er", "Tm", "Yb", "Lu"
			, "Hf", "Ta", "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi", "Po", "At", "Rn"
			, "Fr", "Ra", "Ac", "Th", "Pa", "U", "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm", "Md"
			, "No", "Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt", "Ds", "Rg", "Cn", "Nh", "Fl", "Mc"
			, "Lv", "Ts", "Og", "Uue", "Ubn", "Ubu", "Ubb", "Ubt", "Ubq", "Ubp", "Ubh", "Ubs", "Ubo"
			, "Ube", "Utn", "Utu", "Utb", "Utt", "Utq", "Utp", "Uth", "Uts", "Uto", "Ute", "Uqn", "Uqu"
			, "Uqb", "Uqt", "Uqq", "Uqp", "Uqh", "Uqs", "Uqo", "Uqe", "Upn", "Upu", "Upb", "Upt", "Upq"
			, "Upp", "Uph", "Ups", "Upo", "Upe", "Uhn", "Uhu", "Uhb", "Uht", "Uhq", "Uhp", "Uhh", "Uhs"
			, "Uho", "Uhe", "Usn", "Usu", "Usb", "Ust", "Usq", "Usp"};

	/** The url type. */
	private URLType urlType;
	
	/** The logged in. */
	private boolean debug, loggedIn;
	
	/** The load from jar. */
	private static boolean loadFromJar;
	
	/** The absolute path. */
	private String user, password, id, absolutePath;
	
	/**
	 * Class constructor.
	 */
	public MainDataStructure(){
		initialize();
	}
	
	/**
	 * Initializes all fields.
	 */
	public void initialize(){
		setLoggedIn(false);
		setLoadFromJar(false);
		setUser("");
		setID("");
		setPassword("");
		setAbsolutePath("");
	}
	
	/**
	 * Gets the element symbol from the proton number z.
	 * 
	 * @param	z				the proton number
	 * @return					the element symbol
	 */
	public static String getElementSymbol(int z){return symbolString[z];}
	
	/**
	 * Gets the last absolute path the User used to save a file.
	 * 
	 * @return					the last absolute path the User used to save a file
	 */
	public String getAbsolutePath(){return absolutePath;} 
	
	/**
	 * Sets the last absolute path the User used to save a file.
	 * 
	 * @param	absolutePath	the last absolute path the User used to save a file
	 */
	public void setAbsolutePath(String absolutePath){this.absolutePath = absolutePath;}
	
	/**
	 * Gets the current value of the Enum {@link URLType}.
	 * 
	 * @return					the current value of the Enum URLType
	 */
	public URLType getURLType(){return urlType;}
	
	/**
	 * Sets the current value of the Enum {@link URLType}.
	 * 
	 * @param	urlType		the current value of the Enum URLType
	 */
	public void setURLType(URLType urlType){this.urlType = urlType;}
	
	/**
	 * Gets the boolean value for determining whether server input and output will be printed to stdout.
	 * 
	 * @return					true if server input and output will be printed to stdout
	 */
	public boolean getDebug(){return debug;} 
	
	/**
	 * Sets the boolean value for determining whether server input and output will be printed to stdout.
	 * 
	 * @param	debug	true if server input and output will be printed to stdout
	 */
	public void setDebug(boolean debug){this.debug = debug;}
	
	/**
	 * Gets the boolean value for determining whether a User is logged onto the system.
	 * 
	 * @return					true if a User is logged onto the system
	 */
	public boolean getLoggedIn(){return loggedIn;} 
	
	/**
	 * Sets the boolean value for determining whether a User is logged onto the system.
	 * 
	 * @param	loggedIn		true if a User is logged onto the system
	 */
	public void setLoggedIn(boolean loggedIn){this.loggedIn = loggedIn;}
	
	/**
	 * Gets the boolean value for determining whether to load images and other required 
	 * datafiles from a jar file.
	 * 
	 * @return					true if images and other required datafiles are to be loaded 
	 * from a jar file
	 */
	public static boolean getLoadFromJar(){return loadFromJar;} 
	
	/**
	 * Sets the boolean value for determining whether to load images and other required 
	 * datafiles from a jar file.
	 * 
	 * @param	loadFromJar		true if images and other required datafiles are to be loaded 
	 * from a jar file
	 */
	public static void setLoadFromJar(boolean loadFromJar){MainDataStructure.loadFromJar = loadFromJar;}
	
	/**
	 * Gets the User's username.
	 *
	 * @return 				the User's username
	 */
	public String getUser(){return user;} 
	
	/**
	 * Sets the User's username.
	 *
	 * @param user 		the User's username
	 */
	public void setUser(String user){this.user = user;}
	
	/**
	 * Gets the current session id as assigned by the server.
	 *
	 * @return 				the current session id as assigned by the server
	 */
	public String getID(){return id;} 
	
	/**
	 * Sets the current session id as assigned by the server.
	 *
	 * @param id 			the current session id as assigned by the server
	 */
	public void setID(String id){this.id = id;}
	
	/**
	 * Gets the User's password.
	 *
	 * @return 				the User's password
	 */
	public String getPassword(){return password;} 
	
	/**
	 * Sets the User's password.
	 *
	 * @param password 	the User's password
	 */
	public void setPassword(String password){this.password = password;}
	
}

