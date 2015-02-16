package myPasswords.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

/**
 * Represents a password entry
 * 
 * @author anselm.ringleben
 *
 */
@Data
@Entity
public class PasswordEntry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String category;

	private String name;

	private String url;

	private String password;

	private Date lastChanged;
}
