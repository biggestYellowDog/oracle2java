package org.wisestar.oracle2java;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月19日 下午11:40:24
 */
public class GenUtils {
	
	public static List<String> getTemplates(){
		List<String> templates = new ArrayList<String>();
		templates.add("template/Entity.java.vm");
		/*templates.add("template/Service.java.vm");
		templates.add("template/ServiceImpl.java.vm");*/
		//templates.add("template/Mapper.java.vm");
		//templates.add("template/Mapper.xml.vm");
		/*templates.add("template/MapperDto.java.vm");*/
		//templates.add("template/Resource.java.vm");
		/*
		templates.add("template/Controller.java.vm");
		templates.add("template/list.html.vm");
		templates.add("template/list.js.vm");*/
		return templates;
	}
	
	/**
	 * 生成代码
	 */
	public static void generatorCode(Map<String, String> table, 
			List<Map<String, String>> columns, ZipOutputStream zip){
		//配置信息
		Configuration config = getConfig();
		
		//表信息
		TableEntity tableEntity = new TableEntity();
		tableEntity.setTableName(table.get("tableName"));
		tableEntity.setComments(table.get("tableComment"));
		tableEntity.setTableNameInData(table.get("tableNameInData"));
		//表名转换成Java类名
		String className = tableEntity.getTableName();
		tableEntity.setClassName(className);
		tableEntity.setClassname(StringUtils.uncapitalize(className));
		
		//列信息
		List<ColumnEntity> columsList = new ArrayList<>();
		for(Map<String, String> column : columns){
			ColumnEntity columnEntity = new ColumnEntity();
			columnEntity.setColumnName(column.get("columnName")==null?"":column.get("columnName"));
			columnEntity.setDataType(column.get("dataType")==null?"":column.get("dataType"));
			columnEntity.setComments(column.get("columnComment")==null?"":column.get("columnComment"));
			columnEntity.setColumnNameInData(column.get("columnInTa")==null?"":column.get("columnInTa"));

			if("TIMESTAMP(6)".equals(columnEntity.getDataType()) || "TIMESTAMP(3)".equals(columnEntity.getDataType())|| "date".equals(columnEntity.getDataType()) || "datetime".equals(columnEntity.getDataType()) || "DATETIME".equals(columnEntity.getDataType()) || "DATE".equals(columnEntity.getDataType())){
				columnEntity.setAttTypeInData("TIMESTAMP");
			}else if("VARCHAR2".equals(columnEntity.getDataType()) || "NVARCHAR2".equals(columnEntity.getDataType()) || "CLOB".equals(columnEntity.getDataType())|| "BLOB".equals(columnEntity.getDataType())|| "CHAR".equals(columnEntity.getDataType())){
				columnEntity.setAttTypeInData("VARCHAR");
			}else if("FLOAT".equals(columnEntity.getDataType()) || "NUMBER".equals(columnEntity.getDataType())){
				columnEntity.setAttTypeInData("NUMERIC");
			}
			//列名转换成Java属性名
			String attrName = columnToJava(columnEntity.getColumnName());
			columnEntity.setAttrName(attrName);
			columnEntity.setAttrname(StringUtils.uncapitalize(attrName));
			
			//列的数据类型，转换成Java类型
			String attrType = config.getString(columnEntity.getDataType(), "unknowType");
			columnEntity.setAttrType(attrType);
			
			/*//是否主键
			if("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() != null){
				tableEntity.setPk(columnEntity);
			}*/
			
			columsList.add(columnEntity);
		}
		tableEntity.setColumns(columsList);
		
		//没主键，则第一个字段为主键
		if(tableEntity.getPk() == null){
			tableEntity.setPk(tableEntity.getColumns().get(0));
		}
		
		//设置velocity资源加载器
		Properties prop = new Properties();  
		prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");  
		Velocity.init(prop);
		
		//封装模板数据
		Map<String, Object> map = new HashMap<>();
		map.put("tableName", tableEntity.getTableName());
		map.put("comments", tableEntity.getComments());
		map.put("pk", tableEntity.getPk());
		map.put("className", tableEntity.getClassName());
		map.put("classname", tableEntity.getClassname());
		map.put("pathName", tableEntity.getClassname().toLowerCase());
		map.put("columns", tableEntity.getColumns());
		map.put("package", config.getString("package"));
		map.put("author", config.getString("author"));
		map.put("email", config.getString("email"));
		map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
		map.put("tableNameInData",tableEntity.getTableNameInData());
        VelocityContext context = new VelocityContext(map);
        
        //获取模板列表
		List<String> templates = getTemplates();
		for(String template : templates){
			//渲染模板
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, "UTF-8");
			tpl.merge(context, sw);
			
			try {
				//添加到zip
				zip.putNextEntry(new ZipEntry(getFileName(template, tableEntity.getClassName(), config.getString("package"))));  
				IOUtils.write(sw.toString(), zip, "UTF-8");
				IOUtils.closeQuietly(sw);
				zip.closeEntry();
			} catch (IOException e) {
				throw new RRException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
			}
		}
	}
	
	
	/**
	 * 列名转换成Java属性名
	 */
	public static String columnToJava(String columnName) {
		return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
	}
	
	/**
	 * 表名转换成Java类名
	 */
	public static String tableToJava(String tableName, String tablePrefix) {
		if(StringUtils.isNotBlank(tablePrefix)){
			tableName = tableName.replace(tablePrefix, "");
		}
		return columnToJava(tableName);
	}
	
	/**
	 * 获取配置信息
	 */
	public static Configuration getConfig(){
		try {
			return new PropertiesConfiguration("generator.properties");
		} catch (ConfigurationException e) {
			throw new RRException("获取配置文件失败，", e);
		}
	}
	
	/**
	 * 获取文件名
	 */
	public static String getFileName(String template, String className, String packageName){
		String packagePath = "";
		if(StringUtils.isNotBlank(packageName)){
			packagePath = packageName.replace(".", File.separator) + File.separator;
		}
		
		if(template.contains("Entity.java.vm")){
			return packagePath + "entity" + File.separator + className + ".java";
		}
		
		if(template.contains("Resource.java.vm")){
			return packagePath + "web" + File.separator  + "rest" + File.separator+ className + "Resource.java";
		}
		
		if(template.contains("Dao.xml.vm")){
			return packagePath + "dao" + File.separator + className + "Dao.xml";
		}
		
		if(template.contains("Service.java.vm")){
			return packagePath + "service" + File.separator + "util" +File.separator+ className + "Service.java";
		}
		
		if(template.contains("ServiceImpl.java.vm")){
			return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
		}
		
		if(template.contains("Mapper.java.vm")){
			return packagePath + "mapper" + File.separator + className + "Mapper.java";
		}
		
		if(template.contains("Mapper.xml.vm")){
			return packagePath + "repository" + File.separator + className + "Mapper.xml";
		}
		
		if(template.contains("MapperDto.java.vm")){
			return packagePath + "service"+ File.separator + "mapper"+ File.separator + className + "DTOMapper.java";
		}
		
		return null;
	}
}
