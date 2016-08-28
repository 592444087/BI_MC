import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;
public class Get_lat_lng {
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
		public void insert_lat_lng()
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
		        String lat_lng ="";
		        int i=0;
		        String sql_update = "";
		        con = DriverManager.getConnection(url, user, password);// 获取连接
		        System.out.println("连接成功！");
		        String sql = "select * from dim_area where area_lng_lat is null and area_id is not null";// 预编译语句，“？”代表参数
		        pre = con.prepareStatement(sql);// 实例化预编译语句
		        //pre.setString(1, "青田县");// 设置参数，前面的1表示参数的索引，而不是表中列名的索引
		        result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
		        while (result.next()){
		            // 当结果集不为空时
		        	http_url = "http://api.map.baidu.com/geocoder/v2/?address=" + result.getString("area_province") + result.getString("area_city") + result.getString("area_name") + "&output=json&ak=QvlVLk4tu4FCyCE2ypgu3kn2GkFvnbAG";
		        	JSONObject json = readJsonFromUrl(http_url);
		        	//System.out.println(json.toString());
		        	lat_lng = String.valueOf(json.getJSONObject("result").getJSONObject("location").get("lat")) + "," + String.valueOf(json.getJSONObject("result").getJSONObject("location").get("lng"));
		        	sql_update = "update dim_area set area_lng_lat = '" + lat_lng + "' where area_id = " + result.getString("area_id");
		        	//System.out.println(sql_update);
		        	pre_update = con.prepareStatement(sql_update);
		        	pre_update.executeUpdate();
		        	i++;
		        	if(i==200){
		        		i=0;
		        		result.close();
		        		pre.close();
		        		con.close();
		        		con = DriverManager.getConnection(url, user, password);
		        		pre = con.prepareStatement(sql);
		        		result = pre.executeQuery();
		        		System.out.println("重置了结果集。");
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
		        }
		        catch (Exception e)
		        {
		            e.printStackTrace();
		        }
		    }
		}	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Get_lat_lng().insert_lat_lng();
	}

}
