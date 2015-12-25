/*
 * 
 */
package com.hotpot.commons;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.hotpot.commons.pagination.Page;
import com.hotpot.commons.pagination.PageInfo;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

// TODO: Auto-generated Javadoc

/**
 * The Class JMFastJsonHttpMessageConverter.
 */
public class JMFastJsonHttpMessageConverter extends
		FastJsonHttpMessageConverter {
	
	/**
	 * Instantiates a new JM fast json http message converter.
	 */
	public JMFastJsonHttpMessageConverter() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter#writeInternal(java.lang.Object, org.springframework.http.HttpOutputMessage)
	 */
	@Override
	protected void writeInternal(Object obj, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		// com.jumei.union.pagination.Page 是一个包含分页、排序及数据的List类型
		// 多数的 el/序列化 工具会将其做为集合类型而忽略其分页、排序信息。 
		// 此处将其转换为PageInfo使能顺利序残化
		if (obj instanceof Page<?>) {
			obj = new PageInfo((Page<?>) obj);
		}
		
		
		if(obj instanceof String){
			OutputStream out = outputMessage.getBody();
			out.write(Objects.toString(obj).getBytes("UTF-8"));
		}else{
			super.writeInternal(obj, outputMessage);
		}
	}

	/* (non-Javadoc)
	 * @see org.springframework.http.converter.AbstractHttpMessageConverter#canWrite(java.lang.Class, org.springframework.http.MediaType)
	 */
	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
//		return clazz != String.class && super.canRead(clazz, mediaType);
		return super.canWrite(clazz,mediaType);
	}
	
	/* (non-Javadoc)
	 * @see com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter#supports(java.lang.Class)
	 */
	@Override
	protected boolean supports(Class<?> clazz) {
		// IE8及以下浏览器通过/file/tmpView?fileKey=***, 这个方式来显示图片的兼容
		return ! (clazz.isArray() && (clazz.getComponentType() == byte.class || clazz.getComponentType() == Byte.class));
	}
}
