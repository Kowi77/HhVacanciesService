package kov.develop;

import com.liferay.portal.kernel.util.ReleaseInfo;

import kov.develop.config.ApplicationContextProvider;
import kov.develop.model.Vacancy;
import kov.develop.repository.VacancyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/*@Controller
@RequestMapping("VIEW")*/
public class PortletViewController {

	/*private final VacancyRepository repository = (VacancyRepository) ApplicationContextProvider.
			getApplicationContext().getBean(VacancyRepository.class);
*/
/*	@RenderMapping
	public String question(Model model) {
		model.addAttribute("releaseInfo", ReleaseInfo.getReleaseInfo());

		return "HhVacancies/view";
	}
	@ActionMapping(params = "action=actionOne")
	public void actionOneMethod(
			@RequestParam String vacancyName, @ModelAttribute(value="vacancy")
			Vacancy vacancy,
			ActionRequest request,
			ActionResponse response) {}

	@RenderMapping(params = "action=renderOne")
			public String renderOne(RenderRequest request, RenderResponse response){
		return "renderOne";
	}

	@ModelAttribute("vacancy")
	public Vacancy getVacancy() {
		return new Vacancy();
	}*/


}