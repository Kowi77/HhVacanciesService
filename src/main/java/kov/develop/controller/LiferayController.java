package kov.develop.controller;

import kov.develop.model.Area;
import kov.develop.model.Specialization;
import kov.develop.services.HhVacancyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("VIEW")
public class LiferayController {

    /**
     * Default render phase
     */

    @RenderMapping
    public String renderLandingPage(Model model, PortletRequest portletRequest, PortletResponse portletResponse) {
        model.addAttribute("today", LocalDate.now());
        return "view";
    }
}
