import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;
public class piliang_update_dist {
	  private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
		  }
	  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		   	InputStream is = new URL(url).openStream();
		    try {
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		      return json;
		    } finally {
		      is.close();
		     // System.out.println("同时 从这里也能看出 即便return了，仍然会执行finally的！");
		    }
		  }
		/**
		 * 一个非常标准的连接Oracle数据库的示例代码
		 */
		public void Conn_to_Oracle(int s)
		{
		    Connection con = null;// 创建一个数据库连接
		    PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		    PreparedStatement pre_update = null;
		    ResultSet result = null;// 创建一个结果集对象
		    try
		    {
		        Class.forName("oracle.jdbc.driver.OracleDriver");// 加载Oracle驱动程序
		        System.out.println("开始尝试连接数据库！");
		        String url = "jdbc:oracle:" + "thin:@192.168.12.43:1521:testdb";// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
		        String user = "DMDB";// 用户名,系统默认的账户名
		        String password = "HZMC20160118";// 你安装时选设置的密码
		        String http_url = "";//调用百度api的字符串
		        String dist_s = "";//运距多少公里
		        Float dist;
		        int i=0;
		        //ArrayList<Object[]> list = new ArrayList<Object[]>();
		        /*'江苏','贵州','云南','浙江','福建','湖南','青海','其他省份'
		         * '广东','甘肃','北京','河南','重庆','陕西','天津'
		         * '河北','江西','山西','上海','海南','四川','湖北','辽宁'
		         * '吉林','新疆维吾尔自治区','山东','内蒙古自治区','西藏自治区','黑龙江','安徽','广西','宁夏回族自治区'
		         * */			        
		        String sql_update = "";
		        con = DriverManager.getConnection(url, user, password);// 获取连接
		        System.out.println("连接成功！");
		        String sql = "select * from t_route where dist is null and area_id_s <> area_id_e and province_s = '河南' and "
		        		+ "province_e not in ('江苏','贵州','云南','浙江','福建','湖南','青海','广东','甘肃','北京')";// 预编译语句，“？”代表参数
	        	sql_update = "update t_route set dist = ? where area_id_s = ? and area_id_e =?";		        
		        pre = con.prepareStatement(sql);// 实例化预编译语句
		        result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
		        pre_update = con.prepareStatement(sql_update);
		        while (result.next()){
		            // 当结果集不为空时
		        	http_url = "http://api.map.baidu.com/direction/v1/routematrix?output=json&origins=" + result.getString("lng_lat_s") +
		        			 "&destinations=" + result.getString("lng_lat_e") + "&ak=QvlVLk4tu4FCyCE2ypgu3kn2GkFvnbAG";
		        	JSONObject json = readJsonFromUrl(http_url);
		        	dist_s = ((JSONObject)((JSONObject)json.getJSONObject("result").getJSONArray("elements").get(0)).get("distance")).get("text").toString();
		        	dist_s = dist_s.substring(0,dist_s.length()-2);
		        	if(dist_s!="" && dist_s.length()>0){
		        		dist = Float.parseFloat(dist_s);
		        	}else{
		        		dist = -1f;//-1.0
		        	}	
		        	//Object[] ul = {dist,result.getString("code_s"),result.getString("code_e")};
		        	//list.add(ul);
		        	
		        	pre_update.setFloat(1, dist);
		        	pre_update.setString(2, result.getString("area_id_s"));
		        	pre_update.setString(3, result.getString("area_id_e"));
		        	pre_update.addBatch();
		        	
		        	i++;
		        	//System.out.println(i+":"+dist_s);  //90s调用百度接口查询200条线路运距记录
		        	if(i==200){
		        		//JDBCUtils.batchUpdate(sql_update, list);
		        		//list.removeAll(list);
		        		//pre_update.executeBatch();
		        		try{
		        			pre_update.executeBatch();
		        		}catch(Exception e){
		        			e.printStackTrace();
		        			try{
		        				con.close();
		        				con = DriverManager.getConnection(url, user, password);
		        				pre_update.executeBatch();
		        			}catch(Exception e1){
		        				System.out.println("还是不行，没有解决关闭的连接这个问题"); 
		        				e1.printStackTrace();
		        			}		        			
		        		}
		        		/*
		        		if(con == null){
		        			System.out.println("关闭的连接，重新连接数据库，执行更新语句。");
		        			con = DriverManager.getConnection(url, user, password);// 获取连接
		        			pre_update.executeBatch();
		        		}else{
		        			pre_update.executeBatch();
		        		}*/
		        		con.commit();
		        		i=0;
		        		result.close();
		        		pre.close();
		        		pre_update.close();
		        		con.close();
		        		con = DriverManager.getConnection(url, user, password);
		        		pre = con.prepareStatement(sql);
		        		pre_update = con.prepareStatement(sql_update);
		        		result = pre.executeQuery();
		        		s = 0;
		        		System.out.println(s);
		        		System.out.println("重置了结果集。");//性能:75s一次循环，200条记录
		        	}
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
		            System.out.println("重新启动程序");
		            s++;
		            System.out.println(s);
		            if(s<=10){
		            	new piliang_update_dist().Conn_to_Oracle(s);
		            }else{
		            	System.exit(1);
		            }	
		        }
		        catch (Exception e)
		        {
		            e.printStackTrace();
		        }
		    }
		}	
	public static void main(String[] args) {
		int s = 0;
		new piliang_update_dist().Conn_to_Oracle(s);// TODO Auto-generated method stub

	}

}
