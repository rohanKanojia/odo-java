package com.lordofthejars.odo.maven;

import com.lordofthejars.odo.core.Odo;
import com.lordofthejars.odo.testbed.junit5.OdoExecutorStubInjector;
import com.lordofthejars.odo.testbed.odo.OdoExecutorStub;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.apache.maven.project.MavenProject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.lordofthejars.odo.testbed.assertj.OdoExecutorAssertion.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, OdoExecutorStubInjector.class})
public class OdoStorageCreateMojoTest {
    @Mock
    MavenProject project;

    @Test
    public void testMojoBehavior(OdoExecutorStub odoExecutorStub) {
            // Given
        OdoStorageCreateMojo odoStorageCreateMojo = new OdoStorageCreateMojo();
        Odo odo = new Odo(odoExecutorStub);

        when(project.getBasedir()).thenReturn(new File("/tmp/foodir"));
        Map<String, String> storageCreateConfiguration = new HashMap<>();
        storageCreateConfiguration.put("component", "mycomponent");
        storageCreateConfiguration.put("app", "myapp");
        storageCreateConfiguration.put("project", "myproject");
        storageCreateConfiguration.put("storageName", "foostorage");
        storageCreateConfiguration.put("path", "/storage/foo");
        storageCreateConfiguration.put("size", "1Gi");

        odoStorageCreateMojo.project = project;
        odoStorageCreateMojo.createStorage = storageCreateConfiguration;

        odoStorageCreateMojo.odo = odo;

        // When:
        odoStorageCreateMojo.execute();

        // Then:
        assertThat(odoExecutorStub).hasExecuted("odo storage create foostorage --app myapp --component mycomponent --project myproject --path /storage/foo --size 1Gi");
    }

}
