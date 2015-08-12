package moneymanager.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

abstract class AbstractController extends HttpServlet {
    /**
     * get or post
     */
    private String mode;

    /**
     * テンプレート処理を扱う
     */
    private ServletContextTemplateResolver resolver;

    /**
     * テンプレートに渡す変数をごにょる
     */
    private WebContext context;

    private HttpServletRequest request;

    private HttpServletResponse response;

    public AbstractController() {
        // テンプレートの設定
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setTemplateMode("HTML5");  // テンプレートモード
        resolver.setCharacterEncoding("UTF-8");
        resolver.setPrefix("/WEB-INF/templates/");  // プレフィックス
        resolver.setSuffix(".html");                // サフィックス
        resolver.setCacheTTLMs(3600000L);  // キャッシュの有効時間
        this.resolver = resolver;

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.setCommonProperty(request, response, "get");
        this.action();
        this.showTemplate();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.setCommonProperty(request, response, "post");
        this.action();
        this.showTemplate();
    }

    private void setCommonProperty(HttpServletRequest request, HttpServletResponse response, String mode) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        this.mode = mode;
        this.context = new WebContext(request, response, this.getServletContext(), request.getLocale());
        this.request = request;
        this.response = response;
    }

    private void showTemplate(){
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(this.resolver);
        try {
            engine.process(this.getTemplateName(), this.context, this.response.getWriter());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * テンプレートに変数をアサインします。
     * @param name
     * @param value
     */
    protected void assign(String name, Object value) {
        this.context.setVariable(name, value);
    }

    /**
     * リクエストされたGet, Postパラメータ取得します。
     * @param paramName
     * @return
     */
    protected String getParameter(String paramName){
        return this.request.getParameter(paramName);
    }

    /**
     * Postパラメータを取得したのか、Getパラメータを取得したのか取得します。
     * @return
     */
    protected String getMode()
    {
        return this.mode;
    }

    /**
     * デバッグ用に文字列を出力します。
     * @param text
     */
    protected void debug(String text) {
        PrintWriter out;
        try {
            out = this.response.getWriter();
            out.println(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * デバッグ用に文字列を出力します。
     * @param number
     */
    protected void debug(int number) {
        String text = Integer.toString(number);
        this.debug(text);
    }

    /**
     * この関数の中で、テンプレート名を返してください。
     * @return string
     */
    abstract String getTemplateName();

    /**
     * この関数内に、実処理を記述してください。
     */
    abstract void action();
}
