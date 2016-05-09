package com.etl.es.entity;


/**
 * 切片日志对象
 * 
 * @author kerl
 *
 */
public class AspectEntity extends BaseEsEntity {

	public AspectEntity() {
	}

	public AspectEntity(String id, String methodName, String args) {
		super();
		this.id = id;
		this.methodName = methodName;
		this.args = args;
	}

	public AspectEntity(String id, String methodName, String args, String message) {
		super();
		this.id = id;
		this.methodName = methodName;
		this.args = args;
		this.message = message;
	}

	private String id;
	private String methodName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String args;
	private String message;
}
