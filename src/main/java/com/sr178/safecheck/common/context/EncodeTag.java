package com.sr178.safecheck.common.context;

import java.io.IOException;  
import java.net.URLEncoder;  
  
import javax.servlet.jsp.JspException;  
import javax.servlet.jsp.JspWriter;  
import javax.servlet.jsp.tagext.TagSupport;  
  
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;  
  
public class EncodeTag extends TagSupport {  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1749052332913410328L;
	private Object key;//自定义标签的key属性  
  
    public void setKey(Object key) throws JspException {  
        this.key=ExpressionEvaluatorManager.evaluate("key", key.toString(), Object.class, this, pageContext);  
  
    }  
    public int doEndTag() {  
        try {// 使用JspWriter获得JSP的输出对象  
            JspWriter jspWriterOutput = pageContext.getOut();  
            jspWriterOutput.write(URLEncoder.encode(key.toString(),"utf-8"));  
        } catch (IOException ioEx) {  
            System.out.println("IOException in HelloTag " + ioEx);  
        }  
        return EVAL_PAGE;  
    }  
      
  
}  