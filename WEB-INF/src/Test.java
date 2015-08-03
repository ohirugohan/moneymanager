import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.thymeleaf.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

public class Test extends HttpServlet {
    
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
        
        // TemplateEngineを作成
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);
        
        // WebContextを作成し、テンプレートの処理を実行
        WebContext ctx = new WebContext(request, response, this.getServletContext(), request.getLocale());
        ctx.setVariable("title", "タイトルuuuuuu");
        ctx.setVariable("message", "メッセージiiiiii");
        engine.process("hellotemplate", ctx, response.getWriter());
    }
}