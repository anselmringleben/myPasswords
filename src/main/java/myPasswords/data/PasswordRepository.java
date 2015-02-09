package myPasswords.data;

import java.util.List;

import myPasswords.model.PasswordEntry;

/**
 * Encapsulates Password data access
 * 
 * @author anselm.ringleben
 *
 */
public interface PasswordRepository {

	/**
	 * Save/overwrite {@link PasswordEntry}
	 * 
	 * @param password
	 *            value to save
	 * @return operation successful (True/False)
	 */
	public boolean put(PasswordEntry password);

	/**
	 * Retrieve entry with id
	 * 
	 * @param id
	 *            internal password id
	 * @return {@link PasswordEntry} for given id
	 */
	public PasswordEntry get(String id);

	/**
	 * Delete {@link PasswordEntry} for given id
	 * 
	 * @param id
	 *            internal password id
	 * @return operation successful (True/False)
	 */
	public boolean delete(String id);

	/**
	 * Retrieve all {@link PasswordEntry}s for given category
	 * 
	 * @param category
	 *            category for {@link PasswordEntry} selection
	 * @return list of {@link PasswordEntry}s matching given category
	 */
	public List<PasswordEntry> getList(String category);

	/**
	 * Retrieve number of stored elements
	 * 
	 * @return result
	 */
	public int size();
}
