package kov.develop.controller;

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
import java.util.Map;

@Controller
@RequestMapping("VIEW")
public class LiferayController {

    /**
     * Default render phase
     */

    private final Map<Integer, String> regions = HhVacancyService.getRegions();
    private final Map<Integer, String> specializations = HhVacancyService.getSpecializations();

    @RenderMapping
    public String renderLandingPage(Model model, PortletRequest portletRequest, PortletResponse portletResponse) {
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("regions", regions);
        model.addAttribute("region", 4); // по умолчанию Новосибирская область
        model.addAttribute("specializations", specializations);
        model.addAttribute("specialization", 1); //по умолчанию Информационные технологии
        return "view";
    }
}
