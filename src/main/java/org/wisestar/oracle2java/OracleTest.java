package org.wisestar.oracle2java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

public class OracleTest {
    private Connection con = null;// 创建一个数据库连接
    private PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
    private ResultSet result = null;// 创建一个结果集对象

    /**
     * 一个非常标准的连接Oracle数据库的示例代码
     */
    public List<Map<String,String>> testOracle(String sql,String table) {
        List<Map<String,String>> list= new ArrayList<Map<String,String>>();
        Map<String,String>  map = new HashMap<String,String>();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
            System.out.println("开始尝试连接数据库！");
            String url = "jdbc:oracle:thin:@10.6.181.214:1521:schinta";//
            String user = "sjzyzh";// 用户名,系统默认的账户名
            String password = "sjzyzh";// 你安装时选设置的密码
            con = DriverManager.getConnection(url, user, password);// 获取连接
            System.out.println("连接成功！");
            pre = con.prepareStatement(sql);// 实例化预编译语句
            pre.setString(1, table);
            pre.setString(2, table);
            result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
            String tableName = "";
            String columnName = "";
            String dataType = "";
            String dataLength = "";
            String comments = "";
            String nullAble = "";
            String columnInTa = "";
            while (result != null && result.next()) {
                map = new HashMap<String,String>();
                tableName = result.getString("TABLE_NAME");
                columnName = result.getString("COLUMN_NAME");
                dataType = result.getString("DATA_TYPE");
                dataLength = result.getString("DATA_LENGTH");
                comments = result.getString("COMMENTS");
                nullAble = result.getString("NULLABLE");
                // System.out.println("表名为:" + tableName + ";字段名为:" + columnName + ";字段类型为:" + dataType + ";长度为:" + dataLength + ";注解为:" + comments);
                map.put("tableName",tableName);
                map.put("columnName",columnName);
                map.put("dataType",dataType);
                map.put("dataLength",dataLength);
                map.put("columnComment",comments);
                map.put("nullAble",nullAble);
                map.put("columnInTa",columnName);
                list.add(map);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                // 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
                // 注意关闭的顺序，最后使用的最先关闭
                if (result != null)
                    result.close();
                if (pre != null)
                    pre.close();
                if (con != null)
                    con.close();
                System.out.println("数据库连接已关闭！");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return list;
    }








    //测站对象标识   ZH_ST_O  stationObjectMark  StationObjectMark
    //测站基本信息   ZH_ST_B  stationBaseInfo   StationBaseInfo
    //河道防洪指标  ZH_ST_RVFCCH_B  riverFloodControlIndex   RiverFloodControlIndex  水文
    //水资源测站(地表水水质站)评价结果   ZH_ST_WQ_SWMSAR_A  waterResourcesAssessmentResult   WaterResourcesAssessmentResult  水资源
    //水文测站施测项目信息  ZH_ST_ITEM_B  dischargeSiteProInfo   DischargeSiteProInfo  水文
    //水库与水文测站监测关系  ZH_ST_RES_REL  reservoirDischargeSiteRelation  ReservoirDischargeSiteRelation  水文
    //水位流量关系曲线    ZH_ST_ZQRL_B   waterLevelDischargeCurve   WaterLevelDischargeCurve  水资源
    //测站设计与典型洪水过程  ZH_ST_SJDXHSGC_B  stationDesignTypicalFlood  StationDesignTypicalFlood  水资源

    //水电站  ZH_HYST_B  resPowerStation
    //水库基本信息  ZH_RES_B  resBasicInfo
    //水库特征值  ZH_RES_TZZ_B   resCharVal
    //水库下泄能力曲线  ZH_RES_XXNLQX_B   resDisCapCur
    //水库水位面积曲线  ZH_RES_SWMJQX_B   resWlAreaCurve
    //水库水位库容曲线  ZH_RES_SWKRQX_B   resWLCapCur
    //水库下游水位流量曲线  ZH_RES_XYSWLLQX_B   resDownWQCur
    //水库挡水建筑物信息  ZH_RES_DSJZW_B   resRetainStuctInfo
    //水库泄水建筑物信息  ZH_RES_XSJZW_B   resDischargeStuctInfo
    //水库图片信息  ZH_RES_VIDEOPIC_A   resPicInfo
    //库(湖)站汛限水位  ZH_ST_XXSW_B  reservoirLakeFloodControlLevel   ReservoirLakeFloodControlLevel  水资源
    //库(湖)容曲线   ZH_ST_ZVARL_B  reservoirLakeCurve   ReservoirLakeCurve  水资源
    //库(湖)防洪指标  ZH_ST_RSVRFCCH_B  reservoirLakeFloodControlIndex    ReservoirLakeFloodControlIndex  水文

    //实时降雨量  ZH_ST_PPTN_R  realTimeRainfall   RealTimeRainfall  水文
    //河道水情   ZH_ST_RIVER_R  riverWaterRegime   RiverWaterRegime   水文
    //河道(水库入库)水情预报  ZH_ST_FORECAST_F  riverWaterRegimeForecast   RiverWaterRegimeForecast  水文
    //水库水情  ZH_ST_RSVR_R  reservoirWaterRegime   ReservoirWaterRegime  水文
    //土壤墒情  ZH_ST_SOIL_R  soilMoisture    SoilMoisture  水文
    //水资源测站水质自动监测数据  ZH_ST_WQ_AWQMD_R  waterResourceQualityMonitorData   WaterResourceQualityMonitorData  水资源
    //水资源河道小时水量  ZH_ST_WR_HOURW_R   waterResourceRiverHourWater    WaterResourceRiverHourWater    水资源
    //水资源河道日水量信息  ZH_ST_WR_DAYW_R   waterResourceRiverDayWater    WaterResourceRiverDayWater   水资源
    //测站非金属无机物项目数据  ZH_ST_WQ_NMISP_R  stationNonmetallicInorganicProData     StationNonmetallicInorganicProData  水资源
    //测站酚类有机物项目数据   ZH_ST_WQ_PHNCP_R   stationPhenolOrganicCompoundsProData    StationPhenolOrganicCompoundsProData  水资源
    //测站金属无机物项目数据   ZH_ST_WQ_MISP_R    stationMetalInorganicMatterProData   StationMetalInorganicMatterProData   水资源
    //测站金属有机物及其他有机物项目数据   ZH_ST_WQ_MOOOP_R   stationMetallOrganicOtherOrganicProData   StationMetallOrganicOtherOrganicProData  水资源
    //测站水体卫生项目监测数据   ZH_ST_WQ_WBHP_R    stationWaterHygieneProMonitorData    StationWaterHygieneProMonitorData  水资源
    //测站推算流量信息  ZH_ST_WR_CQ_R  stationCalculationFlowInfo   StationCalculationFlowInfo  水资源

    //河道断面基本信息   ZH_RISE_B    riverSectionBaseInfo
    //控制断面基本信息  ZH_RISE_CSB_B   controlSectionBaseInfo
    //行政区界断面基本信息  ZH_RISE_AB_B   regionSectionBaseInfo
    //断面测验成果   ZH_RISE_CYCG_B   sectionTestResult
    //河道断面与水文测站关系  ZH_RISE_ST_REL   riverSectionDischargeSiteRelation

    static String table_zn = "水电站";
    static String table = "ZH_HYST_B";
    static String table_en = "resPowerStation";
    static  Map<String,String> t = new HashMap<String,String>();

    public static  void main(String [] args) throws java.io.FileNotFoundException{
        String sql = "SELECT A.TABLE_NAME,A.COLUMN_NAME,A.DATA_TYPE,A.DATA_LENGTH,(SELECT B.COMMENTS FROM USER_COL_COMMENTS B WHERE B.TABLE_NAME = ? AND B.COLUMN_NAME=A.COLUMN_NAME) AS COMMENTS,A.NULLABLE FROM USER_TAB_COLUMNS A WHERE A.Table_Name = ?";
        OracleTest te = new OracleTest();
        //te.getZhstbJson(table,sql);
        t.put("tableName",upperFirstChar(table_en));
        t.put("tableComment",table_zn);
        t.put("tableNameInData",table);
        List<Map<String,String>> result = te.testOracle(sql, table);
        //压缩文件名
        /*File objFile = new File("d://createResEntity/"+table+".zip");
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(objFile));
        GenUtils.generatorCode(t,result,zos);*/
    }

    public void getZhstbJson(String tableName,String sql) {
        String api = "station";//reservoir
        //从数据库取出列名等相关信息
        List<Map<String,String>> result = testOracle(sql, tableName);
        //String table_zn = underlineToCamel(tableName);
        StringBuffer news =new StringBuffer();
        StringBuffer reColumn =new StringBuffer();
        StringBuffer reComments = new StringBuffer();
        String s1 =  "    required:\n";
        String s2 =  "";
        reColumn.append(s1);
        reComments.append(s2);
        String newStr = "";
        OutputStream out= null;
        String comments = "";//注解
        String dataLength = "";//数据类型的长度
        String dataType = "";//数据类型
        String columnName = "";//列名
        String nullAble = "";//可否为空
        String findByStnmStr = "";//通过名称查
        String findByStcdStr = "";//通过编码查
        String findAllData = "";//查询所有数据
        String deleteByStcd = "";
        String defineColumeStr = "";
        List<String> requiredCulumn = new ArrayList<String>();
        String headerStr = "swagger: \"2.0\"\n" +
                           "info:\n" +
                          "  version: 1.0.0\n" +
                          "  title: "+ upperFirstChar(api) + "API\n" +
                          "  description: "+table_zn+"相关接口\n" +
                          "\n" +
                          "schemes:\n" +
                          "  - https\n" +
                          "host: "+api+".api\n" +
                          "basePath: /waterConsProj\n" +
                          "\n" +
                          "paths:\n";
        boolean c = true;
        boolean n = true;

        //先找出不能为空的列写到required里面
        for(Map<String,String>l:result){
            columnName = l.get("columnName");
            nullAble = l.get("nullAble");

            //把不能为空的字段名添加进集合中
            if(nullAble.equals("N")){
                requiredCulumn.add(columnName);
            }
        }

        //循环得出必须的字段并拼接
        if(!requiredCulumn.isEmpty()){
           for(String coulumn:requiredCulumn){
               String cou = "      - "+coulumn.toLowerCase()+"\n";
               reColumn.append(cou);
          }
            //将不能为空的字段携写到required属性下
            defineColumeStr =  "definitions:\n" +
                "  "+upperFirstChar(table_en)+"DTO:\n" +
                "    type: object\n" +
                "    description: '"+table_zn+"'\n" +
                reColumn+
                "    properties:\n";
          }else{
              defineColumeStr =  "definitions:\n" +
                "  "+upperFirstChar(table_en)+"DTO:\n" +
                "    type: object\n" +
                "    description: '"+table_zn+"'\n" +
                "    properties:\n";

          }

        //模型建完后的结尾语句
        String endStr = "    xml:\n" +
                        "      name: "+table_zn+"\n";

        //只有存在ST_CD字段的情况下才能通过编码查
        if(c) {
            //通过编码查询
             findByStcdStr = "  /"+api+"/{code}/" + table_en +"/:\n" +
                             "    get:\n" +
                             "      tags:\n" +
                             "        - " + table_zn + "\n" +
                             "      summary: 通过编码来找" + table_zn  + "\n" +
                             "      description: 得到" + table_zn + "\n" +
                             "      operationId: find" + upperFirstChar(table_en) + "ByCode\n" +
                             "      produces:\n" +
                             "        - application/json\n" +
                             "      parameters:\n" +
                             "        - name: code\n" +
                             "          in: path\n" +
                             "          description: 测站编码\n" +
                             "          required: true\n" +
                             "          type: string\n" +
                             "      responses:\n" +
                             "        '200':\n" +
                             "          description: 操作成功\n" +
                             "          schema:\n" +
                             "            type: array\n" +
                             "            items:\n" +
                             "              $ref: '#/definitions/" + upperFirstChar(table_en) + "DTO'\n"+
                             "        '400':\n" +
                             "          description: 无效的编码\n" +
                             "        '404':\n" +
                             "          description: 没有找到相应的" + table_zn + "\n";
        }

/*        //只有存在ST_NM的情况下才能通过名称查
        if(n) {
            //通过名称查询
            findByStnmStr = "  /"+api+"/" + table_en +  "/region/:\n" +
                            "    get:\n" +
                            "      tags:\n" +
                            "        - " + table_zn + "\n" +
                            "      summary: 通过行政区划编码来找" + table_zn + "\n" +
                            "      description: 得到" + table_zn + "\n" +
                            "      operationId: find" + upperFirstChar(table_en) + "ByRegionCode\n" +
                            "      produces:\n" +
                            "        - application/json\n" +
                            "      parameters:\n" +
                            "        - name: regionCode\n" +
                            "          in: header\n" +
                            "          description: 行政区划编码,为空时查所有.\n" +
                            "          required: false\n" +
                            "          type: string\n" +
                            "      responses:\n" +
                            "        '200':\n" +
                            "          description: 操作成功\n" +
                            "          schema:\n" +
                            "            type: array\n" +
                            "            items:\n" +
                            "              $ref: '#/definitions/" + upperFirstChar(table_en) + "DTO'\n"+
                            "        '400':\n" +
                            "          description: 无效的名称\n" +
                            "        '404':\n" +
                            "          description: 没有找到相应的" + upperFirstChar(table_zn) + "\n";
        }*/

        findAllData = "  /"+api+"/"+ table_en +"s/:\n" +
                      "    get:\n" +
                      "      tags:\n" +
                      "        - " + table_zn + "\n" +
                      "      summary: 查所有"+table_zn+"\n" +
                      "      description: 所有的"+table_zn+".并分页.\n" +
                      "      operationId: findAll" + upperFirstChar(table_en) + "s\n" +
                      "      produces:\n" +
                      "        - application/json\n" +
                      "      parameters:\n" +
                      "        - name: pageNumber\n" +
                      "          in: header\n" +
                      "          description: 页数\n" +
                      "          type: integer\n" +
                      "          required: false\n" +
                      "          default: 1\n"+
                      "        - name: pageSize\n" +
                      "          in: header\n" +
                      "          description: 每页数量\n" +
                      "          type: integer\n" +
                      "          default: 20\n"+
                      "      responses:\n" +
                      "        '200':\n" +
                      "          description: 所有的"+table_zn+"并分页\n" +
                      "          schema:\n" +
                      "            type: array\n" +
                      "            items:\n" +
                      "              $ref: '#/definitions/" + upperFirstChar(table_en) + "DTO'\n";
/*                      "  /"+api+"/"+ table_en +"s/outPage/:\n" +
                      "    get:\n" +
                      "      tags:\n" +
                      "        - " + table_zn + "\n" +
                      "      summary: 不分页查所有"+table_zn+"\n" +
                      "      description: 所有的"+table_zn+".不分页.\n" +
                      "      operationId: findAll" + upperFirstChar(table_en) + "sOutPage\n" +
                      "      produces:\n" +
                      "        - application/json\n" +
                      "      responses:\n" +
                      "        '200':\n" +
                      "          description: 所有的"+table_zn+"不分页\n" +
                      "          schema:\n" +
                      "            type: array\n" +
                      "            items:\n" +
                      "              $ref: '#/definitions/" + upperFirstChar(table_en) + "DTO'\n";*/

        deleteByStcd = "    delete:\n" +
                       "      tags:\n" +
                       "        - " + table_zn + "\n" +
                       "      summary: 通过编码来删除" + table_zn + "\n" +
                       "      description: '"+"通过编码来删除" + table_zn+"'\n" +
                       "      operationId: delete" + upperFirstChar(table_en) + "ByCode\n" +
                       "      produces:\n" +
                       "        - application/json\n" +
                       "      parameters:\n" +
                       "        - name: code\n" +
                       "          in: path\n" +
                       "          description: 测站编码\n" +
                       "          required: true\n" +
                       "          type: string\n" +
                       "      responses:\n" +
                       "        '400':\n" +
                       "          description: 提供的编码无效\n" +
                       "        '404':\n" +
                       "          description: 没有找到相应的"+table_zn+"\n";

               //1,先把方法的字符串加进去
                news = new StringBuffer(headerStr);
                news.append(findByStcdStr);
                news.append(deleteByStcd);
                news.append(findByStnmStr);
                news.append(findAllData);
                //2,把定义模型的字符串加进去
                news.append(defineColumeStr);
        try {
          for(Map<String,String>l:result){
              reComments = new StringBuffer();
              comments = l.get("columnComment");
              dataLength = l.get("dataLength");
              dataType = l.get("dataType");
              tableName = l.get("tableName");
              columnName = l.get("columnName");

              if(comments != null) {
                   String s3 = "        description: "+comments+"\n";
                   reComments.append(s3);
              }
              if(dataType.equals("VARCHAR2") || dataType.equals("NVARCHAR2") || dataType.equals("CHAR") || dataType.equals("CLOB") || dataType.equals("BLOB")){
                  dataType = "string\n";
              }
              else if(dataType.equals("FLOAT")){
                  dataType = "number\n" +
                                 "        format: float\n";
              }
              else if(dataType.equals("TIMESTAMP(6)") || dataType.equals("TIMESTAMP(3)")){
                  dataType = "string\n" +
                                 "        format: date-time\n";
              }
              else if(dataType.equals("NUMBER")){
                  dataType =  "number\n";
              }
              else if(dataType.equals("DATE")) {
                  dataType = "string\n" +
                          "        format: date\n";
              }
              else {
                  dataType = "\n";
              }
                  String s = "      "+columnName.toLowerCase()+":\n" +
                                        reComments+
                             "        type: "+dataType;
                 //通过循环把模型中的各种字段属性加进去
                  news.append(s);
             }

            //把尾端加进去并转成String类型写入到对应的文件中
            news.append(endStr);
            newStr = news.toString();

            // 第1步、使用File类找到一个文件
            File f = new File("d://createYaml/"+ table_en +".yaml");    // 声明File对象
            // 第2步、通过子类实例化父类对象
            out = new FileOutputStream(f);
            byte b[] = newStr.getBytes();
            for (int i = 0; i < b.length; i++) {
                out.write(b[i]);
            }
            // 第3步、关闭输出流
            out.close();
         }catch(Exception e){
            e.printStackTrace();
      }
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串
     *
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        String newParam = param.toLowerCase();
        if (newParam == null || "".equals(newParam.trim())) {
            return "";
        }
        int len = newParam.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = newParam.charAt(i);
            if (c == '_') {
                if (++i < len) {
                    sb.append(Character.toUpperCase(newParam.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    //首字母大写
    public static String upperFirstChar(String str) {
        char[] cs=str.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);

    }



}
