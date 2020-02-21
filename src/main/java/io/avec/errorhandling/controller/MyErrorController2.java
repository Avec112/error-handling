package io.avec.errorhandling.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Enable @Controller here and disable the other one or else you will have conflicting URL's
 *
 * This Controller extends default BasicErrorController so you can override or add support for other MediaTypes
 * like ex. text/plain.
 *
 * Json is default, and errorHtml is added with MediaType.TEXT_HTML_VALUE
 *
 * Se inside 500.html for how to display model from ModelAndView
 */
//@Controller
@RequestMapping("/error")
public class MyErrorController2 extends BasicErrorController {

    // ErrorProperties was not in Container for Autowiring. Found that ServerProperties was the way to go.
    public MyErrorController2(ErrorAttributes errorAttributes, ServerProperties serverProperties, List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }


    @Override
    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = getStatus(request);
        Map<String, Object> model = Collections
                .unmodifiableMap(getErrorAttributes(request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
        response.setStatus(status.value());
        ModelAndView modelAndView = resolveErrorView(request, response, status, model);
        return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
    }

//    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
//    public String handleError(HttpServletRequest request) {
//        // get error status
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//
//        // TODO: log error details here
//
//        if (status != null) {
//            int statusCode = Integer.parseInt(status.toString());
//
//            // display specific error page
//            if (statusCode == HttpStatus.NOT_FOUND.value()) {
//                return "error/404";
//            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//                return "error/500";
//            } /*else if (statusCode == HttpStatus.FORBIDDEN.value()) {
//                return "error/403";
//            }*/
//        }
//
//        // display generic error
//        return "error";
//    }
}
