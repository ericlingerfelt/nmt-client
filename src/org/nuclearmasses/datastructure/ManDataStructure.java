package org.nuclearmasses.datastructure;

/**
 * This data structure class is used by the Mass Dataset Manager.
 */
public class ManDataStructure extends CGICallDataStructure{
	
	/**
	 * Enum for determining path selected for mass dataset upload.
	 */
	public enum UploadMethod{
		
		/** Select to paste data into a field. */
		PASTE, 
		
		/** Select to upload a dataset file. */
		FILE, 
		
		/** Select to enter dataset values into a speadsheet table. */
		FIELD}
	
	/** The upload method. */
	private UploadMethod uploadMethod;
	
	/**
	 * Class constructor.
	 */
	public ManDataStructure(){
		super.initialize();
		this.initialize();
	}
	
	/**
	 * Initializes all fields and calls <code>super.initialize</code>.
	 */
	public void initialize(){
		setUploadMethod(UploadMethod.FIELD);
		super.initialize();
	}
	
	/**
	 * Gets the current upload method.
	 *
	 * @return 				one of the values available from {@link UploadMethod}
	 */
	public UploadMethod getUploadMethod(){return uploadMethod;}
	
	/**
	 * Sets the current upload method.
	 *
	 * @param uploadMethod one of the values available from {@link UploadMethod}
	 */
	public void setUploadMethod(UploadMethod uploadMethod){this.uploadMethod = uploadMethod;}
}
