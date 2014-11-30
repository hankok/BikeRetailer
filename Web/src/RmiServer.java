import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
public class RmiServer {
     /**  
        * 启动 RMI 注册服务并进行对象注册  
        */ 
       public static void main(String[] argv)   
       {   
          try 
          {   
             //启动RMI注册服务，指定端口为1099　（1099为默认端口）   
             //也可以通过命令 ＄java_home/bin/rmiregistry 1099启动   
             //这里用这种方式避免了再打开一个DOS窗口   
             //而且用命令rmiregistry启动注册服务还必须事先用RMIC生成一个占位程序(stub类)为它所用   
             LocateRegistry.createRegistry(1099);   //  for sup2, this is: LocateRegistry.createRegistry(8999); sup1: 1099
                                                                                                                                                                                                                                                                                            
             //创建远程对象的一个或多个实例，下面是hello对象   
             //可以用不同名字注册不同的实例   
             SupplyServer Supply = new SupplyServerImpl();   
                                                                                                                                                                                                                                                                                            
             //for sup2 : Hello2
             Naming.rebind("Hello1", Supply);   
                                                                                                                                                                                                                                                                                             
             //如果要把hello实例注册到另一台启动了RMI注册服务的机器上   
             //Naming.rebind("127.0.0.1:8999/hello",Supply);   
                                                                                                                                                                                                                                                                                            
             System.out.println("Supplier Server is ready.");   
          }
          catch (Exception e)   
          {   
             System.out.println("Supplier Server failed: " + e);   
          }   
       }  
}