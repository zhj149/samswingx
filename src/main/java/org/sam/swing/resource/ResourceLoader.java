package org.sam.swing.resource;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 资源加载对象
 * 只加载一次
 * @author sam
 *
 */
public class ResourceLoader
{
	
	//begin constant resource
	
	/**
	 * 工具栏图标
	 */
	public static final String IMAGE_STATIC = "iconfont-stack2.png";
	
	/**
	 * 监视器图标
	 */
	public static final String IMAGE_MONITOR = "iconfont-monitor.png";
	
	/**
	 * 播放图标
	 */
	public static final String IMAGE_PLAY = "Play16x16.png";
	
	/**
	 * 暂停图标
	 */
	public static final String IMAGE_PAUSE = "Pause16x16.png";
	
	/**
	 * 停止按钮
	 */
	public static final String IMAGE_STOP = "Stop16x16.png";
	
	/**
	 * 停止动画按钮
	 */
	public static final String IMAGE_STOP_PRESS = "StopPress16x16.png";
	
	/**
	 * 开始仿真图标
	 */
	public static final String IMAGE_START_SIMULATOR = "export.png";
	
	/**
	 * 停止仿真图标
	 */
	public static final String IMAGE_STOP_SIMULATOR = "exit.png";
	
	/**
	 * 打开功能树窗口图标
	 */
	public static final String IMAGE_OPEN_NAVIGATION = "large_clipart.png";
	
	/**
	 * 打开仿真窗口图标
	 */
	public static final String IMAGE_OPEN_SIMULATOR = "Nitrogen.png";
	
	/**
	 * 打开图层窗口图标
	 */
	public static final String IMAGE_OPEN_LAYER = "add.png";
	
	/**
	 * 打开日志窗口图标
	 */
	public static final String IMAGE_OPEN_LOG = "filter.png";
	
	/**
	 * 打开图层窗口图标
	 */
	public static final String IMAGE_RETRIEVE = "filter.png";
	
	/**
	 * 新增操作按钮
	 */
	public static final String IMAGE_NEW = "new.png";
	
	/**
	 * 新增操作按钮
	 */
	public static final String IMAGE_ADD_SMALL = "edit_add.png";
	
	/**
	 * 移除操作小图片
	 */
	public static final String IMAGE_DEL_SMALL = "edit_remove.png";
	
	/**
	 * 移除操作小图片
	 */
	public static final String IMAGE_INSERT_SMALL = "insert.png";
	
	/**
	 * 修改按钮
	 */
	public static final String IMAGE_MODIFY = "modify.png";
	
	/**
	 * 删除按钮
	 */
	public static final String IMAGE_DELETE = "delete.png";
	
	/**
	 * 保存按钮
	 */
	public static final String IMAGE_SAVE = "save.png";
	
	/**
	 * 导入图片
	 */
	public static final String IMAGE_IMPORT = "large_smartart.png";
	
	/**
	 * 导出图片
	 */
	public static final String IMAGE_EXPORT = "large_shapes.png";
	
	/**
	 * 上移图片
	 */
	public static final String IMAGE_UP = "upenable.png";
	
	/**
	 * 下移图片
	 */
	public static final String IMAGE_DOWN = "downenable.png";
	
	/**
	 * 查询图片
	 */
	public static final String IMAGE_FIND = "find.png";
	
	/**
	 * 程序的logo
	 */
	public static final String IMAGE_LOGO = "logo.png";
	
	/**
	 * 打开shp图层的操作
	 */
	public static final String IMAGE_GEO_OPEN_SHP = "OpenLayer.png";
	
	/**
	 * 连接到数据库的图标
	 */
	public static final String IMAGE_GEO_CONNECT_DB = "connectdb.png";
	
	/**
	 * 选中图元对象的图标
	 */
	public static final String IMAGE_GEO_SELECTOR = "selectgeo.png";
	
	/**
	 * 图层数据集合
	 */
	public static final String IMAGE_GEO_DATASTORE = "datastore.png";
	
	/**
	 * 选中数据集合
	 */
	public static final String IMAGE_GEO_SELECTDATASTORE = "selecteddatastore.png";
	
	/**
	 * 新增点的图片
	 */
	public static final String IMAGE_POINT_ADD = "point.png";
	
	/**
	 * 线条的图片
	 */
	public static final String IMAGE_LINE = "line.png";
	
	/**
	 * 多边形的图片
	 */
	public static final String IMAGE_POLYGON = "polygon.png";
	
	/**
	 * 开始编辑的图片
	 */
	public static final String IMAGE_EDIT_BEGIN = "geoedit.png";
	
	/**
	 * 结束编辑的图片
	 */
	public static final String IMAGE_EDIT_END = "geosave.png"; 
	
	/**
	 * 选择地理信息对象图片
	 */
	public static final String IMAGE_GEO_SELECT = "selectgeo.png";
	/**
	 * 简单选择对象
	 */
	public static final String IMAGE_GEO_SIMPLE_SELECT = "pointer.png";
	/**
	 * 移除点的图片
	 */
	public static final String IMAGE_GEO_REMOVE = "removegeo.png";
	/**
	 * 编辑物理跑道图标
	 */
	public static final String IMAGE_ADD_RUNWAY = "runway.png";
	/**
	 * 编辑逻辑跑道图标
	 */
	public static final String IMAGE_ADD_RUNWAY_LOGIC = "runwayLogic.png";
	/**
	 * 地面点
	 */
	public static final String IMAGE_ADD_GROUND_POINT = "groundPoint.png";
	
	/**
	 * 滑行道编辑按钮
	 */
	public static final String IMAGE_ADD_TAXIWAY = "taxiway.png";
	
	/**
	 * 延长线功能
	 */
	public static final String IMAGE_GEO_EXTENSION_LINE = "extension-line.png";
	
	/**
	 * 停机位编辑按钮
	 */
	public static final String IMAGE_ADD_PARKINGPOINT = "parkingPoint.png";
			
	/**
	 * 跑道入口编辑按钮
	 */
	public static final String IMAGE_ADD_RUNWAYENTRY = "runwayEntry.png";
	
	/**
	 * 跑道出口编辑按钮
	 */
	public static final String IMAGE_ADD_RUNWAYEXIT = "runwayExit.png";
	
	//end
	
	/**
	 * 已加载的资源文件列表
	 */
	protected static Map<String , URL> resources = new HashMap<>();
	
	/**
	 * 获取资源文件
	 * @param name 资源名称
	 * @return 资源链接地址
	 */
	public static URL getResource(String name)
	{
		if (!resources.containsKey(name))
		{
			synchronized (resources) 
			{
				resources.put(name, ResourceLoader.class.getClassLoader().getResource(name));
			}
		}
		
		return resources.get(name);
	}
	
	/**
	 * 以流的方式获取资源
	 * @param name 资源名称 
	 * @return 
	 */
	public static InputStream getResourceStream(String name)
	{	
		return ResourceLoader.class.getClassLoader().getResourceAsStream(name);
	}
}
