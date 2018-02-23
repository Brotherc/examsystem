package cn.examsystem.common.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;


/**
 * 系统提示消息封装类
 * @author brotherChun
 *
 */
public class ResultInfo
{
	public static final int STATUS_RESULT_OK= 200;//[GET],服务器成功返回用户请求的数据
	public static final int STATUS_RESULT_CREATED=201; //[POST/PUT/PATCH]用户新建或修改数据成功
	public static final int STATUS_RESULT_ACCEPTED=202; //[*]表示一个请求已经进入后台排队（异步任务）
	public static final int STATUS_RESULT_NO_CONTENT= 204;//[DELETE]用户删除数据成功
	public static final int STATUS_RESULT_INVALID_REQUEST=400; //[POST/PUT/PATCH]请求出现错误，比如请求头不对等
	public static final int STATUS_RESULT_UNAUTHORIZED=401; //[*]用户身份认证失败
	public static final int STATUS_RESULT_FORBIDDEN=403; //[*]用户授权失败
	public static final int STATUS_RESULT_NOT_FOUND= 404;//[*]请求的内容不存在。
	public static final int STATUS_RESULT_NOT_ACCEPTABLE= 406;//[GET]请求的资源并不符合要求,(比如用户请求JSON格式，但只有XML格式)
	public static final int STATUS_RESULT_GONE= 410;//[GET]请求资源曾经存在，但现在不存在了。
	public static final int STATUS_RESULT_UNPROCESABLE_ENTITY= 422;//[POST/PUT/PATCH]当创建一个对象时，发生一个验证错误
	public static final int STATUS_RESULT_INTERANL_SERVER_ERROR=500;//[*]服务器放生错误，

	// 定义jackson对象
	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * 响应业务状态
	 */
	private int status;

	/**
	 * 响应中的数据
	 */
	private Object data;
	   
	/**
	 * 响应消息
	 */
	private String message;

	/**
	 * 响应消息明细列表
	 */
	private List<ResultInfo> details;

	/**
	 * 响应消息对应操作的序号，方便用户查找问题，通常用于在批量提示消息中标识记录序号
	 */
	private int index;


	public ResultInfo(){}


	/**
	 * 构造函数,根据提交消息代码messageCode获取提示消息
	 * @param status
	 * @param message
	 */
	public ResultInfo(final int status,String message,Object data){
		this.status = status;
		this.message = message;
		this.data=data;
	}

	public ResultInfo(final int status,String message,Object data,List<ResultInfo>details){
		this.status = status;
		this.message = message;
		this.data=data;
		this.details=details;
	}
	
	public List<ResultInfo> getDetails() {
		return details;
	}

	public void setDetails(List<ResultInfo> details) {
		this.details = details;
	}

	public String getMessage() {
		return message;
	}
	
	
	public void setMessage(String message) {
		this.message = message;
	}


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isOk(){
		if(this.status >=200&&this.status<300){
			return true;
		}
		return false;
	}

	public static ResultInfo build(Integer status,String message, Object data) {
		return new ResultInfo(status,message, data);
	}

	/**
	 * 将json结果集转化为ResultInfo对象
	 *
	 * @param jsonData json数据
	 * @param clazz TaotaoResult中的object类型
	 * @return
	 */
	public static ResultInfo formatToPojo(String jsonData, Class<?> clazz) {
		try {
			if (clazz == null) {
				return MAPPER.readValue(jsonData, ResultInfo.class);
			}
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			if (clazz != null) {
				if (data.isObject()) {
					obj = MAPPER.readValue(data.traverse(), clazz);
				} else if (data.isTextual()) {
					obj = MAPPER.readValue(data.asText(), clazz);
				}
			}
			return build(jsonNode.get("status").intValue(),jsonNode.get("message").asText(), obj);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 没有object对象的转化
	 *
	 * @param json
	 * @return
	 */
	public static ResultInfo format(String json) {
		try {
			return MAPPER.readValue(json, ResultInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Object是集合转化
	 *
	 * @param jsonData json数据
	 * @param clazz 集合中的类型
	 * @return
	 */
	public static ResultInfo formatToList(String jsonData, Class<?> clazz) {
		try {
			JsonNode jsonNode = MAPPER.readTree(jsonData);
			JsonNode data = jsonNode.get("data");
			Object obj = null;
			if (data.isArray() && data.size() > 0) {
				obj = MAPPER.readValue(data.traverse(),
						MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
			}
			return build(jsonNode.get("status").intValue(),jsonNode.get("message").asText(), obj);
		} catch (Exception e) {
			return null;
		}
	}

}