package myPasswords.data;

import static org.junit.Assert.*;
import lombok.extern.log4j.Log4j;
import myPasswords.model.PasswordEntry;

import org.junit.Before;
import org.junit.Test;

@Log4j
public class SimplePasswordRepositoryTest {

	private SimplePasswordRepository repository;

	@Before
	public void setUp() {
		log.debug("Execute: setUp");

		repository = new SimplePasswordRepository();
	}

	@Test
	public void testPut() {
		log.debug("Execute: testPut");

		PasswordEntry entry = new PasswordEntry();
		entry.setId(1L);
		entry.setName("test");

		repository.put(entry);

		PasswordEntry result = repository.get("1");
		assertEquals(entry.getName(), result.getName());
	}

	@Test
	public void testPutDouble() {
		log.debug("Execute: testPutDouble");

		PasswordEntry entry1 = new PasswordEntry();
		entry1.setId(1L);
		entry1.setName("test1");

		PasswordEntry entry2 = new PasswordEntry();
		entry2.setId(1L);
		entry2.setName("test2");

		repository.put(entry1);
		repository.put(entry2);

		PasswordEntry result = repository.get("1");
		assertEquals(1, repository.size());
		assertEquals(entry2.getName(), result.getName());
	}

	@Test
	public void testPutNull() {
		log.debug("Execute: testPutNull");

		repository.put(null);

		assertEquals(0, repository.size());
	}

	@Test
	public void testGet() {
		log.debug("Execute: testGet");

		PasswordEntry entry = new PasswordEntry();
		entry.setId(1L);
		entry.setName("test");

		repository.put(entry);

		PasswordEntry result = repository.get("1");
		assertEquals(entry.getName(), result.getName());
	}

	@Test
	public void testGetNull() {
		log.debug("Execute: testGetNull");

		PasswordEntry entry = new PasswordEntry();

		repository.put(entry);

		PasswordEntry result = repository.get(null);
		assertNull(result);
	}
}
