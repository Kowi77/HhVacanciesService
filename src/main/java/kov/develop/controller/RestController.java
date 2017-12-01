package kov.develop.controller;

import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.Controller;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;


public class RestController implements Controller {

    @Override
    public void handleActionRequest(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

    }

    @Override
    public ModelAndView handleRenderRequest(RenderRequest renderRequest, RenderResponse renderResponse) throws Exception {
        return null;
    }
}
