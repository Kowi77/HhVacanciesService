package kov.develop;

import com.liferay.portal.kernel.util.ReleaseInfo;

import kov.develop.config.ApplicationContextProvider;
import kov.develop.repository.VacancyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping("VIEW")
public class PortletViewController {

	/*private final VacancyRepository repository = (VacancyRepository) ApplicationContextProvider.
			getApplicationContext().getBean(VacancyRepository.class);
*/
	@RenderMapping
	public String question(Model model) {
		model.addAttribute("releaseInfo", ReleaseInfo.getReleaseInfo());

		return "HhVacancies/view";
	}

}