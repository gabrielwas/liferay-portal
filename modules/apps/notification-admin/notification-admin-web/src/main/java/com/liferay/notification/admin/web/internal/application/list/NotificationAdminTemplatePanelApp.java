package com.liferay.notification.admin.web.internal.application.list;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.notification.admin.constants.NotificationAdminPortletKeys;
import com.liferay.portal.kernel.model.Portlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true,
	property = {
		"panel.app.order:Integer=100",
		"panel.category.key=" + PanelCategoryKeys.CONTROL_PANEL_OBJECT
	},
	service = PanelApp.class
)
public class NotificationAdminTemplatePanelApp extends BasePanelApp {

	@Override
	public String getPortletId() {
		return NotificationAdminPortletKeys.NOTIFICATION_ADMIN_TEMPLATES;
	}

	@Override
	@Reference(
		target = "(javax.portlet.name=" + NotificationAdminPortletKeys.NOTIFICATION_ADMIN_TEMPLATES + ")",
		unbind = "-"
	)
	public void setPortlet(Portlet portlet) {
		super.setPortlet(portlet);
	}

}
