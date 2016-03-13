package com.young.tag;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class URLDecoderTag extends SimpleTagSupport {
	private String content;
	private String encode;

	public void setContent(String content) {
		this.content = content;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	@Override
	public void doTag() throws JspException, IOException {
		String str = URLDecoder.decode(content, encode == null ? "UTF-8"
				: encode);
		getJspContext().getOut().write(str);
	}
}
