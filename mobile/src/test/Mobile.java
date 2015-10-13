package test;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.junit.Test;

import entity.MobileArea;

public class Mobile {
	static Connection oracle_conn = null;
	static Statement oracle_stmt = null;
	ResultSet oracle_rs = null;

	/**
	 * Main
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		try {
			// MobileArea ma = getMobileArea("15922345647");
			// System.out.println(ma.toString());
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// oracle_conn =
			// DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.215:1521:orcl",
			// "username", "password");
			oracle_conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.11.100:1521:orcl", "username",
					"password");
			oracle_stmt = oracle_conn.createStatement();
			ResultSet rs = oracle_stmt.executeQuery("select * from t_customer");

			List<MobileArea> mas = new ArrayList<MobileArea>();
			while (rs.next()) {
				// String name = rs.getString("name") ;
				// String pass = rs.getString(1) ; // 此方法比较高效
				System.out.println(rs.getString(5));
				String mb = rs.getString(5);
				String cid = rs.getString(1);
				try {
					MobileArea mbo = getMobileArea(mb);
					mbo.setcId(cid);
					mas.add(mbo);
					save(mbo);
				} catch (Exception e) {
					continue;
				}
				// mbo.setcId(cid);
				// if(!hasMobile(mbo.getMbNum())){
				// mas.add(mbo);
				// save(mbo);
				// }
			}
			// for(MobileArea ma : mas){
			// save(oracle_stmt,ma);
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void save(MobileArea mas) throws SQLException {
		String rsIns = "insert into t_mobile_area(num, area, type, code, mail,cid) values (?,?,?,?,?,?)";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			oracle_conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.11.100:1521:orcl", "username",
					"password");
			oracle_conn.setAutoCommit(false);
			PreparedStatement p = oracle_conn.prepareStatement(rsIns);

			p.setString(1, mas.getMbNum());
			p.setString(2, mas.getMbType());
			p.setString(3, mas.getMbArea());
			p.setString(4, mas.getMbCode());
			p.setString(5, mas.getMbMail());
			p.setString(6, mas.getcId());
			p.executeUpdate();
			oracle_conn.commit();

		} catch (Exception e) {
			// 释放资源操作
			oracle_conn.close();
		}

	}

	public static void getRun(String mb) {
		Runnable r = new MobileRunnable(mb);
		Thread t = new Thread(r);
		t.start();
	}

	public static boolean hasMobile(String num) throws ClassNotFoundException,
			SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		oracle_conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@192.168.11.100:1521:orcl", "username",
				"password");
		String sql = "select * from t_mobile_area where num='" + num + "'";

		ResultSet rs1 = oracle_stmt.executeQuery(sql);
		
		while (rs1.next()) {
			System.out.println(rs1.getString(1));
			return true;
		}
		return false;
	}

	
	
	public static MobileArea getMobileArea(String mobile) throws Exception {
		Parser parser = new Parser((HttpURLConnection) (new URL(
				"http://www.ip138.com:8080/search.asp?mobile=" + mobile
						+ "&action=mobile")).openConnection());
		NodeFilter filter = new TagNameFilter("TD");
		NodeList nodes = parser.extractAllNodesThatMatch(filter);

		String regEx_html = "<[^>]+>";
		MobileArea ma = new MobileArea();
		if (nodes != null) {
			for (int i = 0; i < nodes.size(); i++) {
				if (i >= 6 && i % 2 == 0) {
					Node textnode = (Node) nodes.elementAt(i);
					Pattern p_html = Pattern.compile(regEx_html,
							Pattern.CASE_INSENSITIVE);
					Matcher m_html = p_html.matcher(textnode.toHtml());
					String htmlStr = m_html.replaceAll("");
					System.out.println("============" + i + "==========="
							+ htmlStr + "==========================");
					if (i == 6) {
						ma.setMbArea(htmlStr.replaceAll(
								"[^0-9\\u4e00-\\u9fa5]", ""));
					}
					if (i == 8) {
						ma.setMbType(htmlStr.replaceAll(
								"[^0-9\\u4e00-\\u9fa5]", ""));
					}
					if (i == 10) {
						ma.setMbCode(htmlStr.replaceAll(
								"[^0-9\\u4e00-\\u9fa5]", ""));
					}
					if (i == 12) {
						ma.setMbMail(htmlStr.replaceAll("[^0-9]", ""));
					}
				}
			}
			ma.setMbNum(mobile);
		}
		return ma;
	}
	
	@Test
	public void TestFun(){
		try {
			boolean b=hasMobile("18888888047");
			if(b){
				System.out.println("================================");
			}else{
				System.out.println("***********************************");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class MobileRunnable implements Runnable {
	public MobileRunnable(String mb) {
		this.mb = mb;
	}

	public void run() {
	}

	/*
	 * public void run() { try { List<Customer> cs=customerService.getAll();
	 * System.out.println(cs.size()); for(int i=1; i<=cs.size(); i++) {
	 * //ball.move(component.getBounds()); //component.repaint(); MobileArea ma
	 * = Mobile.getMobileArea(mb); System.out.println(ma.toString());
	 * Thread.sleep(DELAY); } } catch(Exception e) { e.printStackTrace(); } }
	 */

	private String mb;
	public static final int STEPS = 1000 * 100;
	public static final int DELAY = 5;

	// @Autowired
	// private CustomerService customerService;
}
