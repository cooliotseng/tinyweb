import controller.Controller;
import entity.HttpRequest;
import entity.HttpResponse;
import filter.Filter;

import java.util.List;
import java.util.Map;

/**
 * Created by Melody on 2016/10/21.
 */
public class TinyWeb {
    private Map< String, Controller > controllers;
    private List< Filter > filters;

    public TinyWeb ( Map< String, Controller > controllers, List< Filter > filters ) {
        this.controllers = controllers;
        this.filters = filters;
    }

    public HttpResponse handleRequest ( HttpRequest httpRequest ) {
        HttpRequest currentRequest = httpRequest;
        for ( Filter filter : filters ) {
            currentRequest = filter.doFilter( currentRequest );
        }

        //找到对应的controller去处理请求 一个请求可以被多个拦截器拦截,但是只能由一个controller处理
        Controller controller = controllers.get( currentRequest.getPath() );
            if ( null == controller )
                return null;
            return controller.handleRequest( currentRequest );
    }
}
