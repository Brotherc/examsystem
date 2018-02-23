package cn.examsystem.common.utils;

import cn.examsystem.common.pojo.ExceptionResultInfo;
import cn.examsystem.common.pojo.ResultInfo;
import cn.examsystem.common.pojo.SubmitResultInfo;

import java.util.List;


/**
 * 系统结果工具类
 * @author mrt
 *
 */
public class ResultUtil {

    /**
     * 创建自定义提示结果
     */
    public static ResultInfo createResultInfo(String fileName,String messageKey,Object[] objs,int status,Object data){
    	
    	String message=null;
    	if(objs == null){
    		message = ResourcesUtil.getValue(fileName, messageKey);
    	}else{
    		message = ResourcesUtil.getValue(fileName, messageKey,objs);
    	}
    	return new ResultInfo(status,message,data);
    }

    
	/**
	 * 抛出异常
	 * @param resultInfo
	 * @throws ExceptionResultInfo
	 */
    public static void throwExcepion(ResultInfo resultInfo) throws ExceptionResultInfo {
		throw new ExceptionResultInfo(resultInfo);
	}
	public static void throwExcepion(ResultInfo resultInfo,List<ResultInfo> details) throws ExceptionResultInfo{
		if(resultInfo != null){
			resultInfo.setDetails(details);
		}
		throw new ExceptionResultInfo(resultInfo);
	}
	/**
	 * 创建提交结果信息
	 * @param resultInfo
	 * @return
	 */
	public static SubmitResultInfo createSubmitResult(ResultInfo resultInfo){
		return new SubmitResultInfo(resultInfo);
	}
	/**
	 * 创建提交结果信息，包括明细信息
	 * @param resultInfo
	 * @param details
	 * @return
	 */
	public static SubmitResultInfo createSubmitResult(ResultInfo resultInfo,List<ResultInfo> details){
		if(resultInfo != null){
			resultInfo.setDetails(details);
		}
		return new SubmitResultInfo(resultInfo);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
