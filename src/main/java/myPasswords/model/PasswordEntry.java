package myPasswords.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Represents a password entry
 * 
 * @author anselm.ringleben
 *
 */
@Data
public class PasswordEntry implements Serializable {
	private static final long serialVersionUID = 1L;

	private long id;

	private String category;

	private String name;

	private String url;

	private String password;

	private Date lastChanged;
}
