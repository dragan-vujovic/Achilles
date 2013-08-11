package info.archinnov.achilles.proxy.wrapper.builder;

import static org.fest.assertions.api.Assertions.assertThat;
import info.archinnov.achilles.context.PersistenceContext;
import info.archinnov.achilles.entity.metadata.PropertyMeta;
import info.archinnov.achilles.entity.operations.EntityProxifier;
import info.archinnov.achilles.proxy.wrapper.EntryIteratorWrapper;
import info.archinnov.achilles.test.mapping.entity.CompleteBean;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * AchillesEntryIteratorWrapperBuilderTest
 * 
 * @author DuyHai DOAN
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class EntryIteratorWrapperBuilderTest
{
    @Mock
    private Map<Method, PropertyMeta> dirtyMap;

    private Method setter;

    @Mock
    private PropertyMeta propertyMeta;

    @Mock
    private EntityProxifier<PersistenceContext> proxifier;

    @Mock
    private PersistenceContext context;

    @Before
    public void setUp() throws Exception
    {
        setter = CompleteBean.class.getDeclaredMethod("setFriends", List.class);
    }

    @Test
    public void should_build() throws Exception
    {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put(1, "FR");
        map.put(2, "Paris");
        map.put(3, "75014");

        Iterator<Entry<Object, Object>> target = map.entrySet().iterator();
        EntryIteratorWrapper wrapper = EntryIteratorWrapperBuilder
                .builder(context, target)
                .dirtyMap(dirtyMap)
                .setter(setter)
                .propertyMeta(propertyMeta)
                .proxifier(proxifier)
                .build();

        assertThat(wrapper.getDirtyMap()).isSameAs(dirtyMap);
        assertThat(Whitebox.getInternalState(wrapper, "target")).isSameAs(target);
        assertThat(Whitebox.getInternalState(wrapper, "propertyMeta")).isSameAs(propertyMeta);
        assertThat(Whitebox.getInternalState(wrapper, "proxifier")).isSameAs(proxifier);
        assertThat(Whitebox.getInternalState(wrapper, "context")).isSameAs(context);

    }
}
