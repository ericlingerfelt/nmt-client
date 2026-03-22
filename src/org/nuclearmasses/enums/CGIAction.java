package org.nuclearmasses.enums;

/**
 * This enum labels all possible CGI calls to the server.
 */
public enum CGIAction {
	
	/** Gets a session id. */
	GET_ID,
	
	/** Logs out a User from the system. */
	LOGOUT,
	
	/** Registers a new User. */
	REGISTER_USER,
	
	/** Creates a new mass dataset. */
	CREATE_MODEL,
	
	/** Deletes a mass dataset. */
	ERASE_MODEL,
	
	/** Gets a list of all available mass datasets. */
	GET_MODELS,

	/** Gets all data for a mass dataset. */
	GET_MODEL_DATA,
	
	/** Gets all metadata for a mass dataset. */
	GET_MODEL_INFO,
	
	/** Copies a User mass dataset to the shared folder. */
	COPY_MODEL_TO_SHARED,
	
	/** Executes a prioritized merge of two or more mass datasets. */
	MERGE_MODELS
}
