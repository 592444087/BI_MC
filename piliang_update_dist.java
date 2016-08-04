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
		     // System.out.println("ͬʱ ������Ҳ�ܿ��� ����return�ˣ���Ȼ��ִ��finally�ģ�");
		    }
		  }
		/**
		 * һ���ǳ���׼������Oracle���ݿ��ʾ������
		 */
		public void Conn_to_Oracle(int s)
		{
		    Connection con = null;// ����һ�����ݿ�����
		    PreparedStatement pre = null;// ����Ԥ����������һ�㶼�������������Statement
		    PreparedStatement pre_update = null;
		    ResultSet result = null;// ����һ�����������
		    try
		    {
		        Class.forName("oracle.jdbc.driver.OracleDriver");// ����Oracle��������
		        System.out.println("��ʼ�����������ݿ⣡");
		        String url = "jdbc:oracle:" + "thin:@192.168.12.43:1521:testdb";// 127.0.0.1�Ǳ�����ַ��XE�Ǿ����Oracle��Ĭ�����ݿ���
		        String user = "DMDB";// �û���,ϵͳĬ�ϵ��˻���
		        String password = "HZMC20160118";// �㰲װʱѡ���õ�����
		        String http_url = "";//���ðٶ�api���ַ���
		        String dist_s = "";//�˾���ٹ���
		        Float dist;
		        int i=0;
		        //ArrayList<Object[]> list = new ArrayList<Object[]>();
		        /*'����','����','����','�㽭','����','����','�ຣ','����ʡ��'
		         * '�㶫','����','����','����','����','����','���'
		         * '�ӱ�','����','ɽ��','�Ϻ�','����','�Ĵ�','����','����'
		         * '����','�½�ά���������','ɽ��','���ɹ�������','����������','������','����','����','���Ļ���������'
		         * */			        
		        String sql_update = "";
		        con = DriverManager.getConnection(url, user, password);// ��ȡ����
		        System.out.println("���ӳɹ���");
		        String sql = "select * from t_route where dist is null and area_id_s <> area_id_e and province_s = '����' and "
		        		+ "province_e not in ('����','����','����','�㽭','����','����','�ຣ','�㶫','����','����')";// Ԥ������䣬�������������
	        	sql_update = "update t_route set dist = ? where area_id_s = ? and area_id_e =?";		        
		        pre = con.prepareStatement(sql);// ʵ����Ԥ�������
		        result = pre.executeQuery();// ִ�в�ѯ��ע�������в���Ҫ�ټӲ���
		        pre_update = con.prepareStatement(sql_update);
		        while (result.next()){
		            // ���������Ϊ��ʱ
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
		        	//System.out.println(i+":"+dist_s);  //90s���ðٶȽӿڲ�ѯ200����·�˾��¼
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
		        				System.out.println("���ǲ��У�û�н���رյ������������"); 
		        				e1.printStackTrace();
		        			}		        			
		        		}
		        		/*
		        		if(con == null){
		        			System.out.println("�رյ����ӣ������������ݿ⣬ִ�и�����䡣");
		        			con = DriverManager.getConnection(url, user, password);// ��ȡ����
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
		        		System.out.println("�����˽������");//����:75sһ��ѭ����200����¼
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
		            // ��һ������ļ�������رգ���Ϊ���رյĻ���Ӱ�����ܡ�����ռ����Դ
		            // ע��رյ�˳�����ʹ�õ����ȹر�
		            if (result != null)
		                result.close();
		            if (pre != null)
		                pre.close();
		            if (con != null)
		                con.close();
		            System.out.println("���ݿ������ѹرգ�");
		            System.out.println("������������");
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
