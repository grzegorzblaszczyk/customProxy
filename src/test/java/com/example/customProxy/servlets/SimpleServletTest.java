package com.example.customProxy.servlets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SimpleServletTest {
	
	@Mock
	CloseableHttpResponse httpResponse;
	
	@Test
    public void testExtractContentPositive() {
        SimpleServlet simpleServlet = new SimpleServlet();
        BasicHttpEntity entity = new BasicHttpEntity();
        entity.setContentType(new BasicHeader(SimpleServlet.CONTENT_TYPE, SimpleServlet.CHARSET_UNDEFINED));
        Mockito.when(httpResponse.getEntity()).thenReturn(entity);
        assertEquals(SimpleServlet.CHARSET_UTF_8, simpleServlet.extractContentType(httpResponse));
    }
	
	@Test
    public void testExtractContentNegative() {
        SimpleServlet simpleServlet = new SimpleServlet();
        BasicHttpEntity entity = new BasicHttpEntity();
        entity.setContentType(new BasicHeader(SimpleServlet.CONTENT_TYPE, "UTF-16"));
        Mockito.when(httpResponse.getEntity()).thenReturn(entity);
        assertNotEquals(SimpleServlet.CHARSET_UTF_8, simpleServlet.extractContentType(httpResponse));
    }

}
