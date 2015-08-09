import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

public class HelloTemplateServlet extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html; charset=UTF-8");

        // TemplateResolverを作成
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setTemplateMode("HTML");  // テンプレートモード
        resolver.setPrefix("/WEB-INF/templates/");  // プレフィックス
        resolver.setSuffix(".html");                // サフィックス
        resolver.setCacheTTLMs(3600000L);  // キャッシュの有効時間

        // TemplateEngineを作成
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        // WebContextを作成し、テンプレートの処理を実行
        WebContext ctx = new WebContext(request, response, this.getServletContext(), request.getLocale());
        String result = engine.process("hello", ctx);
        // System.out.println(result);

        // 結果をクライアントに送信
        PrintWriter out = response.getWriter();
        try {
          out.println(result);
        } finally {
          out.close();
        }

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("肉");

        this.processRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.processRequest(request, response);
    }
}