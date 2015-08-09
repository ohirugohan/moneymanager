package moneymanager.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import moneymanager.dataaccess.TestDao;
import moneymanager.entity.ItemEntity;
import moneymanager.util.ValidationUtil;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;


public class TestMoneyManagerController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.commonProcess(request, response, "get");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        this.commonProcess(request, response, "post");
    }


    private void commonProcess(HttpServletRequest request, HttpServletResponse response, String mode) throws IOException, ServletException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

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


        TestDao dao = new TestDao();

        if (mode.equals("post")) {
            String dateStr = request.getParameter("date");
            String nameStr = request.getParameter("name");
            String valueStr = request.getParameter("value");

            if (this.validationParam(dateStr, nameStr, valueStr)) {
                ctx.setVariable("is_exact_param", true);
                boolean is_success = dao.insert(dateStr, nameStr, valueStr);
                ctx.setVariable("is_success_add_item", is_success);
            } else {
                ctx.setVariable("is_exact_param", false);
            }
        }

        List<ItemEntity> items = dao.getData();
        Map<String, List<ItemEntity>> map = this.createItemMap(items);

        ctx.setVariable("title", "タイトル");
        ctx.setVariable("items", items);
        ctx.setVariable("map", map);
        engine.process("moneymanager", ctx, response.getWriter());
    }


    private Map<String, List<ItemEntity>> createItemMap(List<ItemEntity> items) {
        Map<String, List<ItemEntity>> map = new TreeMap<>();

        for (ItemEntity item: items) {
            if (map.containsKey(item.date)) {
                map.get(item.date).add(item);
            } else {
                List<ItemEntity> list_per_date = new ArrayList<>();
                list_per_date.add(item);
                map.put(item.date, list_per_date);
            }
        }

        return map;
    }

    private boolean validationParam(String dateStr, String nameStr, String valueStr) {
        return ValidationUtil.validateDate(dateStr) &&
               ValidationUtil.validateString(nameStr) &&
               ValidationUtil.validateInteger(valueStr);
    }
}