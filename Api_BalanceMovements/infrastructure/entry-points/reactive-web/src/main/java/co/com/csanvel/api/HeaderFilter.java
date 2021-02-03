package co.com.csanvel.api;


import co.com.csanvel.model.commons.ErrorHeader;
import co.com.csanvel.model.commons.HeaderBM;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class HeaderFilter implements HandlerFilterFunction<ServerResponse, ServerResponse>{

    @Override
    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {
        HeaderBM headers;
        headers = (HeaderBM) UtilHeader.getHeaders(request, HeaderBM.class);

        ErrorHeader error = Common.validateObject(headers);
        return error!=null?Common.responseErrorMessage(error):next.handle(request);
    }

    @Override
    public HandlerFunction<ServerResponse> apply(HandlerFunction<ServerResponse> handler) {
        return HandlerFilterFunction.super.apply(handler);
    }

}

