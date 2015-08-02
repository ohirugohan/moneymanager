import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.thymeleaf.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import java.sql.*;


public class DaoTest extends HttpServlet {
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        // TemplateResolverを作成
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setTemplateMode("HTML5");  // テンプレートモード
        resolver.setCharacterEncoding("UTF-8");
        resolver.setPrefix("/WEB-INF/templates/");  // プレフィックス
        resolver.setSuffix(".html");                // サフィックス
        resolver.setCacheTTLMs(3600000L);  // キャッシュの有効時間
        
        String result = this.getData();
        out.println(77777);
        
        // TemplateEngineを作成
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);
        
        // WebContextを作成し、テンプレートの処理を実行
        WebContext ctx = new WebContext(request, response, this.getServletContext(), request.getLocale());
        ctx.setVariable("title", "タイトルuuuuuu");
        ctx.setVariable("message", result);
        // engine.process("daotest", ctx, out);
        engine.process("daotest", ctx, response.getWriter());
    }
    
    
    private String getData()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            
            // MySQLに接続
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/test", "testuser", "pw");
            // ステートメント生成
            Statement stmt = con.createStatement();

            // SQLを実行
            String sqlStr = "SELECT * FROM items";
            ResultSet rs = stmt.executeQuery(sqlStr);
            String result = "no_data";
            // 結果行をループ
            while(rs.next()){
                // レコードの値
                result = rs.getString("name");
            }

            // 接続を閉じる
            rs.close();
            stmt.close();
            con.close();
            
            return result;
            
        } catch (ClassNotFoundException e) {
            return "ClassNotFoundException";
        } catch (Exception e) {
            return "Exception";
        }
    }
}