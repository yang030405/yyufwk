package mocktest;

import java.io.File;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest( PersistenceManager.class )
public class PersistenceManagerTest {

	@Test
	public void testCreateDirectoryStructure_ok() throws Exception {
		File fileMock = PowerMock.createMock(File.class);
		final String path = "directoryPath";
		PowerMock.expectNew(File.class, path).andReturn(fileMock);

		EasyMock.expect(fileMock.exists()).andReturn(false);
		EasyMock.expect(fileMock.mkdirs()).andReturn(true);

		PowerMock.replay(fileMock, File.class);
		PersistenceManager tested = new PersistenceManager();
		Assert.assertTrue(tested.createDirectoryStructure(path));
		PowerMock.verify(fileMock, File.class);
	}
}