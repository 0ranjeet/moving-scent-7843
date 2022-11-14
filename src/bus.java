import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class DBUtil {
	
	
	public static Connection provideConnection() {
		
		Connection conn = null;
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String url= "jdbc:mysql://localhost:3306/busticket";
		
		try {
			conn = DriverManager.getConnection(url, "root", "007ranjeet");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return conn;
	}
}

public class bus {
	public bus() {
		
	}
	
	 public static void main(String[] args) throws Exception {
    	 try {
 			Class.forName("com.mysql.cj.jdbc.Driver");
 		} catch (ClassNotFoundException e1) {
 			
 			e1.printStackTrace();
 		}
 		bus b1=new bus();
 		System.out.println();
		System.out.println("                                  ******************************************************************************");
		System.out.println("                ******************              WELCOME  TO   BUS  TICKET  RESEVATION  SYSTEM                *******************");
		System.out.println("         		           ******************************************************************************");

		System.out.println("             		       **************************************************************************************");
 		System.out.println();
 		System.out.println();
		

 		
 		b1.show();
 		

}
	 static String bustype(int a) {
		 if(a==1) {
			 return "ac";
		 }else {
			 return "nonac";
		 }
	 }
	static int ft(String a,String b,String c,String d) {
		
		int mat=0;
		if(a.equals(b) && c.equals(d)) {
			 mat=1;
		}else {
			return 0;
		}
		
		return mat;
	}
	
	static void fft() throws Exception {
		try (Scanner scanner = new Scanner(System.in)) {
			bus b1=new bus();
			Connection conn=DBUtil.provideConnection();
			System.out.println("Admin Log in");
			System.out.println("User Name :" );
			System.out.println("=========");
			String uStr=scanner.next();
			PreparedStatement u=conn.prepareStatement("select * from login_data where username like '"+uStr+"'");
			Statement st= conn.createStatement();
			ResultSet ui=u.executeQuery();
			
			while(ui.next()) {
			String up=ui.getString("username");
			System.out.println();
			 int a=bus.ffbt(uStr, up);
			 System.out.println();
			 if(a>=1) {
				 PreparedStatement u1=conn.prepareStatement("select password from login_data where username like '"+uStr+"'");
					ResultSet ui1=u1.executeQuery();
					while(ui1.next()) {
					String up1=ui1.getString(1);
					System.out.println();
					System.out.println("Password :" );
					System.out.println("=========");
					String pwd=scanner.next();
					int a2=bus.ffbt(up1, pwd);
					
					if(a2>=1) {
					System.out.println("welcome :"+uStr);
					System.out.println("e.enter bus detail: \nc.confirm seats:");
					String ai=scanner.next();
					switch (ai) {
					case "e": {
						System.out.println("Bus Name:");
						System.out.println("=========");
						String bname=scanner.next();
						System.out.println("Bus from:");
						System.out.println("=========");
						String brout=scanner.next();
						System.out.println("Bus to:");
						System.out.println("=========");
						String brin=scanner.next();
						System.out.println("Bus Type \n1.ac \n2.nonac:");
						System.out.println("=========");
						int pbt=scanner.nextInt();
						String obty=bus.bustype(pbt);
						System.out.println("Seats count :");
						System.out.println("=========");
						int sec=scanner.nextInt();
						System.out.println("Departure Time :");
						System.out.println("=========");
						String dept=scanner.next();
						System.out.println("Arival Time :");
						System.out.println("=========");
						String Avrt=scanner.next();
						st.executeUpdate("insert into bus(busname,location,tow,Bus_type,Seat,Booked,Departure_time,Arrival_time) values('"+bname+"','"+brout+"','"+brin+"','"+obty+"',"+sec+",null,'"+dept+"','"+Avrt+"')");
						System.out.println();
						System.out.println("+++++++===+++++++");
						System.out.println();
						 
					}
					case "c": {
						PreparedStatement buytkt=conn.prepareStatement("select * from bus ");
						ResultSet bi=buytkt.executeQuery();
						System.out.println("Giving conformation");
						System.out.println("+-----------------+");
						while(bi.next()) {
							String obn=bi.getString("busname");
							
							System.out.println("|      "+obn+"     |");
							System.out.println("+-----------------+");
						}
						System.out.println("confirm by bus name");
						System.out.println("+-----------+");
						System.out.println();
						String obn=scanner.next();
						
						PreparedStatement tktc=conn.prepareStatement("select * from bus where busname like '"+obn+"'");
						ResultSet de=tktc.executeQuery();
						de.next();
						int cs=de.getInt("seat");
						int sa=de.getInt("booked");
						if(cs<sa) {
							System.out.println("It can't be confirmed there is less seat avilable than "+sa);
						}else if(cs>=sa) {
							st.executeUpdate("update bus set booked=booked-"+sa+" where busname like '"+obn+"'");
							System.out.println("in "+obn+" bus "+sa+" ticket confirmed");
							int sl=de.getInt("seat");
							System.out.println("in "+obn+" bus there "+(sl-sa )+"seats left");
							st.executeUpdate("update bus set seat="+(cs-sa )+" where busname like '"+obn+"'");
							st.executeUpdate("update bus set booked=0 where busname like '"+obn+"'");
							PreparedStatement ctktc=conn.prepareStatement("select * from customer ");
							ResultSet cde=ctktc.executeQuery();
							System.out.print("+-------------------------------------------------------------------------------------------------------------------------+");
							System.out.printf("\n| %-10s  | %-10s | %-10s  | %-10s | %-10s  | %-10s | %-10s | %-10s | %-10s |", "CUSTOMER","BUS NAME", "FROM","TO","AC/NONAC","MOBILE", "DPRT.TIME","ARV.TIME","CONFIRMATION","\n" );
							System.out.println();
							System.out.print("+-------------------------------------------------------------------------------------------------------------------------+");
							while(cde.next()) {
								String c1=cde.getString("cname");
								String c2=cde.getString("bname");
								String c3=cde.getString("location");
								String c4=cde.getString("tol");
								String c5=cde.getString("bus_type");
								int c6=cde.getInt("mob");
								String c7=cde.getString("dtime");
								String c8=cde.getString("atime");
								boolean c9=true;
								System.out.printf("\n| %-10s  | %-10s | %-10s  | %-10s | %-10s  | %-10s | %-10s | %-10s | %-10s |\"",c1, c2,c3,c4,c5,c6,c7,c8,c9,"\n" );
								System.out.println();
								System.out.print("+-------------------------------------------------------------------------------------------------------------------------+");
								
							}
							System.out.println();
							System.out.println();
							System.out.println("+++++++++===++++++++");
							System.out.println();
							System.out.println();
							b1.show();
						}
						
						System.out.println("+++++++++===++++++++");
						 b1.re();	
						
					}
					}
					}
						}

				}else {
					System.out.println("+++++++===++++++++");
					bus.fft();
				}
			}
		}
	
			}

	static int ffbt(String a,String b) {
		int mat=0;
		if(a.equals(b) ) {
			 mat++;
		}else {
			return 0;
		}
		return mat;
	}
	void show() throws Exception {
		bus b1=new bus();
		Connection conn=DBUtil.provideConnection();
		Statement st= conn.createStatement();
		Scanner scanner=new Scanner(System.in);
		PreparedStatement Obuytkt=conn.prepareStatement("select * from bus ");
		ResultSet obi=Obuytkt.executeQuery();
		System.out.print("+-----------------------------------------------------------------------------------------------------------+");
		System.out.printf("\n| %-10s  | %-10s | %-10s  | %-10s | %-10s  | %-10s | %-10s  | %-10s |", "BUS NAME", "FROM","TO","AC/NONAC","SEAT AVL.","BOOKED", "DPRT.TIME","ARV.TIME","\n" );
		System.out.println();
		System.out.print("+-----------------------------------------------------------------------------------------------------------+");
		while(obi.next()) {
			
			String bn=obi.getString("busname");
			String bf=obi.getString("Location");
			String bt=obi.getString("tow");
			String bty=obi.getString("bus_type");
			int bs=obi.getInt("seat");
			int bbk=obi.getInt("booked");
			String bda=obi.getString("Departure_time");
			String bat=obi.getString("arrival_time");
			
			System.out.printf("\n| %-10s  | %-10s | %-10s  | %-10s | %-10s  | %-10s | %-10s  | %-10s |",bn, bf,bt,bty,bs,bbk,bda,bat,"\n" );
			System.out.println();
			System.out.printf("+-----------------------------------------------------------------------------------------------------------+");
		
		}
System.out.println();
System.out.println();
b1.re();
scanner.close();
	}
	void bkbybn(List<String> sl) throws Exception {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println();
			System.out.println("Enter the name of Bus want to book:");
			System.out.println("------------------------------------");
			String bna=scanner.next();
			bus b1=new bus();
			int a1=0;
			for(String s:sl) {
				if(s.equals(bna)) {
					a1++;
				}
			}
			if(a1>=1) {
				Connection conn=DBUtil.provideConnection();
				Statement st= conn.createStatement();
				PreparedStatement Obuytkt=conn.prepareStatement("select * from bus ");
				ResultSet obi=Obuytkt.executeQuery();
				System.out.println();
			System.out.println();
			System.out.println();
			 System.out.println("Number of seat want to book? :");
			    System.out.println("-------------------------------");
				String tb=scanner.next();
				st.executeUpdate("update bus set booked="+tb+" where busname like '"+bna+"'");
				st.executeUpdate("update bus set seat=seat-"+tb+" where Busname like '"+obi+"'");
				System.out.println("wait for confirmation");
				System.out.println("+++++++++++==++++++++++++");
				Thread.sleep(2000);
				b1.show();
				scanner.close();
				;
			
}else {
			b1.bkbybn(sl);
}
		}
	}
	void re() throws Exception{
		bus b1=new bus();
		
		Connection conn=DBUtil.provideConnection();
		Statement st= conn.createStatement();
		Scanner scanner=new Scanner(System.in);
		PreparedStatement Obuytkt=conn.prepareStatement("select * from bus ");
		ResultSet obi=Obuytkt.executeQuery();

		
					System.out.println("1.buy ticket \n2.admin");
					System.out.println("------------------");
					System.out.println();
					int in1=scanner.nextInt();
					switch (in1) {
					case 1: {
						System.out.println("Name :");
						System.out.println("----------");
						String cn=scanner.next();
						System.out.println("Dprt. From Location:");
						System.out.println("----------------------");
						String frm=scanner.next();
						System.out.println("To Location:");
						System.out.println("----------------------");
						String to=scanner.next();
						System.out.println("Mob :");
						System.out.println("---------------");
						String tel=scanner.next();
						PreparedStatement Obuytkt1=conn.prepareStatement("select * from bus where location like '"+frm+"' and tow like'"+to+"' ");
						ResultSet obi1=Obuytkt1.executeQuery();
						
						while(obi1.next()) {
							
							
							String obf=obi1.getString("Location");
							String obt=obi1.getString("tow");
							 int a= bus.ft(obf, frm, obt, to);
						System.out.println();
							if(a>=1) {
								
								PreparedStatement buytkt=conn.prepareStatement("select * from bus where location like'"+obf+"' and tow like '"+obt+"' ");
								System.out.println(obt+","+obf);
								ResultSet bi=buytkt.executeQuery();
								List<String> sList=new ArrayList<String>();
								System.out.print("+-----------------------------------------------------------------------------------------------------------+");
								System.out.printf("\n| %-10s  | %-10s | %-10s  | %-10s | %-10s  | %-10s | %-10s  | %-10s |", "BUS NAME", "FROM","TO","AC/NONAC","SEAT AVL.","CONFORMED", "DPRT.TIME","ARV.TIME","\n" );
								System.out.println();
								System.out.printf("+-------------|------------|-------------|------------|-------------|------------|-------------|------------+");
								while(bi.next()) {
								String obn=bi.getString("busname");
								sList.add(obn);
								String oobf=bi.getString("Location");
								String oobt=bi.getString("tow");
								String obty=bi.getString("bus_type");
								int obs=bi.getInt("seat");
								int obbk=bi.getInt("booked");
								String obda=bi.getString("Departure_time");
								String obat=bi.getString("arrival_time");
								
								
								System.out.printf("\n| %-10s  | %-10s | %-10s  | %-10s | %-10s  | %-10s | %-10s  | %-10s |",obn, oobf,oobt,obty,obs,obbk,obda,obat,"\n" );
								System.out.println();
								System.out.printf("+-----------------------------------------------------------------------------------------------------------+");
								st.executeUpdate("insert into customer(cname,bname,location,tol,Bus_type,Mob,dtime,atime ,confirmed ) values('"+cn+"','"+obn+"','"+obf+"','"+obty+"',"+obbk+",'"+tel+"','"+obda+"','"+obat+"',False)");
								}
								
								b1.bkbybn(sList);
								
								}else {
									System.out.println();
									System.out.println();
									System.out.println("+++++++++++==++++++++++++");
									System.out.println();
									System.out.println();
									b1.re();
								}
							
						}
						scanner.close();
						b1.show();
					
		}case 2: {
			
			
			bus.fft();
			
			
		}
				
			
		
		
	}
					
		scanner.close();
	}
	
}
